package com.elaine.kebabbangu.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.elaine.kebabbangu.base.Order;
import com.elaine.kebabbangu.base.Product;

import java.util.LinkedList;

/**
 * Created by elaine on 02/06/17.
 */

public class OrderDAO extends SQLiteOpenHelper{

    public OrderDAO(Context context) {
        super(context, "KebabDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreateTableOrders =
                "CREATE TABLE Orders (" +
                        "OrderID INTEGER PRIMARY KEY,"+
                        "OrderNumber INTEGER NOT NULL,"+
                        "IsPaid TEXT NOT NULL," +
                        "OrderPaymentMethod TEXT,"+
                        "OrderPrice DOUBLE NOT NULL,"+
                        "OrderDate DATE NOT NULL)";

        db.execSQL(sqlCreateTableOrders);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    public LinkedList<Order> read() {
        SQLiteDatabase database = getReadableDatabase();
        String sqlReadOrders = "SELECT * FROM Orders";
        Cursor cursorReadOrders = database.rawQuery(sqlReadOrders, null);

        LinkedList<Order> orders = new LinkedList<Order>();
        while (cursorReadOrders.moveToNext()){
            Order order = new Order();
            order.setId(cursorReadOrders.getInt(
                    cursorReadOrders.getColumnIndex("OrderID")));
            order.setNumber(cursorReadOrders.getInt(
                    cursorReadOrders.getColumnIndex("OrderNumber")));
            String isPaid = cursorReadOrders.getString(
                    cursorReadOrders.getColumnIndex("IsPaid"));
            order.setPaid(isPaid.equals("TRUE"));
            order.setPaymentMethod(cursorReadOrders.getString(
                    cursorReadOrders.getColumnIndex("OrderPaymentMethod")));
            order.setPrice(cursorReadOrders.getDouble(
                    cursorReadOrders.getColumnIndex("OrderPrice")));

            String sqlReadOrderProducts = "SELECT * FROM OrderProduct WHERE OrderID = " + order.getId();
            Cursor cursorReadOrderProducts = database.rawQuery(sqlReadOrderProducts, null);

            LinkedList<Product> products = new LinkedList<Product>();
            LinkedList<Integer> quantity = new LinkedList<Integer>();
            while (cursorReadOrderProducts.moveToNext()) {
                Product product = new Product();
                int productID = cursorReadOrders.getInt(
                        cursorReadOrders.getColumnIndex("ProductID"));

                String sqlReadProducts = "SELECT * FROM Products WHERE ProductID = " + productID;
                Cursor cursorReadProducts = database.rawQuery(sqlReadProducts, null);

                while (cursorReadOrderProducts.moveToNext()) {
                    product.setId(productID);
                    product.setName(cursorReadProducts.getString(
                            cursorReadProducts.getColumnIndex("ProductName")));
                    product.setPrice(cursorReadProducts.getDouble(
                            cursorReadProducts.getColumnIndex("ProductPrice")));
                }

                products.add(product);
                quantity.add(cursorReadOrders.getInt(
                        cursorReadOrders.getColumnIndex("ProductQuantity")));

                cursorReadProducts.close();
            }

            order.setProducts(products);
            order.setQuantity(quantity);
            orders.add(order);

            cursorReadOrderProducts.close();
        }

        cursorReadOrders.close();

        return orders;
    }




}
