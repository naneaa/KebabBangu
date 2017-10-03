package com.elaine.kebabbangu.activities;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.elaine.kebabbangu.R;
import com.elaine.kebabbangu.base.Register;
import com.elaine.kebabbangu.dao.RegisterDAO;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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

        if(register != null) {
            DecimalFormat df = new DecimalFormat("0.00");

            totalText.setText("Em caixa: R$ " + df.format(register.getTotal()));
            cashText.setText("Dinheiro: R$ " + df.format(register.getCash()));
            debitText.setText("Débito: R$ " + df.format(register.getDebit()));
            creditText.setText("Crédito: R$ " + df.format(register.getCredit()));
        }


    }

    public void startRegister(View view){
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
                        RegisterDAO registerDAO = new RegisterDAO(RegisterActivity.this);
                        registerDAO.deleteAll();
                    }
                }).show();
    }
}
