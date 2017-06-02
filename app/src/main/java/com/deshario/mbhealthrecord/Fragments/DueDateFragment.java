package com.deshario.mbhealthrecord.Fragments;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.deshario.mbhealthrecord.MainActivity;
import com.deshario.mbhealthrecord.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * A simple {@link Fragment} subclass.
 */
public class DueDateFragment extends Fragment {

    public static String data;
    int ok_length = 0;
    int calduelength = 0;

    String date_inputed,length_inputed;

    EditText user_date;
    Spinner spinner_length;

    Button save,clear;
    View positiveAction;



    public DueDateFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_due_date, container, false); // Define View
        ((MainActivity) getActivity()).setActionBarTitle("คำนวณวันคลอด");

        user_date = (EditText)view.findViewById(R.id.inputdate);
        spinner_length = (Spinner)view.findViewById(R.id.input_length);
        save = (Button)view.findViewById(R.id.savedata);
        clear = (Button)view.findViewById(R.id.cleardata);

        user_date.setInputType(InputType.TYPE_NULL);

        user_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datepicker();
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.duelength, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_length.setAdapter(adapter);
        spinner_length.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getActivity(),"pos"+  parent.getItemAtPosition(position).toString(),Toast.LENGTH_LONG).show();
                length_inputed = ""+parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date_inputed = user_date.getText().toString();
                if (TextUtils.isEmpty(date_inputed)){
                    Toast.makeText(getActivity(), " กรุณากรอกข้อมูลให้ครบ ! \n แล้วลองใหม่อีกครั้ง", Toast.LENGTH_LONG).show();
                }else{
                    Integer compiled_length = Integer.valueOf(length_inputed);
                    calculate(compiled_length);
                }
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_date.setText("");
                spinner_length.setSelection(0);
            }
        });

        return view;
    }

    public void datepicker(){
        //user_date.setInputType(InputType.TYPE_NULL);
        new DatePickerDialog(getActivity(), date,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    Calendar myCalendar = Calendar.getInstance();
    Calendar todayCal = Calendar.getInstance();
    Calendar periodCal = Calendar.getInstance();
    Calendar dueCal = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here //"MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        //DateFormat df_medium = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT); // DATETIME
        DateFormat mydate = DateFormat.getDateInstance(DateFormat.LONG); // DATE

        //Toast.makeText(getActivity(),sdf.format(myCalendar.getTime()),Toast.LENGTH_SHORT).show();
        user_date.setText(sdf.format(myCalendar.getTime()));
        data = user_date.getText().toString();
        user_date.setText(mydate.format(myCalendar.getTime()));

    }



    public void calculate(int length_due){
        String myFormats = "MM/dd/yy";
        Date todaydate = todayCal.getTime();
        SimpleDateFormat fds = new SimpleDateFormat(myFormats, Locale.US);
        DateFormat mydateformat = DateFormat.getDateInstance(DateFormat.LONG); // DATE
        //SimpleDateFormat sdfout = new SimpleDateFormat("EEEE, MMM, d, yyyy");

        String periodDate = data;
        //String periodDate = data;
        Date perDate = null;
        try {
            perDate = fds.parse(periodDate);
            calduelength = length(length_due);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        periodCal.setTime(perDate);
        dueCal.setTime(perDate);
        //dueCal.add(dueCal.DATE, 280);

        dueCal.add(dueCal.DATE,280+calduelength);

        Date dueAsDate = dueCal.getTime();

        long between = todaydate.getTime() - perDate.getTime();
        long inDays = TimeUnit.DAYS.convert(between, TimeUnit.MILLISECONDS);
        int weeks = (int)(inDays/7);
        if (inDays < 31) { // old = 31
            sweet("ยังไม่สามารถคำนวนได้");
        }else if(inDays > 280){
            sweet("คุณไม่ได้ตั้งครรภ์");
        } else {
            String toWrite;
            String thing;
            switch (weeks) {
                case 1:
                case 2:
                case 3:
                    thing = "เมล็ดพืชขนาดเล็ก"; // a small seed
                    break;
                case 4:
                    //thing = "a poppy seed";
                    thing = "เมล็ดงาดำ";
                    break;
                case 5:
                    thing = "เมล็ดพริกไทย";
                    break;
                case 6:
                    thing = "เมล็ดทับทิม";
                    break;
                case 7:
                    thing = "บลูเบอร์รี่ขนาดเล็ก";
                    break;
                case 8:
                    thing = "ถั่วแครนเบอร์รี่";
                    break;
                case 9:
                    thing = "ลูกเชอร์รี่";
                    break;
                case 10:
                    thing = "ส้มจี๊ด"; //kumquat
                    break;
                case 11:
                    thing = "กะหล่ำดาว";
                    break;
                case 12:
                    thing = "เสาวรส";
                    break;
                case 13:
                    thing = "มะนาว";
                    break;
                case 14:
                    thing = "ผลไม้เนกเตอริน";
                    break;
                case 15:
                    thing = "แอบเปิ้ล";
                    break;
                case 16:
                    thing = "อะโวคาโด";
                    break;
                case 17:
                    thing = "ลูกแพร์";
                    break;
                case 18:
                    thing = "ขวดแยม";
                    break;
                case 19:
                    thing = "มะม่วง";
                    break;
                case 20:
                    thing = "อาติโช๊ค";
                    break;
                case 21:
                    thing = "แครอท";
                    break;
                case 22:
                    thing = "มะละกอ";
                    break;
                case 23:
                    thing = "มะเขือยาว";
                    break;
                case 24:
                    thing = "หูของข้าวโพด";
                    break;
                case 25:
                    thing = "โอ๊กสควอช"; //an acorn squash
                    break;
                case 26:
                    thing = "บวบ";
                    break;
                case 27:
                    thing = "กะหล่ำดอก";
                    break;
                case 28:
                    thing = "ฟักทอง"; // a kabocha squash
                    break;
                case 29:
                    thing = "ฟักบัตเตอร์นัท"; // a butternut squash
                    break;
                case 30:
                    thing = "กะหล่ำปลีขนาดใหญ่";
                    break;
                case 31:
                    thing = "พวงของกระเทียม";
                    break;
                case 32:
                    thing = "กะหล่ำปลี";
                    break;
                case 33:
                    thing = "สับปะรด";
                    break;
                case 34:
                    thing = "ลูกแคนตาลูป";
                    break;
                case 35:
                    thing = "ลูกเมลอนขนาดเล็ก";
                    break;
                case 36:
                    thing = "ลูกเมลอนขนาดใหญ่";
                    break;
                case 37:
                    thing = "ใบผักสวิสชาร์ท";
                    break;
                case 38:
                    thing = "ก้านของผักชนิดหนึ่ง";
                    break;
                case 39:
                    thing = "แตงโมขนาดเล็ก";
                    break;
                case 40:
                case 41:
                case 42:
                    thing = "แตงโม";
                    break;
                default:
                    thing = "ไม่พบ";

            }

            String finalDate = mydateformat.format(dueAsDate);
            DateFormat aaa = new SimpleDateFormat("yyyy-MM-dd");
            String today_date = aaa.format(dueAsDate);

            //toWrite = "Congratulations! You baby is due on or around\n "+finalDate+"\n Right Now "+ "you're about "+weeks+" weeks pregnant \n and your baby is the size of "+thing+" !";
            toWrite = " Congratulations ! \n Your Baby is due on : "+finalDate+"\n You're : "+weeks+" weeks pregnant"+"\n Baby Size : "+thing;



            String atoday_date = dateThai(today_date);
            String date_ok = "คุณจะคลอดประมาณวันที่ "+atoday_date;

            String how_long = "คุณตั้งครรภ์มานาน "+weeks+" สัปดาห์แล้ว";
            String baby_size = "ขนาดของลูก : "+thing;

            showdata(date_ok,how_long,baby_size);


        }
    }

    public int length(int length){
        int type = length;
        // Toast.makeText(getActivity(),"io_length : "+io_length,Toast.LENGTH_SHORT).show();
        //  int ok_length = 0;
        //String[] type = {"28"};
//        for(int x=0; x<40; x++){
//            ok_length = x;
//        }
        if(type == 28){
            ok_length = 0;
        }else if(type == 29){
            ok_length = 1;
        }else if(type == 30){
            ok_length = 2;
        }else if(type == 31){
            ok_length = 3;
        }else if(type == 32){
            ok_length = 4;
        }else if(type == 33){
            ok_length = 5;
        }else if(type == 34){
            ok_length = 6;
        }else if(type == 35){
            ok_length = 7;
        }else if(type == 36){
            ok_length = 8;
        }else if(type == 37){
            ok_length = 9;
        }else if(type == 38){
            ok_length = 10;
        }else if(type == 39){
            ok_length = 11;
        }else if(type == 40){
            ok_length = 12;
        }else{
            ok_length = 0;
        }
        //Toast.makeText(getActivity()," Original : "+type+"\n Modified : "+ok_length,Toast.LENGTH_LONG).show();
        return ok_length;
    }

    public void showdata(String date, String howl_long, String baby_size){
        SweetAlertDialog alertDialog = new SweetAlertDialog(getActivity());
        alertDialog.setTitleText("วันคลอดของฉัน");
        alertDialog.setContentText(""+date+"\n\n"+howl_long+"\n\n"+baby_size);
        alertDialog.setConfirmText("ปิดหน้าต่างนี้");
        alertDialog.setCancelable(false);
        alertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
                user_date.setText("");
                spinner_length.setSelection(0);
            }
        });
        alertDialog.show();
        WindowManager.LayoutParams lp = alertDialog.getWindow().getAttributes();
        lp.dimAmount = 1f;
        alertDialog.getWindow().setAttributes(lp);
        alertDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

    }

    public static String dateThai(String strDate){
        String Months[] = {
                "มกราคม", "กุมภาพันธ์", "มีนาคม", "เมษายน",
                "พฤษภาคม", "มิถุนายน", "กรกฎาคม", "สิงหาคม",
                "กันยายน", "ตุลาคม", "พฤศจิกายน", "ธันวาคม"};

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        int year=0,month=0,day=0;
        try {
            Date date = df.parse(strDate);
            Calendar c = Calendar.getInstance();
            c.setTime(date);

            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DATE);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return String.format("%s %s %s", day,Months[month],year+543);
    }

    public void sweet(String title){
        SweetAlertDialog alertDialog = new SweetAlertDialog(getActivity(),SweetAlertDialog.WARNING_TYPE);
        alertDialog.setTitleText(title);
        alertDialog.setConfirmText("ปิดหน้าต่างนี้");
        alertDialog.setCancelable(false);
        alertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
                user_date.setText("");
                spinner_length.setSelection(0);
            }
        });
        alertDialog.show();
        WindowManager.LayoutParams lp = alertDialog.getWindow().getAttributes();
        lp.dimAmount = 1f;
        alertDialog.getWindow().setAttributes(lp);
        alertDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
    }

}
