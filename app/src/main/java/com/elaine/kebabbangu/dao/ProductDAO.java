package com.elaine.kebabbangu.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.elaine.kebabbangu.base.Product;

import java.util.LinkedList;

/**
 * Created by elaine on 02/06/17.
 */

public class ProductDAO extends SQLiteOpenHelper {

    public ProductDAO(Context context) {
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

    public void create(Product product) {
        ContentValues productValues = getContentValues(product);

        SQLiteDatabase database = getWritableDatabase();
        database.insert("Products", null, productValues);
    }

    public void delete(int id) {
        String[] params = {Integer.toString(id)};

        SQLiteDatabase database = getWritableDatabase();
        database.delete("Products", "ProductID = ?", params);
    }

    public void update(Product product) {
        ContentValues productValues = getContentValues(product);
        String[] params = {Integer.toString(product.getId())};

        SQLiteDatabase database = getWritableDatabase();
        database.update("Products", productValues, "ProductID = ?", params);
    }

    @NonNull
    private ContentValues getContentValues(Product product) {
        ContentValues productValues = new ContentValues();
        productValues.put("ProductName", product.getName());
        productValues.put("ProductPrice", product.getPrice());
        productValues.put("HasSauce", product.hasSauce());
        productValues.put("HasSalad", product.hasSalad());
        productValues.put("HasCheese", product.hasCheese());
        productValues.put("OnMenu", product.isOnMenu());

        return productValues;
    }

    public LinkedList<Product> read() {
        SQLiteDatabase database = getReadableDatabase();
        String sqlReadProducts =
                "SELECT * FROM Products";

        Cursor cursorReadProducts = database.rawQuery(sqlReadProducts, null);

        LinkedList<Product> products = new LinkedList<Product>();
        while (cursorReadProducts.moveToNext()) {

            Product product = new Product();
            product.setId(cursorReadProducts.getInt(
                    cursorReadProducts.getColumnIndex("ProductID")));
            product.setName(cursorReadProducts.getString(
                    cursorReadProducts.getColumnIndex("ProductName")));
            product.setPrice(cursorReadProducts.getDouble(
                    cursorReadProducts.getColumnIndex("ProductPrice")));
            product.setHasSauce(cursorReadProducts.getString(
                    cursorReadProducts.getColumnIndex("HasSauce")).equals("1"));
            product.setHasSalad(cursorReadProducts.getString(
                    cursorReadProducts.getColumnIndex("HasSalad")).equals("1"));
            product.setHasCheese(cursorReadProducts.getString(
                    cursorReadProducts.getColumnIndex("HasCheese")).equals("1"));
            product.setOnMenu(cursorReadProducts.getString(
                    cursorReadProducts.getColumnIndex("OnMenu")).equals("1"));
            products.add(product);
        }

        cursorReadProducts.close();

        return products;
    }

    public LinkedList<Product> readMenu() {
        SQLiteDatabase database = getReadableDatabase();
        String sqlReadProducts =
                "SELECT * FROM Products";

        Cursor cursorReadProducts = database.rawQuery(sqlReadProducts, null);

        LinkedList<Product> products = new LinkedList<Product>();
        while (cursorReadProducts.moveToNext()) {
            Product product = new Product();

            product.setId(cursorReadProducts.getInt(
                    cursorReadProducts.getColumnIndex("ProductID")));
            product.setName(cursorReadProducts.getString(
                    cursorReadProducts.getColumnIndex("ProductName")));
            product.setPrice(cursorReadProducts.getDouble(
                    cursorReadProducts.getColumnIndex("ProductPrice")));
            product.setHasSauce(cursorReadProducts.getString(
                    cursorReadProducts.getColumnIndex("HasSauce")).equals("1"));
            product.setHasSalad(cursorReadProducts.getString(
                    cursorReadProducts.getColumnIndex("HasSalad")).equals("1"));
            product.setHasCheese(cursorReadProducts.getString(
                    cursorReadProducts.getColumnIndex("HasCheese")).equals("1"));
            product.setOnMenu(cursorReadProducts.getString(
                    cursorReadProducts.getColumnIndex("OnMenu")).equals("1"));

            if (product.isOnMenu())
                products.add(product);
        }

        cursorReadProducts.close();

        return products;
    }
}
