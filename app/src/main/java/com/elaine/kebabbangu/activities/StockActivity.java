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
import com.elaine.kebabbangu.adapters.StockAdapter;
import com.elaine.kebabbangu.base.Product;
import com.elaine.kebabbangu.base.StockItem;
import com.elaine.kebabbangu.dao.ProductDAO;
import com.elaine.kebabbangu.dao.StockDAO;

import java.util.LinkedList;

public class StockActivity extends AppCompatActivity {

    private ListView list;

    @Override
    protected void onResume() {
        super.onResume();
        buildStockList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);

        list = (ListView) findViewById(R.id.list_stock);
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

    private void buildStockList() {
        ProductDAO productDAO = new ProductDAO(StockActivity.this);
        LinkedList<Product> productsList = productDAO.readMenu();
        productDAO.close();

        StockDAO stockDAO = new StockDAO(StockActivity.this);
        LinkedList<StockItem> expensesList = stockDAO.read(productsList);
        stockDAO.close();

        StockAdapter expenseListViewAdapter = new StockAdapter(this, expensesList);
        list.setAdapter(expenseListViewAdapter);
    }

    private void buildDelete(final AdapterView.AdapterContextMenuInfo menuInfo, MenuItem deleteMenuItem) {
        deleteMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo adapterMenuInfo = menuInfo;
                StockItem expense = (StockItem) list.getItemAtPosition(adapterMenuInfo.position);

                StockDAO expenseDAO = new StockDAO(StockActivity.this);
                expenseDAO.delete(expense.getProduct().getId());
                expenseDAO.close();

                buildStockList();

                Toast.makeText(StockActivity.this, "Item removido do estoque!",
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
                StockItem stockItem = (StockItem) list.getItemAtPosition(adapterMenuInfo.position);

                Intent intentNewStockItem = new Intent(StockActivity.this, NewStockItemActivity.class);
                intentNewStockItem.putExtra("stockItem", stockItem);
                startActivity(intentNewStockItem);

                return false;
            }
        });
    }


    public void callCreateStockItem(View view) {
        Intent intent = new Intent(StockActivity.this, NewStockItemActivity.class);
        startActivity(intent);
    }

}
