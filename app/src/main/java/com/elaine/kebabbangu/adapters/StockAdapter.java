package com.elaine.kebabbangu.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.elaine.kebabbangu.R;
import com.elaine.kebabbangu.base.Product;
import com.elaine.kebabbangu.base.StockItem;

import java.util.LinkedList;

/**
 * Created by Elaine on 6/2/2017.
 */

public class StockAdapter extends BaseAdapter {

    private final Context context;
    private LinkedList<StockItem> stockItem;

    public StockAdapter(Context context, LinkedList<StockItem> stockItem) {
        this.stockItem = stockItem;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.stockItem.size();
    }

    @Override
    public Object getItem(int position) {
        return this.stockItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }//this.stockItem.getProducts().get(position).getId(); }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Product product = this.stockItem.get(position).getProduct();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.orders_list, parent, false);
        }

        TextView orderString = (TextView) view.findViewById(R.id.order_string);
        TextView orderValue = (TextView) view.findViewById(R.id.order_value);

        orderString.setText(product.getName());
        orderValue.setText(stockItem.get(position).getQuantity());

        return view;
    }


}
