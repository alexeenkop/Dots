package ua.cn.palexp.dots;

import android.widget.TextView;

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
        lightP(currentPlayer);
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

        view.drawDots(cellX,cellY,colors[currentPlayer]);

        if (currentPlayer == 2) {
            currentPlayer = 0;
        } else {
            currentPlayer++;
        }

        lightP(currentPlayer);

        isZamk(cellX,cellY);

    }

    public boolean isZamk (int x, int y){

        return  true;
    }

}
