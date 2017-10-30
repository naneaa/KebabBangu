package com.elaine.kebabbangu.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.elaine.kebabbangu.base.Expense;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

/**
 * Created by Elaine on 10/26/2017.
 */

public class ExpenseDAO extends SQLiteOpenHelper{

    public ExpenseDAO(Context context) {
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

    public long create(Expense expense) {
        ContentValues expenseValues = getContentValues(expense);

        SQLiteDatabase database = getWritableDatabase();
        return database.insert("Expenses", null, expenseValues);
    }

    public void delete(int id) {
        String[] params = {Integer.toString(id)};

        SQLiteDatabase database = getWritableDatabase();
        database.delete("Expenses", "ExpenseID = ?", params);
    }

    public void update(Expense expense) {
        ContentValues expenseValues = getContentValues(expense);
        String[] params = {Integer.toString(expense.getId())};

        SQLiteDatabase database = getWritableDatabase();
        database.update("Expenses", expenseValues, "ExpenseID = ?", params);
    }

    private ContentValues getContentValues(Expense expense) {
        ContentValues expenseValues = new ContentValues();
        expenseValues.put("ExpenseDescription", expense.getDescription());
        expenseValues.put("ExpenseDate", expense.getDate());
        expenseValues.put("ExpenseValue", expense.getValue());

        return expenseValues;
    }

    public LinkedList<Expense> read() {
        SQLiteDatabase database = getReadableDatabase();
        String sqlReadExpenses =
                "SELECT * FROM Expenses";

        Cursor cursorReadExpenses = database.rawQuery(sqlReadExpenses, null);

        LinkedList<Expense> expenses = new LinkedList<>();
        while (cursorReadExpenses.moveToNext()) {

            Expense expense = new Expense();
            expense.setId(cursorReadExpenses.getInt(
                    cursorReadExpenses.getColumnIndex("ExpenseID")));
            expense.setDescription(cursorReadExpenses.getString(
                    cursorReadExpenses.getColumnIndex("ExpenseDescription")));
            expense.setDate(cursorReadExpenses.getString(
                    cursorReadExpenses.getColumnIndex("ExpenseDate")));
            expense.setValue(cursorReadExpenses.getDouble(
                    cursorReadExpenses.getColumnIndex("ExpenseValue")));
            expenses.add(expense);
        }

        cursorReadExpenses.close();

        return expenses;
    }


    public LinkedList<Expense> readTodaysExpenses() {
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();

        SQLiteDatabase database = getReadableDatabase();
        String sqlReadExpenses =
                "SELECT * FROM Expenses WHERE ExpenseDate = '" + sdf.format(date) + "'";

        Cursor cursorReadExpenses = database.rawQuery(sqlReadExpenses, null);

        LinkedList<Expense> expenses = new LinkedList<>();
        while (cursorReadExpenses.moveToNext()) {

            Expense expense = new Expense();
            expense.setId(cursorReadExpenses.getInt(
                    cursorReadExpenses.getColumnIndex("ExpenseID")));
            expense.setDescription(cursorReadExpenses.getString(
                    cursorReadExpenses.getColumnIndex("ExpenseDescription")));
            expense.setDate(cursorReadExpenses.getString(
                    cursorReadExpenses.getColumnIndex("ExpenseDate")));
            expense.setValue(cursorReadExpenses.getDouble(
                    cursorReadExpenses.getColumnIndex("ExpenseValue")));
            expenses.add(expense);
        }

        cursorReadExpenses.close();

        return expenses;
    }


}
