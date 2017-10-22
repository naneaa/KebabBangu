package com.elaine.kebabbangu.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.elaine.kebabbangu.R;
import com.elaine.kebabbangu.base.Order;
import com.elaine.kebabbangu.base.Product;
import com.elaine.kebabbangu.base.Register;
import com.elaine.kebabbangu.dao.ProductDAO;
import com.elaine.kebabbangu.dao.RegisterDAO;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

public class NewOrderActivity extends AppCompatActivity {

    private Order order;
    private Spinner spinner;
    private CheckBox checkLettuce, checkTomato, checkOnion, checkPicles, checkOlives, checkRaisins,
            checkCabbage;
    private CheckBox checkHotSauce, checkMangoSauce, checkTahineSauce, checkHoneySauce,
            checkBarbecueSauce, checkChimichurriSauce, checkGarlicSauce, checkHoneyMustardSauce;
    private CheckBox checkCheddar, checkCatupiry;
    private EditText textObs;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);

        RegisterDAO registerDAO = new RegisterDAO(NewOrderActivity.this);
        Register register = registerDAO.getTodaysRegister();

        if(register == null) {
            Toast.makeText(NewOrderActivity.this, "Inicie o caixa antes de criar pedidos!",
                    Toast.LENGTH_LONG).show();
            finish();
        }

        order = new Order();
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        order.setDate(sdf.format(date));

        spinner = (Spinner) findViewById(R.id.spinner_menu);

        checkLettuce = (CheckBox) findViewById(R.id.checkLettuce);
        checkTomato = (CheckBox) findViewById(R.id.checkTomato);
        checkOnion = (CheckBox) findViewById(R.id.checkOnion);
        checkPicles = (CheckBox) findViewById(R.id.checkPicles);
        checkOlives = (CheckBox) findViewById(R.id.checkOlives);
        checkRaisins = (CheckBox) findViewById(R.id.checkRaisins);
        checkCabbage = (CheckBox) findViewById(R.id.checkCabbage);

        checkHotSauce = (CheckBox) findViewById(R.id.checkHotSauce);
        checkMangoSauce = (CheckBox) findViewById(R.id.checkMangoSauce);
        checkTahineSauce = (CheckBox) findViewById(R.id.checkTahineSauce);
        checkHoneySauce = (CheckBox) findViewById(R.id.checkHoneySauce);
        checkBarbecueSauce = (CheckBox) findViewById(R.id.checkBarbecueSauce);
        checkChimichurriSauce = (CheckBox) findViewById(R.id.checkChimichurriSauce);
        checkGarlicSauce = (CheckBox) findViewById(R.id.checkGarlicSauce);
        checkHoneyMustardSauce = (CheckBox) findViewById(R.id.checkHoneyMustardSauce);

        checkCheddar = (CheckBox) findViewById(R.id.checkCheddar);
        checkCatupiry = (CheckBox) findViewById(R.id.checkCatupiry);

        textObs = (EditText) findViewById(R.id.textObs);

        loadSpinnerData();
    }

    private void loadSpinnerData() {
        ProductDAO productDAO = new ProductDAO(NewOrderActivity.this);
        LinkedList<Product> productsList = productDAO.readMenu();
        productDAO.close();

        LinkedList<String> nameList = new LinkedList<>();
        for (Product c : productsList)
            nameList.add(c.getName());

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nameList);
        spinner.setAdapter(dataAdapter);
    }

    public void callAddProductToOrder(View view) {
        ProductDAO productDAO = new ProductDAO(NewOrderActivity.this);
        LinkedList<Product> productsList = productDAO.readMenu();
        productDAO.close();

        Product selectedProduct = productsList.get((int) spinner.getSelectedItemId());
        String productDescription = "";

        if (selectedProduct.hasSalad()) {
            productDescription += "Salada: ";
            if (checkLettuce.isChecked())
                productDescription += "Alface. ";
            if (checkTomato.isChecked())
                productDescription += "Tomate. ";
            if (checkOnion.isChecked())
                productDescription += "Cebola. ";
            if (checkPicles.isChecked())
                productDescription += "Picles. ";
            if (checkOlives.isChecked())
                productDescription += "Azeitona. ";
            if (checkRaisins.isChecked())
                productDescription += "Passas. ";
            if (checkCabbage.isChecked())
                productDescription += "Repolho. ";
            productDescription += "\n\n";
        }

        if (selectedProduct.hasSauce()) {
            productDescription += "Molho: ";
            if (checkHotSauce.isChecked())
                productDescription += "Picante. ";
            if (checkMangoSauce.isChecked())
                productDescription += "Manga. ";
            if (checkTahineSauce.isChecked())
                productDescription += "Tahine. ";
            if (checkHoneySauce.isChecked())
                productDescription += "Mel. ";
            if (checkBarbecueSauce.isChecked())
                productDescription += "Barbecue. ";
            if (checkChimichurriSauce.isChecked())
                productDescription += "ChimiChurri. ";
            if (checkGarlicSauce.isChecked())
                productDescription += "Pasta de Alho. ";
            if (checkHoneyMustardSauce.isChecked())
                productDescription += "Mostarda com Mel. ";
            productDescription += "\n\n";
        }

        if (selectedProduct.hasCheese()) {
            productDescription += "Queijo";
            if (checkCheddar.isChecked())
                productDescription += "Cheddar. ";
            if (checkCatupiry.isChecked())
                productDescription += "Catupiry. ";
            productDescription += "\n\n";
        }

        productDescription += textObs.getText();
        System.out.println(productDescription);

        order.AddItem(selectedProduct, productDescription);
        Toast.makeText(NewOrderActivity.this, "Produto Adicionado!",
                Toast.LENGTH_SHORT).show();
        callClearOptions(view);
    }

    public void callClearOptions(View view) {
        checkLettuce.setChecked(false);
        checkTomato.setChecked(false);
        checkOnion.setChecked(false);
        checkPicles.setChecked(false);
        checkOlives.setChecked(false);
        checkRaisins.setChecked(false);
        checkCabbage.setChecked(false);

        checkHotSauce.setChecked(false);
        checkMangoSauce.setChecked(false);
        checkTahineSauce.setChecked(false);
        checkHoneySauce.setChecked(false);
        checkBarbecueSauce.setChecked(false);
        checkChimichurriSauce.setChecked(false);
        checkGarlicSauce.setChecked(false);
        checkHoneyMustardSauce.setChecked(false);

        checkCheddar.setChecked(false);
        checkCatupiry.setChecked(false);

        textObs.setText("");
    }

    public void callConfirmOrderScreen(View view) {
        Intent intent = new Intent(NewOrderActivity.this, ConfirmOrderActivity.class);
        intent.putExtra("order", order);
        startActivity(intent);
    }

}
