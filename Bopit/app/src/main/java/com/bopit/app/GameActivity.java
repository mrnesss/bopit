package com.bopit.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Random;


public class GameActivity extends ActionBarActivity implements Runnable {
    Bundle extras;
    private int players = 0;
    private int playeds[] = {0,1,2,3,4,5};
    private int movements = 0;
    private boolean gameOver = false;
    private int lastPlayed = 0;
    private Random r = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        extras = getIntent().getExtras();
        if(extras != null){
           players = extras.getInt("players");
        }

        setContentView(R.layout.activity_game);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void run() {
       if (!gameOver){
           movements = r.nextInt(1+players)+1;
           for (int i = 0;i<players;i++){

           }
       }else{//falta implementar el gameOver

       }
    }
}
