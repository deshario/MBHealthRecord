package com.deshario.mbhealthrecord.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.deshario.mbhealthrecord.Models.Weeks;

import java.util.ArrayList;

/**
 * Created by Deshario on 1/14/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "weeks.db";
    public static final String TABLE_NAME = "per_weeks";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_WEIGHT = "weight";
    public static final String COLUMN_HEIGHT = "height";
    public static final String COLUMN_WEEK = "weekno";


    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_WEIGHT + " INTEGER, " +
                COLUMN_HEIGHT + " INTEGER, " +
                COLUMN_WEEK + " INTEGER UNIQUE" +
                ");";

        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addWeeksData(Weeks data){
        ContentValues values = new ContentValues();
        values.put(COLUMN_WEIGHT, data.getWeight());
        values.put(COLUMN_HEIGHT, data.getHeight());
        values.put(COLUMN_WEEK, data.getWeek_no());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void updateweek(Weeks data){
        ContentValues values = new ContentValues();
        values.put(COLUMN_WEIGHT, data.getWeight());
        values.put(COLUMN_HEIGHT, data.getHeight());
        values.put(COLUMN_WEEK, data.getWeek_no());
        SQLiteDatabase db = getWritableDatabase();
        db.update(TABLE_NAME, values, COLUMN_ID + "= ?", new String[] { String.valueOf(data.getId())});
        db.close();
    }

    public void update_all_heights(double height){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_NAME + " SET " + COLUMN_HEIGHT + " =  " + height + ";");
        db.close();
    }

    public Cursor weekbyno(int weekno){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_WEEK + " = " + weekno + ";";
        Cursor c = db.rawQuery(query, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public Cursor getWeeksData() {
        SQLiteDatabase db = getReadableDatabase();
        String query = ("SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_WEEK + " + 0 ASC;");
        //String query = ("SELECT * FROM per_weeks ORDER BY weekno + 0 DESC;");
        Cursor c = db.rawQuery(query, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public ArrayList<String> getWeeksData_Arr(String txt, String field_name){ //  get weeks columns
        ArrayList<String> arraylist = new ArrayList<String>();
        SQLiteDatabase db = getReadableDatabase();
        String query = ("SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_WEEK + " + 0 ASC;");
        Cursor c = db.rawQuery(query, null);
        while(c.moveToNext()) {
            //arraylist.add("นำหนัก : "+c.getString());
            arraylist.add(txt+" : "+c.getString(c.getColumnIndexOrThrow(field_name)));
        }
        c.close();
        return arraylist;
    }

    public ArrayList<String> getWeeksData_Arr_INT(String field_name){ //  get weeks columns
        ArrayList<String> arraylist = new ArrayList<String>();
        SQLiteDatabase db = getReadableDatabase();
        String query = ("SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_WEEK + " + 0 ASC;");
        Cursor c = db.rawQuery(query, null);
        while(c.moveToNext()) {
            arraylist.add(c.getString(c.getColumnIndexOrThrow(field_name)));
        }
        c.close();
        return arraylist;
    }

    public ArrayList<Integer> getIntegerAllWeeks(String fieldname){
        ArrayList<Integer> weeks_data = new ArrayList<Integer>();
        SQLiteDatabase db = getReadableDatabase();
        String query = ("SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_WEEK + " + 0 ASC;");
        Cursor car = db.rawQuery(query, null);
        while(car.moveToNext()) {
            //arraylist.add("นำหนัก : "+c.getString());
            weeks_data.add(car.getInt(car.getColumnIndexOrThrow(fieldname)));
        }
        car.close();
        return weeks_data;
    }


    public Cursor getWeeksOnly() {
        SQLiteDatabase db = getReadableDatabase();
        String query = ("SELECT "+ COLUMN_WEEK +" FROM " + TABLE_NAME + " ORDER BY " + COLUMN_WEEK + " + 0 ASC;");
        //String query = ("SELECT weekno FROM per_weeks ORDER BY weekno + 0 ASC;");
        Cursor c = db.rawQuery(query, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public Cursor getWeeksWeight(int from_a, int to_b) {
        SQLiteDatabase db = getReadableDatabase();
        String query_stat = "SELECT "+COLUMN_WEIGHT+" FROM "+TABLE_NAME+" WHERE "+COLUMN_WEEK+" BETWEEN "+from_a+" AND "+to_b+" ;";
        //String selectQuery1 = "SELECT weight FROM per_weeks WHERE weekno BETWEEN 3 AND 6;"; // Return 1
       //String selectQuery1 = "SELECT * FROM per_weeks;"; // Returns all
        //Cursor c = db.rawQuery(selectQuery1, null);
        Cursor c = db.rawQuery(query_stat, null);
        if (c != null) {
            c.moveToFirst();
        }
        //db.close();
        return c;
    }

    public void deleteweek(int which_week){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_WEEK + " = " + which_week + ";");
        db.close();
    }

    public boolean uniqueweek(int week){
       SQLiteDatabase db = getWritableDatabase();
       String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_WEEK + " = " + week + ";";
       Cursor cursor = db.rawQuery(query, null);
       boolean exists = (cursor.getCount() > 0);
       return exists;
    }

}