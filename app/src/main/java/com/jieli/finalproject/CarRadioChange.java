package com.jieli.finalproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CarRadioChange extends AppCompatActivity {
    Context ctx;
    private Bundle clickedRadio;

    private TextView radioId;
    private EditText radioName;
    private EditText radioChannel;
    private Button update;
    private Button delete;
    private Button cancel;

    private TextView dialogInfo;
    Boolean result = false;
    int rid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_radio_change);
        ctx = this;
        // define textviews and buttons
        radioId = (TextView) findViewById(R.id.radio_change_id);
        radioName = (EditText) findViewById(R.id.radio_change);
        radioChannel = (EditText) findViewById(R.id.channel_change);
        update = (Button) findViewById(R.id.radio_update);
        delete = (Button) findViewById(R.id.radio_del);
        cancel = (Button) findViewById(R.id.radio_cancel);
        //retrieve data from intent passed
        clickedRadio = this.getIntent().getExtras();
        rid = clickedRadio.getInt("id");
        // set the info of the clicked radio and display
        radioId.setText("Clicked ID: " + Integer.toString(rid));
        radioName.setText(clickedRadio.getString("radio"));
        radioChannel.setText(clickedRadio.getString("channel"));

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Update the radio ...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent();
                intent.putExtra("update", true);
                intent.putExtra("id", rid);
                intent.putExtra(CarRadioDatabase.RADIOCOL, radioName.getText().toString());
                intent.putExtra(CarRadioDatabase.SETCOL, radioChannel.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog("Are you sure to delete it?");
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void dialog(String info) {

        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle("Warning:");
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.car_radio_change_dialog, null);
        builder.setView(view);
        dialogInfo = (TextView) view.findViewById(R.id.dialog_info);
        dialogInfo.setText(info);

// Add the buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                Intent intent = new Intent();
                intent.putExtra("delete", true);
                intent.putExtra("id", rid);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //do nothing
            }
        });
        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
