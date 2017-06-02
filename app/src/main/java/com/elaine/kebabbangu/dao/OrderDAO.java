package com.elaine.kebabbangu.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
                        "id INTEGER PRIMARY KEY,"+
                        "name TEXT NOT NULL,"+
                        "price DOUBLE NOT NULL)";

        db.execSQL(sqlCreateTableOrders);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}







}
