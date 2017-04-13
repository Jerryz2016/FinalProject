package com.jieli.finalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
/**
 * The Class Tv extends AppCompatActivity. It is used to
 * set up TV in the living room
 * @author   Jie Wang
 * @version v1.3.  Date: Apr 12, 2017
 */
public class Tv extends AppCompatActivity {

    /**
     * callback function by Android.
     * *@param savedInstanceState: Bundle
     */
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
