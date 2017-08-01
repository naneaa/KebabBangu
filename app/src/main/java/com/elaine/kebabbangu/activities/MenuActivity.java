package com.elaine.kebabbangu.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.elaine.kebabbangu.R;
import com.elaine.kebabbangu.adapters.ProductAdapter;
import com.elaine.kebabbangu.base.Product;
import com.elaine.kebabbangu.dao.ProductDAO;

import java.util.LinkedList;

public class MenuActivity extends AppCompatActivity {

    private ListView list;

    @Override
    protected void onResume() {
        super.onResume();
        buildMenuList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        list = (ListView) findViewById(R.id.list_menu);
    }

    private void buildMenuList(){
        ProductDAO productDAO = new ProductDAO(MenuActivity.this);
        LinkedList<Product> productsList = productDAO.read();
        productDAO.close();

        ProductAdapter orderListViewAdapter = new ProductAdapter(this, productsList);
        list.setAdapter(orderListViewAdapter);
    }
}
