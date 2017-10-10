package com.elaine.kebabbangu.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.elaine.kebabbangu.R;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLOutput;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void callOrdersScreen(View view) {
        Intent intent = new Intent(MainActivity.this, OrdersActivity.class);
        startActivity(intent);
    }

    public void callRegisterScreen(View view) {
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
    }


    public void callSettingsScreen(View view) {
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    public void callNewOrderScreen(View view) {
        Intent intent = new Intent(MainActivity.this, NewOrderActivity.class);
        startActivity(intent);
    }

    public void testPrinter(View view) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Socket sock = new Socket("192.168.1.12", 9100);
                    PrintWriter oStream = new PrintWriter(sock.getOutputStream());

                    oStream.println("NHE3");

                    oStream.close();
                    sock.close();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }).start();
    }

}
