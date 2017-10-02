package com.elaine.kebabbangu.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

import com.elaine.kebabbangu.base.Order;
import com.elaine.kebabbangu.R;
import com.elaine.kebabbangu.base.Product;
import com.elaine.kebabbangu.dao.ProductDAO;

import java.util.LinkedList;

public class NewOrderActivity extends AppCompatActivity {

    private Order order;
    private Spinner spinner;
    private CheckBox checkLettuce, checkTomato, checkOnion, checkPicles, checkOlives, checkRaisins,
                checkCabbage;
    private CheckBox checkHotSauce, checkMangoSauce, checkTahineSauce, checkHoneySauce,
                checkBarbecueSauce, checkChimichurriSauce, checkGarlicSauce, checkHoneyMustardSauce;
    private CheckBox checkCheddar, checkCatupiry;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);

        order = new Order();

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

        loadSpinnerData();
    }

    private void loadSpinnerData() {
        ProductDAO productDAO = new ProductDAO(NewOrderActivity.this);
        LinkedList<Product> productsList = productDAO.readMenu();
        productDAO.close();

        LinkedList<String> nameList = new LinkedList<>();
        for(Product c : productsList)
            nameList.add(c.getName());

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nameList);
        spinner.setAdapter(dataAdapter);
    }

    public void callAddProductToOrder(View view){
        order.setId(001);

    }

    public void callClearOptions(View view){
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
    }

    public void callConfirmOrderScreen(View view){
        Intent intent = new Intent(NewOrderActivity.this, ConfirmOrderActivity.class);
        startActivity(intent);
    }

}
