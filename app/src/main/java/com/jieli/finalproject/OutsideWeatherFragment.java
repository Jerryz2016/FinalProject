package com.jieli.finalproject;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static android.app.Activity.RESULT_OK;

/**
 * Created by fhx20 on 4/6/2017.
 */

public class OutsideWeatherFragment extends Fragment {
    long id;
    String value,min,max;
    Bitmap bm;
    View gui;
//    Context parent;
//    String temp;
//    boolean isTablet;
//    TextView textTemperature;
    HouseSettingStartActivity houseSettingStartActivity;


    public OutsideWeatherFragment() {
    }

    public OutsideWeatherFragment(HouseSettingStartActivity house) {
        houseSettingStartActivity = house;
    }
    //no matter how you got here, the data is in the getArguments

    public void onCreate(Bundle b) {
        super.onCreate(b);
        Bundle bun = getArguments();
        id=bun.getLong("ID");
new ForecastQuery().execute();
    }

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        gui = inflater.inflate(R.layout.weather_forcast_layout, null);

        return gui;
    }

    private class ForecastQuery extends AsyncTask<String, Integer, String>

    {
        protected String doInBackground(String... args) {

            String in = "";
            try {
                URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric");
                //("http://www.google.com/");


                //URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inStream = urlConnection.getInputStream();

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);

                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(inStream, "UTF-8");

                Log.i("XML parsing:", "" + xpp.getEventType());
                int type;
                while ((type = xpp.getEventType()) != XmlPullParser.END_DOCUMENT) {
                    if (xpp.getEventType() == XmlPullParser.START_TAG) {
                        if (xpp.getName().equals("temperature")) {
                            value = xpp.getAttributeValue(null, "value");
                            Thread.sleep(1000);
                            publishProgress(25);
                            min = xpp.getAttributeValue(null, "min");
                            Thread.sleep(1000);
                            publishProgress(50);
                            max = xpp.getAttributeValue(null, "max");
                            Thread.sleep(1000);
                            publishProgress(75);

                        } else if (xpp.getName().equals("weather")) {

                            String icon = xpp.getAttributeValue(null, "icon");

                            if (!fileExistance(icon + ".png")) {

                                URL ImageURL = new URL("http://openweathermap.org/img/w/" + icon + ".png");
                                Bitmap image = getImage(ImageURL);
                                FileOutputStream outputStream = getActivity().openFileOutput(icon + ".png", Context.MODE_PRIVATE);
                                image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                                outputStream.flush();
                                outputStream.close();

                                FileInputStream fis = null;
                                try {
                                    fis = getActivity().openFileInput(icon + ".png");

                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }

                                bm = BitmapFactory.decodeStream(fis);
                            } else {
                                FileInputStream fis = null;
                                try {
                                    fis = getActivity().openFileInput(icon + ".png");

                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }

                                bm = BitmapFactory.decodeStream(fis);

                            }
                            // Thread.sleep(1000);
                            publishProgress(100);

                        }


                    }
                    xpp.next();
                }

            } catch (Exception me) {
                Log.e("AsyncTask", "Malformed URL:" + me.getMessage());
            }

            return in;
        }

        public void onProgressUpdate(Integer... progress) {
            ProgressBar progressBar = (ProgressBar) gui.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);

            progressBar.setProgress(progress[0]);


        }

        public void onPostExecute(String work) {

            ImageView imageView = (ImageView) gui.findViewById(R.id.imageView3);
            imageView.setImageBitmap(bm);

            TextView tx = (TextView) gui.findViewById(R.id.textView);
            tx.setText("current Temperature is : " + value);

            ProgressBar progressBar = (ProgressBar) gui.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.INVISIBLE);


            TextView tx2 = (TextView)gui. findViewById(R.id.textView3);
            tx2.setText("min Temperature is : " + min);

            TextView tx3 = (TextView) gui.findViewById(R.id.textView5);
            tx3.setText("max Temperature is : " + max);
        }


        public boolean fileExistance(String fname) {

            File file = getActivity().getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }

        public Bitmap getImage(URL url) {
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    return BitmapFactory.decodeStream(connection.getInputStream());
                } else
                    return null;
            } catch (Exception e) {
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }

        public Bitmap getImage(String urlString) {
            try {
                URL url = new URL(urlString);
                return getImage(url);
            } catch (MalformedURLException e) {
                return null;
            }
        }

    }
}















