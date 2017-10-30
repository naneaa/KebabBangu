package com.elaine.kebabbangu.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.elaine.kebabbangu.R;
import com.elaine.kebabbangu.adapters.ExpensesAdapter;
import com.elaine.kebabbangu.base.Expense;
import com.elaine.kebabbangu.base.Register;
import com.elaine.kebabbangu.dao.ExpenseDAO;
import com.elaine.kebabbangu.dao.RegisterDAO;

import java.util.LinkedList;

public class ExpensesActivity extends AppCompatActivity {

    private ListView list;

    @Override
    protected void onResume() {
        super.onResume();
        buildExpensesList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);

        list = (ListView) findViewById(R.id.expenses_list);
        registerForContextMenu(list);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    final ContextMenu.ContextMenuInfo menuInfo) {

        MenuItem editMenuItem = menu.add("Editar");
        MenuItem deleteMenuItem = menu.add("Remover");

        buildEdit((AdapterView.AdapterContextMenuInfo) menuInfo, editMenuItem);
        buildDelete((AdapterView.AdapterContextMenuInfo) menuInfo, deleteMenuItem);
    }

    private void buildExpensesList() {
        ExpenseDAO expenseDAO = new ExpenseDAO(ExpensesActivity.this);
        LinkedList<Expense> expensesList = expenseDAO.readTodaysExpenses();
        expenseDAO.close();

        ExpensesAdapter expenseListViewAdapter = new ExpensesAdapter(this, expensesList);
        list.setAdapter(expenseListViewAdapter);
    }

    private void buildDelete(final AdapterView.AdapterContextMenuInfo menuInfo, MenuItem deleteMenuItem) {
        deleteMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo adapterMenuInfo = menuInfo;
                Expense expense = (Expense) list.getItemAtPosition(adapterMenuInfo.position);

                ExpenseDAO expenseDAO = new ExpenseDAO(ExpensesActivity.this);
                expenseDAO.delete(expense.getId());
                expenseDAO.close();

                RegisterDAO registerDAO = new RegisterDAO(ExpensesActivity.this);
                Register register = registerDAO.getTodaysRegister();
                register.setCash(register.getCash() + expense.getValue());
                register.setTotal(register.getTotal() + expense.getValue());
                registerDAO.update(register);
                registerDAO.close();

                buildExpensesList();

                Toast.makeText(ExpensesActivity.this, "Despesa " + expense.getDescription() + " removida!",
                        Toast.LENGTH_SHORT).show();

                return false;
            }
        });
    }

    private void buildEdit(final AdapterView.AdapterContextMenuInfo menuInfo, MenuItem editMenuItem) {
        editMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo adapterMenuInfo = menuInfo;
                Expense expense = (Expense) list.getItemAtPosition(adapterMenuInfo.position);

                Intent intentNewExpense = new Intent(ExpensesActivity.this, NewExpenseActivity.class);
                intentNewExpense.putExtra("expense", expense);
                startActivity(intentNewExpense);

                return false;
            }
        });
    }

    public void callNewExpenseScreen(View view){
        Intent intentNewExpense = new Intent(ExpensesActivity.this, NewExpenseActivity.class);
        startActivity(intentNewExpense);
    }

}
