package com.elaine.kebabbangu.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.elaine.kebabbangu.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void callNewProductScreen(View view){
        Intent intent = new Intent(SettingsActivity.this, NewProductActivity.class);
        startActivity(intent);
    }

    public void callMenuScreen(View view){
        Intent intent = new Intent(SettingsActivity.this, MenuActivity.class);
        startActivity(intent);
    }

    public void callAllProductsScreen(View view){
        Intent intent = new Intent(SettingsActivity.this, AllProductsActivity.class);
        startActivity(intent);
    }


    public void callStockScreen(View view) {
        Intent intent = new Intent(SettingsActivity.this, StockActivity.class);
        startActivity(intent);
    }
}
