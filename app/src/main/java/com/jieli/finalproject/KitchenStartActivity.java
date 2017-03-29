package com.jieli.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import static android.R.attr.button;

public class KitchenStartActivity extends AppCompatActivity {

    String[] buttonArray;
    ListView buttonList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen_start);

        buttonArray = getResources().getStringArray(R.array.button_array);
        buttonList = (ListView) findViewById(R.id.button_List);

        buttonList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, buttonArray));

        buttonList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Toast.makeText(getApplicationContext(), buttonArray[position], Toast.LENGTH_SHORT).show();
            }
        });

        Button returnButton = (Button) findViewById(R.id.button1);
        returnButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
