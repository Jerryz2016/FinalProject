package com.jieli.finalproject;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import static com.jieli.finalproject.R.id.button_living;
import static com.jieli.finalproject.R.id.livingRoomListView;

public class LivingRoomActivity extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "LivingRoomActivity";
    ListView livingRoomListView;
    String[] livingRoomItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_living_room);

        Resources resource = getResources();
        livingRoomItems = getResources().getStringArray(R.array.livingroom_array);


        livingRoomListView = (ListView)findViewById(R.id.livingRoomListView);
        livingRoomListView.setAdapter(new ArrayAdapter<String>(this,  R.layout.living_room_items, livingRoomItems));

        livingRoomListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Toast.makeText(LivingRoomActivity.this, "Setting " + livingRoomItems[position], Toast.LENGTH_SHORT).show();

                switch (position) {
                    case 0:  //setting Lamp1
                        Log.i("ListView", "Lamp1");
                    case 1:  //setting Lamp2
                        Log.i("ListView", "Lamp2");
                    case 2:   //setting Lamp3
                        Log.i("ListView", "Lamp3");
                    case 3:   //setting Television
                        Log.i("ListView",  "Television");


                    case 4:   //setting Smart Window Blinds
                        Log.i("ListView", "Smart Window Blinds");

                }
            }
        });

        Button button_exit = (Button)findViewById(button_living);
        button_exit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.i (ACTIVITY_NAME, "User clicked Start Chart");
                Intent intent_exit = new Intent(LivingRoomActivity.this, MainActivity.class);
                startActivity(intent_exit);
            }
        });
    }
}

