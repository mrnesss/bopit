package com.bopit.app;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
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
    Sensor accel;
    float rx, ry, rz, lrx, lry, lrz;
    ArrayList<float[]> motion;
    boolean capture;

    TextView tvx, tvy, tvz, tvm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        extras = getIntent().getExtras();
        if(extras != null){
           players = extras.getInt("players");
        }
        Log.e("rP",""+players);
        //new Handler().


        setContentView(R.layout.activity_game);

        rx = ry = rz = lrx = lry = lrz = 0f;
        motion = new ArrayList<float[]>();
        capture = true;

        tvx = (TextView)findViewById(R.id.textView);
        tvy = (TextView)findViewById(R.id.textView3);
        tvz = (TextView)findViewById(R.id.textView4);
        tvm = (TextView)findViewById(R.id.textView5);
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

    void processMotion(float x, float y, float z) {
        float mrx = x, mry = y, mrz = z;
        if(capture) {
            if(mrx > 15 && mry > 15 && mrz > 9) {
                //Toast.makeText(this, "Fap", Toast.LENGTH_SHORT).show();
                tvm.setText("FAP");
                capture = false;
            }
            if(mrx > 7 && mrz > 7 && mrx > mry && mrz > mry) {
                //Toast.makeText(this, "Roll", Toast.LENGTH_SHORT).show();
                tvm.setText("ROLL");
                capture = false;
            }
            if(mrx > 9 && mry > 9 && mrz < 12 && mrx > mrz && mry > mrz) {
                //Toast.makeText(this, "Twist", Toast.LENGTH_SHORT).show();
                tvm.setText("TWIST");
                capture = false;
            }
            if(mry > 9 && mrz > 9 && mry > mrx && mrz > mrx) {
                //Toast.makeText(this, "Flip", Toast.LENGTH_SHORT).show();
                tvm.setText("FLIP");
                capture = false;
            }
        }

        motion.clear();
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
        tvx.setText(lrx + "");
        tvy.setText(lry + "");
        tvz.setText(lrz + "");
        if(rx < lrx && ry < lry && rz < lrz)
            processMotion(lrx, lry, lrz);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("touch", event.getX() + " " + event.getY());
        lrx = 0;
        lry = 0;
        lrz = 0;
        capture = true;
        return true;
    }
}
