package com.deshario.mbhealthrecord.Fragments;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.deshario.mbhealthrecord.Database.DBHelper;
import com.deshario.mbhealthrecord.MainActivity;
import com.deshario.mbhealthrecord.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.FillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.DecimalFormat;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class GraphFragment extends Fragment {
    private LineChart mChart;
    ArrayList < String > xVals;
    ArrayList < Entry > yVals;
    ArrayList < Entry > myVals;
    ArrayList < Entry > scanner;
    MainActivity home = new MainActivity();
    DBHelper dbHandler;

    ArrayList<Integer> data = new ArrayList<Integer>();
    ArrayList<Float> chk_max_val = new ArrayList<Float>();
    ArrayList<Float> chk_min_val = new ArrayList<Float>();
    ArrayList<Float> chk_user_value = new ArrayList<Float>();
    ArrayList<Float> max_colored = new ArrayList<Float>();

    TextView status;

    public GraphFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_graph, container, false); // Define View

        //((MainActivity) getActivity()).setActionBarTitle("กราฟประจำเดือนที่ "+month_no);

        mChart = (LineChart) view.findViewById(R.id.linechart); // Inflate the layout for this fragment
        status = (TextView)view.findViewById(R.id.graph_status);
        generate_graph();
        return view;
    }

    public void generate_graph(){
        mChart.setDrawGridBackground(false);
        setData(); // Add Data

        Legend leg = mChart.getLegend(); // Get the legend (only possible after setting data)
        leg.setForm(Legend.LegendForm.SQUARE); // modify the legend
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Kanit.ttf");

        // Description Text
        mChart.setDescription("กราฟโภชนาการสำหรับหญิงตั้งครรภ์");
        mChart.setDescriptionTextSize(15);
        mChart.setDescriptionTypeface(font);
        int Desccolor = Color.rgb(255,255,0);
        mChart.setDescriptionColor(Desccolor);
        mChart.setNoDataTextDescription("ไม่พบข้อมูล");

        // Enable Touch gestures
        mChart.setTouchEnabled(true); // Tapable Graph

        // Enable Scaling and Dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setScaleXEnabled(true);
        mChart.setScaleYEnabled(true);
        //mChart.setPinchZoom(true);
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines

        leftAxis.setAxisMaxValue(130f);
        leftAxis.setAxisMinValue(80f);
        //leftAxis.setYOffset(20f);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);
        //leftAxis.setDrawTopYLabelEntry(false);

        // limit lines are drawn behind data (and not on top)
        leftAxis.setDrawLimitLinesBehindData(true);

        mChart.getAxisRight().setEnabled(false);

        // mChart.animateX(2500, Easing.EasingOption.EaseInOutQuart); // EaseInOutQuart // Animation Start When Port
         //mChart.animateX(1500, Easing.EasingOption.EaseInOutBounce); // EaseInOutQuart // Animation Start When Port

        mChart.invalidate();
    }

    private ArrayList < String > setXAxisValues() { // X Value
        xVals = new ArrayList < String > ();
        for(int i=12; i<=40; i++){
            xVals.add(""+i);
        }
        return xVals;
    }

    private ArrayList < Entry > maximum_values() { // Data Value
        yVals = new ArrayList < Entry > ();
        float max_values[] = {
                98,98.5f,99.8f,100,101,101.3f,102,103,103.5f,104.2f,105.1f,106.8f,108,108.9f,109.8f,110.8f,111.8f,112.5f,
                113.5f,114.3f,115,116,117,117.7f,118,119,119.2f,119.8f,120
        };
        for(int i=0; i<max_values.length; i++){
            yVals.add(new Entry(max_values[i], i));
            chk_max_val.add(max_values[i]);
         }
        return yVals;
    }

    private ArrayList < Entry > colored_datas() {
        yVals = new ArrayList < Entry > ();
        for(int i=0; i<29; i++){
            yVals.add(new Entry(190+i,i));
        }
        return yVals;
    }


    private ArrayList < Entry > minimum_values() { // Data Value
        yVals = new ArrayList < Entry > ();
        float min_values[] = {88,88.5f,89,89.7f,90,90.7f,91,91.7f,92,92.6f,93,93.7f,94.4f,95,96.2f,96.8f,97.4f,98,98.7f,
                99.5f,100,100.7f,101.4f,102,102.7f,103.5f,104,104.7f,105.4f};
         for(int i=0; i<min_values.length; i++){
            yVals.add(new Entry(min_values[i], i));
            chk_min_val.add(min_values[i]);
         }
        return yVals;
    }



    public ArrayList < Entry > userdata() {
        DecimalFormat desharioformat = new DecimalFormat();
        desharioformat.applyPattern("0.00");
        myVals = new ArrayList < Entry > ();
        dbHandler = new DBHelper(getContext(), null, null, 1);
        ArrayList<Integer> weight = dbHandler.getIntegerAllWeeks("weight");
        ArrayList<Integer> height = dbHandler.getIntegerAllWeeks("height");
        int all_weight = weight.size();
        int all_height = height.size();

        if(all_weight == 0 || all_height ==0){
           // status.setText("ha");
        }else{
            ArrayList<Integer> weight_array = new ArrayList<Integer>();
            for(int j=0; j<all_weight; j++){
                double met = getMetersfromcm(height.get(j));
                double val = getweekvalue(weight.get(j),met); // get data for port
                //float longValue = Math.round(val);
                String portedvalue = desharioformat.format(val);
                float longValue = Float.valueOf(portedvalue);
                myVals.add(new Entry(longValue, j));
                chk_user_value.add(longValue);
            }
        }


            if(chk_user_value.size() <= 0){
                status.setText("เลือกแถบบันทึกน้ำหนักเพื่อเพิ่มน้ำหนัก");
                status.setTextSize(16);
            }else{
                float latset_data = chk_user_value.get(chk_user_value.size()-1); // value = 87
                int latset_data_pos = chk_user_value.indexOf(latset_data); // position of 87 is 1

                float userdata = chk_user_value.get(latset_data_pos); // 87
                float max_data = chk_max_val.get(latset_data_pos); //99
                float min_data = chk_min_val.get(latset_data_pos); // 85

                if(latset_data > chk_max_val.get(latset_data_pos)){
                    //status.setText("อ้วนแล้วนะครับ : "+latset_data);
                    status.setText("อ้วนแล้วนะครับ");
                }
                if(latset_data < chk_min_val.get(latset_data_pos)){
                    //status.setText("ผอมแล้วนะครับ : "+latset_data);
                    status.setText("ผอมแล้วนะครับ");
                }
                if(latset_data < chk_max_val.get(latset_data_pos) && latset_data > chk_min_val.get(latset_data_pos)){
                    //status.setText("user: "+latset_data+" max:"+chk_max_val.get(latset_data_pos)+" min:"+chk_min_val.get(latset_data_pos));
                    //status.setText("น้ำหนักอยูในเกณฑ์ปกตินะครับ : "+latset_data);
                    status.setText("น้ำหนักอยูในเกณฑ์ปกตินะครับ");
                }
                if(latset_data == chk_max_val.get(latset_data_pos)){
                    //status.setText("น้ำหนักเริมจะมากแล้วนะครับ: "+latset_data);
                    status.setText("น้ำหนักเริมจะมากแล้วนะครับ");
                }
                if(latset_data == chk_min_val.get(latset_data_pos)){
                    //status.setText("น้ำหนักเริมจะน้อยแล้วนะครับ: "+latset_data);
                    status.setText("น้ำหนักเริมจะน้อยแล้วนะครับ");
                }
            }
        return myVals;
    }


    private void setData() {
        ArrayList < String > xVals = setXAxisValues();
        ArrayList < Entry > colored = colored_datas();
        ArrayList < Entry > max = maximum_values();
        ArrayList < Entry > min = minimum_values();
        ArrayList < Entry > users_data = userdata();

        // Create a dataset
        LineDataSet maxdata_bg;
        maxdata_bg = new LineDataSet(colored,"");
        maxdata_bg.setDrawFilled(true);
        int topcolor = Color.rgb(92,184,92);
        maxdata_bg.setFillColor(topcolor);
        maxdata_bg.setFillAlpha(200);
        int mdcolor = Color.rgb(255,255,0);
        maxdata_bg.setColor(mdcolor);
        maxdata_bg.setDrawValues(false);
        maxdata_bg.setLabel("อยู่ในเกณฑ์");

        LineDataSet maxdata;
        maxdata = new LineDataSet(max, "maximum");
        maxdata.setDrawFilled(true);
        int middlecolor = Color.rgb(255,255,0);
        maxdata.setFillColor(middlecolor);
        maxdata.setColor(topcolor);
        maxdata.setDrawCircles(false);
        maxdata.setFillAlpha(230);
        maxdata.setLineWidth(2f);
        maxdata.setDrawValues(false);
        maxdata.setLabel("3,000 กรัม");

        // Create a dataset
        LineDataSet mindata;
        mindata = new LineDataSet(min, "minimum"); // Give it a type
        mindata.setDrawFilled(true); // Set Background To Dataset
        int mincolor = Color.rgb(255,140,255);
        mindata.setFillColor(mincolor);
        int userdatacolor_ = Color.rgb(255,120,255);
        mindata.setColor(userdatacolor_);
        mindata.setDrawCircles(false);
        mindata.setFillAlpha(200);
        mindata.setLineWidth(2f);
        mindata.setDrawValues(false);
        // mindata.setDrawStepped(true);
        mindata.setLabel("2,500 กรัม");


        // Create a dataset
        LineDataSet users_weights;
        users_weights = new LineDataSet(users_data, "users");
        users_weights.setDrawFilled(false);
        int userdatacolor = Color.rgb(124,86,54);
        users_weights.setCircleColor(userdatacolor);
        users_weights.setColor(userdatacolor);
        users_weights.setLineWidth(1f);
        users_weights.setDrawValues(true); // show value
        users_weights.setDrawStepped(false); // show step anim
        users_weights.setLabel("ผู้ใช้งาน");


        ArrayList < ILineDataSet > dataSets = new ArrayList < ILineDataSet > ();
        dataSets.add(maxdata_bg); // Add the datasets
        dataSets.add(maxdata); // Add the datasets
        dataSets.add(mindata); // Add the datasets
        dataSets.add(users_weights); // Add the datasets


        // Create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);

        mChart.setData(data); // Set data

    }

    public void checkdata(){
        // Toast.makeText(getActivity(),"myVals : "+myVals,Toast.LENGTH_LONG).show();
    }

    public double getweekvalue(double weight, double height){
        double meters = (int) height / 100; // original double
        double a = weight * 100;
        double b = height * height * 21;
        double val = a/b;
        return val;
    }

    public double getMetersfromcm(int cms){
        double meters = (double) cms / 100;
        return meters;
    }


}