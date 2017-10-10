package com.elaine.kebabbangu.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.elaine.kebabbangu.base.Register;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

/**
 * Created by Elaine on 6/3/2017.
 */

public class RegisterDAO extends SQLiteOpenHelper {

    public RegisterDAO(Context context) {
        super(context, "KebabDB", null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        String sqlCreateTableOrders =
                "CREATE TABLE Orders (" +
                        "OrderID INTEGER PRIMARY KEY," +
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

    public void create(Register register) throws SQLiteException {
        ContentValues registerValues = getContentValues(register);

        SQLiteDatabase seachDatabase = getReadableDatabase();
        String sqlSearch = "SELECT * FROM Registers WHERE RegisterDate = '" + register.getDate() + "'";
        Cursor cursorSearch = seachDatabase.rawQuery(sqlSearch, null);

        if (cursorSearch.getCount() > 0)
            throw new SQLiteException();

        SQLiteDatabase database = getWritableDatabase();
        database.insert("Registers", null, registerValues);
    }

    public void delete(int id) {
        String[] params = {Integer.toString(id)};

        SQLiteDatabase database = getWritableDatabase();
        database.delete("Registers", "RegisterID = ?", params);
    }

    public void deleteAll() {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("DELETE FROM Registers");
    }

    public void update(Register register) {
        ContentValues registerValues = getContentValues(register);
        String[] params = {Integer.toString(register.getId())};

        SQLiteDatabase database = getWritableDatabase();
        database.update("Registers", registerValues, "RegisterID = ?", params);
    }

    private ContentValues getContentValues(Register register) {
        ContentValues registerValues = new ContentValues();
        registerValues.put("RegisterStarting", register.getStarting());
        registerValues.put("RegisterTotal", register.getTotal());
        registerValues.put("RegisterCash", register.getCash());
        registerValues.put("RegisterDebit", register.getDebit());
        registerValues.put("RegisterCredit", register.getCredit());
        registerValues.put("RegisterDate", register.getDate());

        return registerValues;
    }

    public LinkedList<Register> read() {
        SQLiteDatabase database = getReadableDatabase();
        String sqlReadRegisters =
                "SELECT * FROM Registers";

        Cursor cursorReadRegisters = database.rawQuery(sqlReadRegisters, null);

        LinkedList<Register> registers = new LinkedList<>();
        while (cursorReadRegisters.moveToNext()) {

            Register register = new Register();
            register.setId(cursorReadRegisters.getInt(
                    cursorReadRegisters.getColumnIndex("RegisterID")));
            register.setDate(cursorReadRegisters.getString(
                    cursorReadRegisters.getColumnIndex("RegisterDate")));
            registers.add(register);
        }

        cursorReadRegisters.close();

        return registers;
    }

    public Register getTodaysRegister() {
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();

        SQLiteDatabase seachDatabase = getReadableDatabase();
        String sqlSearch = "SELECT * FROM Registers WHERE RegisterDate = '" + sdf.format(date) + "'";
        Cursor cursorSearch = seachDatabase.rawQuery(sqlSearch, null);

        if (cursorSearch.getCount() == 0)
            return null;

        cursorSearch.moveToFirst();

        Register register = new Register();
        register.setId(cursorSearch.getInt(cursorSearch.getColumnIndex("RegisterID")));
        register.setStarting(cursorSearch.getDouble(cursorSearch.getColumnIndex("RegisterStarting")));
        register.setTotal(cursorSearch.getDouble(cursorSearch.getColumnIndex("RegisterTotal")));
        register.setCash(cursorSearch.getDouble(cursorSearch.getColumnIndex("RegisterCash")));
        register.setDebit(cursorSearch.getDouble(cursorSearch.getColumnIndex("RegisterDebit")));
        register.setCredit(cursorSearch.getDouble(cursorSearch.getColumnIndex("RegisterCredit")));
        register.setDate(cursorSearch.getString(cursorSearch.getColumnIndex("RegisterDate")));

        return register;
    }

}
