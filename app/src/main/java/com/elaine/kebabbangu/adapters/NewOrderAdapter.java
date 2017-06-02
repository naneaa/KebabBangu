package com.elaine.kebabbangu.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.elaine.kebabbangu.base.Order;
import com.elaine.kebabbangu.base.Product;
import com.elaine.kebabbangu.R;

/**
 * Created by elaine on 02/06/17.
 */

public class NewOrderAdapter extends BaseAdapter{
    private final Context context;
    private Order order;

    public NewOrderAdapter(Context context, Order order) {
        this.order = this.order;
        this.context = context;
    }

    @Override
    public int getCount() { return this.order.getProducts().size(); }

    @Override
    public Object getItem(int position) { return this.order.getProducts().get(position); }

    @Override
    public long getItemId(int position) { return this.order.getId(); }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Product product = this.order.getProducts().get(position);
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = convertView;
        if(view == null){
            view = inflater.inflate(R.layout.product_list, parent, false);
        }

        TextView orderNumber = (TextView) view.findViewById(R.id.order_number);
        TextView orderString = (TextView) view.findViewById(R.id.order_string);
        TextView orderValue = (TextView) view.findViewById(R.id.order_value);

        orderNumber.setText(order.getQuantity().get(position));
        orderString.setText(order.getProducts().get(position).getName());
        orderValue.setText("R$ " + String.format("%1$.2f", order.getQuantity().get(position) * order.getProducts().get(position).getPrice()));

        return view;
    }
}
