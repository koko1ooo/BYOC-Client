package de.kokoware.byoc.client;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;


public class LoginActivity extends ActionBarActivity implements View.OnClickListener {

    private Socket cs;
    private EditText editText;
    private Intent MHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        editText = (EditText) findViewById(R.id.editText) ;

        Button OK = (Button) findViewById(R.id.button);


        OK.setOnClickListener(this);


         MHandler = new Intent(this, de.kokoware.byoc.client.MainActivity.class);









    }

    public void onClick(View v)  {

        String IP = editText.getText().toString();

        //Pingtest
        try {
             cs = new Socket(IP, 3141);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            PrintWriter out = new PrintWriter(cs.getOutputStream());
            out.println(4);
        } catch (IOException e) {
            e.printStackTrace();
            

        }


        MHandler.putExtra("Socketadr", IP);
        startActivity(MHandler);

    }

}
