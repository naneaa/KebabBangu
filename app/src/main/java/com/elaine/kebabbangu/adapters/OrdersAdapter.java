package com.elaine.kebabbangu.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.elaine.kebabbangu.base.Order;
import com.elaine.kebabbangu.R;

import java.util.LinkedList;

/**
 * Created by elaine on 01/06/17.
 */

public class OrdersAdapter extends BaseAdapter{

    private final Context context;
    private LinkedList<Order> orders;

    public OrdersAdapter(Context context, LinkedList<Order> orders) {
        this.orders = orders;
        this.context = context;
    }

    @Override
    public int getCount() { return this.orders.size(); }

    @Override
    public Object getItem(int position) { return this.orders.get(position); }

    @Override
    public long getItemId(int position) { return this.orders.get(position).getId(); }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Order order = this.orders.get(position);
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = convertView;
        if(view == null){
            view = inflater.inflate(R.layout.orders_list, parent, false);
        }

        TextView orderNumber = (TextView) view.findViewById(R.id.order_number);
        TextView orderString = (TextView) view.findViewById(R.id.order_string);
        TextView orderValue = (TextView) view.findViewById(R.id.order_value);

        orderNumber.setText(Integer.toString(order.getId()));
        orderString.setText(order.stringList());
        orderValue.setText("R$ " + String.format("%1$.2f", order.getTotalPrice()));

        return view;
    }





}
