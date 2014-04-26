package com.bopit.app;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.Button;



public class MainActivity extends ActionBarActivity {
    private int players = 0;
    private ImageButton player1;
    private ImageButton player2;
    private ImageButton player3;
    private ImageButton player4;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        player1 = (ImageButton) findViewById(R.id.P1);
        player2 = (ImageButton) findViewById(R.id.P2);
        player3 = (ImageButton) findViewById(R.id.P3);
        player4 = (ImageButton) findViewById(R.id.P4);
        intent = new Intent(this,GameActivity.class);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
    protected void onResume() {
        super.onResume();
        intent = new Intent(this, GameActivity.class);
        player1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActivity(1,intent);
            }
        });
        player2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActivity(1,intent);

            }
        });
        player3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActivity(1,intent);

            }
        });
        player4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActivity(1,intent);

            }
        });
    }
    private void changeActivity(int player,Intent intent){
        intent.putExtra("players",player);
        startActivity(intent);
    }
}
