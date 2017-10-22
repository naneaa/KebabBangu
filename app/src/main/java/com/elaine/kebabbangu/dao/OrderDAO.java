package com.elaine.kebabbangu.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.elaine.kebabbangu.base.Order;
import com.elaine.kebabbangu.base.Product;

import java.util.LinkedList;

/**
 * Created by elaine on 02/06/17.
 */

public class OrderDAO extends SQLiteOpenHelper {

    public OrderDAO(Context context) {
        super(context, "KebabDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreateTableOrders =
                "CREATE TABLE Orders (" +
                        "OrderID INTEGER PRIMARY KEY," +
                        "OrderNumber INTEGER NOT NULL," +
                        "OrderClientName TEXT NOT NULL," +
                        "IsPaid TEXT NOT NULL," +
                        "OrderPaymentMethod TEXT," +
                        "OrderPrice DOUBLE NOT NULL," +
                        "OrderDate TEXT NOT NULL)";

        db.execSQL(sqlCreateTableOrders);

        String sqlCreateTableProducts =
                "CREATE TABLE Products (" +
                        "ProductID INTEGER PRIMARY KEY," +
                        "ProductName TEXT NOT NULL," +
                        "ProductPrice DOUBLE NOT NULL," +
                        "HasSauce TEXT NOT NULL," +
                        "HasSalad TEXT NOT NULL," +
                        "HasCheese TEXT NOT NULL," +
                        "OnMenu TEXT NOT NULL)";

        db.execSQL(sqlCreateTableProducts);

        String sqlCreateTableRegister =
                "CREATE TABLE Registers (" +
                        "RegisterID INTEGER PRIMARY KEY," +
                        "RegisterStarting DOUBLE NOT NULL," +
                        "RegisterTotal DOUBLE NOT NULL," +
                        "RegisterCash DOUBLE NOT NULL," +
                        "RegisterDebit DOUBLE NOT NULL," +
                        "RegisterCredit DOUBLE NOT NULL," +
                        "RegisterDate TEXT NOT NULL)";

        db.execSQL(sqlCreateTableRegister);

        String sqlCreateTableOrderProduct =
                "CREATE TABLE OrderProduct (" +
                        "OrderID int NOT NULL," +
                        "ProductID int NOT NULL," +
                        "ProductDescription TEXT NOT NULL," +
                        "FOREIGN KEY (OrderID) REFERENCES Orders(OrderID)," +
                        "FOREIGN KEY (ProductID) REFERENCES Products(ProductID))";


        db.execSQL(sqlCreateTableOrderProduct);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public long create(Order order) {
        ContentValues orderValues = getContentValues(order);

        SQLiteDatabase database = getWritableDatabase();
        return database.insert("Orders", null, orderValues);
    }

    @NonNull
    private ContentValues getContentValues(Order order) {
        ContentValues orderValues = new ContentValues();
        orderValues.put("OrderClientName", order.getClientName());
        orderValues.put("OrderNumber", order.getNumber());
        orderValues.put("IsPaid", order.isPaid());
        orderValues.put("OrderPaymentMethod", order.getPaymentMethod());
        orderValues.put("OrderPrice", order.getPrice());
        orderValues.put("OrderDate", order.getDate());

        return orderValues;
    }

    public LinkedList<Order> read() {
        SQLiteDatabase database = getReadableDatabase();
        String sqlReadOrders = "SELECT * FROM Orders";
        Cursor cursorReadOrders = database.rawQuery(sqlReadOrders, null);

        LinkedList<Order> orders = new LinkedList<Order>();
        while (cursorReadOrders.moveToNext()) {
            Order order = new Order();
            order.setId(cursorReadOrders.getInt(
                    cursorReadOrders.getColumnIndex("OrderID")));
            order.setNumber(cursorReadOrders.getInt(
                    cursorReadOrders.getColumnIndex("OrderNumber")));
            order.setClientName(cursorReadOrders.getString(
                    cursorReadOrders.getColumnIndex("OrderClientName")));
            order.setPaid(cursorReadOrders.getString(
                    cursorReadOrders.getColumnIndex("IsPaid")).equals("1"));
            order.setPaymentMethod(cursorReadOrders.getString(
                    cursorReadOrders.getColumnIndex("OrderPaymentMethod")));
            order.setPrice(cursorReadOrders.getDouble(
                    cursorReadOrders.getColumnIndex("OrderPrice")));
            order.setDate(cursorReadOrders.getString(
                    cursorReadOrders.getColumnIndex("OrderDate")));

            String sqlReadOrderProducts = "SELECT * FROM OrderProduct WHERE OrderID = " + order.getId();
            Cursor cursorReadOrderProducts = database.rawQuery(sqlReadOrderProducts, null);

            LinkedList<Product> products = new LinkedList<Product>();
            LinkedList<String> description = new LinkedList<String>();
            while (cursorReadOrderProducts.moveToNext()) {
                Product product = new Product();
                int productID = cursorReadOrderProducts.getInt(
                        cursorReadOrderProducts.getColumnIndex("ProductID"));

                String sqlReadProducts = "SELECT * FROM Products WHERE ProductID = " + productID;
                Cursor cursorReadProducts = database.rawQuery(sqlReadProducts, null);

                while (cursorReadProducts.moveToNext()) {
                    product.setId(productID);
                    product.setName(cursorReadProducts.getString(
                            cursorReadProducts.getColumnIndex("ProductName")));
                    product.setPrice(cursorReadProducts.getDouble(
                            cursorReadProducts.getColumnIndex("ProductPrice")));
                }

                products.add(product);
                description.add(cursorReadOrderProducts.getString(
                        cursorReadOrderProducts.getColumnIndex("ProductDescription")));

                cursorReadProducts.close();
            }

            order.setProducts(products);
            order.setDescriptions(description);
            orders.add(order);

            cursorReadOrderProducts.close();
        }

        cursorReadOrders.close();

        return orders;
    }

    public int getOrderByRow(long row){
        SQLiteDatabase database = getReadableDatabase();
        String sqlReadOrders = "SELECT * FROM Orders";

        Cursor cursorReadOrders = database.rawQuery(sqlReadOrders, null);

        if (cursorReadOrders.getCount() == 0) {
            System.out.println("No orders!");
            return -1;
        }

        cursorReadOrders.moveToPosition((int) row-1);
        return cursorReadOrders.getInt(cursorReadOrders.getColumnIndex("OrderID"));
    }


}
