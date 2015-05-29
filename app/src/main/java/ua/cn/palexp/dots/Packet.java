package ua.cn.palexp.dots;

/**
 * Created by pALEXp on 28.05.2015.
 */
public class Packet {

    public static class PACKET_CODES {
        /* "Server to client" commands and codes */
        public static final String SUCCESS_CODE          = "200";
        public static final String PLAYER_STATISTIC_CODE = "201";
        public static final String GAMES_LIST_CODE       = "202";
        public static final String GAME_CREATED_CODE     = "203";
        public static final String CURRENT_STATE_CODE    = "204";

        public static final String USER_ALREADY_EXIST_CODE  = "400";
        public static final String INCORRECT_COMMAND_CODE   = "401";
        public static final String SERVER_ERROR_CODE        = "402";
        public static final String LOGIN_ERROR_CODE         = "403";
        public static final String USER_NOT_SINGING_UP_CODE = "404";
        public static final String GAME_NOT_EXIST_CODE      = "405";
        public static final String GAME_IS_FULL_CODE        = "406";
        public static final String CANNOT_LEAVE_GAME_CODE   = "407";
        public static final String INCORRECT_TURN_CODE      = "408";

        public static final String GAME_STATE_CHANGE_CMD = "GSC";

        /* "Client to server" commands and codes */
        public static final String REGISTRATION_CMD       = "REG";
        public static final String LOGIN_CMD              = "LOG";
        public static final String LOGOUT_CMD             = "LGT";
        public static final String GET_USER_STATISTIC_CMD = "UST";
        public static final String GET_GAME_LIST_CMD      = "GLS";
        public static final String NEW_GAME_CMD           = "NEW";
        public static final String JOIN_TO_GAME_CMD       = "JOI";
        public static final String LEAVE_GAME_CMD         = "FIN";
        public static final String MAKE_TURN_CMD          = "TRN";
        public static final String FINISH_GAME_CMD        = "SRD";
        public static final String UPDATE_CMD             = "UPD";

        /* Packet's commands */
        public static final String NUM_PLAYED_GAME = "NPG";
        public static final String NUM_WIN_GAME = "NWG";
        public static final String NUM_LOSE_GAME = "NLG";
        public static final String NUM_DRAW_GAME = "NDG";
        public static final String GAME_ID = "GID";
        public static final String GAME_PLAYER_MAX = "GPM";
        public static final String GAME_PLAYER_CURRECT = "GPC";
        public static final String GAME_AREA_SIZE = "GAS";
        public static final String GAME_TURN_TIMEOUT = "GTT";
        public static final String GAME_EXTRA_TURN = "GET";
        public static final String GAME_USERS_LIST = "GUL";

        public static final String ACTIVE_USER = "ACU";
        public static final String ACTIVE_USER_FLAGS = "AUF";
        public static final String SCORE = "SCR";
        public static final String GAME_AREA_LINE = "GAL";
    }

    public static final String LOGIN_KEY = "Login";
    public static final String PASSWORD_KEY = "Password";

    private static final Packet SUCCESS_PACKET = new Packet(PACKET_CODES.SUCCESS_CODE);

    private static final Packet ERROR_400_PACKET = new Packet(PACKET_CODES.USER_ALREADY_EXIST_CODE);
    private static final Packet ERROR_401_PACKET = new Packet(PACKET_CODES.INCORRECT_COMMAND_CODE);
    private static final Packet ERROR_402_PACKET = new Packet(PACKET_CODES.SERVER_ERROR_CODE);
    private static final Packet ERROR_403_PACKET = new Packet(PACKET_CODES.LOGIN_ERROR_CODE);
    private static final Packet ERROR_404_PACKET = new Packet(PACKET_CODES.USER_NOT_SINGING_UP_CODE);
    private static final Packet ERROR_405_PACKET = new Packet(PACKET_CODES.GAME_NOT_EXIST_CODE);
    private static final Packet ERROR_406_PACKET = new Packet(PACKET_CODES.GAME_IS_FULL_CODE);
    private static final Packet ERROR_407_PACKET = new Packet(PACKET_CODES.CANNOT_LEAVE_GAME_CODE);
    private static final Packet ERROR_408_PACKET = new Packet(PACKET_CODES.INCORRECT_TURN_CODE);

    private static final Packet LOGOUT_PACKET = new Packet(PACKET_CODES.LOGOUT_CMD);
    private static final Packet STATISTIC_PACKET = new Packet(PACKET_CODES.GET_USER_STATISTIC_CMD);
    private static final Packet GAME_LIST_PACKET = new Packet(PACKET_CODES.GET_GAME_LIST_CMD);
    private static final Packet LEAVE_GAME_PACKET = new Packet(PACKET_CODES.LEAVE_GAME_CMD);
    private static final Packet FINISH_GAME_PACKET = new Packet(PACKET_CODES.FINISH_GAME_CMD);
    private static final Packet UPDATE_PACKET = new Packet(PACKET_CODES.UPDATE_CMD);

    private static final Packet GAME_STATE_CHANGE_PACKET = new Packet(PACKET_CODES.GAME_STATE_CHANGE_CMD);


    private String code;
    private Object data;

    public Packet(String code) {
        this.code = code;
        this.data = null;
    }

    public String getCode() {
        return code;
    }
    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static Packet getSuccessPacket() {
        return SUCCESS_PACKET;
    }
    public static Packet getUserAlreadyExistPacket() {
        return ERROR_400_PACKET;
    }
    public static Packet getIncorrectCommandPacket() {
        return ERROR_401_PACKET;
    }
    public static Packet getServerErrorPacket() {
        return ERROR_402_PACKET;
    }
    public static Packet getLoginErrorPacket() {
        return ERROR_403_PACKET;
    }
    public static Packet getUserNotSingingUpPacket() {
        return ERROR_404_PACKET;
    }
    public static Packet getGameNotExistPacket() {
        return ERROR_405_PACKET;
    }
    public static Packet getGameIsFullPacket() {
        return ERROR_406_PACKET;
    }
    public static Packet getCannotLeaveGamePacket() {
        return ERROR_407_PACKET;
    }
    public static Packet getIncorrectTurnPacket() {
        return ERROR_408_PACKET;
    }
    public static Packet getLogoutPacket() {
        return LOGOUT_PACKET;
    }
    public static Packet getStatisticPacket() {
        return STATISTIC_PACKET;
    }
    public static Packet getGameListPacket() {
        return GAME_LIST_PACKET;
    }
    public static Packet getLeaveGamePacket() {
        return LEAVE_GAME_PACKET;
    }
    public static Packet getFinishGamePacket() {
        return FINISH_GAME_PACKET;
    }
    public static Packet getGameStateChangePacket() {
        return GAME_STATE_CHANGE_PACKET;
    }
    public static Packet getUpdatePacket() {
        return UPDATE_PACKET;
    }

}

