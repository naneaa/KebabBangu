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

public class ProductDAO extends SQLiteOpenHelper{

    public ProductDAO(Context context) {
        super(context, "KebabDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreateTableProducts =
                "CREATE TABLE Products (" +
                        "ProductID INTEGER PRIMARY KEY,"+
                        "ProductName TEXT NOT NULL,"+
                        "ProductPrice DOUBLE NOT NULL," +
                        "HasSauce TEXT NOT NULL," +
                        "HasSalad TEXT NOT NULL," +
                        "HasCheese TEXT NOT NULL," +
                        "OnMenu TEXT NOT NULL)";

        db.execSQL(sqlCreateTableProducts);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

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
        ContentValues studentValues = new ContentValues();
        studentValues.put("ProductName", product.getName());
        studentValues.put("ProductPrice", product.getPrice());
        studentValues.put("HasSauce", product.hasSauce());
        studentValues.put("HasSalad", product.hasSalad());
        studentValues.put("HasCheese", product.hasCheese());
        studentValues.put("OnMenu", product.isOnMenu());

        return studentValues;
    }

    public LinkedList<Product> read() {
        SQLiteDatabase database = getReadableDatabase();
        String sqlReadProducts =
                "SELECT * FROM Products";

        Cursor cursorReadProducts = database.rawQuery(sqlReadProducts, null);

        LinkedList<Product> products = new LinkedList<Product>();
        while (cursorReadProducts.moveToNext()){

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
        while (cursorReadProducts.moveToNext()){
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

            if(product.isOnMenu())
                products.add(product);
        }

        cursorReadProducts.close();

        return products;
    }
}
