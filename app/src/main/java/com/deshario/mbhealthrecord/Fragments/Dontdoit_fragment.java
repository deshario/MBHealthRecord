package com.deshario.mbhealthrecord.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.deshario.mbhealthrecord.MainActivity;
import com.deshario.mbhealthrecord.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Dontdoit_fragment extends Fragment {

    View positiveAction;
    TextView item1;
    ImageView dataimg;
    int[] listviewImage = new int[]{
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
    };


    public Dontdoit_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_one, container, false);
        ((MainActivity) getActivity()).setActionBarTitle("12 ข้อห้าม");
        ArrayList<String> listviewTitle = new ArrayList<String>();
       // for(int xx=0; xx<10; xx++){
            listviewTitle.add("ห้ามเครียด");
            listviewTitle.add("ห้ามใส่ส้นสูง");
            listviewTitle.add("ห้ามกินยาพร่ำเพรื่อ");
            listviewTitle.add("ห้ามนอนดึก");
            listviewTitle.add("ห้ามอดอาหาร – ลดน้ำหนัก");
            listviewTitle.add("ห้ามผิดนัดฝากครรภ์");
            listviewTitle.add("ห้ามรับประทานอาหารที่ไม่มีประโยชน์");
            listviewTitle.add("ห้ามเดินทางไกลเมื่อใกล้คลอด");
            listviewTitle.add("ห้ามดื่มนมเยอะเกินวันละ 2 แก้ว");
            listviewTitle.add("ห้ามใส่ชุดรัดแน่นพอดีตัวจนเกินไป");
            listviewTitle.add("ห้ามสวนล้างช่องคลอด");
            listviewTitle.add("ห้ามยกของหนัก – ปีนป่ายที่สูง");
        //}


        ArrayList<String> myArrList = new ArrayList<String>();
        for(int x=0; x<listviewTitle.size(); x++){
            myArrList.add("หมวด : ข้อห้าม");
        }

        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < listviewTitle.size(); i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("title", listviewTitle.get(i));
            hm.put("description", myArrList.get(i));
            hm.put("image", Integer.toString(listviewImage[i])); //listviewImage[i]
            aList.add(hm);
        }

        String[] from = {"image", "title"};
        int[] to = {R.id.listview_image, R.id.listview_item_title};

        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), aList, R.layout.listview_custom, from, to);
        ListView listview = (ListView)view.findViewById(R.id.mylistview);
        listview.setAdapter(simpleAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3) {
                //String name = arg0.getItemAtPosition(position).toString();
                TextView temp = (TextView)arg1.findViewById(R.id.listview_item_title);
                String title = temp.getText().toString();
                showdata(position,title);
            }
        });
        return view;
    }

    public void showdata(int position, String title){
        // Toast.makeText(getActivity(),"Position : "+position,Toast.LENGTH_SHORT).show();
        boolean wrapInScrollView = true;
        //int openfile = fetchdata(position);
        MaterialDialog dialog = new MaterialDialog.Builder(getContext())
                .title(title)
                .customView(R.layout.dontolist, true)
                .positiveText("ปิด")
                .titleColorRes(R.color.primary_bootstrap)
                //.backgroundColorRes(R.color.primary_bootstrap)
                .positiveColorRes(R.color.colorPrimaryDark)
                .negativeColorRes(R.color.primary_bootstrap)
                //.widgetColorRes(R.color.warning_stroke_color)
                .canceledOnTouchOutside(false)
                .cancelable(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .build();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.dimAmount=0.5f;
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        positiveAction = dialog.getActionButton(DialogAction.POSITIVE);
        //mytitle = (TextView)dialog.getCustomView().findViewById(R.id.title);
        item1 = (TextView)dialog.getCustomView().findViewById(R.id.item1);
        dataimg = (ImageView)dialog.getCustomView().findViewById(R.id.img);

        String fetched_datas[] = intializedata(position);
        Integer draw_img = Integer.valueOf(fetched_datas[1]);
        dataimg.setBackgroundResource(draw_img);
        item1.setText(fetched_datas[0]);
        dialog.show();
    }

    public String[] intializedata(int position){
        String item1 = null;
        int draw = 0;
        switch (position){
            case 0:
                item1 = " ขณะที่ร่างกายมีความเครียด สมองจะสั่งการไปยังต่อมแอดรินาลีน ซึ่งอยู่เหนือบริเวณไตให้หลั่งฮอร์โมนความเครียดชนิดหนึ่งออกมา นั่นคือ ฮอร์โมนคอร์ติโซล (Coltisal) ส่งผลให้ร่างกายรู้สึกอยากอาหารทำให้คุณแม่ต้องรับประทานอาหารเข้าไปเป็นปริมาณมาก เครียดมากกินมาก อ้วนมาก  อย่างที่เขาว่า นอกจากนี้ ฮอร์โมนคอร์ติโซล ยังสามารถเข้าไปกระตุ้นให้ปริมาณของอินซูลินและเลพตินเพิ่มขึ้น โดยอินซูลินคือฮอร์โมนที่ถูกขับออกมาในตับอ่อน ซึ่งจะส่งผลให้รู้สึกอยากอาหารอยู่ตลอดเวลา ทำให้คุณแม่ตั้งครรภ์น้ำหนักพรุ่งพรวดได้ นอกจากนั้นยังพบว่า คุณแม่ที่มีความเครียดสูง ทารกก็รับรู้ได้ด้วยเช่นกัน หลังคลอดพบว่าทารกมักมีพฤติกรรมเลี้ยงยาก งอแง ";
                //draw = R.drawable.pregnancy_women;
                break;
            case 1:
                item1 = " การที่คุณแม่ตั้งครรภ์สวมรองเท้าส้นสูง ทำให้กล้ามเนื้อบริเวณน่อง ต้นขา เอว และหลัง เกิดความตึงเตรียด และเกิดอาการปวดตามมา นอกจากนั้นยังพบว่า รองเท้าส้นสูงทำให้จุดศูนย์ถ่วงของแม่ตั้งครรภ์เสียสมดุลอาจทำให้ลื่นล้มแท้งบุตรได้";
                // draw = R.drawable.pregnancy_women;
                break;
            case 2:
                item1 = " เป็นช่วงก่อร่างสร้างอวัยวะที่สำคัญๆของตัวอ่อน หากกินยาที่อันตราย ส่งผลต่อความพิการของทารกในครรภ์ได้เช่นแขนขาพิการ ปากแหว่ง เพดานโหว่ โดยเฉพาะยากลุ่มลดสิว  สำหรับยามัญประจำบ้านคุณแม่สามารถรับประทานได้ แต่ถ้ามีอาการรุนแรงแนะนำให้พบแพทย์เท่านั้น";
                // draw = R.drawable.pregnancy_women;
                break;
            case 3:
                item1 = " แม่ตั้งครรภ์ต้องนอนหลับพักผ่อนให้เพียงพออย่างน้อย 8 ชั่วโมง หากนอนกลางคืนไม่เพียงพอ ในช่วงกลางวันให้คุณแม่ตั้งครรภ์งีบ สัก 1 งีบ โดยเฉพาะอย่างยิ่งในระยะ 14 สัปดาห์แรกของการตั้งครรภ์มีงานวิจัยในสหรัฐอเมริกาพบว่า แม่ตั้งครรภ์นอนน้อยกว่าคืนละ 5 ชั่วโมงเสี่ยงต่อการถูกโรคต่าง ๆ รุมเร้าตลอดการตั้งครรภ์ เช่น ความดันโลหิตสูง  ซึ่งอาจเป็นอันตรายต่อคุณแม่และทารกในครรภ์ในที่สุด ";
                // draw = R.drawable.pregnancy_women;
                break;
            case 4:
                item1 = " หากคุณแม่ไม่มีข้อจำกัดทางการแพทย์ ว่าต้องจำกัดอาหาร ควรรับประทานอาหารให้ครบ และเพียงพอกับความต้องการของร่างกาย เพราะงานวิจัยพบว่า คุณแม่อดอาหารขณะตั้งครรภ์ ทารกมีอัตราคลอดก่อนกำหนดสูง และสมองพิการได้ เนื่องจาก ในอาหาร 5หมู่มีสารอาหารที่สำคัญต่อการบำรุงสมองทารก เช่น โฟเลตที่ได้จากผักผลไม้ และวิตามินต่างๆที่ช่วยในการสร้างอวัยวะสำคัญๆ คุณแม่ที่เกรงว่าจะอ้วน หลังคลอดแนะนำให้เลี้ยงลูกด้วยนมแม่อย่างเดียวรับรองน้ำหนักลดลงอย่างรวดเร็วแน่นอน";
                // draw = R.drawable.pregnancy_women;
                break;
            case 5:
                item1 = " คุณแม่ตั้งครรภ์ต้องตระหนักให้มากในเรื่องความสำคัญของการฝากครรภ์ เพราะร่างกายของแม่และลูกมีการเปลี่ยนแปลงตลอดเวลา หากพบความผิดปกติของมารดาหรือทารก แพทย์จะได้ให้การช่วยเหลือทันเวลา  หากพลาดนัด ควรไปพบแพทย์ในวันถัดไป  ";
                // draw = R.drawable.pregnancy_women;
                break;
            case 6:
                item1 = " อาหารไม่มีประโยชน์ที่ว่านี้ นอกจากจะทำลายสุขภาพแม่แล้ว อาหารบางอย่างยังทำลายสมองลูกด้วย ได้แก่ แอลกอฮอล์ กาเฟอีน หวานจัด มันจัด เผ็ดจัด ดิบๆสุกๆ ของหมักดอง อาหารกระป๋อง และผงชูรส ";
                // draw = R.drawable.pregnancy_women;
                break;
            case 7:
                item1 = " สำหรับคุณแม่ตั้งครรภ์ไตรมาสสุดท้ายโดยเฉพาะคุณแม่อายุครรภ์ 32 สัปดาห์ขึ้นไป ควรงดเดินทางไกล เพราะในช่วงเวลาดังกล่าวเสี่ยงต่อการติดเชื้อ คลอดก่อนกำหนดได้ง่ายๆ ";
                // draw = R.drawable.pregnancy_women;
                break;
            case 8:
                item1 = " คุณแม่หลายๆคนกระหน่ำดื่มนมเพื่อบำรุงครรภ์ตั้งแต่รู้ว่าเริ่มตั้งครรภ์ ตามหลักโภชนาการควรเริ่มดื่มนมบำรุงครรภ์เมื่อเข้าสู่ไตรมาสที่2ของการตั้งครรภ์ เพราะช่วงเวลาดังกล่าวทารกดึงแคลเซี่ยมจากคุณแม่ไปใช้มากขึ้นอาจทำให้คุณแม่สูญเสียเเคลเซี่ยมในร่างกายไปมากกว่าปกติ แต่… การดื่มนมวัววันละ 1 แก้ว หรือนมถั่วเหลืองวันละ 2แก้วก็เพียงพอแล้ว ผลงานการวิจัยยังพบว่า คุณแม่ตั้งครรภ์ที่ดื่มนมมากเกินความจำเป็น ทำให้ทารกมีความเสี่ยงต่อการแพ้ได้ง่ายเช่น แพ้โปรตีนในนมวัว เป็นต้น";
                // draw = R.drawable.pregnancy_women;
                break;
            case 9:
                item1 = " คุณแม่ตั้งครรภ์ควรสวมใส่เสื้อผ้าที่หลวมๆสบายๆ ระบายอากาศได้ดี เนื่องจากระหว่างตั้งครรภ์มีการเปลี่ยนแปลงระดับฮอร์โมนในร่างกายอย่างมากโดยเฉพาะฮอร์โมนเพศ ทำให้คุณแม่ตั้งครรภ์มีกลิ่นตัว กลิ่นอวัยะเพศที่แรงขึ้น การสวมใส่เสื้อผ้าที่รัดแน่น ทำให้เกิดกลิ่นอับมากขึ้น และยังทำให้ระบบไหลเวียนเลือดไม่ดี หายใจไม่สะดวกอาจหน้ามืดเป็นลมได้";
                // draw = R.drawable.pregnancy_women;
                break;
            case 10:
                item1 = " ในระหว่างตั้งครรภ์ มีการเปลี่ยนเเปลงฮอร์โมนส่งผลให้อวัยเพศมีกลิ่นมากขึ้น คุณแม่ตั้งครรภ์จึงทำการสวนล้างช่องคลอด และเลือกใช้ผลิตภัณฑ์กำจัดกลิ่น การกระทำดังกล่าวเป็นการนำพาเชื้อโรคเข้าสู่ช่องคลอดโดยตรง ปกติแล้วบริเวณจะมีเชื้อประจำถิ่น ที่คอยดักจับเชื้อโรคที่ผ่านเข้าไปในช่องคลอด คนปกติจึงไม่เกิดการติดเชื้อได้ง่ายๆ แต่ถ้าเมื่อไหร่ มีการสวนล้างช่องคลอดหรือใช้น้ำยารุนแรง เชื้อประจำถิ่นที่เคยมีก็จะตายไป ทำให้เชื้อต่างๆวิ่งเข้าสู่ช่องคลอด โพลงมดลูกเกิดการติดเชื้อทั้งแม่และลูกได้อย่างง่ายดาย";
                // draw = R.drawable.pregnancy_women;
                break;
            case 11:
                item1 = " ขณะยกของที่มีน้ำหนักมาก แรงดันจะไปอยู่ที่มดลุกเพราะเป็นจุดศุูนย์กลาง แรงดันขณะยกของเสี่ยงต่อการแท้งบุตรได้ การยกของหนัก – ปีนป่ายที่สูง เสียการทรงตัวทำให้ล้มกระแทกได้เช่นกัน";
                // draw = R.drawable.pregnancy_women;
                break;
            default:
                item1 = "";
                //draw = R.drawable.pregnancy_women;
        }

        return new String[] {item1, String.valueOf(draw)};

    }



}
