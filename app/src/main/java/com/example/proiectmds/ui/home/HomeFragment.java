package com.example.proiectmds.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.proiectmds.R;
import com.example.proiectmds.databinding.FragmentHomeBinding;
import com.example.proiectmds.domain.Manager;
import com.example.proiectmds.services.ManagerService;

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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (chosenManager == null) {
                    chosenManager = new ManagerService().getAllManagers().get(position);
                    textView.setVisibility(TextView.GONE);

                    ManagerService managerService = new ManagerService();
                    String[] nameAndPriceOfProduct = managerService.getListOfProductsInStock(chosenManager.getId()).
                            stream()
                            .map(p -> "Nume: " + p.getName() + "\nPret:" + p.getPrice())
                            .toArray(String[]::new);
                    listViewAdapter = new ArrayAdapter<String>(
                            getActivity(),
                            android.R.layout.simple_list_item_1,
                            nameAndPriceOfProduct
                    );
                    listView.setAdapter(listViewAdapter);
                }

            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}