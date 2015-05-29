package ua.cn.palexp.dots;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class LoginActivity extends ActionBarActivity {

    EditText e1;
    EditText e2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        e1 = (EditText)findViewById(R.id.editText);
        e2 = (EditText)findViewById(R.id.editText2);
    }

    public void onLoginClick (View v) throws Exception {
        Packet request = getLoginPacket();
        if(request == null)
            return;

        try {
            sendToServer(request);
            Packet answer = PacketManager.readPacketFromStream(Global.in);

            if(answer == null) {
                return;
            }

            if(answer.getCode().equals(Packet.PACKET_CODES.SUCCESS_CODE)) {
                Global.nickname = e1.getText().toString();
                refreshGameList();
                if (Global.arrayList != null) {
                    join();
                }

            } else if(answer.getCode().equals(Packet.PACKET_CODES.INCORRECT_COMMAND_CODE)) {
                //warning("Incorrect command.");

            } else if(answer.getCode().equals(Packet.PACKET_CODES.SERVER_ERROR_CODE)) {
                //warning("Unknown server error.");

            } else if(answer.getCode().equals(Packet.PACKET_CODES.LOGIN_ERROR_CODE)) {
                //loginPanel.setMessage("Login failed. Check login and/or password.");
            }

        } catch(IOException e1) {
            return;
        }


    }

    public Packet getLoginPacket() {

        Packet res = null;

//        if(!isFillField())
//            return res;
//        if(!isValidField())
//            return res;

        res = new Packet(Packet.PACKET_CODES.LOGIN_CMD);
        HashMap<String, String> data = new HashMap<String, String>();
        data.put(Packet.LOGIN_KEY, e1.getText().toString());
        data.put(Packet.PASSWORD_KEY, e2.getText().toString());
        res.setData(data);

        return res;
    }

    private void sendToServer(Packet packet) throws IOException {

        if(packet != null) {
            String srt = PacketManager.pack(packet);

            //XXX Send from ClientFrame
            System.out.println("== SEND ==");
            System.out.println(srt);
            System.out.println("== END ==");

            byte[] buf = srt.getBytes("UTF-8");
            Global.out.write(buf, 0, buf.length);
        }
    }

    private void refreshGameList() throws Exception {

        try {
            sendToServer(Packet.getGameListPacket());
            Packet answer = PacketManager.readPacketFromStream(Global.in);

            if(answer == null) {
                return;
            }

            if(answer.getCode().equals(Packet.PACKET_CODES.GAMES_LIST_CODE)) {
                Global.arrayList = ((ArrayList<GameLobby>) answer.getData() );

            } else if(answer.getCode().equals(Packet.PACKET_CODES.INCORRECT_COMMAND_CODE)) {

            } else if(answer.getCode().equals(Packet.PACKET_CODES.SERVER_ERROR_CODE)) {

            } else if(answer.getCode().equals(Packet.PACKET_CODES.USER_NOT_SINGING_UP_CODE)) {
                            }

        } catch(IOException e1) {
            return;
        }
    }

    private void join () throws Exception {
        Integer GameID = Global.arrayList.get(0).ID;

        Packet request = new Packet(Packet.PACKET_CODES.JOIN_TO_GAME_CMD);
        request.setData(GameID);
        try {
            sendToServer(request);
            Packet answer = PacketManager.readPacketFromStream(Global.in);

            if(answer == null) {
                return;
            }

            if(answer.getCode().equals(Packet.PACKET_CODES.SUCCESS_CODE)) {
                Intent intent = new Intent(this, GameActivity.class);
                startActivity(intent);

            } else if(answer.getCode().equals(Packet.PACKET_CODES.INCORRECT_COMMAND_CODE)) {
                return;

            } else if(answer.getCode().equals(Packet.PACKET_CODES.SERVER_ERROR_CODE)) {
                return;//warning("Unknown server error.");

            } else if(answer.getCode().equals(Packet.PACKET_CODES.USER_NOT_SINGING_UP_CODE)) {
                return;//warning("User not singing up.");

            } else if(answer.getCode().equals(Packet.PACKET_CODES.GAME_NOT_EXIST_CODE)) {
                return;//warning("Game not exist.");

            } else if(answer.getCode().equals(Packet.PACKET_CODES.GAME_IS_FULL_CODE)) {
                return;//warning("Game is full.");
            }

        } catch(IOException e1) {
            e1.printStackTrace();
            return;
        }
    }

}
