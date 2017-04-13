package com.jieli.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import me.priyesh.chroma.ChromaDialog;
import me.priyesh.chroma.ColorMode;
import me.priyesh.chroma.ColorSelectListener;

import static com.jieli.finalproject.LivingDatabaseHelper.KEY_ITEM;
import static com.jieli.finalproject.LivingDatabaseHelper.KEY_MESSAGE;
import static com.jieli.finalproject.LivingDatabaseHelper.KEY_NUMBER;
/**
 * The Class Lamp3 extends AppCompatActivity. It is used to
 * set up Lamp3 in the living room
 * @author   Jie Wang
 * @version v1.3.  Date: Apr 12, 2017
 */
public class Lamp3 extends AppCompatActivity {
    ChromaDialog.Builder lamp3Cb;
    SharedPreferences prefs  ;
    SharedPreferences.Editor myEditor ;

    SQLiteDatabase livingDB = null;
    LivingDatabaseHelper dbHelper;

    int lastColor, id3;
    ContentValues newMessage;
    int rid;
    private Bundle clickedItem;

    Button  set;
    Button  cancel;
    Button delete;
    private TextView dialogInfo;

    /**
     * callback function by Android.
     * *@param savedInstanceState: Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        dbHelper = new LivingDatabaseHelper(this);
        livingDB = dbHelper.getWritableDatabase();

        prefs = getSharedPreferences("livingRoomItems", Context.MODE_PRIVATE);
        myEditor = prefs.edit();

        id3=prefs.getInt("lmp3",0);

        newMessage = new ContentValues();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lamp3);

        clickedItem = getIntent().getExtras();
        rid = clickedItem.getInt("id");

        set = (Button) findViewById(R.id.lamp3_setting);
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("ListView", "Lamp3");
                Log.i("lamp3's id3==== ", Integer.toString(id3));
                Log.i("ListView", "Setting Lamp3....");

                lamp3Cb = new ChromaDialog.Builder();
                lamp3Cb.initialColor(prefs.getInt("lastColor"+Integer.toString(rid),0))
                        .colorMode(ColorMode.RGB).onColorSelected(new ColorSelectListener() {
                    @Override
                    public void onColorSelected(@ColorInt int i) {
                        int green = Color.green(i);
                        int red = Color.red(i);
                        int blue = Color.blue(i);
                        lastColor=  i;
                        Log.i("Color of lmp3 ===", "" + lastColor);

                        myEditor.putInt("lastColor"+Integer.toString(rid),lastColor);
                        id3++;
                        myEditor.putInt("lmp3",id3);
                        myEditor.commit();

                        newMessage.put(KEY_ITEM,"3");
                        newMessage.put(KEY_NUMBER,Integer.toString(id3));
                        newMessage.put(KEY_MESSAGE,  "The color of Lamp3 is : " + lastColor
                                +"----------------"
                                + prefs.getInt("lmp3",id3));
                        livingDB.insert(LivingDatabaseHelper.TABLE_NAME, null, newMessage);
                    }
                });
                lamp3Cb.create().show( getSupportFragmentManager(), "pick a color");
            }
        });

        cancel = (Button) findViewById(R.id.lamp3_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        delete = (Button) findViewById(R.id.lamp3_del);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete_dialog("Are you sure to delete it?");
            }
        });
    }

    public void delete_dialog(String info) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Warning:");
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.living_delete_dialog, null);
        builder.setView(view);

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked delete button
                Intent intent = new Intent();
                intent.putExtra("delete", true);
                intent.putExtra("id", rid);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //do nothing
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

