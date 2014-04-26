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
    private Random r = new Random();
    SensorManager sensorManager;
    Sensor rotation;

    float rx, ry, rz, lrx, lry, lrz;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        rotation = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

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
        sensorManager.registerListener(this, rotation, 1500000);
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
        Log.i("accel", event.values[0] + "\t" + event.values[1] + "\t" + event.values[2]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("touch", event.getX() + " " + event.getY());
        return true;
    }
}
