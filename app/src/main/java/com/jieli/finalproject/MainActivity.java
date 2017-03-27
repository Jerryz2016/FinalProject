package com.jieli.finalproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import static com.jieli.finalproject.R.menu.main_toolbar_menu;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public boolean onCreateOptionsMenu(Menu m) {
        getMenuInflater().inflate(main_toolbar_menu, m);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem mi) {     //item clicked

        switch (mi.getItemId()) {
            case R.id.action_one:             //option1 clicked
                Log.d("Toolbar", "Option1 selected");
                Toast toast1 = Toast.makeText(this, "Smart Living Room Version 1.0, by Jie Wang", Toast.LENGTH_LONG);
                toast1.show();
                break;

            case R.id.action_two:            //option2 clicked
                Log.d("Toolbar", "Option2 selected");
                Toast toast2 = Toast.makeText(this, "Smart Kitchen Version 1.0, by Chao Gu", Toast.LENGTH_LONG);
                toast2.show();
                break;

            case R.id.action_three:                     //option3 clicked
                Log.d("Toolbar", "Option3 selected");
                Toast toast3 = Toast.makeText(this, "Smart House Setting Version 1.0, by Haixia Feng", Toast.LENGTH_LONG);
                toast3.show();

            case R.id.action_four:
                Log.d("Toolbar", "About selected");
                Toast toast4 = Toast.makeText(this, "Smart Automobile Version 1.0, by Jieli Zhang", Toast.LENGTH_LONG);
                toast4.show();
                break;
        }
        return true;
    }
}

