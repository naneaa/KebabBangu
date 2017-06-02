package com.elaine.kebabbangu.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.elaine.kebabbangu.R;

public class StockActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);
    }

    public void callCreateProduct(View view){
        Intent intent = new Intent(StockActivity.this, NewProductActivity.class);
        startActivity(intent);
    }

}
