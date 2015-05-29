package ua.cn.palexp.dots;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onPlayClick (View v){
        Global.mode = false;
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public void onPlayOnlineClick (View v){
        //Global global = new Global();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Global.socket =null;
                Global.mode = true;
                try {
                    //InetAddress serverAddr = InetAddress.getByName("192.168.0.104");
                    Global.socket = new Socket("192.168.43.103", 5555);
                    Global.out = new DataOutputStream(Global.socket.getOutputStream());
                    Global.in = new BufferedReader(new InputStreamReader(Global.socket.getInputStream(),"UTF-8"));
                    Global.is = Global.socket.getInputStream();

                    //Toast.makeText(this,"step1!", Toast.LENGTH_SHORT);
                } catch (UnknownHostException e1) {
                    e1.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

        Intent intent1 = new Intent(this, LoginActivity.class);
        startActivity(intent1);
    }
}
