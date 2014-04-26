package com.bopit.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
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
    static final int MIN_DISTANCE = 150;
    private HashMap<Integer,String> actionsMap;
    private int i;


    SensorManager sensorManager;
    Sensor accel;
    Timer timer;
    TimerTask timerTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        movements = new ArrayList<Integer>();
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
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        extras = getIntent().getExtras();
        if(extras != null){
           players = extras.getInt("players");
        }
        for (int i = 0;i<players;i++){
            playersArray[i] = 1;
        }
        game();



    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_GAME);
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

    @Override
    public void onSensorChanged(SensorEvent event) {
        //Log.i("accel", event.values[0] + " " + event.values[1] + " " + event.values[2]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
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

        display((Integer) movements.get(i));

    }
    private void display(int move){
        switch (move){
            case 0:
                layout.setBackgroundResource(R.drawable.flip);
                break;
            case 1:
                layout.setBackgroundResource(R.drawable.shake);
                break;
            case 2:
                layout.setBackgroundResource(R.drawable.slide);
                break;
            case 3:
                layout.setBackgroundResource(R.drawable.tap);
                break;
            case 4:
                layout.setBackgroundResource(R.drawable.turn);
                break;
            case 5:
                layout.setBackgroundResource(R.drawable.twist);
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
