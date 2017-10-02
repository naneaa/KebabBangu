package com.elaine.kebabbangu.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Elaine on 6/3/2017.
 */

public class RegisterDAO extends SQLiteOpenHelper {

    public RegisterDAO(Context context) {
        super(context, "KebabDB", null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        String sqlCreateTableRegister =
                "CREATE TABLE Register (" +
                        "RegisterID INTEGER PRIMARY KEY,"+
                        "RegisterStarting DOUBLE NOT NULL,"+
                        "RegisterTotal DOUBLE NOT NULL,"+
                        "RegisterCash DOUBLE NOT NULL,"+
                        "RegisterDebit DOUBLE NOT NULL,"+
                        "RegisterCredit DOUBLE NOT NULL,"+
                        "RegisterDate DATE NOT NULL)";

        db.execSQL(sqlCreateTableRegister);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
