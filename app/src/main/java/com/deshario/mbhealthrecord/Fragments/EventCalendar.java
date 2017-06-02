package com.deshario.mbhealthrecord.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.deshario.mbhealthrecord.MainActivity;
import com.deshario.mbhealthrecord.R;
import com.squareup.timessquare.CalendarPickerView;

import java.util.Calendar;
import java.util.Date;

import static android.widget.Toast.LENGTH_SHORT;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventCalendar extends Fragment {

    CalendarPickerView calendar;


    public EventCalendar() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_calendar, container, false);
        ((MainActivity) getActivity()).setActionBarTitle("ตารางพบแพทย์");

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toast = "วันที่ : " + calendar.getSelectedDate().getTime();
                Toast.makeText(getActivity(),""+toast, LENGTH_SHORT).show();

                Calendar cal = Calendar.getInstance();
                Intent intent = new Intent(Intent.ACTION_EDIT);
                intent.setType("vnd.android.cursor.item/event");
                //intent.putExtra("beginTime", date);
                //intent.putExtra("allDay", true);
                //intent.putExtra("rrule", "FREQ=YEARLY");
                //intent.putExtra("endTime", date);
                intent.putExtra("title", "");

                //  intent.putExtra("eventLocation", blankevent.EventLocation);
                intent.putExtra("description", "นัดหมายจากแอพบันทึกสุขภาพแม่และเด็ก");
                intent.putExtra("beginTime",  toast);
                // intent.putExtra("endTime",fulldate.getTime()+60*60*1000);


                startActivity(intent);
            }
        });


        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

        calendar = (CalendarPickerView)view.findViewById(R.id.calendar_view);
        Date today = new Date();

        calendar.init(today, nextYear.getTime()).withSelectedDate(today);
        //calendar.init(today, nextYear.getTime(), new Locale("th", "TH")) .withSelectedDate(today);



//        calendar.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
//            @Override
//            public void onDateSelected(Date date) {
////                String toast = "วันที่ : " + calendar.getSelectedDate().getTime();
////                Toast.makeText(getActivity(),""+toast, LENGTH_SHORT).show();
////
////                Calendar cal = Calendar.getInstance();
////                Intent intent = new Intent(Intent.ACTION_EDIT);
////                intent.setType("vnd.android.cursor.item/event");
////                intent.putExtra("beginTime", date);
////                //intent.putExtra("allDay", true);
////                //intent.putExtra("rrule", "FREQ=YEARLY");
////                intent.putExtra("endTime", date);
////                intent.putExtra("title", "");
////
////              //  intent.putExtra("eventLocation", blankevent.EventLocation);
////                intent.putExtra("description", "นัดหมายจากแอพบันทึกสุขภาพแม่และเด็ก");
////                intent.putExtra("beginTime",  toast);
////               // intent.putExtra("endTime",fulldate.getTime()+60*60*1000);
////
////
////                startActivity(intent);
//            }
//
//            @Override
//            public void onDateUnselected(Date date) {
//
//            }
//        });

        return view;
    }



}
