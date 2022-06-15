package com.example.proiectmds.ui.home;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import com.example.proiectmds.R;
import com.example.proiectmds.databinding.FragmentHomeBinding;
import com.example.proiectmds.domain.Client;
import com.example.proiectmds.domain.Manager;
import com.example.proiectmds.domain.Product;
import com.example.proiectmds.services.ClientService;
import com.example.proiectmds.services.ManagerService;
import com.example.proiectmds.services.ProductService;
import com.example.proiectmds.ui.budget.BudgetFragment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ArrayAdapter<String> listViewAdapter;
    private Manager chosenManager = null;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Bundle bundle = getActivity().getIntent().getExtras();
        String email = bundle.getString("user");

        if (new ManagerService().checkMail(email)) {
            ((TextView)view.findViewById(R.id.textViewChooseLocation)).setText("You are logged into a manager account.");
            return view;
        }

        int clientId = new ClientService().idByEmail(email);

        String[] locationOfEachManager = new ManagerService().getAllManagers().stream()
                .map(Manager::getLocation).toArray(String[]::new);

        ListView listView = view.findViewById(R.id.listview);

        listViewAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                locationOfEachManager
        );

        listView.setAdapter(listViewAdapter);
        TextView textView = view.findViewById(R.id.textViewChooseLocation);

        Button buyButton = view.findViewById(R.id.button);
        Button cancelButton = view.findViewById(R.id.cancel_button);

        List<Product> basket = new ArrayList<>();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (chosenManager == null) {
                    chosenManager = new ManagerService().getAllManagers().get(position);
                    textView.setVisibility(TextView.GONE);

                    ManagerService managerService = new ManagerService();
                    String[] nameAndPriceOfProduct = managerService.getListOfProductsInStock(chosenManager.getId()).
                            stream()
                            .map(p -> getActivity().getString(R.string.product_name) + ": " +
                                    p.getName() +'\n'+ getActivity().getString(R.string.product_price) + ": " + p.getPrice())
                            .toArray(String[]::new);
                    listViewAdapter = new ArrayAdapter<String>(
                            getActivity(),
                            android.R.layout.simple_list_item_1,
                            nameAndPriceOfProduct
                    );
                    listView.setAdapter(listViewAdapter);
                    buyButton.setVisibility(Button.VISIBLE);
                    cancelButton.setVisibility(Button.VISIBLE);
                }
                else {
                    basket.add(new ManagerService().getListOfProductsInStock(chosenManager.getId()).
                            get(position));
                    buyButton.setText(getActivity().getString(R.string.buy) + "(" + basket.size()+")");
                }
            }
        });

        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!basket.isEmpty()) {
                    double total = 0;
                    ProductService productService = new ProductService();
                    for (Product product : basket) {
                        productService.selectProductById(clientId, chosenManager.getId(), product.getId());
                        ClientService clientService = new ClientService();
                        Integer clientPartnerId = clientService.idPartnerOf(clientId);
                        if (clientPartnerId != null) {
                            productService.selectProductById(clientPartnerId, product.getId());
                        }
                        total += product.getPrice();
                    }
                    View budgetView = inflater.inflate(R.layout.fragment_budget, container, false);
                    double budget = BudgetFragment.getBudget();
                    double spending = BudgetFragment.getSpending();

                    if (budget - spending - total < 0) {
                        Toast.makeText(getActivity(), getActivity().getString(R.string.insufficient_funds), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    basket.clear();
                    buyButton.setText(getActivity().getString(R.string.buy));
                    DecimalFormat df = new DecimalFormat();
                    df.setMaximumFractionDigits(2);
                    Toast.makeText(getActivity(), getActivity().getString(R.string.purhcase_made) +
                            "\n" + df.format(total) + " RON", Toast.LENGTH_SHORT).show();

                    spending += total;
                    BudgetFragment.setSpending(spending);
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                basket.clear();
                buyButton.setText(getActivity().getString(R.string.buy));
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {

        super.onResume();
        chosenManager = null;
    }
}