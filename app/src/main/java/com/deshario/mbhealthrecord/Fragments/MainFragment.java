package com.deshario.mbhealthrecord.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Selection;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.deshario.mbhealthrecord.Database.DBHelper;
import com.deshario.mbhealthrecord.FirstTimeData;
import com.deshario.mbhealthrecord.MainActivity;
import com.deshario.mbhealthrecord.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private static final String CONFIG_NAME = "userfirsttime";
    DBHelper dbHandler;
    ArrayList<String> weight;
    ArrayList<String> height;
    ArrayList<String> week_no;
    ArrayList<Integer> userdata;
    View positiveAction;
    LinearLayout my_title;
    Button savedata;
    ImageButton ed;

    TextView user_name,user_weight,user_height;
    String _name,_weight,_height;

    String mom_name,mom_first_week,mom_first_weight,mom_first_height;
    TextView mother_name,mother_weight,mother_height,mother_week,mother_bmi;

    Button openmygraph,editdata;

    DecimalFormat desharioformat = new DecimalFormat();

    public MainFragment(){}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ((MainActivity) getActivity()).setActionBarTitle("บันทึกสุขภาพแม่และเด็ก");


        SharedPreferences prfs = getActivity().getSharedPreferences(CONFIG_NAME, Context.MODE_PRIVATE);
        desharioformat.applyPattern("0.00");
        mom_name = prfs.getString("Nickname", "");
        mom_first_week = prfs.getString("Week", "");
        mom_first_weight = prfs.getString("Weight", "");
        mom_first_height = prfs.getString("Height", "");

        mother_name = (TextView) view.findViewById(R.id.name);
        mother_weight = (TextView) view.findViewById(R.id.weight);
        mother_height = (TextView) view.findViewById(R.id.height);
        mother_week = (TextView) view.findViewById(R.id.week);
        mother_bmi = (TextView) view.findViewById(R.id.bmi);
        openmygraph = (Button) view.findViewById(R.id.mygraph);
        editdata = (Button) view.findViewById(R.id.editdata);
        //ed = (ImageButton)view.findViewById(R.id.editdataa);
        openmygraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).setActionBarTitle("บันทึกสุขภาพประจำสัปดาห์");
                GraphTabFragment fragment2 = new GraphTabFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.containerView, fragment2);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        editdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(),"Developing Mode",Toast.LENGTH_SHORT).show();
                MaterialDialog aboutdialog = new MaterialDialog.Builder(getActivity())
                        .title("ปรับปรุงข้อมูล")
                        .titleColorRes(R.color.colorPrimaryDark)
                        .customView(R.layout.updatefirstweek, true)
                        .positiveText("บันทึก")
                        .negativeText("ยกเลิก")
                        .backgroundColorRes(R.color.pink_bootstrap)

                        //.dividerColorRes(R.color.success_bootstrap)
                       // .widgetColorRes(R.color.info_bootstrap)
                       // .buttonRippleColorRes(R.color.default_bootstrap)

                        .positiveColorRes(R.color.colorPrimaryDark)
                        .negativeColorRes(R.color.danger_bootstrap)
                        .canceledOnTouchOutside(false)
                        .cancelable(false)
                        .onPositive(new MaterialDialog.SingleButtonCallback(){
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                _name = user_name.getText().toString();
                                _weight = user_weight.getText().toString();
                                _height = user_height.getText().toString();
                                if (TextUtils.isEmpty(_name) || TextUtils.isEmpty(_weight) || TextUtils.isEmpty(_height)){
                                    Toast.makeText(getActivity(), " กรุณากรอกข้อมูลให้ครบ ! \n แล้วลองใหม่อีกครั้ง", Toast.LENGTH_LONG).show();
                                }else{
                                    FirstTimeData fd = new FirstTimeData(getActivity());
                                    fd.setFirstTimedata(_name,_weight,_height,"0");

                                    double height_updateall  = Double.parseDouble(_height);
                                    update_height(height_updateall);

                                    FragmentManager fragmentManager2 = getFragmentManager();
                                    FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                                    MainFragment fragment2 = new MainFragment();
                                    fragmentTransaction2.hide(MainFragment.this);
                                    fragmentTransaction2.add(R.id.containerView, fragment2);
                                    fragmentTransaction2.disallowAddToBackStack();
                                    fragmentTransaction2.commit();

                                }
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                                //Toast.makeText(getActivity(),"gafasf",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .build();

                positiveAction = aboutdialog.getActionButton(DialogAction.POSITIVE);
                savedata = (Button) aboutdialog.getCustomView().findViewById(R.id.save_data);
                user_name = (EditText)aboutdialog.getCustomView().findViewById(R.id.input_nickname);
                user_weight = (EditText)aboutdialog.getCustomView().findViewById(R.id.input_weight);
                user_height = (EditText)aboutdialog.getCustomView().findViewById(R.id.input_height);

                user_name.setText(mom_name);
                user_name.requestFocus(mom_name.length());
                user_weight.setText(mom_first_weight);
                user_height.setText(mom_first_height);

               // my_title = (LinearLayout) aboutdialog.getCustomView().findViewById(R.id.intro_title);
               // my_title.setVisibility(View.GONE);
               // savedata.setVisibility(View.GONE);
                aboutdialog.show();
                WindowManager.LayoutParams lp = aboutdialog.getWindow().getAttributes();
                lp.dimAmount = 1f;
                aboutdialog.getWindow().setAttributes(lp);
                aboutdialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
            }
        });

        mother_name.setText("คุณ "+mom_name);
        mother_weight.setText(mom_first_weight);
        mother_height.setText(mom_first_height);
        mother_week.setText(mom_first_week);

        double firstbmi;
        Double weight = Double.valueOf(mom_first_weight);
        Double height = Double.valueOf(mom_first_height);
        double meters = (double) height / 100;
        firstbmi = weight/(meters*meters);
        mother_bmi.setText(desharioformat.format(firstbmi));
        getlatestdata();

        return view;

    }

    public void update_height(double heights){
        dbHandler.update_all_heights(heights);
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
            mother_weight.setText(latest_weight);
            mother_height.setText(latest_height);
            mother_week.setText(latest_week);
            mother_bmi.setText(desharioformat.format(bmi));
        }else{
            mother_week.setText("0");
        }

    }


}
