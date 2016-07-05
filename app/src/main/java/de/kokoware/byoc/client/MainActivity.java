package de.kokoware.byoc.client;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import android.view.Menu;
import android.view.MenuItem;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class MainActivity extends ListActivity {

    String[] dateinamen;

    public String mCurrentPhotoPath;
    int Ad, i = 0, A;
    int Eingegeben;
    int bytesread;
    public Socket cs = null;
    public byte[] Buffer = new byte[16384];
    public Scanner in = null;
    public File file;
    public PrintWriter oupt;
    public static boolean Handleract;
    private Thread Networking;
    private Runnable Networkingn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        Scanner board = new Scanner(System.in);


        Handleract = false;

        try {
            cs = new Socket("kokoware.de", 3414);
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            in = new Scanner(cs.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            oupt = new PrintWriter(cs.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }


        oupt.println(1);
        Ad = in.nextInt();

        dateinamen = new String[Ad];

        while (i < Ad) {
            dateinamen[i] = in.next();
            i++;
        }

        i = 0;
        while (i < Ad) {

            System.out.print(i + 1);
            System.out.println(dateinamen[i]);
            i++;
        }


        System.out.println(dateinamen[1]);


        //Listview adapter
        setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, dateinamen));
    }

    public void onListItemClick(ListView parent, View v,
                                int position, long id) {


        if (!Handleract || !Networking.isAlive()) {
            Networking = new Thread(new Handler(dateinamen, cs, file, oupt, position, i, bytesread, Buffer, mCurrentPhotoPath));
            Networking.setDaemon(true);
            Networking.start();


            Handleract = true;
        }




        Toast.makeText(this, "File downloaded.",
                Toast.LENGTH_LONG).show();


    }

    // android Options Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        return true;
    }

    public void onGroupItemClick(MenuItem item) {
        if (item.isChecked()) {
            item.setChecked(false);
        } else {
            item.setChecked(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(this, item.getTitle(), Toast.LENGTH_LONG).show();
        return true;
    }


}































