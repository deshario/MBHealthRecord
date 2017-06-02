package com.deshario.mbhealthrecord.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.deshario.mbhealthrecord.Database.DBHelper;
import com.deshario.mbhealthrecord.Models.Weeks;
import com.deshario.mbhealthrecord.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * A simple {@link Fragment} subclass.
 */
public class GraphDataFragment extends Fragment { //implements OnFABMenuSelectedListener

   // GraphDataAdapter adapter;
    //Cursor cursor;
    EditText user_weight,user_height,user_weeks;
    String u_weight,u_height,u_week;
    double height_u,week_u;
    Spinner spinner;
    double weight_u;
    boolean insert_permission = false;
    ListView listview;
    TextView week_txtview;
    BaseFragment currentFragment = new BaseFragment();
    LinearLayout alertempty_layout;
    private static final String CONFIG_NAME = "userfirsttime";
    Integer mom_height_int;

    DBHelper dbHandler;
    SimpleAdapter simpleAdapter;

    View positiveAction;
    int[] listviewImage = new int[]{
            R.mipmap.ic_launcher,
            R.mipmap.num1,
            R.mipmap.num2,
            R.mipmap.num3,
            R.mipmap.num4,
            R.mipmap.num5,
            R.mipmap.num6,
            R.mipmap.num7,
            R.mipmap.num8,
            R.mipmap.num9,
            R.mipmap.num10,
            R.mipmap.num11,
            R.mipmap.num12,
            R.mipmap.num13,
            R.mipmap.num14,
            R.mipmap.num15,
            R.mipmap.num16,
            R.mipmap.num17,
            R.mipmap.num18,
            R.mipmap.num19,
            R.mipmap.num20,
            R.mipmap.num21,
            R.mipmap.num22,
            R.mipmap.num23,
            R.mipmap.num24,
            R.mipmap.num25,
            R.mipmap.num26,
            R.mipmap.num27,
            R.mipmap.num28,
            R.mipmap.num29,
            R.mipmap.num30,
            R.mipmap.num31,
            R.mipmap.num32,
            R.mipmap.num33,
            R.mipmap.num34,
            R.mipmap.num35,
            R.mipmap.num36,
            R.mipmap.num37,
            R.mipmap.num38,
            R.mipmap.num39,
            R.mipmap.num40,
    };

    String[] from = {"image","weight", "height", "description"};
    String[] list_options = {"แก้ไข", "ลบ"};
    int[] to = {R.id.listview_image, R.id.myweight, R.id.myheight, R.id.mybmi};
    List<HashMap<String, String>> datalist;
    ArrayList<String> weight;
    ArrayList<String> height;
    ArrayList<String> week_no_string;
    ArrayList<Integer> week_no;
    ArrayList<String> myArrList,myArrList1;
    TextView alert_txt;


    public GraphDataFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addgraphdata, container, false);
        dbHandler = new DBHelper(getContext(), null, null, 1);
        listview = (ListView)view.findViewById(R.id.mylistview);
        alertempty_layout = (LinearLayout)view.findViewById(R.id.alertempty);
        alert_txt = (TextView)view.findViewById(R.id.alert_toadddata);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addnewdata();
            }
        });


        dowork();
        return view;
    }

    // @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        final FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
//        final FABRevealMenu fabMenu = (FABRevealMenu) view.findViewById(R.id.fabMenu);
//
//        try {
//            if (fab != null && fabMenu != null) {
//                currentFragment.setFabMenu(fabMenu);
//                fabMenu.bindAncherView(fab);
//                fabMenu.setOnFABMenuSelectedListener(this);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    @Override
//    public void onMenuItemSelected(View view) {
//        int id = (int) view.getTag();
//        if (id == R.id.add_week) {
//             addnewdata();
//        } else if (id == R.id.update_week) {
//            userinsertedweeks("update","แก้ไข");
//        } else if (id == R.id.delete_week) {
//            userinsertedweeks("delete","ลบ");
//        }
//    }


    public void dowork(){
        //ArrayList<String> weight = dbHandler.getWeeksData_Arr("weight");
        weight = dbHandler.getWeeksData_Arr("น้ำหนัก","weight");
        height = dbHandler.getWeeksData_Arr("ส่วนสูง","height");
        week_no = dbHandler.getIntegerAllWeeks("weekno");
        week_no_string = dbHandler.getWeeksData_Arr("","weekno");

        int count = weight.size();
        if(count == 0){
            alertempty_layout.setVisibility(View.VISIBLE);
            YoYo.with(Techniques.Shake).duration(1500).playOn(alert_txt);
        }else{
            alertempty_layout.setVisibility(View.GONE);
        }

        //Toast.makeText(getActivity(),"Weight : "+weight.size(),Toast.LENGTH_SHORT).show();

        //ArrayList<String> myArrList = new ArrayList<String>();
        myArrList = new ArrayList<String>();
        for (int i = 0; i < weight.size(); i++) {
          //myArrList.add("              ค่าดัชนีมวลกาย : 20");
        }

        //   List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();
        datalist = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < weight.size(); i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("weight", weight.get(i));
            hm.put("height", height.get(i));
            hm.put("week", ""+week_no.get(i)); //myArrList.get(i)+1
            hm.put("description", ""); //myArrList.get(i)+1
            hm.put("image", Integer.toString(listviewImage[week_no.get(i)])); //listviewImage[i]
            datalist.add(hm);
        }
        simpleAdapter = new SimpleAdapter(getActivity(), datalist, R.layout.fragment_graph_data, from, to);
        listview.setAdapter(simpleAdapter);
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> map = (Map<String, Object>)listview.getItemAtPosition(position);
                final String map_week = (String) map.get("week");
                final Integer week = Integer.valueOf(map_week);
                MaterialDialog dialog_ask = new MaterialDialog.Builder(getContext())
                        .title("ข้อมูลสัปดาห์ที่ "+map_week)
                        .items(R.array.listview_longclick)
                        .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                               if(which == 0){
                                   updatedata(week);
                               }
                                if(which == 1){
                                  delete_confirmation(week);
                               }
                                return true;
                            }
                        })
                        .positiveText("เลือก")
                .build();
                dialog_ask.show();
                return false;
            }
        });
    }

    public void addnewdata(){
        //add_weekselect = null; // Very Important  to clear value when reload dialog
        boolean wrapInScrollView = true;
        MaterialDialog dialog = new MaterialDialog.Builder(getContext())
                .title("เพิ่มข้อมูล")
                .customView(R.layout.pregnant_form, true)
                .positiveText("ตกลง")
                .negativeText("ยกเลิก")
                .titleColorRes(R.color.primary_bootstrap)
                //.backgroundColorRes(R.color.primary_bootstrap)
                .positiveColorRes(R.color.primary_bootstrap)
                .negativeColorRes(R.color.primary_bootstrap)
                //.widgetColorRes(R.color.warning_stroke_color)
                .canceledOnTouchOutside(false)
                .cancelable(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        u_weight = user_weight.getText().toString();
                        boolean previousweek_inserted = false,already_registered = false;
                        if (TextUtils.isEmpty(u_weight)){
                            Toast.makeText(getActivity(), " กรุณากรอกข้อมูลให้ครบ ! \n แล้วลองใหม่อีกครั้ง", Toast.LENGTH_LONG).show();
                        } else {
                           String c_u_week = convertweeks(u_week);
                            if(c_u_week.equals("")){
                                Toast.makeText(getActivity()," กรุณากรอกข้อมูลให้ครบ ! \n แล้วลองใหม่อีกครั้ง",Toast.LENGTH_SHORT).show();
                            }else{
                                Integer week_integer = Integer.valueOf(c_u_week);
                                //Toast.makeText(getActivity(),"Week : "+week_integer,Toast.LENGTH_SHORT).show();

                                SharedPreferences prfs = getActivity().getSharedPreferences(CONFIG_NAME, Context.MODE_PRIVATE);
                                String mom_height = prfs.getString("Height", "");
                                Double mom_height_int = Double.valueOf(mom_height);
                                weight_u = Double.parseDouble(u_weight);
                                height_u = mom_height_int;

                                previousweek_inserted = validweek(week_integer-1);
                                already_registered = validweek(week_integer);


                                //  For First Week Only
                                if(week_integer == 12 && already_registered == true){ // If 1st week is already registered
                                    Toast.makeText(getActivity(),"ในสัปดาห์นี้คุณได้ลงทะเบียนไปแล้ว",Toast.LENGTH_SHORT).show();
                                    insert_permission = false;
                                }else if(week_integer == 12 && already_registered == false){
                                    insert_permission = true; // Grant Permission }
                                }



                                // For others week except first week
                                if(week_integer > 12){
                                    if(previousweek_inserted == false){
                                        //Toast.makeText(getActivity(),"คุณยังไม่ได้ลงทะเบียนในสัปดาห์ที่ผ่านมา",Toast.LENGTH_SHORT).show();
                                        insert_permission = true; // Deny Permission || give true
                                    }
                                    if(already_registered == true){
                                        Toast.makeText(getActivity(),"ในสัปดาห์นี้คุณได้ลงทะเบียนไปแล้ว",Toast.LENGTH_SHORT).show();
                                        insert_permission = false; // Deny Permission
                                    }
                                    if(already_registered == false && previousweek_inserted == true){
                                        //Toast.makeText(getActivity(),"Ready to insert",Toast.LENGTH_SHORT).show();
                                        insert_permission = true;
                                    }

                                }

                                if(week_integer<12 || week_integer > 40){
                                    Toast.makeText(getActivity()," สัปดาห์ไม่ถูกต้อง !\n กรุณาลองใหม่อีกครั้ง",Toast.LENGTH_SHORT).show();
                                    insert_permission = false;
                                }

                                // Check Permission To Insert
                                if(insert_permission == true){
                                    insert(weight_u,height_u,week_integer);
                                    insert_permission = false;
                                }

                            }

                        }
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        //onBackPressed();
                    }
                })
                .build();

        positiveAction = dialog.getActionButton(DialogAction.POSITIVE);
        user_weight = (EditText)dialog.getCustomView().findViewById(R.id.my_weight);
        //user_height = (EditText)dialog.getCustomView().findViewById(R.id.my_height);
       // user_weeks = (EditText)dialog.getCustomView().findViewById(R.id.my_weekno);
        spinner = (Spinner)dialog.getCustomView().findViewById(R.id.spinner);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.weeks_array_th, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               // Toast.makeText(getActivity(),"pos"+position,Toast.LENGTH_LONG).show();
                //u_week = ""+id;
                u_week = ""+parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                u_week = ""+0;

            }
        });

        //user_weeks.setText("สัปดาห์ที่หนึ่ง");
        //add_weeks_wheel = (WheelView)dialog.getCustomView().findViewById(R.id.wheelview3);
        List<String> add_weeks_names = Arrays.asList(getResources().getStringArray(R.array.weeks_array));

        dialog.show();
        //super.onBackPressed();
    }

    public void userinsertedweeks(final String purpose, String alerttitle) {
        ArrayList<String> myweek = new ArrayList<String>();
        myweek = weekfordelete();
        if (myweek.isEmpty()) {
            new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("ไม่พบข้อมูล")
                    .setContentText("ดูเหมือนว่าจะคูณยังไม่มีข้อมูลรายสัปดาห์")
                    .setConfirmText("ตกลง")
                    .show();
        } else {
            new MaterialDialog.Builder(getActivity())
                    .title("เลือกสัปดาห์ที่ต้องการ"+alerttitle)
                    .items(myweek)
                    .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                        @Override
                        public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                            if (TextUtils.isEmpty(text)) {
                                Toast.makeText(getActivity(), " กรุณาเลือกสัปดาห์ใดสัปดาห์หนึง ! \n แล้วลองใหม่อีกครั้ง", Toast.LENGTH_LONG).show();
                            } else {
                                try {
                                    String selected_week = CharSequencetoString(text);
                                    String selected_week_int = convertweeks(selected_week);
                                    Integer week_integer = Integer.valueOf(selected_week_int);
                                    boolean check_week = validweek(week_integer);
                                    if (check_week == true) {
                                        if(purpose.equals("update")){
                                            //Toast.makeText(getActivity()," for update",Toast.LENGTH_SHORT).show();
                                            //getdataforupdate(week_integer);
                                             updatedata(week_integer);
                                        }
                                        if(purpose.equals("delete")){
                                            //Toast.makeText(getActivity()," for delete",Toast.LENGTH_SHORT).show();
                                            delete_confirmation(week_integer);
                                        }
                                    } else {
                                        new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                                .setTitleText("ไม่พบข้อมูล")
                                                .setContentText("ดูเหมือนว่าจะไม่พบข้อมูลที่คุณต้องการ")
                                                .setConfirmText("ตกลง")
                                                .show();
                                    }
                                } catch (Exception error) {
                                    Toast.makeText(getActivity(), "Error : " + error, Toast.LENGTH_SHORT).show();
                                }
                            }
                            return true;
                        }
                    })
                    .positiveText("เลือก")
                    .show();
        }
    }

    public void updatedata(final int week4update){
        boolean wrapInScrollView = true;
        final Cursor cu_update = dbHandler.weekbyno(week4update);
        if(cu_update!=null && cu_update.getCount()>0){
            cu_update.moveToFirst();
        }
        MaterialDialog dialog = new MaterialDialog.Builder(getContext())
                .title("ปรับปรุงข้อมูลสัปดาห์ที่ "+week4update)
                .customView(R.layout.pregnant_form, true)
                .positiveText("ตกลง")
                .negativeText("ยกเลิก")
                .titleColorRes(R.color.primary_bootstrap)
                .positiveColorRes(R.color.primary_bootstrap)
                .negativeColorRes(R.color.primary_bootstrap)
                .canceledOnTouchOutside(false)
                .cancelable(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        u_weight = user_weight.getText().toString();
                       // u_height = user_height.getText().toString();
                        if (TextUtils.isEmpty(u_weight)){
                            Toast.makeText(getActivity(), " กรุณากรอกข้อมูลให้ครบ ! \n แล้วลองใหม่อีกครั้ง", Toast.LENGTH_LONG).show();
                        } else {
                            SharedPreferences prfs = getActivity().getSharedPreferences(CONFIG_NAME, Context.MODE_PRIVATE);
                            String mom_height = prfs.getString("Height", "");
                            Double mom_height_int = Double.valueOf(mom_height);
                            weight_u = Double.parseDouble(u_weight);
                            height_u = mom_height_int;
                            //int idglobal = cu_update.getInt(cu_update.getColumnIndexOrThrow("_id"));
                            update(weight_u,height_u,week4update);
                            //Toast.makeText(getActivity()," weight : "+weight_u+"\n height : "+height_u+"\n week : "+week4update,Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        //onBackPressed();
                    }
                })
                .build();

        positiveAction = dialog.getActionButton(DialogAction.POSITIVE);
        user_weight = (EditText)dialog.getCustomView().findViewById(R.id.my_weight);
       // user_height = (EditText)dialog.getCustomView().findViewById(R.id.my_height);
        //user_weeks = (EditText)dialog.getCustomView().findViewById(R.id.my_weekno);
        //
        user_weight.setText(cu_update.getString(cu_update.getColumnIndexOrThrow("weight")));
       // user_height.setText(cu_update.getString(cu_update.getColumnIndexOrThrow("height")));
       // user_weeks.setText(cu_update.getString(cu_update.getColumnIndexOrThrow("weekno")));
        week_txtview = (TextView)dialog.getCustomView().findViewById(R.id.weeks_txt);
        spinner = (Spinner)dialog.getCustomView().findViewById(R.id.spinner);
        week_txtview.setVisibility(View.GONE);
        spinner.setVisibility(View.GONE);

       // user_weeks.setVisibility(View.GONE);

        dialog.show();
        //super.onBackPressed();
    }

    public ArrayList<String> weekfordelete(){
        dbHandler = new DBHelper(getContext(), null, null, 1);
        Cursor ha = dbHandler.getWeeksOnly();
        ArrayList<String> saved_weeks = new ArrayList<String>();
        ha.moveToFirst();
        for(int i = 0; i < ha.getCount(); i++){
            int row = ha.getInt(ha.getColumnIndexOrThrow("weekno"));
            saved_weeks.add("สัปดาห์ที่ "+row);
            ha.moveToNext();
        }
        return saved_weeks;
    }


    public void delete_confirmation(final int confirm_week) {
        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("คุณแน่ใจหรือไม่?")
                //.setContentText("Won't be able to recover this file!")
                .setConfirmText("ฉันแน่ใจ")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        delete(confirm_week);
                        update_cursor();
                        sDialog
                                .setTitleText("ลบเรียบร้อยแล้ว!")
                                //.setContentText("บันทึกของคุณได้ถูกลบ!")
                                .setConfirmText("ตกลง")
                                .setConfirmClickListener(null)
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                    }
                })
                .show();
    }

    public void delete(int week_no){
        dbHandler.deleteweek(week_no);
    }

    public void insert(double weight,double height,int week){
        // DBHelper dbHandler;
        dbHandler = new DBHelper(getContext(), null, null, 1);
        Weeks thisweek = new Weeks(weight,height,week);
        dbHandler.addWeeksData(thisweek);
        inform_added_or_updated("บันทึกสำเร็จ","ข้อมูลของคุณได้รับการบันทึกเรียบร้อยแล้ว");
        update_cursor();
        cleartext();
    }

    public void update(double weight,double height,int weekno){
        Weeks weeker = new Weeks(weight,height,weekno);
        Cursor cat = dbHandler.weekbyno(weekno);
        if(cat!=null && cat.getCount()>0){
            cat.moveToFirst();
        }
        //Toast.makeText(getActivity()," weight : "+weight+"\n height : "+height+"\n weekno : "+week,Toast.LENGTH_LONG).show();
        int idglobal = cat.getInt(cat.getColumnIndexOrThrow("_id"));
        weeker.setId(idglobal);
        dbHandler.updateweek(weeker);
        update_cursor();
        inform_added_or_updated("ปรับปรุงสำเร็จ","ข้อมูลของคุณได้รับการปรับปรุงเรียบร้อยแล้ว");
    }


    public String CharSequencetoString(CharSequence data){
        final StringBuilder string_builder = new StringBuilder(data.length());
        string_builder.append(data);
        return string_builder.toString();
    }


    public void update_cursor(){
        datalist.clear();
        weight.clear();
        myArrList.clear();
        dowork();
   }

    public void inform_added_or_updated(String sweettitle, String sweetcontent) {
        new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(""+sweettitle)
                .setContentText(""+sweetcontent)
                .setConfirmText("ตกลง")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();
    }

    public void cleartext() {
        user_weight.setText("");
        //user_height.setText("");
       // user_weeks.setText("");
    }


    public boolean validweek(int validthisweek){
        boolean check = dbHandler.uniqueweek(validthisweek);
        return check;
    }


    public String convertweeks(String week_identity){
        String week_string = week_identity;
        String week_int = week_string.replaceAll("[^0-9]", "");
        return week_int;
    }


}
