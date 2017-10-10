package com.elaine.kebabbangu.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.print.PrintManager;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.elaine.kebabbangu.R;
import com.elaine.kebabbangu.adapters.NewOrderAdapter;
import com.elaine.kebabbangu.base.Order;
import com.elaine.kebabbangu.base.Product;
import com.elaine.kebabbangu.dao.OrderDAO;
import com.elaine.kebabbangu.dao.OrderProductDAO;

import java.text.DecimalFormat;

public class ConfirmOrderActivity extends AppCompatActivity {

    private Order order;
    private ListView list;
    private TextView totalPrice;
    private EditText clientText;
    private RadioButton radioCash, radioDebit, radioCredit;
    private CheckBox isPaid;

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

        clientText = (EditText) findViewById(R.id.clientText);
        radioCash = (RadioButton) findViewById(R.id.radioCash);
        radioCash.toggle();
        radioDebit = (RadioButton) findViewById(R.id.radioDebit);
        radioCredit = (RadioButton) findViewById(R.id.radioCredit);

        isPaid = (CheckBox) findViewById(R.id.checkPaid);
    }

    private void buildMenuList() {
        NewOrderAdapter orderListViewAdapter = new NewOrderAdapter(this, order);
        list.setAdapter(orderListViewAdapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    final ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem deleteMenuItem = menu.add("Remover");

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

    public void callCreateOrder(View view) {
        try {
            OrderDAO orderDAO = new OrderDAO(ConfirmOrderActivity.this);
            buildOrderForInsert();

            //order.printOrder();
            long row = orderDAO.create(order);
            int orderID = orderDAO.getOrderByRow(row);
            order.setId(orderID);

            //System.out.println("Linha: " + row + " OrderID: " + orderID);
            OrderProductDAO orderProductDAO = new OrderProductDAO(ConfirmOrderActivity.this);
            orderProductDAO.create(order);

            orderProductDAO.close();
            orderDAO.close();
            Toast.makeText(ConfirmOrderActivity.this, "Pedido Realizado!",
                    Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Toast.makeText(ConfirmOrderActivity.this, "Erro ao criar pedido. \n" + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void buildOrderForInsert() throws Exception {
        if (clientText.getText().toString().equals("")) {
            throw new Exception("Campo 'nome' obrigatório!");
        }

        String clientName = clientText.getText().toString();
        order.setClientName(clientName);

        order.setPaid(isPaid.isChecked());

        if (radioCash.isChecked())
            order.setPaymentMethod("Cash");
        else if (radioDebit.isChecked())
            order.setPaymentMethod("Debit");
        else if (radioCredit.isChecked())
            order.setPaymentMethod("Credit");

        System.out.println(order.toString());
    }
}

