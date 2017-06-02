package com.elaine.kebabbangu.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.elaine.kebabbangu.base.Product;
import com.elaine.kebabbangu.R;
import com.elaine.kebabbangu.base.Stock;

/**
 * Created by Elaine on 6/2/2017.
 */

public class StockAdapter extends BaseAdapter{

    private final Context context;
    private Stock stock;

    public StockAdapter(Context context, Stock stock) {
        this.stock = stock;
        this.context = context;
    }

    @Override
    public int getCount() { return this.stock.getProducts().size(); }

    @Override
    public Object getItem(int position) { return this.stock.getProducts().get(position); }

    @Override
    public long getItemId(int position) { return 0;}//this.stock.getProducts().get(position).getId(); }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Product product = this.stock.getProducts().get(position);
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = convertView;
        if(view == null){
            view = inflater.inflate(R.layout.orders_list, parent, false);
        }

        TextView orderString = (TextView) view.findViewById(R.id.order_string);
        TextView orderValue = (TextView) view.findViewById(R.id.order_value);

        orderString.setText(product.getName());
        orderValue.setText(stock.getQuantity().get(position));

        return view;
    }


}
