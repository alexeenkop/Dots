package ua.cn.palexp.dots;

/**
 * Created by pALEXp on 28.05.2015.
 */
public class GameSettings {

    public Integer maxCountOfPlayer;

    public Integer XSize;
    public Integer YSize;

    public Integer turnTime;
    public Boolean extraTurn;

    public GameSettings() {

        maxCountOfPlayer = Integer.valueOf(0);

        XSize = Integer.valueOf(0);
        YSize = Integer.valueOf(0);

        turnTime = Integer.valueOf(0);
        extraTurn = Boolean.valueOf(false);
    }

}
