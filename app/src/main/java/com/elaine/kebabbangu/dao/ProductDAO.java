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
                        "id INTEGER PRIMARY KEY,"+
                        "name TEXT NOT NULL,"+
                        "price DOUBLE NOT NULL)";

        db.execSQL(sqlCreateTableProducts);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    public void create(Product product) {

        SQLiteDatabase database = getWritableDatabase();

        ContentValues productValues = getContentValues(product);

        database.insert("Students", null, productValues);
    }

    public LinkedList<Product> read() {
        SQLiteDatabase database = getReadableDatabase();
        String sqlReadStudents =
                "SELECT * FROM Students";

        Cursor cursorReadStudents = database.rawQuery(sqlReadStudents, null);

        LinkedList<Product> products = new LinkedList<Product>();
        while (cursorReadStudents.moveToNext()){

            Product product = new Product();
            product.setId(cursorReadStudents.getInt(
                    cursorReadStudents.getColumnIndex("id")));
            product.setName(cursorReadStudents.getString(
                    cursorReadStudents.getColumnIndex("name")));
            product.setPrice(cursorReadStudents.getDouble(
                    cursorReadStudents.getColumnIndex("price")));

            products.add(product);
        }

        cursorReadStudents.close();

        return products;
    }

    public void delete(int id) {
        SQLiteDatabase database = getWritableDatabase();
        String[] params = {Integer.toString(id)};
        database.delete("Students", "id = ?", params);
    }

    public void update(Product product) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues studentValues = getContentValues(product);
        String[] params = {Integer.toString(product.getId())};
        database.update("Students", studentValues, "id = ?", params);
    }

    @NonNull
    private ContentValues getContentValues(Product product) {
        ContentValues studentValues = new ContentValues();
        studentValues.put("name", product.getName());
        studentValues.put("photopath", product.getPrice());
        return studentValues;
    }
}
