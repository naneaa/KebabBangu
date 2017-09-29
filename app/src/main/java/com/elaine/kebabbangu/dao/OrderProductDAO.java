package com.elaine.kebabbangu.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Elaine on 6/3/2017.
 */

public class OrderProductDAO extends SQLiteOpenHelper {

    public OrderProductDAO(Context context) {
        super(context, "KebabDB", null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        String sqlCreateTableStock =
                "CREATE TABLE OrderProduct (" +
                        "OrderID int FOREIGN KEY REFERENCES Orders(OrderID),"+
                        "ProductID int FOREIGN KEY REFERENCES Products(ProductID),"+
                        "ProductQuantity INTEGER NOT NULL," +
                        "ProductDescription TEXT NOT NULL)";

        db.execSQL(sqlCreateTableStock);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
