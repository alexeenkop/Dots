package ua.cn.palexp.dots;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by pALEXp on 28.05.2015.
 */
public class Global {

    public static Socket socket;
    public static volatile DataOutputStream out;
    public static volatile BufferedReader in;
    public static volatile InputStream is;
    public static String nickname;
    public static ArrayList<GameLobby> arrayList;
    public static volatile boolean stopFlag;
    public static boolean mode = false;
    public static GameState ggs;

    public Global(){
        this.socket = null;
        this.out = null;
        this.in = null;
        this.is = null;
        this.nickname = null;
        this.arrayList = null;
        this.mode = false;
        this.stopFlag = false;
    }

}
