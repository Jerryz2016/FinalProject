package com.jieli.finalproject;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import static android.app.Activity.RESULT_OK;

/**
 * CST2335 Final Project-Automobile activity
 * The Class CarTemperatureFragment, used to creat a fragment for displaying the temperature stored
 * or setting the new temperature either for a tablet or a phone.
 * Group     3
 * @author Jieli Zhang
 * @version v1.0
 * Date      2017.04.12
 */
public class CarTemperatureFragment extends Fragment {

    /**
     * parent, the context.
     */
    Context parent;

    /**
     * The temp from the bundle passed.
     */
    String temp;

    /** isTablet is a boolean from the bundle passed. */
    boolean isTablet;

    /**
     * The text temperature user inputs.
     */
    TextView textTemperature;


    /**
     * OnCreate.
     *
     * @param b the bundle
     */
    //no matter how you got here, the data is in the getArguments
    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        Bundle bundle = getArguments();
        temp = bundle.getString("carTemperature");
        Log.i("fragment temp", temp);
        isTablet = bundle.getBoolean("isTablet");

    }

    /**
     * OnAttach metohd to attach the context.
     *
     * @param context the context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        parent = context;
    }

    /**
     * To create the view of the fragment.
     *
     * @param inflater           the inflater
     * @param container          the container
     * @param savedInstanceState the saved instance state
     * @return the view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View gui = inflater.inflate(R.layout.car_temp_frag_layout, null);

        textTemperature = (TextView) gui.findViewById(R.id.temp_set);
        textTemperature.setText(temp);


        Button okButton = (Button) gui.findViewById(R.id.temp_ok);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp = textTemperature.getText().toString();
                if (isTablet) {
                    //call store method in CarSettings class to save temp in db
                    Log.i("carfragment isTablet: ", temp);
                    ((CarSettings) getActivity()).saveTempPreference("cartemperatrure", temp);
                    CarTemperatureFragment mf = (CarTemperatureFragment) getFragmentManager().
                            findFragmentById(R.id.car_framelayout);   // find the fragmnet
                    getFragmentManager().beginTransaction().remove(mf).commit();  //remove the car_framelayour from sw600dp

                } else {
                    Intent data = new Intent();
                    data.putExtra("carTemperature", temp);
                    getActivity().setResult(RESULT_OK, data);
                    getActivity().finish();
                }
            }
        });

        return gui;
    }

    /**
     * constructor for instantiating a new car temperature fragment.
     */
	public CarTemperatureFragment() {
		super();
	}
}
