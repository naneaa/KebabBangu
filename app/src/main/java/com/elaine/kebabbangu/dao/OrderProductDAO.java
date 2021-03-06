package com.elaine.kebabbangu.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.elaine.kebabbangu.base.Order;
import com.elaine.kebabbangu.base.Product;

import java.util.LinkedList;

/**
 * Created by Elaine on 6/3/2017.
 */

public class OrderProductDAO extends SQLiteOpenHelper {

    public OrderProductDAO(Context context) {
        super(context, "KebabDB", null, 1);
    }

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

        String sqlCreateTableExpenses =
                "CREATE TABLE Expenses (" +
                        "ExpenseID INTEGER PRIMARY KEY," +
                        "ExpenseDescription TEXT NOT NULL," +
                        "ExpenseDate TEXT NOT NULL," +
                        "ExpenseValue DOUBLE NOT NULL)";

        db.execSQL(sqlCreateTableExpenses);

        String sqlCreateTableStock =
                "CREATE TABLE Stock (" +
                        "ProductID int NOT NULL," +
                        "ProductQuantity INTEGER NOT NULL," +
                        "FOREIGN KEY (ProductID) REFERENCES Products(ProductID))";

        db.execSQL(sqlCreateTableStock);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void create(Order order) {
        LinkedList<Product> products = order.getProducts();
        LinkedList<String> descriptions = order.getDescriptions();

        for(int i = 0; i < products.size(); i++){
            ContentValues orderProductValues = new ContentValues();
            orderProductValues.put("OrderID", order.getId());
            orderProductValues.put("ProductID", products.get(i).getId());
            orderProductValues.put("ProductDescription", descriptions.get(i));

            SQLiteDatabase database = getWritableDatabase();
            database.insert("OrderProduct", null, orderProductValues);
        }
    }

}
