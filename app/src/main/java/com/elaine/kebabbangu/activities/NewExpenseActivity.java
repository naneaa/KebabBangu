package com.elaine.kebabbangu.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.elaine.kebabbangu.R;
import com.elaine.kebabbangu.base.Expense;
import com.elaine.kebabbangu.base.Register;
import com.elaine.kebabbangu.dao.ExpenseDAO;
import com.elaine.kebabbangu.dao.RegisterDAO;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewExpenseActivity extends AppCompatActivity {

    private EditText descritionText;
    private EditText valueText;
    private Expense expense;
    private double prevValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_expense);

        descritionText = (EditText) findViewById(R.id.descriptionText);
        valueText = (EditText) findViewById(R.id.valueText);


        Intent intent = getIntent();
        expense = (Expense) intent.getSerializableExtra("expense");
        if (expense != null) {
            descritionText.setText(expense.getDescription());
            valueText.setText(Double.toString(expense.getValue()));
            prevValue = expense.getValue();
        } else {
            expense = new Expense();
        }
    }

    public void callCreateExpense(View view){
        Expense expense;
        try {
            ExpenseDAO expenseDAO = new ExpenseDAO(NewExpenseActivity.this);
            expense = buildExpenseForInsert();

            System.out.println(expense.toString());

            if (expense.getId() != null) {
                RegisterDAO registerDAO = new RegisterDAO(NewExpenseActivity.this);
                Register register = registerDAO.getTodaysRegister();
                register.setCash(register.getCash() + prevValue - expense.getValue());
                register.setTotal(register.getTotal() + prevValue - expense.getValue());
                registerDAO.update(register);
                registerDAO.close();

                expenseDAO.update(expense);
            } else {
                RegisterDAO registerDAO = new RegisterDAO(NewExpenseActivity.this);
                Register register = registerDAO.getTodaysRegister();
                register.setCash(register.getCash() - expense.getValue());
                register.setTotal(register.getTotal() - expense.getValue());
                registerDAO.update(register);
                registerDAO.close();

                expenseDAO.create(expense);
            }

            expenseDAO.close();
            Toast.makeText(NewExpenseActivity.this,
                    "Despesa " + expense.getDescription() + " adicionada!",
                    Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Toast.makeText(NewExpenseActivity.this,
                    "Erro ao salvar nova despesa. \n" + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private Expense buildExpenseForInsert() throws Exception {

        if (descritionText.getText().toString().equals("")) {
            throw new Exception("Campo 'descrição' obrigatório!");
        }

        if (valueText.getText().toString().equals("")) {
            throw new Exception("Campo 'valor' obrigatório!");
        }

        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        expense.setDate(sdf.format(date));

        String description = descritionText.getText().toString();
        String value = valueText.getText().toString();

        expense.setDescription(description);
        expense.setValue(Double.valueOf(value));

        return expense;
    }

}
