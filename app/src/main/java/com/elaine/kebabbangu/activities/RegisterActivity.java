package com.elaine.kebabbangu.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.elaine.kebabbangu.R;
import com.elaine.kebabbangu.base.Expense;
import com.elaine.kebabbangu.base.Register;
import com.elaine.kebabbangu.dao.ExpenseDAO;
import com.elaine.kebabbangu.dao.RegisterDAO;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

public class RegisterActivity extends AppCompatActivity {

    TextView totalText, cashText, debitText, creditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        totalText = (TextView) findViewById(R.id.totalText);
        cashText = (TextView) findViewById(R.id.cashText);
        debitText = (TextView) findViewById(R.id.debitText);
        creditText = (TextView) findViewById(R.id.creditText);

        RegisterDAO registerDAO = new RegisterDAO(RegisterActivity.this);
        Register register = registerDAO.getTodaysRegister();

        if (register != null) {
            DecimalFormat df = new DecimalFormat("0.00");

            totalText.setText("Em caixa: R$ " + df.format(register.getTotal()));
            cashText.setText("Dinheiro: R$ " + df.format(register.getCash()));
            debitText.setText("Débito: R$ " + df.format(register.getDebit()));
            creditText.setText("Crédito: R$ " + df.format(register.getCredit()));
        }


    }

    public void startRegister(View view) {
        final EditText input = new EditText(RegisterActivity.this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        new AlertDialog.Builder(RegisterActivity.this)
                .setTitle("Valor inicial no caixa:")
                .setMessage("")
                .setView(input)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String editable = input.getText().toString();

                        Register register = new Register();
                        register.setStarting(Double.parseDouble(editable));
                        register.setTotal(Double.parseDouble(editable));
                        register.setCash(Double.parseDouble(editable));
                        register.setDebit(0);
                        register.setCredit(0);

                        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                        Date date = new Date();
                        register.setDate(sdf.format(date));

                        try {
                            RegisterDAO registerDAO = new RegisterDAO(RegisterActivity.this);
                            registerDAO.create(register);

                            DecimalFormat df = new DecimalFormat("0.00");
                            totalText.setText("Em caixa: R$ " + df.format(register.getTotal()));
                            cashText.setText("Dinheiro: R$ " + df.format(register.getCash()));
                        } catch (Exception e) {
                            Toast.makeText(RegisterActivity.this, "O caixa já foi aberto hoje!",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                       // RegisterDAO registerDAO = new RegisterDAO(RegisterActivity.this);
                      //  registerDAO.deleteAll();
                    }
                }).show();
    }

    public void closeRegister(View view) {
        RegisterDAO registerDAO = new RegisterDAO(RegisterActivity.this);
        Register register = registerDAO.getTodaysRegister();
        String report = "KEBAB BANGU";

        report += "\n\nTotal em dinheiro: R$ " + register.getCash();
        report += "\nTotal em debito: R$ " + register.getDebit();
        report += "\nTotal em credito: R$ " + register.getCredit();

        ExpenseDAO expenseDAO = new ExpenseDAO(RegisterActivity.this);
        LinkedList<Expense> expenses = expenseDAO.readTodaysExpenses();

        if(expenses.size() != 0){

        double expensesTotal = 0;

        for(Expense c : expenses)
            expensesTotal += c.getValue();

        double total = register.getCash() + register.getCredit() + register.getDebit();

        report += "\n\nTotal de entrada: R$ " + (total + expensesTotal);
        report += "\nTotal de saida: R$ " + expensesTotal;
        report += "\nTotal em caixa: R$ " + register.getTotal();

        report += "\n\nDespesas do Dia: ";

        for(Expense c : expenses)
            report += "\n" + c.getDescription() + " - R$ " + c.getValue();

        } else {
            double total = register.getCash() + register.getCredit() + register.getDebit();

            report += "\n\nTotal de entrada: " + total;

            report += "\n\nNão houveram despesas!";
        }

        System.out.println(report);
        MainActivity.print(report);
    }
}
