package ua.cn.palexp.dots;

/**
 * Created by pALEXp on 28.05.2015.
 */
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class PacketManager {

    public static Packet unpack(String packet) {

        Packet res = null;

        if (packet != null) {

            if (packet.length() < 3)
                return res;

            String code = packet.substring(0, 3);

            if (code.equals(Packet.PACKET_CODES.SUCCESS_CODE)) {
                res = Packet.getSuccessPacket();

            } else if (code.equals(Packet.PACKET_CODES.GAMES_LIST_CODE)) {
                res = getGamesListPacket(packet);

            } else if (code.equals(Packet.PACKET_CODES.GAME_CREATED_CODE)) {
                res = getGameCreatedPacket(packet);

            } else if (code.equals(Packet.PACKET_CODES.CURRENT_STATE_CODE)) {
                res = getGameCurrentStatePacket(packet);

            } else if (code.equals(Packet.PACKET_CODES.USER_ALREADY_EXIST_CODE)) {
                res = Packet.getUserAlreadyExistPacket();

            } else if (code.equals(Packet.PACKET_CODES.INCORRECT_COMMAND_CODE)) {
                res = Packet.getIncorrectCommandPacket();

            } else if (code.equals(Packet.PACKET_CODES.SERVER_ERROR_CODE)) {
                res = Packet.getServerErrorPacket();

            } else if (code.equals(Packet.PACKET_CODES.LOGIN_ERROR_CODE)) {
                res = Packet.getLoginErrorPacket();

            } else if (code
                    .equals(Packet.PACKET_CODES.USER_NOT_SINGING_UP_CODE)) {
                res = Packet.getUserNotSingingUpPacket();

            } else if (code.equals(Packet.PACKET_CODES.GAME_NOT_EXIST_CODE)) {
                res = Packet.getGameNotExistPacket();

            } else if (code.equals(Packet.PACKET_CODES.GAME_IS_FULL_CODE)) {
                res = Packet.getGameIsFullPacket();

            } else if (code.equals(Packet.PACKET_CODES.CANNOT_LEAVE_GAME_CODE)) {
                res = Packet.getCannotLeaveGamePacket();

            } else if (code.equals(Packet.PACKET_CODES.INCORRECT_TURN_CODE)) {
                res = Packet.getIncorrectTurnPacket();

            } else if (code.equals(Packet.PACKET_CODES.GAME_STATE_CHANGE_CMD)) {
                res = Packet.getGameStateChangePacket();

            } else if (code.equals(Packet.PACKET_CODES.REGISTRATION_CMD)) {
                res = getRegistrationPacket(packet);

            } else if (code.equals(Packet.PACKET_CODES.LOGIN_CMD)) {
                res = getLoginPacket(packet);

            } else if (code.equals(Packet.PACKET_CODES.LOGOUT_CMD)) {
                res = Packet.getLogoutPacket();

            } else if (code.equals(Packet.PACKET_CODES.GET_USER_STATISTIC_CMD)) {
                res = new Packet(Packet.PACKET_CODES.GET_USER_STATISTIC_CMD);

            } else if (code.equals(Packet.PACKET_CODES.GET_GAME_LIST_CMD)) {
                res = Packet.getGameListPacket();

            } else if (code.equals(Packet.PACKET_CODES.NEW_GAME_CMD)) {
                res = getNewGamePacket(packet);

            } else if (code.equals(Packet.PACKET_CODES.JOIN_TO_GAME_CMD)) {
                res = getJoinPacket(packet);

            } else if (code.equals(Packet.PACKET_CODES.LEAVE_GAME_CMD)) {
                res = Packet.getLeaveGamePacket();

            } else if (code.equals(Packet.PACKET_CODES.MAKE_TURN_CMD)) {
                res = getMakeTurnPacket(packet);

            } else if (code.equals(Packet.PACKET_CODES.FINISH_GAME_CMD)) {
                res = Packet.getFinishGamePacket();

            } else if (code.equals(Packet.PACKET_CODES.UPDATE_CMD)) {
                res = Packet.getUpdatePacket();
            }

        }

        return res;
    }

    private static Packet getGamesListPacket(String packet) {

        Scanner sc = new Scanner(packet);

        if (!sc.next().equals(Packet.PACKET_CODES.GAMES_LIST_CODE))
            return null;
        Packet res = new Packet(Packet.PACKET_CODES.GAMES_LIST_CODE);

        ArrayList<GameLobby> games = new ArrayList<GameLobby>();

        while (sc.hasNext()) {

            GameLobby gl = new GameLobby();

            if (!sc.next().equals(Packet.PACKET_CODES.GAME_ID))
                break;
            if (!sc.hasNextInt())
                break;
            gl.ID = sc.nextInt();

            if (!sc.next().equals(Packet.PACKET_CODES.GAME_PLAYER_MAX))
                break;
            if (!sc.hasNextInt())
                break;
            gl.settings.maxCountOfPlayer = sc.nextInt();

            if (!sc.next().equals(Packet.PACKET_CODES.GAME_PLAYER_CURRECT))
                break;
            if (!sc.hasNextInt())
                break;
            gl.currentCountOfPlayer = sc.nextInt();

            if (!sc.next().equals(Packet.PACKET_CODES.GAME_AREA_SIZE))
                break;
            if (!sc.hasNextInt())
                break;
            gl.settings.XSize = sc.nextInt();
            if (!sc.hasNextInt())
                break;
            gl.settings.YSize = sc.nextInt();

            if (!sc.next().equals(Packet.PACKET_CODES.GAME_TURN_TIMEOUT))
                break;
            if (!sc.hasNextInt())
                break;
            gl.settings.turnTime = sc.nextInt();

            if (!sc.next().equals(Packet.PACKET_CODES.GAME_EXTRA_TURN))
                break;
            if (!sc.hasNextInt())
                break;
            int f = sc.nextInt();
            if (f == 0)
                gl.settings.extraTurn = false;
            else if (f == 1)
                gl.settings.extraTurn = true;
            else
                break;

            if (!sc.next().equals(Packet.PACKET_CODES.GAME_USERS_LIST))
                break;
            String str = sc.nextLine();
            Scanner temp = new Scanner(str);
            while (temp.hasNext()) {
                gl.players.add(temp.next());
            }

            games.add(gl);
        }

        res.setData(games);

        return res;
    }

    private static Packet getGameCreatedPacket(String packet) {

        Scanner sc = new Scanner(packet);

        if (!sc.next().equals(Packet.PACKET_CODES.GAME_CREATED_CODE))
            return null;
        Packet res = new Packet(Packet.PACKET_CODES.GAME_CREATED_CODE);

        Integer ID;

        if (!sc.next().equals(Packet.PACKET_CODES.GAME_ID))
            return null;
        if (!sc.hasNextInt())
            return null;
        ID = new Integer(sc.nextInt());

        res.setData(ID);

        return res;
    }

    private static Packet getGameCurrentStatePacket(String packet) {

        Scanner sc = new Scanner(packet);
        Scanner temp;

        if (!sc.next().equals(Packet.PACKET_CODES.CURRENT_STATE_CODE))
            return null;
        Packet res = new Packet(Packet.PACKET_CODES.CURRENT_STATE_CODE);

        GameState gs = new GameState();
        ArrayList<PlayerState> ps = new ArrayList<PlayerState>();

        if (!sc.next().equals(Packet.PACKET_CODES.ACTIVE_USER))
            return null;
        gs.activePlayer = sc.next();

        if (!sc.next().equals(Packet.PACKET_CODES.GAME_USERS_LIST))
            return null;
        temp = new Scanner(sc.nextLine());
        while (temp.hasNext()) {
            ps.add(new PlayerState(temp.next()));
        }

        if (!sc.next().equals(Packet.PACKET_CODES.ACTIVE_USER_FLAGS))
            return null;
        temp = new Scanner(sc.nextLine());
        for (int i = 0; i < ps.size(); i++) {
            int f;
            try {
                f = Integer.parseInt(temp.next());
            } catch (Exception e) {
                return null;
            }

            if (f == 1)
                ps.get(i).setActive(Boolean.valueOf(true));
            else if (f == 0)
                ps.get(i).setActive(Boolean.valueOf(false));
            else
                return null;
        }

        if (!sc.next().equals(Packet.PACKET_CODES.SCORE))
            return null;
        temp = new Scanner(sc.nextLine());
        for (int i = 0; i < ps.size(); i++) {
            int s;
            try {
                s = Integer.parseInt(temp.next());
            } catch (Exception e) {
                return null;
            }

            ps.get(i).setScore(s);
        }

        gs.playerStates = ps;

        ArrayList<String> mapLines = new ArrayList<String>();
        while (sc.hasNext()) {
            if (!sc.next().equals(Packet.PACKET_CODES.GAME_AREA_LINE))
                return null;

            if (!sc.hasNext())
                return null;
            mapLines.add(sc.nextLine().substring(1));
        }

        if (mapLines.isEmpty())
            return null;

        GameMap gm = new GameMap(mapLines.get(0).length(), mapLines.size());
        for (int i = 0; i < mapLines.get(0).length(); i++) {
            try {
                for (int j = 0; j < mapLines.size(); j++) {
                    gm.getCell(i, j).setCode(mapLines.get(j).charAt(i) - '0');
                }
            } catch (IndexOutOfBoundsException e) {
                return null;
            }
        }

        gs.map = gm;
        res.setData(gs);
        return res;
    }

    private static Packet getRegistrationPacket(String packet) {

        Scanner sc = new Scanner(packet);

        if (!sc.next().equals(Packet.PACKET_CODES.REGISTRATION_CMD))
            return null;
        Packet res = new Packet(Packet.PACKET_CODES.REGISTRATION_CMD);

        HashMap<String, String> data = new HashMap<String, String>();

        if (!sc.hasNext())
            return null;
        data.put(Packet.LOGIN_KEY, sc.next());

        if (!sc.hasNext())
            return null;
        data.put(Packet.PASSWORD_KEY, sc.next());

        res.setData(data);

        return res;
    }

    private static Packet getLoginPacket(String packet) {

        Scanner sc = new Scanner(packet);

        if (!sc.next().equals(Packet.PACKET_CODES.LOGIN_CMD))
            return null;
        Packet res = new Packet(Packet.PACKET_CODES.LOGIN_CMD);

        HashMap<String, String> data = new HashMap<String, String>();

        if (!sc.hasNext())
            return null;
        data.put(Packet.LOGIN_KEY, sc.next());

        if (!sc.hasNext())
            return null;
        data.put(Packet.PASSWORD_KEY, sc.next());

        res.setData(data);

        return res;
    }

    private static Packet getNewGamePacket(String packet) {

        Scanner sc = new Scanner(packet);

        if (!sc.next().equals(Packet.PACKET_CODES.NEW_GAME_CMD))
            return null;
        Packet res = new Packet(Packet.PACKET_CODES.NEW_GAME_CMD);

        GameSettings gs = new GameSettings();

        if (!sc.hasNextInt())
            return null;
        gs.maxCountOfPlayer = sc.nextInt();

        if (!sc.hasNextInt())
            return null;
        gs.XSize = sc.nextInt();

        if (!sc.hasNextInt())
            return null;
        gs.YSize = sc.nextInt();

        if (!sc.hasNextInt())
            return null;
        gs.turnTime = sc.nextInt();

        if (!sc.hasNextInt())
            return null;
        int f = sc.nextInt();

        if (f == 0)
            gs.extraTurn = false;
        else if (f == 1)
            gs.extraTurn = true;
        else
            return null;

        res.setData(gs);

        return res;
    }

    private static Packet getJoinPacket(String packet) {

        Scanner sc = new Scanner(packet);

        if (!sc.next().equals(Packet.PACKET_CODES.JOIN_TO_GAME_CMD))
            return null;
        Packet res = new Packet(Packet.PACKET_CODES.JOIN_TO_GAME_CMD);

        Integer ID;

        if (!sc.hasNextInt())
            return null;
        ID = new Integer(sc.nextInt());

        res.setData(ID);

        return res;
    }

    private static Packet getMakeTurnPacket(String packet) {

        Scanner sc = new Scanner(packet);

        if (!sc.next().equals(Packet.PACKET_CODES.MAKE_TURN_CMD)) {
            return null;
        }
        Packet res = new Packet(Packet.PACKET_CODES.MAKE_TURN_CMD);

        TurnCoordinate tc = new TurnCoordinate();

        try {
            tc.x = Integer.parseInt(sc.next());
        } catch (Exception e) {
            return null;
        }
        try {
            tc.y = Integer.parseInt(sc.next());
        } catch (Exception e) {
            return null;
        }

        res.setData(tc);
        return res;
    }

    public static String pack(Packet packet) {

        StringBuilder res = new StringBuilder();

        res.append(packet.getCode());

        if (packet.getData() != null) {
            if (packet.getCode().equals(Packet.PACKET_CODES.LOGIN_CMD)
                    || packet.getCode().equals(
                    Packet.PACKET_CODES.REGISTRATION_CMD)) {
                res.append(packLogRegPacket(packet));

/*            } else if (packet.getCode().equals(
                    Packet.PACKET_CODES.GAMES_LIST_CODE)) {
                res.append(packGamesListPacket(packet));
*/
            } else if (packet.getCode().equals(
                    Packet.PACKET_CODES.GAME_CREATED_CODE)) {
                res.append(packGameCreatedPacket(packet));

            } else if (packet.getCode()
                    .equals(Packet.PACKET_CODES.NEW_GAME_CMD)) {
                res.append(packNewGamePacket(packet));

            } else if (packet.getCode().equals(
                    Packet.PACKET_CODES.MAKE_TURN_CMD)) {
                res.append(packMakeTurnPacket(packet));

/*            } else if (packet.getCode().equals(
                    Packet.PACKET_CODES.CURRENT_STATE_CODE)) {
                res.append(packCurrentStatePacket(packet));
*/
            } else if (packet.getCode().equals(
                    Packet.PACKET_CODES.JOIN_TO_GAME_CMD)) {
                res.append(packJoinPacket(packet));
            }
        } else {
            res.append("\n");
        }

        res.append("\n\n");

        return res.toString();
    }

    private static String packLogRegPacket(Packet packet) {
        StringBuilder res = new StringBuilder();

        @SuppressWarnings("unchecked")
        HashMap<String, String> data = (HashMap<String, String>) packet
                .getData();
        res.append(" " + data.get(Packet.LOGIN_KEY));
        res.append(" " + data.get(Packet.PASSWORD_KEY));
        res.append("\n");

        return res.toString();
    }
/*
    private static String packGamesListPacket(Packet packet) {
        StringBuilder res = new StringBuilder();

        @SuppressWarnings("unchecked")
        ArrayList<GameLobby> data = (ArrayList<GameLobby>) packet.getData();

        res.append("\n");

        for (int i = 0; i < data.size(); i++) {
            GameLobby gl = data.get(i);

            res.append(Packet.PACKET_CODES.GAME_ID + " " + gl.ID + "\n");
            res.append(Packet.PACKET_CODES.GAME_PLAYER_MAX + " "
                    + gl.settings.maxCountOfPlayer + "\n");
            res.append(Packet.PACKET_CODES.GAME_PLAYER_CURRECT + " "
                    + gl.currentCountOfPlayer + "\n");
            res.append(Packet.PACKET_CODES.GAME_AREA_SIZE + " "
                    + gl.settings.XSize + " " + gl.settings.YSize + "\n");
            res.append(Packet.PACKET_CODES.GAME_TURN_TIMEOUT + " "
                    + gl.settings.turnTime + "\n");
            res.append(Packet.PACKET_CODES.GAME_EXTRA_TURN + " ");
            if (gl.settings.extraTurn) {
                res.append("1\n");
            } else {
                res.append("0\n");
            }
            res.append(Packet.PACKET_CODES.GAME_USERS_LIST + " ");
            for (int j = 0; j < gl.players.size(); j++) {
                res.append(gl.players.get(j));
                if (j != gl.players.size() - 1) {
                    res.append(" ");
                }
            }
            res.append("\n");

            if (i != data.size() - 1) {
                res.append("\n");
            }
        }

        return res.toString();
    }
*/
    private static String packGameCreatedPacket(Packet packet) {
        StringBuilder res = new StringBuilder();

        Integer ID = (Integer) packet.getData();
        res.append("\n");
        res.append(Packet.PACKET_CODES.GAME_ID + " " + ID + "\n");

        return res.toString();
    }

    private static String packNewGamePacket(Packet packet) {
        StringBuilder res = new StringBuilder();

        GameSettings gs = (GameSettings) packet.getData();

        res.append(" " + gs.maxCountOfPlayer);
        res.append(" " + gs.XSize);
        res.append(" " + gs.YSize);
        res.append(" " + gs.turnTime);
        if (gs.extraTurn) {
            res.append(" 1\n");
        } else {
            res.append(" 0\n");
        }

        return res.toString();
    }

    private static String packMakeTurnPacket(Packet packet) {
        StringBuilder res = new StringBuilder();

        TurnCoordinate tc = (TurnCoordinate) packet.getData();

        res.append(" " + tc.x);
        res.append(" " + tc.y);
        res.append("\n");

        return res.toString();
    }

/*    private static String packCurrentStatePacket(Packet packet) {

        StringBuilder sb = new StringBuilder();

        ServerGameState sgs = (ServerGameState) packet.getData();
        sb.append("\n");

        sb.append(Packet.PACKET_CODES.ACTIVE_USER + " " + sgs.activePlayer
                + "\n");

        sb.append(Packet.PACKET_CODES.GAME_USERS_LIST);
        for (PlayerState ps : sgs.playerStates) {
            sb.append(" " + ps.getNickname());
        }
        sb.append("\n");

        sb.append(Packet.PACKET_CODES.ACTIVE_USER_FLAGS);
        for (PlayerState ps : sgs.playerStates) {
            if (ps.isActive()) {
                sb.append(" 1");
            } else {
                sb.append(" 0");
            }
        }
        sb.append("\n");

        sb.append(Packet.PACKET_CODES.SCORE);
        for (PlayerState ps : sgs.playerStates) {
            sb.append(" " + ps.getScore());
        }
        sb.append("\n");

        for (int j = 0; j < sgs.map.getYSize(); j++) {
            sb.append(Packet.PACKET_CODES.GAME_AREA_LINE + " ");
            for (int i = 0; i < sgs.map.getXSize(); i++) {
                sb.append(sgs.map.getCell(i, j).getCode());
            }
            sb.append("\n");
        }

        return sb.toString();
    }
*/
    private static String packJoinPacket(Packet packet) {
        StringBuilder res = new StringBuilder();

        Integer ID = (Integer) packet.getData();
        res.append(" " + ID + "\n");

        return res.toString();
    }

    public static Packet readPacketFromStream(BufferedReader in)
            throws IOException {

        StringBuilder sb = new StringBuilder();
        String line;

        while (true) {

            System.out.println("WFT1");

            //line = new Scanner(Global.socket.getInputStream(),"UTF-8").nextLine();
            //line = IOUtils.toString(Global.socket.getInputStream(),"UTF-8");
            //line = CharStreams.toString(new InputStreamReader(Global.socket.getInputStream(), "UTF-8"));
            //line = Global.in.readLine();
            InputStream inputStream = Global.socket.getInputStream();
            byte[] data = new byte[100];
            int count = inputStream.read(data);
            System.out.println("countcountcountcountcountcountcountcountcountcount");
            System.out.println(count);
            System.out.println("countcountcountcountcountcountcountcountcountcount");
            line = in.readLine();
            //line = recv(Global.is);
            if (line.isEmpty()) {

                sb.append("\n");
                line = in.readLine();

                if (line.isEmpty()) {
                    sb.append("\n");
                    break;
                } else {
                    sb.append(line + "\n");
                }

            } else {
                sb.append(line + "\n");
            }
        }

        // XXX Read from socket
        System.out.println("== READ ==");
        System.out.println(sb.toString());
        System.out.println("== END ==");

        return unpack(sb.toString());
    }

    public static String recv(InputStream is) throws IOException{
        String buff = "";
        int c = is.read();
        while (c>=0 && c != '\n'){
            buff += (char)c;
            c = is.read();
        }
        return buff;
    }

}
