package com.jieli.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class LivingDatabaseActivity extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "LivingDatabaseActivity";
    LivingDatabaseHelper dbHelper;
    ContentValues newMessage;
    SQLiteDatabase livingDB = null;
    Cursor cursor;
    public LivingAdapter livingAdapter;

    public ListView dbListView;
    protected ArrayList<String> arrayList_livingItems = new ArrayList<String>();
    protected ArrayList<Long> ids = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_living_database);

        dbHelper = new LivingDatabaseHelper(this);
        livingDB = dbHelper.getWritableDatabase();

        dbListView = (ListView) findViewById(R.id.listView);
        livingAdapter = new LivingAdapter(this);

        //ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
       // progressBar.setVisibility(View.VISIBLE);

        DataBaseQuery thread = new DataBaseQuery();
        thread.execute();

        dbListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Log.d("dbListView", "onItemClick: " + i + " " + l);
                deleteListMessage(l);
                livingAdapter.notifyDataSetChanged(); //this restarts the process of getCount()/getView()
            }
        });
    }

        public class DataBaseQuery extends AsyncTask<String, Integer, String> {

            @Override
            protected String doInBackground(String ... args){
                String in= "LivingRoom";
                cursor = livingDB.query(false, LivingDatabaseHelper.TABLE_NAME,
                new String[] {LivingDatabaseHelper.KEY_ID,
                        LivingDatabaseHelper.KEY_ITEM,
                        LivingDatabaseHelper.KEY_NUMBER,
                        LivingDatabaseHelper.KEY_MESSAGE
                },
                "Message not null", null,null,null,null,null);
        Log.i(ACTIVITY_NAME, "cursor.getCount() =="  + cursor.getCount());
        for(int i = 0; i < cursor.getColumnCount(); i++)
        {
            Log.i(ACTIVITY_NAME, "Column's name =="  + cursor.getColumnName(i));
        }
                publishProgress(25);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (cursor !=null){
            int rows = cursor.getCount();
            cursor.moveToFirst();
        }
                publishProgress(50);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                while (!cursor.isAfterLast()){
            arrayList_livingItems.add(cursor.getString(cursor.getColumnIndex(LivingDatabaseHelper.KEY_MESSAGE)));
            ids.add(cursor.getLong(cursor.getColumnIndex(LivingDatabaseHelper.KEY_ID)));

            Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + cursor.getString(cursor.getColumnIndex( LivingDatabaseHelper.KEY_MESSAGE) ) );
            Log.i(ACTIVITY_NAME, "cursor's column count = " + cursor.getColumnCount());
            Log.i(ACTIVITY_NAME, "cursor's row number = "  + cursor.getPosition());

            cursor.moveToNext();
        }
                publishProgress(100);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return in;
   }

   public void onProgressUpdate(Integer ... values){

                ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
                progressBar.setProgress(values[0]);
                progressBar.setVisibility(View.VISIBLE);
            }

   public void onPostExecute(String result) {
       dbListView.setAdapter(livingAdapter);
   }
}

    public void deleteListMessage(Long id)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.delete(LivingDatabaseHelper.TABLE_NAME, "_id=?", new String[]{Long.toString(id)});
            arrayList_livingItems.clear();
            ids.clear();

            cursor = db.query(false, LivingDatabaseHelper.TABLE_NAME,
                    new String[]{LivingDatabaseHelper.KEY_ID, LivingDatabaseHelper.KEY_MESSAGE}, null, null, null, null, null, null);
            int rows = cursor.getCount();
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                arrayList_livingItems.add(cursor.getString(cursor.getColumnIndex(LivingDatabaseHelper.KEY_MESSAGE)));
                Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + cursor.getString(cursor.getColumnIndex(LivingDatabaseHelper.KEY_MESSAGE)));
                ids.add(cursor.getLong(cursor.getColumnIndex(LivingDatabaseHelper.KEY_ID)));

                cursor.moveToNext();
            }

            //livingAdapter.notifyDataSetChanged();
        }catch (Exception e)
        {
            Log.d("CRASH", e.getMessage());
        }
    }

    public class LivingAdapter extends ArrayAdapter<String> {

        public LivingAdapter(Context ctx){super(ctx,0);}

        public int getCount(){
            return arrayList_livingItems.size();
        }
        public String getItem(int position){
            return arrayList_livingItems.get(position);
        }
        public long getItemId(int position)
        {
            return ids.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent){

            LayoutInflater inflater = LivingDatabaseActivity.this.getLayoutInflater();
            View result = null;
            if(position%2 ==0)
                result = inflater.inflate(R.layout.living_room_rowe,null);
            else
                result = inflater.inflate(R.layout.living_room_rowo,null);


           TextView message = (TextView)result.findViewById(R.id.message_text);
           message.setText(  getItem(position)  ); // get the string at position
            return result;
        }
    }
}
