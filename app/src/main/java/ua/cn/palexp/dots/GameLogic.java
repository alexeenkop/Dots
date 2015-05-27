package ua.cn.palexp.dots;

import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by pALEXp on 22.05.2015.
 */
public class GameLogic {

    private final GameView view;
    private final GameActivity activity;
    private final int[] colors={0xffff0000, 0xff00ff00, 0xff0000ff};//цвета точек
    private final int COUNT_OF_PLAYERS, BOARD_WIDTH, BOARD_HEIGHT;
    private int currentPlayer=0;
    private Dots[][] dots = null;
    private Dots [] cepochka = null;
    private boolean volnakray;
    private boolean fin;
    private boolean isLock=false;

    public GameLogic(GameView view, GameActivity activity) {
        this.view = view;
        this.activity=activity;
        //инициализируем игровые параметры (количество игроков, размер доски)
        this.COUNT_OF_PLAYERS=2;
        this.BOARD_HEIGHT=35;
        this.BOARD_WIDTH=25;
        dots=new Dots[BOARD_WIDTH][BOARD_HEIGHT];
        for(int x=0; x<BOARD_WIDTH; x++){
            for(int y=0; y<BOARD_HEIGHT; y++){
                dots[x][y] = new Dots(x,y,true,false,-1);
            }
        }
        initCep();
        lightP(currentPlayer);
    }

    private void initCep() {
        cepochka = new Dots[150];
        for (int x1=0; x1<150; x1++){
            cepochka[x1] = new Dots(-1,-1);
        }
    }

    public void lightP (int currentPlayer) {
        TextView t=new TextView(this.activity);
        t=(TextView)activity.findViewById(R.id.textView);
        TextView t1=new TextView(this.activity);
        t1=(TextView)activity.findViewById(R.id.textView2);
        TextView t2=new TextView(this.activity);
        t2=(TextView)activity.findViewById(R.id.textView3);
        if (currentPlayer == 0) {
            t.setPadding(20,0,0,0);
            t1.setPadding(0,0,0,0);
            t2.setPadding(0,0,0,0);
        } else if (currentPlayer == 1) {
            t.setPadding(0,0,0,0);
            t1.setPadding(20,0,0,0);
            t2.setPadding(0,0,0,0);
        } else if (currentPlayer == 2) {
            t.setPadding(0,0,0,0);
            t1.setPadding(0,0,0,0);
            t2.setPadding(20,0,0,0);
        } else return;
    }

    //вызывается из вьюхи по одиночному тапу
    public void addDots(int cellX, int cellY) {

        if (!dots[cellX][cellY].isFree()) {
            return;
        }

        dots[cellX][cellY].free = false;
        dots[cellX][cellY].player = currentPlayer;

        view.drawDots(cellX, cellY, colors[currentPlayer]);

        initCep();
        volnakray = false;
        fin = false;
        volna(cellX, cellY, currentPlayer, 0);
        if (fin) {
            Toast.makeText(activity,"okr",Toast.LENGTH_SHORT).show();
        }
        

        if (currentPlayer == 2) {
            currentPlayer = 0;
        } else {
            currentPlayer++;
        }

        lightP(currentPlayer);

    }

    public void volna (int x, int y, int player, int wave){

        if (fin) {
            return;
        }

        if (x<0 || x>=BOARD_WIDTH || y<0 || y>=BOARD_HEIGHT) {
            volnakray = true;
            return;
        } else {
            volnakray = false;
        }

        if (volnakray) {
            return;
        }

        if (wave == 0) {
            cepochka[wave] = new Dots(x,y);
        } else {
            if (dots[x][y].player == currentPlayer && !dots[x][y].isZah()) {
                if (wave>3) {
                    cepochka[wave] = new Dots(x, y);
                    if (cepochka[wave].x == cepochka[0].x && cepochka[wave].y == cepochka[0].y) {
                        fin = true;
                        return;
                    }
                }
                for (int i=0; i<wave; i++){
                    if (cepochka[i].x != x || cepochka[i].y != y) { //cepochka[wave - 1].x != x && cepochka[wave - 1].y != y
                    } else return;
                }
                cepochka[wave] = new Dots(x, y);
            } else {
                return;
            }
        }

        volna(x-1,y,player,wave+1);
        volna(x-1,y-1,player,wave+1);
        volna(x,y-1,player,wave+1);
        volna(x+1,y-1,player,wave+1);
        volna(x+1,y,player,wave+1);
        volna(x+1,y+1,player,wave+1);
        volna(x,y+1,player,wave+1);
        volna(x-1,y+1,player,wave+1);

    }

}
