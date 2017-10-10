package com.elaine.kebabbangu.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.elaine.kebabbangu.R;
import com.elaine.kebabbangu.adapters.OrdersAdapter;
import com.elaine.kebabbangu.base.Order;
import com.elaine.kebabbangu.dao.OrderDAO;

import java.util.LinkedList;

public class OrdersActivity extends AppCompatActivity {

    private ListView list;

    @Override
    protected void onResume() {
        super.onResume();
        buildOrdersList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        list = (ListView) findViewById(R.id.lista_pedidos);
    }

    private void buildOrdersList() {
        OrderDAO orderDAO = new OrderDAO(OrdersActivity.this);
        LinkedList<Order> ordersList = orderDAO.read();
        orderDAO.close();

        OrdersAdapter orderListViewAdapter = new OrdersAdapter(this, ordersList);
        list.setAdapter(orderListViewAdapter);
    }

    public void callEditOrder(View view) {
        Toast toast = Toast.makeText(getApplicationContext(), "Pedido Editado!", Toast.LENGTH_SHORT);
        toast.show();
    }

    public void callCancelOrder(View view) {
        Toast toast = Toast.makeText(getApplicationContext(), "Pedido Cancelado!", Toast.LENGTH_SHORT);
        toast.show();
    }

}
