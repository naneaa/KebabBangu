package com.elaine.kebabbangu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void callPedido(View view) {
        Intent intent = new Intent(MainActivity.this, OrdersActivity.class);
        startActivity(intent);
    }

    public void callCaixa(View view) {
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    public void callEstoque(View view) {
        Intent intent = new Intent(MainActivity.this, StockActivity.class);
        startActivity(intent);
    }

    public void callAddPedido(View view) {
        Intent intent = new Intent(MainActivity.this, NewOrderActivity.class);
        startActivity(intent);
    }

}
