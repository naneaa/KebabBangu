package com.elaine.kebabbangu.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.elaine.kebabbangu.adapters.NewOrderAdapter;
import com.elaine.kebabbangu.base.Order;
import com.elaine.kebabbangu.R;

public class NewOrderActivity extends AppCompatActivity {

    private ListView list;
    private Order order;

    @Override
    protected void onResume() {
        super.onResume();
        buildOrderList();

        order = new Order(0); //mudar ID a partir do BD
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);

        list = (ListView) findViewById(R.id.new_order_list);
    }

    private void buildOrderList(){
        NewOrderAdapter orderListViewAdapter = new NewOrderAdapter(this, order);
        list.setAdapter(orderListViewAdapter);
    }

    private void callAddProduct(){
        buildOrderList();
    }

    private void callSendOrder(){}

}
