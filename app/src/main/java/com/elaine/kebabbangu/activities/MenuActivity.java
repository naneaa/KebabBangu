package com.elaine.kebabbangu.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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
        registerForContextMenu(list);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    final ContextMenu.ContextMenuInfo menuInfo) {

        MenuItem editMenuItem = menu.add("Editar");
        MenuItem deleteMenuItem = menu.add("Remover");

        buildEdit((AdapterView.AdapterContextMenuInfo) menuInfo, editMenuItem);
        buildDelete((AdapterView.AdapterContextMenuInfo) menuInfo, deleteMenuItem);
    }

    private void buildMenuList() {
        ProductDAO productDAO = new ProductDAO(MenuActivity.this);
        LinkedList<Product> productsList = productDAO.readMenu();
        productDAO.close();

        ProductAdapter menuListViewAdapter = new ProductAdapter(this, productsList);
        list.setAdapter(menuListViewAdapter);
    }

    private void buildDelete(final AdapterView.AdapterContextMenuInfo menuInfo, MenuItem deleteMenuItem) {
        deleteMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo adapterMenuInfo = menuInfo;
                Product product = (Product) list.getItemAtPosition(adapterMenuInfo.position);

                ProductDAO productDAO = new ProductDAO(MenuActivity.this);
                productDAO.delete(product.getId());
                productDAO.close();

                buildMenuList();

                Toast.makeText(MenuActivity.this, "Produto " + product.getName() + " removido!",
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

                Intent intentNewProduct = new Intent(MenuActivity.this, NewProductActivity.class);
                intentNewProduct.putExtra("product", product);
                startActivity(intentNewProduct);

                return false;
            }
        });
    }
}
