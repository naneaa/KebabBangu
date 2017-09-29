package com.elaine.kebabbangu.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.elaine.kebabbangu.R;
import com.elaine.kebabbangu.base.Order;

public class ConfirmOrderActivity extends AppCompatActivity {

    private Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

        Intent intent = getIntent();
        order = (Order) intent.getSerializableExtra("order");
    }
}
