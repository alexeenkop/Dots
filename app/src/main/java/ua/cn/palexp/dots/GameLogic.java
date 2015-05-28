package ua.cn.palexp.dots;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
    private int[] zDots = null;
    private Dots[][] dots = null;
    private Dots[] cepochka = null;
    private boolean volnakray;
    private boolean fin;
    private boolean isLock=false;
    private int gWave=0;

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
        zDots = new int[COUNT_OF_PLAYERS+1];
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
            t.setText("Player1\t" + zDots[0]);
            t1.setPadding(0,0,0,0);
            t1.setText("Player2\t" + zDots[1]);
            t2.setPadding(0,0,0,0);
            t2.setText("Player3\t" + zDots[2]);
        } else if (currentPlayer == 1) {
            t.setPadding(0,0,0,0);
            t.setText("Player1\t" + zDots[0]);
            t1.setPadding(20,0,0,0);
            t1.setText("Player2\t" + zDots[1]);
            t2.setPadding(0,0,0,0);
            t2.setText("Player3\t" + zDots[2]);
        } else if (currentPlayer == 2) {
            t.setPadding(0,0,0,0);
            t.setText("Player1\t" + zDots[0]);
            t1.setPadding(0,0,0,0);
            t1.setText("Player2\t" + zDots[1]);
            t2.setPadding(20,0,0,0);
            t2.setText("Player3\t" + zDots[2]);
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
        volna(cellX, cellY, 0);
        if (fin) {
            //zahvDots();
            int[] masX = new int[gWave];
            int[] masY = new int[gWave];
            for (int i=0; i<gWave; i++) {
                masX[i] = cepochka[i].x;
                masY[i] = cepochka[i].y;
            }
            view.drawKontur(masX,masY,gWave,colors[currentPlayer]);
            Toast.makeText(activity,"okr",Toast.LENGTH_SHORT).show();
        }


        if (currentPlayer == 2) {
            currentPlayer = 0;
        } else {
            currentPlayer++;
        }

        lightP(currentPlayer);

        if (isEndGame()) {
            int max = 0;
            int pl = -1;
            for (int j=0;j<zDots.length;j++){
                if (zDots[j]>max){
                    max = zDots[j];
                    pl = j;
                }
            }
            endGame(pl);
        }

    }

    private void endGame(int pl) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Игра оконченна!")
                .setMessage("Выиграл игрок "+pl+"\nЗахватил: "+zDots[pl]+"точек")
                .setCancelable(false)
                .setNegativeButton("ОК",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private boolean isEndGame() {
        for(int x=0; x<BOARD_WIDTH; x++){
            for(int y=0; y<BOARD_HEIGHT; y++){
                if (dots[x][y].free){
                    return false;
                }
            }
        }
        return true;
    }

//    private void zahvDots(int x, int y) {
//        if (x<0 || x>=BOARD_WIDTH || y<0 || y>=BOARD_HEIGHT) {
//            volnakray = true;
//            return;
//        } else {
//            volnakray = false;
//        }
//    }

    public void volna (int x, int y, int wave){

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
                        gWave = wave;
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

        volna(x-1,y,wave+1);
        volna(x-1,y-1,wave+1);
        volna(x,y-1,wave+1);
        volna(x+1,y-1,wave+1);
        volna(x+1,y,wave+1);
        volna(x+1,y+1,wave+1);
        volna(x,y+1,wave+1);
        volna(x-1,y+1,wave+1);

    }

}
