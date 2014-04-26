package com.bopit.app;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import android.widget.RelativeLayout;

import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class GameActivity extends Activity implements SensorEventListener {
    Bundle extras;
    private int players = 0;
    private int actualPlayer = 0;
    private int playeds[] = {0,1,2,3,4,5};
    private int movementsNumber = 0;
    private boolean gameOver = false;
    private int lastPlayer = 0;
    private Random r;
    private ArrayList<Integer> movements;
    private int playersArray[] = {0,0,0,0};
    private int lastPlayed = 0;
    private float x1,x2;
    private int dificult;
    private RelativeLayout layout;
    private MediaPlayer mp;
    private SoundPool sound;
    static final int MIN_DISTANCE = 150;
    private HashMap<Integer,String> actionsMap;
    private int i;


    SensorManager sensorManager;
    Sensor accel;
    float rx, ry, rz, lrx, lry, lrz;
    boolean capture;
    Timer timer;
    TimerTask timerTask;

    //TextView tvx, tvy, tvz, tvm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        movements = new ArrayList<Integer>();
        sound = new SoundPool(6, AudioManager.STREAM_MUSIC, 0);


        r = new Random();
        dificult = 2;
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                completeTask();
            }
            private void completeTask() {
                try {
                    //se pasa a la pantalla de termino el juego si todos los jugadores
                    playersArray[actualPlayer] = 0;
                    for (int i=0;i<playersArray.length;i++){
                        if (playersArray[i]>0){
                            return;
                        }
                        play(movements,0);
                    }
                    //se pasa ala pantalla de termino el juego

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        };
        actionsMap = new HashMap<Integer, String>();
        actionsMap.put(0,"flip");
        actionsMap.put(1,"shake");
        actionsMap.put(2,"slide");
        actionsMap.put(3,"tap");
        actionsMap.put(4,"turn");
        actionsMap.put(5,"twist");
        i = 0;
        layout = (RelativeLayout) findViewById(R.id.back);

        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        extras = getIntent().getExtras();
        if(extras != null){
           players = extras.getInt("players");
        }
        for (int i = 0;i<players;i++){
            playersArray[i] = 1;
        }
        game();

        //setContentView(R.layout.activity_game);

        rx = ry = rz = lrx = lry = lrz = 0f;
        capture = true;

        /*tvx = (TextView)findViewById(R.id.textView);
        tvy = (TextView)findViewById(R.id.textView3);
        tvz = (TextView)findViewById(R.id.textView4);
        tvm = (TextView)findViewById(R.id.textView5);*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        //sensorManager.registerListener(this, rotation, 1500000);
        sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_UI);
        //sensorManager.registerListener(this, magnet, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(this);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        sensorManager.unregisterListener(this);
        super.onDestroy();
    }



    void processMotion(float x, float y, float z) {
        float mrx = x, mry = y, mrz = z;
        if(capture) {
            if(mrx > 15 && mry > 15 && mrz > 9) {
                Toast.makeText(this, "Fap", Toast.LENGTH_SHORT).show();
                //tvm.setText("FAP");
                capture = false;
                timer.cancel();
                if(movements.get(i) == 2){
                    i++;
                    play(movements,i);
                }else{
                    completeTask();
                }
            }
            if(mrx > 7 && mrz > 7 && mrx > mry && mrz > mry) {
                Toast.makeText(this, "Roll", Toast.LENGTH_SHORT).show();
                //tvm.setText("ROLL");
                capture = false;
                timer.cancel();
                if(movements.get(i) == 2){
                    i++;
                    play(movements,i);
                }else{
                    completeTask();
                }
            }
            if(mrx > 9 && mry > 9 && mrz < 12 && mrx > mrz && mry > mrz) {
                Toast.makeText(this, "Twist", Toast.LENGTH_SHORT).show();
                //tvm.setText("TWIST");
                capture = false;
                timer.cancel();
                if(movements.get(i) == 2){
                    i++;
                    play(movements,i);
                }else{
                    completeTask();
                }
            }
            if(mry > 9 && mrz > 9 && mry > mrx && mrz > mrx) {
                Toast.makeText(this, "Flip", Toast.LENGTH_SHORT).show();
                //tvm.setText("FLIP");
                capture = false;
                timer.cancel();
                if(movements.get(i) == 2){
                    i++;
                    play(movements,i);
                }else{
                    completeTask();
                }
            }

            if (movements.size()<= i){
                i = 0;
                game();
            }

        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        rx = event.values[0];
        ry = event.values[1];
        rz = event.values[2];

        if(rx > lrx)
            lrx = rx;
        if(ry > lry)
            lry = ry;
        if(rz > lrz)
            lrz = rz;
        /*tvx.setText(lrx + "");
        tvy.setText(lry + "");
        tvz.setText(lrz + "");*/
        if(rx < lrx && ry < lry && rz < lrz) {
            processMotion(lrx, lry, lrz);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        Log.i("touch", event.getX() + " " + event.getY());
        lrx = 0;
        lry = 0;
        lrz = 0;
        capture = true;
        //return true;
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                //Toast.makeText(this, "x1: "+x1, Toast.LENGTH_SHORT).show ();

                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                //Toast.makeText(this, "x2: "+x2, Toast.LENGTH_SHORT).show ();
                float deltaX = x2 - x1;
                //Toast.makeText(this, "delta: "+deltaX, Toast.LENGTH_SHORT).show ();
                if (deltaX > MIN_DISTANCE)
                {
                    Toast.makeText(this, "left2right swipe", Toast.LENGTH_SHORT).show ();
                    timer.cancel();
                    if(movements.get(i) == 2){
                        i++;
                        play(movements,i);
                    }else{
                        completeTask();
                    }
                }
                else if (deltaX < -MIN_DISTANCE)
                {
                    // consider as something else - a screen tap for example
                    Toast.makeText(this, "right2left swipe", Toast.LENGTH_SHORT).show ();
                    timer.cancel();
                    if(movements.get(i) == 2){
                        i++;
                        play(movements,i);
                    }else{
                        completeTask();
                    }
                }
                else{
                    Toast.makeText(this, "tap", Toast.LENGTH_SHORT).show ();
                    timer.cancel();

                    if(movements.get(i) == 3){
                        i++;
                        play(movements,i);
                    }else{
                        completeTask();
                    }

                }
                break;
        }
        if (movements.size()<= i){
            i = 0;
            game();
        }
        return super.onTouchEvent(event);
    }

    public void play(ArrayList movements,int i){

        if(timer ==null) {
            timer = new Timer(true);
            timer.scheduleAtFixedRate(timerTask, 0, dificult * 1000);
        }

        capture = true;
        display((Integer) movements.get(i));

    }
    private void display(int move){
        switch (move){
            case 0:
                layout.setBackgroundResource(R.drawable.flip);
                //sound.playShortResource(R.raw.sflip);
                //mp = MediaPlayer.create(this, R.raw.sflip);
                //mp.start();
                //sound.play(R.raw.sflip, 0.99f, 0.99f, 0, 0, 1);
                break;
            case 1:
                layout.setBackgroundResource(R.drawable.shake);
                //sound.playShortResource(R.raw.sflip);
                //mp = MediaPlayer.create(this, R.raw.shake);
                //mp.start();
                //sound.play(R.raw.sflip, 0.99f, 0.99f, 0, 0, 1);
                break;
            case 2:
                layout.setBackgroundResource(R.drawable.slide);
                //sound.playShortResource(R.raw.sflip);
                //mp = MediaPlayer.create(this, R.raw.sswipe);
                //mp.start();
                //sound.play(R.raw.sflip, 0.99f, 0.99f, 0, 0, 1);
                break;
            case 3:
                layout.setBackgroundResource(R.drawable.tap);
                //sound.playShortResource(R.raw.sflip);
                //mp = MediaPlayer.create(this, R.raw.tap);
                //mp.start();
                //sound.play(R.raw.sflip, 0.99f, 0.99f, 0, 0, 1);
                break;
            case 4:
                layout.setBackgroundResource(R.drawable.turn);
                //sound.playShortResource(R.raw.sflip);
                //mp = MediaPlayer.create(this, R.raw.turn);
                //mp.start();
                //sound.play(R.raw.sflip, 0.99f, 0.99f, 0, 0, 1);
                break;
            case 5:
                layout.setBackgroundResource(R.drawable.twist);
                //sound.playShortResource(R.raw.sflip);
                //mp = MediaPlayer.create(this, R.raw.twist);
                //mp.start();
                //sound.play(R.raw.sflip, 0.99f, 0.99f, 0, 0, 1);
                break;
        }


    }
    private void completeTask() {
        try {
            //se pasa a la pantalla de termino el juego si todos los jugadores
            playersArray[actualPlayer] = 0;
            for (int i=0;i<playersArray.length;i++){
                if (playersArray[i]>0){
                    return;
                }

                i = 0;
                play(movements,0);

            }
            //se pasa ala pantalla de termino el juego

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void game(){
        Log.w("swag","swag");
        if (!gameOver) {
            actualPlayer = r.nextInt(0 + players);
            if (players>1){
                while (actualPlayer == lastPlayer){
                    actualPlayer = r.nextInt(0 + players);
                }
            }
            while(playersArray[actualPlayer] == 0 && playersArray.length>1){
                actualPlayer = r.nextInt(1 + players+1);
            }

            movementsNumber = r.nextInt(3 + 9);

            for (int i = 0;i<movementsNumber;i++){
                movements.add(playeds[r.nextInt(0+playeds.length)]);
            }

            play(movements,0);
            //i = 0;
           // movements.clear();

            lastPlayer  = actualPlayer;
        }
    }

}
