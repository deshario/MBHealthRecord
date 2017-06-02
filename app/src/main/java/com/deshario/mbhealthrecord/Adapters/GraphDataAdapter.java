package com.deshario.mbhealthrecord.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.deshario.mbhealthrecord.R;

/**
 * Created by Deshario on 1/25/2017.
 */


    public class GraphDataAdapter extends CursorAdapter {
        public GraphDataAdapter(Context context, Cursor cursor) {
            super(context, cursor, 0);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(R.layout.fragment_graph_data, parent, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {

            TextView weight_key = (TextView) view.findViewById(R.id.listview_item_title);
           // TextView bmi_key = (TextView) view.findViewById(R.id.listview_item_short_description);

            //int id_txt = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
            String weight = cursor.getString(cursor.getColumnIndexOrThrow("weight"));
            String bmi = cursor.getString(cursor.getColumnIndexOrThrow("height"));
            //String weekno_txt = cursor.getString(cursor.getColumnIndexOrThrow("weekno"));
           // int weekno_txt = cursor.getInt(cursor.getColumnIndexOrThrow("weekno"));

            // txt_id.setText(String.valueOf(id_txt));
            weight_key.setText("สัปดาห์ที่ "+weight);
           // bmi_key.setText("สัปดาห์ที่ "+bmi);

        }
    }