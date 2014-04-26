package com.bopit.app;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import android.widget.Toast;


import java.util.Random;


public class GameActivity extends Activity implements Runnable,SensorEventListener {
    Bundle extras;
    private int players = 0;
    private int actualPlayer = 0;
    private int playeds[] = {0,1,2,3,4,5};
    private int movements = 0;
    private boolean gameOver = false;
    private int lastPlayed = 0;
    private float x1,x2;
    private RelativeLayout layout = (RelativeLayout) findViewById(R.id.back);
    static final int MIN_DISTANCE = 150;
    private Random r = new Random();
    SensorManager sensorManager;
    Sensor accel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //SimpleGestureFilter detector = new SimpleGestureFilter(this,this);

        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        extras = getIntent().getExtras();
        if(extras != null){
           players = extras.getInt("players");
        }
        Log.e("rP",""+players);
        //new Handler().


        setContentView(R.layout.activity_game);
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
    public void run() {
        Log.e("rP",""+actualPlayer);
        if (!gameOver) {
            Context context = getApplicationContext();
            actualPlayer = r.nextInt(1 + players) + 1;
            movements = r.nextInt(3 + 9) + 1;
            CharSequence text = "Hello toast!"+actualPlayer+movements;
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            Log.e("rP",""+actualPlayer);
            Log.e("rM",""+movements);


        } else {//falta implementar el gameOver

        }
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
                Toast.makeText(this, "x1: "+x1, Toast.LENGTH_SHORT).show ();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                Toast.makeText(this, "x2: "+x2, Toast.LENGTH_SHORT).show ();
                float deltaX = x2 - x1;
                Toast.makeText(this, "delta: "+deltaX, Toast.LENGTH_SHORT).show ();
                if (deltaX > MIN_DISTANCE)
                {
                    Toast.makeText(this, "left2right swipe", Toast.LENGTH_SHORT).show ();
                }
                else if (deltaX < -MIN_DISTANCE)
                {
                    // consider as something else - a screen tap for example
                    Toast.makeText(this, "right2left swipe", Toast.LENGTH_SHORT).show ();
                }
                else{
                    Toast.makeText(this, "x1: "+x1, Toast.LENGTH_SHORT).show ();

                }
                break;
        }
        return super.onTouchEvent(event);
    }



}
