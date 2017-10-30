package com.elaine.kebabbangu.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.elaine.kebabbangu.R;
import com.elaine.kebabbangu.adapters.OrdersAdapter;
import com.elaine.kebabbangu.base.Order;
import com.elaine.kebabbangu.base.Register;
import com.elaine.kebabbangu.dao.OrderDAO;
import com.elaine.kebabbangu.dao.RegisterDAO;

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
        registerForContextMenu(list);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    final ContextMenu.ContextMenuInfo menuInfo) {

        MenuItem editMenuItem = menu.add("Pagar");
        MenuItem deleteMenuItem = menu.add("Remover");

        buildPaid((AdapterView.AdapterContextMenuInfo) menuInfo, editMenuItem);
        buildDelete((AdapterView.AdapterContextMenuInfo) menuInfo, deleteMenuItem);
    }

    private void buildOrdersList() {
        OrderDAO orderDAO = new OrderDAO(OrdersActivity.this);
        LinkedList<Order> ordersList = orderDAO.readTodaysOrders();
        orderDAO.close();

        OrdersAdapter orderListViewAdapter = new OrdersAdapter(this, ordersList);
        list.setAdapter(orderListViewAdapter);
    }

    private void buildDelete(final AdapterView.AdapterContextMenuInfo menuInfo, MenuItem deleteMenuItem) {
        deleteMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo adapterMenuInfo = menuInfo;
                Order order = (Order) list.getItemAtPosition(adapterMenuInfo.position);

                OrderDAO orderDAO = new OrderDAO(OrdersActivity.this);
                orderDAO.delete(order.getId());
                orderDAO.close();

                if(order.isPaid()) {
                    RegisterDAO registerDAO = new RegisterDAO(OrdersActivity.this);
                    Register register = registerDAO.getTodaysRegister();
                    if (order.getPaymentMethod().equals("Cash")) {
                        register.setCash(register.getCash() - order.getPrice());
                    } else if (order.getPaymentMethod().equals("Debit")) {
                        register.setDebit(register.getDebit() - order.getPrice());
                    } else if (order.getPaymentMethod().equals("Credit")) {
                        register.setCredit(register.getCredit() - order.getPrice());
                    }
                    register.setTotal(register.getTotal() - order.getPrice());
                    registerDAO.update(register);
                    registerDAO.close();
                }
                buildOrdersList();

                Toast.makeText(OrdersActivity.this, "Pedido " + order.getNumber() + " removido!",
                        Toast.LENGTH_SHORT).show();

                return false;
            }
        });
    }

    private void buildPaid(final AdapterView.AdapterContextMenuInfo menuInfo, MenuItem editMenuItem) {
        editMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo adapterMenuInfo = menuInfo;
                Order order = (Order) list.getItemAtPosition(adapterMenuInfo.position);
                if(!order.isPaid()) {
                    order.setPaid(true);

                    OrderDAO orderDAO = new OrderDAO(OrdersActivity.this);
                    orderDAO.update(order);
                    orderDAO.close();

                    RegisterDAO registerDAO = new RegisterDAO(OrdersActivity.this);
                    Register register = registerDAO.getTodaysRegister();
                    if (order.getPaymentMethod().equals("Cash")) {
                        register.setCash(register.getCash() + order.getPrice());
                    } else if (order.getPaymentMethod().equals("Debit")) {
                        register.setDebit(register.getDebit() + order.getPrice());
                    } else if (order.getPaymentMethod().equals("Credit")) {
                        register.setCredit(register.getCredit() + order.getPrice());
                    }
                    register.setTotal(register.getTotal() + order.getPrice());
                    registerDAO.update(register);
                    registerDAO.close();

                    buildOrdersList();

                    Toast.makeText(OrdersActivity.this, "Pedido " + order.getNumber() + " pago!",
                            Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }

}
