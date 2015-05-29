package ua.cn.palexp.dots;

/**
 * Created by pALEXp on 28.05.2015.
 */
public class TurnCoordinate {

    public int x;
    public int y;

    public TurnCoordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public TurnCoordinate() {
        this(0, 0);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        TurnCoordinate tc = (TurnCoordinate) obj;
        return x == tc.x && y == tc.y;
    }

}
