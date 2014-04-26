package com.bopit.app;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by Mario on 4/25/2014.
 */
public class GameActivity extends Activity implements SensorEventListener {
    SensorManager sensorManager;
    Sensor rotation;

    float rx, ry, rz, lrx, lry, lrz;

    Bundle extras;
    int players = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        rotation = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

        extras = getIntent().getExtras();
        if(extras != null){
           players = extras.getInt("players");
        }

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
