package com.elaine.kebabbangu.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Elaine on 6/3/2017.
 */

public class StockDAO extends SQLiteOpenHelper {

    public StockDAO(Context context) {
        super(context, "KebabDB", null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        String sqlCreateTableStock =
                "CREATE TABLE Stock (" +
                        "ProductID int FOREIGN KEY REFERENCES Products(ProductID),"+
                        "ProductQuantity INTEGER NOT NULL)";

        db.execSQL(sqlCreateTableStock);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
