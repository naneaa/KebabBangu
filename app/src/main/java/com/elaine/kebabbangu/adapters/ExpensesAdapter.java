package com.elaine.kebabbangu.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.elaine.kebabbangu.R;
import com.elaine.kebabbangu.base.Expense;

import java.util.LinkedList;

/**
 * Created by Elaine on 10/26/2017.
 */

public class ExpensesAdapter extends BaseAdapter {
    private final Context context;
    private LinkedList<Expense> expenses;

    public ExpensesAdapter(Context context, LinkedList<Expense> expenses) {
        this.expenses = expenses;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.expenses.size();
    }

    @Override
    public Object getItem(int position) {
        return this.expenses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.expenses.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Expense expense = this.expenses.get(position);
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.expense_list, parent, false);
        }

        TextView expenseDescription = (TextView) view.findViewById(R.id.expense_string);
        TextView expenseValue = (TextView) view.findViewById(R.id.expense_value);

        expenseDescription.setText(expense.getDescription());
        expenseValue.setText("R$ " + String.format("%1$.2f", expense.getValue()));

        return view;
    }

}
