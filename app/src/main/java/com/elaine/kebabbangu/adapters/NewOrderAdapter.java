package com.elaine.kebabbangu.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.elaine.kebabbangu.R;
import com.elaine.kebabbangu.base.Order;

/**
 * Created by elaine on 02/06/17.
 */

public class NewOrderAdapter extends BaseAdapter {
    private final Context context;
    private Order order;

    public NewOrderAdapter(Context context, Order order) {
        this.order = order;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.order.getProducts().size();
    }

    @Override
    public Object getItem(int position) {
        return this.order.getProducts().get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.order.getProducts().get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.product_order_list, parent, false);
        }

        TextView productNameText = (TextView) view.findViewById(R.id.productNameText);
        TextView productPriceText = (TextView) view.findViewById(R.id.productPriceText);
        TextView productDescription = (TextView) view.findViewById(R.id.productDescription);

        productNameText.setText(order.getProducts().get(position).getName());
        productPriceText.setText("R$ " + String.format("%1$.2f", order.getProducts().get(position).getPrice()));
        productDescription.setText(order.getDescriptions().get(position));

        return view;
    }
}
