package com.example.proiectmds.ui.budget;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.proiectmds.R;
import com.example.proiectmds.databinding.FragmentBudgetBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class BudgetFragment extends Fragment {

    private FragmentBudgetBinding binding;
    private static double spending = 0;
    private static double budget;
    private View _view;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_budget, container, false);
        _view = view;
        ((TextView)view.findViewById(R.id.Spending)).setText(String.valueOf(spending));
        binding = FragmentBudgetBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final TextView textView1 = binding.textBudget;

        ((EditText) view.findViewById(R.id.Budget)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals(""))
                    return;
                budget = Double.parseDouble(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
//
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
        ((TextView)_view.findViewById(R.id.Spending)).setText(String.valueOf(spending));
    }

    public static void setSpending(double _spending) {
        spending = _spending;
    }

    public static double getSpending() {
        return spending;
    }

    public static double getBudget() {
        return budget;
    }
}
