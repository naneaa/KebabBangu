package com.elaine.kebabbangu.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.elaine.kebabbangu.R;
import com.elaine.kebabbangu.base.Product;

import java.util.LinkedList;

/**
 * Created by Elaine on 7/31/2017.
 */

public class ProductAdapter extends BaseAdapter {

    private final Context context;
    private LinkedList<Product> products;

    public ProductAdapter(Context context, LinkedList<Product> products) {
        this.products = products;
        System.out.println(products.size());
        this.context = context;
    }

    @Override
    public int getCount() { return this.products.size(); }

    @Override
    public Object getItem(int position) { return this.products.get(position); }

    @Override
    public long getItemId(int position) { return this.products.get(position).getId(); }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Product product = this.products.get(position);
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = convertView;
        if(view == null){
            view = inflater.inflate(R.layout.product_list, parent, false);
        }

        TextView productNumber = (TextView) view.findViewById(R.id.product_quantity);
        TextView productString = (TextView) view.findViewById(R.id.product_name);
        TextView productPrice = (TextView) view.findViewById(R.id.product_value);

        productNumber.setText(Integer.toString(product.getId()));
        productString.setText(product.getName());
        productPrice.setText("R$ " + String.format("%1$.2f", product.getPrice()));

        return view;
    }
}
