package de.kokoware.byoc.client;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by kokok_000 on 27.06.2016.
 */
public class Handler implements Runnable {

    private  Socket cs;
    private  PrintWriter oupt;
    private  int position;
    private int i;
    private  int bytesread;
    private  byte[] Buffer;
    private  String mCurrentPhotoPath;
    private  File file;
    private String[] dateinamen;
    private int A;
    private Scanner SC_CS;
    private Boolean Full = true;
    private int MAX_READ = 0;


    public Handler(String[] dateinamen, Socket cs, File file, PrintWriter oupt, int position,int i, int bytesread, byte[] Buffer, String mCurrentPhotoPath ) {

        this.dateinamen = dateinamen;
        this.cs = cs;
        this.file = file;
        this.oupt = oupt;
        this.position = position;
        this.i = i;
        this.bytesread = bytesread;
        this.Buffer = Buffer;
        this.mCurrentPhotoPath = mCurrentPhotoPath;





    }


    @Override
    public void run() {


        try {
            SC_CS = new Scanner(cs.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }


        // Toast.makeText(MainActivity, "You have selected : " + dateinamen[position] +" to download. Please wait.",
        //         Toast.LENGTH_LONG).show();


        //init
        PrintWriter out = null;
        try {
            out = new PrintWriter(cs.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream fileout = null;


        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        file = new File(path, dateinamen[position]);



        oupt.println(2);
        oupt.println(position+1);
        A = SC_CS.nextInt();





        InputStream nin = null;
        try {
            nin = cs.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        i = 0;

        try {
            fileout = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        try {
            while (((bytesread = nin.read(Buffer)) > 0) && Full)
            {
                try {
                    fileout.write(Buffer, 0, bytesread);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                MAX_READ += bytesread;




                if (MAX_READ >= A)
                {
                    Full = false;
                    fileout.close();

                    return;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        Log.println(5,"koko", "Nicht weiter als Übertragung");













    }



























}

