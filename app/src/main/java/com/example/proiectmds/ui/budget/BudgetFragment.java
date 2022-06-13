package com.example.proiectmds.ui.budget;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.proiectmds.databinding.FragmentBudgetBinding;
import com.google.android.material.snackbar.Snackbar;

public class BudgetFragment extends Fragment {

    private FragmentBudgetBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        BudgetViewModel budgetViewModel =
                new ViewModelProvider(this).get(BudgetViewModel.class);

        binding = FragmentBudgetBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final Button btn = binding.btnSpending;
        final TextView textView = binding.textBudget;
        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                int a = Integer.parseInt(binding.Budget.getText().toString());
                int b = Integer.parseInt(binding.Spending.getText().toString());
                int diff = a - b;
                if (diff < 0)
                {
                    Snackbar.make(view, "Budget cannot be lower than 0", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
                else
                {
                    binding.Budget.setText(String.valueOf(diff));
                }

            }
        });
        budgetViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

//        public void onClick(View view)
//        {
////                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
//            int a = Integer.parseInt(binding.Budget.getText().toString());
//            int b = Integer.parseInt(binding.Spending.getText().toString());
//            int diff = a - b;
//            if (diff < 0)
//            {
////                    Snackbar.make(view, "Budget cannot be lower than 0", Snackbar.LENGTH_LONG).setAction("Action", null).show();
//            }
//            else
//            {
//                binding.Budget.setText(diff);
//            }
//
//        }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}