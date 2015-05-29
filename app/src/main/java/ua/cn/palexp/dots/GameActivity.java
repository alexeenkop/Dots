package ua.cn.palexp.dots;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;


public class GameActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_game);
        GameView view=(GameView)findViewById(R.id.game_view);
        GameLogic logic=new GameLogic(view, this);
        view.setLogic(logic);
        if (Global.mode){
            view.gameUpdateThread();
        }
    }
}
