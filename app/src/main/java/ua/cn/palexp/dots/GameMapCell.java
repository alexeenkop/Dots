package ua.cn.palexp.dots;

/**
 * Created by pALEXp on 28.05.2015.
 */
public class GameMapCell {

    public static final int MAX_PLAYER_NUM = 3;

    public static final boolean SURROUNDED_OPT = false;
    public static final boolean FREE_OPT = true;

    public static final int WITHOUT_OWNER_OPT = 0;

    public boolean status;
    public int owner;

    public GameMapCell(int code) {
        this.setCode(code);
    }

    public GameMapCell(int owner, boolean status) {
        this.owner = owner;
        this.status = status;
    }
    public GameMapCell() {
        this.owner = WITHOUT_OWNER_OPT;
        this.status = FREE_OPT;
    }

    public int getCode() {

        int res = owner;
        if(!status) {
            if(owner == WITHOUT_OWNER_OPT)
                res = MAX_PLAYER_NUM*2 + 1;
            else
                res += MAX_PLAYER_NUM;
        }

        return res;
    }

    public void setInfo(int owner, boolean status) {
        this.owner = owner;
        this.status = status;
    }
    public void setCode(int code) {

        if(code <= MAX_PLAYER_NUM) {
            this.owner = code;
            this.status = FREE_OPT;

        } else if(code <= MAX_PLAYER_NUM*2) {
            this.owner = code - MAX_PLAYER_NUM;
            this.status = SURROUNDED_OPT;

        } else if(code == MAX_PLAYER_NUM*2 + 1) {
            this.owner = WITHOUT_OWNER_OPT;
            this.status = SURROUNDED_OPT;

        } else {
            this.owner = WITHOUT_OWNER_OPT;
            this.status = FREE_OPT;
        }
    }
}
