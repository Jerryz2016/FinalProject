package com.jieli.finalproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class HouseSettingStartActivity extends AppCompatActivity {

    String[] houseSettingButtonArray;
    ListView houseSettingButtonList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_housesetting_start);

        houseSettingButtonArray = getResources().getStringArray(R.array.houseSettingButton_array);
        houseSettingButtonList = (ListView) findViewById(R.id.houseSettingButton_List);

        houseSettingButtonList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, houseSettingButtonArray));

        houseSettingButtonList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Toast.makeText(getApplicationContext(), houseSettingButtonArray[position], Toast.LENGTH_SHORT).show();
            }
        });

        Button returnButton = (Button) findViewById(R.id.houseSettingButton1);
        returnButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
