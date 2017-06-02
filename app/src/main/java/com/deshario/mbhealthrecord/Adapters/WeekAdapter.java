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
 * Created by Deshario on 1/14/2017.
 */

public class WeekAdapter extends CursorAdapter {
    public WeekAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.custom_listview, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView txt_name = (TextView) view.findViewById(R.id.name);

        //int id_txt = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
        //String weight_txt = cursor.getString(cursor.getColumnIndexOrThrow("weight"));
        //String height_txt = cursor.getString(cursor.getColumnIndexOrThrow("height"));
        //String weekno_txt = cursor.getString(cursor.getColumnIndexOrThrow("weekno"));
         int weekno_txt = cursor.getInt(cursor.getColumnIndexOrThrow("weekno"));

        // txt_id.setText(String.valueOf(id_txt));
        txt_name.setText("สัปดาห์ที่ "+weekno_txt);

    }
}