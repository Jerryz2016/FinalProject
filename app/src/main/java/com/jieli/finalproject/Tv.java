package com.jieli.finalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Tv extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv);

        TVFragment tvfrag = new TVFragment( );
        Bundle bun = getIntent().getExtras();

        if(bun !=null){
            tvfrag.setArguments( bun );
            getSupportFragmentManager().beginTransaction().add(R.id.fragmentHolder, tvfrag).commit();
        }
    }
}
