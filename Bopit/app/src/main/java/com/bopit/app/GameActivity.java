package com.bopit.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
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
import android.widget.Toast;

import java.util.ArrayList;
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
    static final int MIN_DISTANCE = 150;
    SensorManager sensorManager;
    Sensor accel;
    Timer timer;
    TimerTask timerTask;


    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        movements = new ArrayList<Integer>();
        r = new Random();
        timer = new Timer();
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        extras = getIntent().getExtras();
        if(extras != null){
           players = extras.getInt("players");
        }
        for (int i = 0;i<players;i++){
            playersArray[i] = 1;
        }


        final Handler handler = new Handler();

        final Runnable game = new Runnable()
        {
            public void run()
            {
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
                    play(actualPlayer,movements);
                    movements.clear();

                    lastPlayer  = actualPlayer;
                } else {//falta implementar el gameOver

                }
                handler.post(this);
            }
        };

        handler.post(game);
    }

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_GAME);
    }

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    @Override
    protected void onPause() {
        sensorManager.unregisterListener(this);
        super.onPause();
    }

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
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
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                float deltaX = x2 - x1;
                if (Math.abs(deltaX) > MIN_DISTANCE)
                {
                    Toast.makeText(this, "left2right swipe", Toast.LENGTH_SHORT).show ();
                }
                else
                {
                    // consider as something else - a screen tap for example
                    Toast.makeText(this, "right2left swipe", Toast.LENGTH_SHORT).show ();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    public void play(int actualPlayer,ArrayList movementsNumber){

    }
    private void completeTask() {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
