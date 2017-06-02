package com.deshario.mbhealthrecord.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.deshario.mbhealthrecord.Database.DBHelper;
import com.deshario.mbhealthrecord.R;

import java.text.DecimalFormat;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private static final String CONFIG_NAME = "userfirsttime";
    DBHelper dbHandler;
    ArrayList<String> weight;
    ArrayList<String> height;
    ArrayList<String> week_no;
    ArrayList<Integer> userdata;

    String mom_name,mom_first_week,mom_first_weight,mom_first_height;
    TextView mother_name,mother_weight,mother_height,mother_week,mother_bmi;

    DecimalFormat desharioformat = new DecimalFormat();


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        SharedPreferences prfs = getActivity().getSharedPreferences(CONFIG_NAME, Context.MODE_PRIVATE);
        mom_name = prfs.getString("Nickname", "");
        mom_first_week = prfs.getString("Week", "");
        mom_first_weight = prfs.getString("Weight", "");
        mom_first_height = prfs.getString("Height", "");

        mother_name = (TextView) view.findViewById(R.id.name);
        mother_weight = (TextView) view.findViewById(R.id.weight);
        mother_height = (TextView) view.findViewById(R.id.height);
        mother_week = (TextView) view.findViewById(R.id.week);
        mother_bmi = (TextView) view.findViewById(R.id.bmi);

        mother_name.setText("คุณ "+mom_name);
        mother_weight.setText(mom_first_weight);
        mother_height.setText(mom_first_height);
        getlatestdata();

        return view;
    }

    public void getlatestdata(){
        dbHandler = new DBHelper(getContext(), null, null, 1);
        weight = dbHandler.getWeeksData_Arr_INT("weight");
        height = dbHandler.getWeeksData_Arr_INT("height");
        week_no = dbHandler.getWeeksData_Arr_INT("weekno");

        int count_weight = weight.size();
        int count_week = week_no.size();

        if (count_weight > 0 || count_week > 0) {
            String latset_data = weight.get(count_weight - 1);  // value
            int latset_data_pos = weight.indexOf(latset_data); // position of value

            String latest_weight = weight.get(latset_data_pos); // 87
            String latest_height = height.get(latset_data_pos); //99
            String latest_week = week_no.get(latset_data_pos); //99

            double bmi;
            Double weight = Double.valueOf(latest_weight);
            Double height = Double.valueOf(latest_height);
            double meters = (double) height / 100;
            bmi = weight/(meters*meters);

            Double mybmi = Double.valueOf(bmi);
            Integer bmi_int = Integer.valueOf(mybmi.intValue());

            desharioformat.applyPattern("0.00");
            desharioformat.format(bmi);

            mother_weight.setText(latest_weight);
            mother_height.setText(latest_height);
            mother_week.setText(latest_week);
            mother_bmi.setText(desharioformat.format(bmi));
        }

    }

}
