package com.elaine.kebabbangu.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.elaine.kebabbangu.R;
import com.elaine.kebabbangu.adapters.NewOrderAdapter;
import com.elaine.kebabbangu.adapters.OrdersAdapter;
import com.elaine.kebabbangu.base.Order;
import com.elaine.kebabbangu.base.Product;

import java.text.DecimalFormat;

public class ConfirmOrderActivity extends AppCompatActivity {

    private Order order;
    private ListView list;
    private TextView totalPrice;

    @Override
    protected void onResume() {
        super.onResume();
        buildMenuList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

        Intent intent = getIntent();
        order = (Order) intent.getSerializableExtra("order");

        totalPrice = (TextView) findViewById(R.id.textTotal);
        DecimalFormat df = new DecimalFormat("0.00");
        totalPrice.setText("Valor Total: R$ " + df.format(order.getPrice()));

        list = (ListView) findViewById(R.id.orderList);
        registerForContextMenu(list);
    }

    private void buildMenuList() {
        NewOrderAdapter orderListViewAdapter = new NewOrderAdapter(this, order);
        list.setAdapter(orderListViewAdapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
        final ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem editMenuItem = menu.add("Editar");
            MenuItem deleteMenuItem = menu.add("Remover");

            buildEdit((AdapterView.AdapterContextMenuInfo) menuInfo, editMenuItem);
            buildDelete((AdapterView.AdapterContextMenuInfo) menuInfo, deleteMenuItem);
    }


    private void buildDelete(final AdapterView.AdapterContextMenuInfo menuInfo, MenuItem deleteMenuItem) {
        deleteMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo adapterMenuInfo = menuInfo;
                Product product = (Product) list.getItemAtPosition(adapterMenuInfo.position);
                order.removeItem(adapterMenuInfo.position);

                buildMenuList();
                Toast.makeText(ConfirmOrderActivity.this, "Produto " + product.getName() + " removido!",
                        Toast.LENGTH_SHORT).show();

                return false;
            }
        });
    }

    private void buildEdit(final AdapterView.AdapterContextMenuInfo menuInfo, MenuItem editMenuItem) {
        editMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo adapterMenuInfo = menuInfo;
                Product product = (Product) list.getItemAtPosition(adapterMenuInfo.position);

                //TODO Edit method

                //Intent intentNewProduct = new Intent(ConfirmOrderActivity.this, NewOrderActivity.class);
                //intentNewProduct.putExtra("product", product);
                //startActivity(intentNewProduct);

                return false;
            }
        });
    }
}

