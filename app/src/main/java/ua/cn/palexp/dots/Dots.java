package ua.cn.palexp.dots;

/**
 * Created by pALEXp on 01.04.2015.
 */
public class Dots {

    public int x;
    public int y;
    public boolean free;
    public boolean zah;
    public int player;

    public Dots(int x, int y, boolean free, boolean zah, int player) {
        this.x = x;
        this.y = y;
        this.free = free;
        this.zah = zah;
        this.player = player;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public boolean isZah() {
        return zah;
    }

    public void setZah(boolean zah) {
        this.zah = zah;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer (int player) {
        this.player = player;
    }
}