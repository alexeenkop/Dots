package ua.cn.palexp.dots;

/**
 * Created by pALEXp on 28.05.2015.
 */
public class GameMap {

    public GameMapCell map[][];

    public GameMap(int x, int y) {

        map = new GameMapCell[x][y];
        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[i].length; j++) {
                map[i][j] = new GameMapCell();
            }
        }
    }

    public int getXSize() {
        return map.length;
    }
    public int getYSize() {
        if(map.length != 0)
            return map[0].length;
        else
            return 0;
    }

    public GameMapCell getCell(int x, int y) {
        return map[x][y];
    }
}
