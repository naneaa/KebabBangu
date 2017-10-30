package com.elaine.kebabbangu.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.elaine.kebabbangu.R;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.sql.SQLOutput;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


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

    public static void print(final String st) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Socket sock = new Socket("192.168.15.2", 9100);
                    PrintWriter oStream = new PrintWriter(sock.getOutputStream());

                    if(sock.isConnected()){
                        System.out.println("Conectou!");
                    } else {
                        System.out.println("Não conectou (?)");
                    }

                    Date date = new Date();
                    DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    oStream.println(sdf.format(date) + "\n" + st);

                    oStream.write(new char[]{27, 'i'});

                    oStream.close();
                    sock.close();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }).start();
    }

}
