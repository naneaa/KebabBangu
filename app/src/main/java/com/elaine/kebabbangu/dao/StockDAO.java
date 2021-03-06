package com.elaine.kebabbangu.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.elaine.kebabbangu.base.Product;
import com.elaine.kebabbangu.base.StockItem;

import java.util.LinkedList;

/**
 * Created by Elaine on 6/3/2017.
 */

public class StockDAO extends SQLiteOpenHelper {

    public StockDAO(Context context) {
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

    public long create(StockItem expense) {
        ContentValues stockValues = getContentValues(expense);

        SQLiteDatabase database = getWritableDatabase();
        return database.insert("Stock", null, stockValues);
    }

    public void delete(int id) {
        String[] params = {Integer.toString(id)};

        SQLiteDatabase database = getWritableDatabase();
        database.delete("Stock", "ProductID = ?", params);
    }

    public void update(StockItem stockItem) {
        ContentValues stockValues = getContentValues(stockItem);
        String[] params = {Integer.toString(stockItem.getProduct().getId())};

        SQLiteDatabase database = getWritableDatabase();
        database.update("Stock", stockValues, "ProductID = ?", params);
    }

    private ContentValues getContentValues(StockItem stockItem) {
        ContentValues stockValues = new ContentValues();
        stockValues.put("ProductID", stockItem.getProduct().getId());
        stockValues.put("ProductQuantity", stockItem.getQuantity());

        return stockValues;
    }

    public LinkedList<StockItem> read(LinkedList<Product> products) {
        SQLiteDatabase database = getReadableDatabase();
        String sqlReadExpenses =
                "SELECT * FROM Stock";

        Cursor cursorReadStock = database.rawQuery(sqlReadExpenses, null);

        LinkedList<StockItem> stock = new LinkedList<>();
        while (cursorReadStock.moveToNext()) {
            StockItem stockItem = new StockItem();

            int productID = cursorReadStock.getInt(
                    cursorReadStock.getColumnIndex("ProductID"));
            for(Product p : products)
                if(p.getId() == productID)
                    stockItem.setProduct(p);

            stockItem.setQuantity( cursorReadStock.getInt(
                    cursorReadStock.getColumnIndex("ProductQuantity")));

            stock.add(stockItem);
        }

        cursorReadStock.close();

        return stock;
    }

}
