package com.example.proiectmds.ui.statistics;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.proiectmds.R;
import com.example.proiectmds.databinding.FragmentStatisticsBinding;
import com.example.proiectmds.domain.Client;
import com.example.proiectmds.domain.Manager;
import com.example.proiectmds.domain.Product;
import com.example.proiectmds.helpers.ProductTuple;
import com.example.proiectmds.services.ClientService;
import com.example.proiectmds.services.ManagerService;
import com.example.proiectmds.services.ProductService;

import java.text.DecimalFormat;

public class StatisticsFragment extends Fragment {

    private FragmentStatisticsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        View view = inflater.inflate(R.layout.fragment_statistics, container, false);

        Bundle bundle = getActivity().getIntent().getExtras();
        String email = bundle.getString("user");

        ClientService clientService = new ClientService();
        ManagerService managerService = new ManagerService();

        Button megaButton = view.findViewById(R.id.statistics_button);

        if (new ClientService().checkMail(email)) {
            Button partnerButton = view.findViewById(R.id.linked_client_button);
            partnerButton.setVisibility(Button.VISIBLE);

            partnerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                    alertDialog.setTitle(getActivity().getString(R.string.title_alert_partner));
                    alertDialog.setMessage(getActivity().getString(R.string.message_alert_partner));

                    final EditText input = new EditText(getActivity());
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT);
                    input.setLayoutParams(lp);
                    alertDialog.setView(input);
                    alertDialog.setIcon(R.drawable.ic_baseline_group_24);

                    alertDialog.setPositiveButton(getActivity().getString(android.R.string.ok),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    String newEmail = input.getText().toString();
                                    int idOldClient = clientService.idByEmail(email);
                                    clientService.addPartnerToAccount(idOldClient, newEmail);
                                }
                            });

                    alertDialog.setNegativeButton(android.R.string.cancel,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                    alertDialog.show();
                }
            });
        }

        megaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idLoggedIn;

                if (new ClientService().checkMail(email)) {
                    idLoggedIn = clientService.idByEmail(email);
                    Product product = new ProductService().getFavouriteProduct(idLoggedIn);
                    String alertMessage;
                    if (product != null) {
                        alertMessage = product.getName();
                    } else {
                        alertMessage = getActivity().getString(R.string.no_statistics);
                    }
                    new AlertDialog.Builder(getContext())
                            .setTitle(getActivity().getString(R.string.favourite_product))
                            .setMessage(alertMessage)
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setIcon(R.drawable.ic_baseline_favorite_24)
                            .show();
                }
                else {
                    idLoggedIn = -1;
                    for (Manager manager : managerService.getAllManagers()) {
                        if (manager.getEmail().equals(email)) {
                            idLoggedIn = manager.getId();
                            break;
                        }
                    }
                    ProductTuple[] products = new ProductService().bestSellingProducts(idLoggedIn);
                    String alertMessage = getActivity().getString(R.string.most_bought);
                    alertMessage += ":\n";

                    boolean productExists = false;
                    DecimalFormat df = new DecimalFormat();
                    df.setMaximumFractionDigits(2);
                    for (ProductTuple productTuple : products) {
                        if (productTuple != null) {
                            alertMessage += productTuple.product.getName() + " " +
                                    df.format(productTuple.percentage) + "%\n";
                            productExists = true;
                        }
                    }
                    new AlertDialog.Builder(getContext())
                            .setTitle(getActivity().getString(R.string.favourite_product))
                            .setMessage(productExists ? alertMessage : getActivity().getString(R.string.no_statistics))
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setIcon(R.drawable.ic_baseline_favorite_24)
                            .show();
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