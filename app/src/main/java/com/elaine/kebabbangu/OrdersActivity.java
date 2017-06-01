package com.elaine.kebabbangu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.LinkedList;

public class OrdersActivity extends AppCompatActivity {

    private ListView list;
    private LinkedList<Order> nhe = new LinkedList<>();

    @Override
    protected void onResume() {
        super.onResume();
        buildOrdersList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);

        list = (ListView) findViewById(R.id.lista_pedidos);
    }

    private void buildOrdersList(){
        //StudentDAO studentDAO = new StudentDAO(StudentsListActivity.this);
        //List<Student> studentList = studentDAO.read();
        //studentDAO.close();
        nhe.add(new Order(0));
        nhe.getFirst().AddItem(new Product("NHE", 10.00));
        OrdersAdapter orderListViewAdapter = new OrdersAdapter(this, nhe);
        list.setAdapter(orderListViewAdapter);
    }

    public void callEditOrder(View view) {
        buildOrdersList();
    }
}
