package com.jieli.finalproject;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import static com.jieli.finalproject.R.menu.main_toolbar_menu;

public class MainActivity extends AppCompatActivity {
    Context ctx;
    int item = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ctx = this;

    }

    public boolean onCreateOptionsMenu(Menu m) {
        getMenuInflater().inflate(main_toolbar_menu, m);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem mi) {     //item clicked
        int item = mi.getItemId();

        switch (mi.getItemId()) {
            case R.id.action_one:             //option1 clicked
                Log.d("Toolbar", "Option1 selected");
                Toast toast1 = Toast.makeText(this, "Smart Living Room Version 1.0, by Jie Wang", Toast.LENGTH_LONG);
                toast1.show();

                Intent livingRoomIntent = new Intent(MainActivity.this, LivingRoomActivity.class);
                startActivity(livingRoomIntent);
                break;

            case R.id.action_two:            //option2 clicked
                Log.d("Toolbar", "Option2 selected");
                Toast toast2 = Toast.makeText(this, "Smart Kitchen Version 1.0, by Chao Gu", Toast.LENGTH_SHORT);
                toast2.show();
                Intent toKitchen = new Intent(getApplicationContext(), KitchenStartActivity.class);
                startActivity(toKitchen);
                break;

            case R.id.action_three:                     //option3 clicked
                Log.d("Toolbar", "Option3 selected");
                Toast toast3 = Toast.makeText(this, "Smart House Setting Version 1.0, by Haixia Feng", Toast.LENGTH_LONG);
                toast3.show();

                Intent toHouseSetting = new Intent(getApplicationContext(), HouseSettingStartActivity.class);
                startActivity(toHouseSetting);

                break;

            case R.id.action_four:
                Log.d("Toolbar", "Option4 selected");

                Toast toast4 = Toast.makeText(this, "Smart Automobile Version 1.0, by Jieli Zhang", Toast.LENGTH_SHORT);
                toast4.show();
                Intent intent = new Intent(ctx, CarSettings.class);
                startActivity(intent);
                break;
            case R.id.action_help:
                Log.d("Toolbar", "Help selected");
                Toast toast5 = Toast.makeText(this, "Smart House Version 1.0, by Group3:Chao Gu,Haixia Feng,Jie Wang,Jieli Zhang", Toast.LENGTH_LONG);
                toast5.show();
                break;

            case R.id.help_living:
//                Log.d("Toolbar", "Help selected");
//                Toast toast6 = Toast.makeText(this, "Replace with Instruction for Smart Living Room Version 1.0, by Group3:Jie Wang", Toast.LENGTH_LONG);
//                toast6.show();
                dialog(R.string.help_living_title, R.string.help_living_version, R.string.help_living_room_info);
                break;
            case R.id.help_kitchen:
                Log.d("Toolbar", "Help selected");
                Toast toast7 = Toast.makeText(this, "Replace with Instruction for Smart Kitchen Version 1.0, by Group3: Chao Gu", Toast.LENGTH_LONG);
                toast7.show();
                break;
            case R.id.help_house:
                Log.d("Toolbar", "Help selected");
                Toast toast8 = Toast.makeText(this, "Replace with Instruction for Smart Hose Version 1.0, by Group3:Haixia Feng", Toast.LENGTH_LONG);
                toast8.show();
                break;
            case R.id.help_car:
//                Log.d("Toolbar", "Help selected");
//                Toast toast9 = Toast.makeText(this, "Instruction for Smart Car Version 1.0, by Group3:Jieli Zhang", Toast.LENGTH_LONG);
//                toast9.show();
                dialog(R.string.help_car_title, R.string.help_car_version, R.string.help_car_info);
                break;
        }
        return true;
    }

    public void dialog(int title, int version, int helpinfo) {

        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle(title);

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.help_dialog_layout, null);
        builder.setView(view);

        TextView versionInfo = (TextView) view.findViewById(R.id.version_info);
        versionInfo.setText(version);

        TextView helpInfo = (TextView) view.findViewById(R.id.help_info);
        helpInfo.setText(helpinfo);

// Add the buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            }
        });
 /*       builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //do nothing
            }
        });   */
        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}

