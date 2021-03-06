package ua.cn.palexp.dots;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by pALEXp on 19.05.2015.
 */

public class GameView extends View {
    private final int horizontalCountOfCells, verticalCountOfCells;
    private final int viewSizeH, viewSizeW;
    private final ScaleGestureDetector scaleGestureDetector;
    private final GestureDetector detector;
    private float canvasSizeH, canvasSizeW;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Paint mBitmapPaint, paint, paint1;
    private float mScaleFactor;
    private GameLogic logic;
    private GameActivity activity;
    private boolean isLock=false;


    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //размер игрового поля
        horizontalCountOfCells =26;
        verticalCountOfCells =36;
        //в xml разметке позднее пропишем размер вьюхи равный 300dp
        viewSizeH=(int)convertDpToPixel(420, context);
        viewSizeW=(int)convertDpToPixel(310, context);
        mScaleFactor=1f;//значение зума по умолчанию
        canvasSizeH=(int)(viewSizeH*mScaleFactor);//определяем размер канваса
        canvasSizeW=(int)(viewSizeW*mScaleFactor);//определяем размер канваса
        mBitmap = Bitmap.createBitmap((int) canvasSizeW, (int) canvasSizeH, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mBitmapPaint = new Paint(Paint.DITHER_FLAG);

        //определяем параметры кисти, которой будем рисовать сетку и атомы
        paint =new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(0xff000000);
        paint.setStrokeWidth(1f);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);

        paint1 =new Paint();
        paint1.setAntiAlias(true);
        paint1.setDither(true);
        paint1.setColor(0xff000000);
        paint1.setStrokeWidth(1f);
        paint1.setStyle(Paint.Style.FILL);

        //рисуем сетку
        for(int x=0;x< horizontalCountOfCells +1;x++)
            mCanvas.drawLine((float)x* canvasSizeW / horizontalCountOfCells, 0, (float)x* canvasSizeW / horizontalCountOfCells, canvasSizeH, paint);
        for(int y=0;y< verticalCountOfCells +1;y++)
            mCanvas.drawLine(0, (float)y* canvasSizeH / verticalCountOfCells, canvasSizeW, (float)y* canvasSizeH / verticalCountOfCells, paint);

        scaleGestureDetector=new ScaleGestureDetector(context, new MyScaleGestureListener());
        detector=new GestureDetector(context, new MyGestureListener());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.scale(mScaleFactor, mScaleFactor);//зумируем канвас
        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        canvas.restore();
    }

    public boolean isLock() {
        return isLock;
    }

    public void lock(){
        isLock=true;
    }

    public void unlock(){
        isLock=false;
    }

    //переводим dp в пиксели
    public float convertDpToPixel(float dp,Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp * (metrics.densityDpi/160f);
    }

    //в случае касания пальем передаем управление MyScaleGestureListener
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        detector.onTouchEvent(event);
        scaleGestureDetector.onTouchEvent(event);
        return true;
    }

    public void drawDots(int cellX, int cellY, int color) {
        //считаем координаты центра ячейки
        float x0 = ((1f/(horizontalCountOfCells)) * viewSizeW + (1f / (horizontalCountOfCells)) * cellX * viewSizeW);
        float y0 = ((1f/(verticalCountOfCells)) * viewSizeH + (1f / (verticalCountOfCells)) * cellY * viewSizeH);
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        mCanvas.drawCircle(x0, y0, 3, paint);
        invalidate();//перерисовываем канвас
    }

    public void drawKontur(int[] x, int[] y, int cnt, int color) {
        float[] x0 = new float[cnt];
        float[] y0 = new float[cnt];
        for (int i=0; i<cnt; i++){
            x0[i] = ((1f/(horizontalCountOfCells)) * viewSizeW + (1f / (horizontalCountOfCells)) * x[i] * viewSizeW);
            y0[i] = ((1f/(verticalCountOfCells)) * viewSizeH + (1f / (verticalCountOfCells)) * y[i] * viewSizeH);
        }
        paint1.setColor(color);
        paint1.setAlpha(150);
        paint1.setStyle(Paint.Style.FILL);

        Path wallpath = new Path();
        wallpath.reset(); // only needed when reusing this path for a new build
        wallpath.moveTo(x0[0], y0[0]); // used for first point
        for (int i=1; i<cnt; i++){
            wallpath.lineTo(x0[i], y0[i]);
        }
        wallpath.lineTo(x0[0], y0[0]); // there is a setLastPoint action but i found it not to work as expected
        mCanvas.drawPath(wallpath, paint1);
        invalidate();//перерисовываем канвас
    }

    //унаследовались от ScaleGestureDetector.SimpleOnScaleGestureListener, чтобы не писать пустую реализацию ненужных
    //методов интерфейса OnScaleGestureListener
    private class MyScaleGestureListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        //обрабатываем "щипок" пальцами
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            float scaleFactor = scaleGestureDetector.getScaleFactor();//получаем значение зума относительно предыдущего состояния
            //получаем координаты фокальной точки - точки между пальцами
            float focusX = scaleGestureDetector.getFocusX();
            float focusY = scaleGestureDetector.getFocusY();
            //следим чтобы канвас не уменьшили меньше исходного размера и не допускаем увеличения больше чем в 2 раза
            if (mScaleFactor * scaleFactor > 1 && mScaleFactor * scaleFactor < 4) {
                mScaleFactor *= scaleGestureDetector.getScaleFactor();
                canvasSizeH = (int) (viewSizeH * mScaleFactor);//изменяем хранимое в памяти значение размера канваса
                canvasSizeW = (int) (viewSizeW * mScaleFactor);//изменяем хранимое в памяти значение размера канваса
                //используется при расчетах
                //по умолчанию после зума канвас отскролит в левый верхний угол.
                //Скролим канвас так, чтобы на экране оставалась
                //область канваса, над которой был жест зума
                //Для получения данной формулы достаточно школьных знаний математики (декартовы координаты).
                int scrollX = (int) ((getScrollX() + focusX) * scaleFactor - focusX);
                scrollX = Math.min(Math.max(scrollX, 0), (int) canvasSizeW - viewSizeW);
                int scrollY = (int) ((getScrollY() + focusY) * scaleFactor - focusY);
                scrollY = Math.min(Math.max(scrollY, 0), (int) canvasSizeH - viewSizeH);
                scrollTo(scrollX, scrollY);
            }
            //вызываем перерисовку принудительно
            invalidate();
            return true;
        }

    }

    //унаследовались от GestureDetector.SimpleOnGestureListener, чтобы не писать пустую
    //реализацию ненужных методов интерфейса OnGestureListener
    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener
    {
        //обрабатываем скролл (перемещение пальца по экрану)
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
        {
            //не даем канвасу показать края по горизонтали
            if(getScrollX()+distanceX< canvasSizeW -viewSizeW && getScrollX()+distanceX>0){
                scrollBy((int)distanceX, 0);
            }
            //не даем канвасу показать края по вертикали
            if(getScrollY()+distanceY< canvasSizeH -viewSizeH && getScrollY()+distanceY>0){
                scrollBy(0, (int)distanceY);
            }
            return true;
        }

        //обрабатываем одиночный тап
        @Override
        public boolean onSingleTapConfirmed(MotionEvent event){
            if(isLock)return true;
            //получаем координаты ячейки, по которой тапнули
            int cellX=(int)((event.getX()+getScrollX())/mScaleFactor);
            int cellY=(int)((event.getY()+getScrollY())/mScaleFactor);
            if (Global.mode){
                makeTurn((int)((horizontalCountOfCells-1) *cellX/viewSizeW), (int)((verticalCountOfCells-1) *cellY/viewSizeH));
            } else {
            logic.addDots((int)((horizontalCountOfCells-1) *cellX/viewSizeW), (int)((verticalCountOfCells-1) *cellY/viewSizeH));
            }
           // drawDots((int)((horizontalCountOfCells-1) *cellX/viewSizeW), (int)((verticalCountOfCells-1) *cellY/viewSizeH), Color.RED);
            return true;
        }

        //обрабатываем двойной тап
        @Override
        public boolean onDoubleTapEvent(MotionEvent event){
            //зумируем канвас к первоначальному виду
            mScaleFactor=1f;
            canvasSizeH =viewSizeH;
            canvasSizeW =viewSizeW;
            scrollTo(0, 0);//скролим, чтобы не было видно краев канваса.
            invalidate();//перерисовываем канвас
            return true;
        }
    }

    private void makeTurn(int x, int y) {
        Packet packet = new Packet(Packet.PACKET_CODES.MAKE_TURN_CMD);
        packet.setData(new TurnCoordinate(x,y));
        try {
            sendToServer(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendToServer(Packet packet) throws IOException {

        if(packet != null) {
            String srt = PacketManager.pack(packet);

            //XXX Send from ClientFrame
            System.out.println("== SEND ==");
            System.out.println(srt);
            System.out.println("== END ==");

            byte[] buf = srt.getBytes("UTF-8");
            Global.out.write(buf, 0, buf.length);
        }
    }

    public void setLogic(GameLogic logic) {
        this.logic = logic;
    }

    public void gameUpdateThread (){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Global.stopFlag = false;
                try {
                    while (!Global.stopFlag) {
                        if (Global.in.ready()){
                            Packet packet = PacketManager.readPacketFromStream(Global.in);

                            if(packet.getCode().equals(Packet.PACKET_CODES.GAME_STATE_CHANGE_CMD)) {
                                sendToServer(Packet.getUpdatePacket());
                            } else if(packet.getCode().equals(Packet.PACKET_CODES.CURRENT_STATE_CODE)) {
                                GameState gs = (GameState) packet.getData();
                                Global.ggs = gs;
                                for (int i=0;i<25;i++){
                                    for (int j=0;j<35;i++){
                                        if (gs.map.getCell(i,j).owner >= 4 && gs.map.getCell(i,j).owner <=7){
                                            logic.dots[i][j].zah = true;
                                            logic.dots[i][j].free = false;
                                        }
                                    }
                                }for (int i=0;i<25;i++){
                                    for (int j=0;j<35;i++){
                                        if (gs.map.getCell(i,j).status != logic.dots[i][j].free){
                                            logic.addDots(i,j);
                                        }
                                    }
                                }
                            } else if(packet.getCode().equals(Packet.PACKET_CODES.SUCCESS_CODE)) {
                                //Toast.makeText(this,"Success",Toast.LENGTH_SHORT);
                                System.out.println("Success");

                            } else if(packet.getCode().equals(Packet.PACKET_CODES.INCORRECT_TURN_CODE)) {
                                //gamePanel.setMessage("Incorrect turn.");
                                System.out.println("Incorrect turn.");
                            } else {
                                //warning("Unknow request.");
                                System.out.println("Unknow request.");
                            }

                        } else {
                            Thread.sleep(100);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        thread.start();
    }
}
