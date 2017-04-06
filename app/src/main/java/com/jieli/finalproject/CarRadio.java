package com.jieli.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


public class CarRadio extends AppCompatActivity {
    private Context ctx;
    private ProgressBar progressBar;
    private ListView listView;
    private EditText radio;
    private EditText channel;
    private Button addRadio;
    private Button exitButton;
    private SimpleCursorAdapter adapter;

    private CarRadioDatabase carRadiodb;
    protected SQLiteDatabase db;
    protected Cursor cursor;
    private String[] from;
    private int[] to;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_radio);
        ctx = this;
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        radio = (EditText) findViewById(R.id.addradio_text);
        channel = (EditText) findViewById(R.id.addchennal_text);
        addRadio = (Button) findViewById(R.id.addradio_ok);
        exitButton = (Button) findViewById(R.id.radio_exit);


        RadioSet thread = new RadioSet();
        thread.execute();
    }

    public CarRadio open() {
        carRadiodb = new CarRadioDatabase(ctx);
        db = carRadiodb.getWritableDatabase();
        return this;
    }

    public Cursor getCursor() {
        return db.query(false, CarRadioDatabase.TABLE_NAME,
                new String[]{CarRadioDatabase.KEY_ID, CarRadioDatabase.RADIOCOL, CarRadioDatabase.SETCOL},
                null, null, null, null, null, null);
    }

    public class RadioSet extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String[] args) {   //you cannot update GUI in this method
            listView = (ListView) findViewById(R.id.listView_radio);
            open();
            publishProgress((int) 30);
            SystemClock.sleep(1500);
            cursor = getCursor();
            publishProgress((int) 60);
            SystemClock.sleep(1500);

            if (cursor != null) {
                from = new String[]{CarRadioDatabase.RADIOCOL, CarRadioDatabase.SETCOL};
                to = new int[]{R.id.radio_name, R.id.radio_set};

                adapter = new SimpleCursorAdapter(ctx, R.layout.car_raio_lv_rowcol, cursor, from, to, 0);
                publishProgress((int) 100);
            }

            return "ok";
        }

        @Override
        protected void onProgressUpdate(Integer... value) {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(value[0]);
        }

        @Override
        protected void onPostExecute(String result) {  //the parameter must has the same type
            if (result.equals("ok")) {                // with the return from doInbackground()
                listView.setAdapter(adapter);
                addRadio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (radio.getText() != null && channel.getText() != null
                                && !radio.getText().toString().equals("") && !channel.getText().toString().equals("")) {
                            ContentValues newValue = new ContentValues();
                            newValue.put(CarRadioDatabase.RADIOCOL, radio.getText().toString());
                            newValue.put(CarRadioDatabase.SETCOL, Double.parseDouble(channel.getText().toString()));
                            insertRadio(newValue);

                            cursor = getCursor();
                            radio.setText("");
                            channel.setText("");

                        } else {
                            CharSequence warning = "Blank setting cannot be add!";
                            Toast toast = Toast.makeText(CarRadio.this, warning, Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                });

                exitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        Log.d("ListView", "onItemClick: " + position + " " + id);

                        //  get cursor positions to the corresponding row in the result set(cursor)
//                        Cursor cursorRadio = (Cursor) listView.getItemAtPosition(position);

                        cursor.moveToPosition(position);
                        // Get the radio and channel from this row in the database.
                        int clickedID = cursor.getInt(cursor.getColumnIndex(CarRadioDatabase.KEY_ID));
                        String clickedRadio =
                                cursor.getString(cursor.getColumnIndex(CarRadioDatabase.RADIOCOL));
                        String clickedChannel = cursor.getString(cursor.getColumnIndex(CarRadioDatabase.SETCOL));
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", clickedID);//id is the database ID of selected item
                        bundle.putString("radio", clickedRadio);
                        bundle.putString("channel", clickedChannel);

                        Intent intent = new Intent(ctx, CarRadioChange.class);
                        intent.putExtras(bundle); //pass the clicked item radio to next activity
                        startActivityForResult(intent, 11); //go to RadioDetails activity to update or delete the radio
                    }
                });
                progressBar.setVisibility(View.INVISIBLE);
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (data.getBooleanExtra("update", false)) {
                int id = data.getIntExtra("id", -1);
                ContentValues updateValues = new ContentValues();
                updateValues.put(CarRadioDatabase.RADIOCOL, data.getStringExtra(CarRadioDatabase.RADIOCOL));
                updateValues.put(CarRadioDatabase.SETCOL, Double.parseDouble(data.getStringExtra(CarRadioDatabase.SETCOL)));

                updateRadio(id, updateValues);
            }
            if (data.getBooleanExtra("delete", false)) {
                int id = data.getIntExtra("id", -1);
                deleteRadio(id);
            }
//            adapter.changeCursor(getCursor());
        }

    }

    public void insertRadio(ContentValues insertValues) {
        db.insert(CarRadioDatabase.TABLE_NAME, null, insertValues);
        cursor = getCursor();
        adapter.changeCursor(cursor);
    }

    public void updateRadio(int id, ContentValues updateValues) {
        String[] stringID = {String.valueOf(id)};
        db.update(CarRadioDatabase.TABLE_NAME, updateValues, "_id=?", stringID);
        cursor = getCursor();
        adapter.changeCursor(cursor);
    }

    public void deleteRadio(int id) {
        db.delete(CarRadioDatabase.TABLE_NAME, "_id=?", new String[]{Integer.toString(id)});
        cursor = getCursor();
        adapter.changeCursor(cursor);
    }

    public void onDestory() {
        if (cursor != null) cursor.close();
        if (carRadiodb != null) carRadiodb.close();
    }

}

