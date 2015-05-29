package ua.cn.palexp.dots;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pALEXp on 28.05.2015.
 */
public class GameLobby {

    public Integer ID;

    public Integer currentCountOfPlayer;
    public List<String> players;

    public GameSettings settings;

    public GameLobby() {
        players = new ArrayList<String>();
        settings = new GameSettings();
        currentCountOfPlayer = Integer.valueOf(0);
        ID = Integer.valueOf(0);
    }

}
