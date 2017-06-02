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
import com.deshario.mbhealthrecord.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Quarter_Month1 extends Fragment {

    View positiveAction;
    TextView mytitle,subtitle,item1,item2,item3,item4;
    ImageView dataimg;

    public Quarter_Month1() {
        // Required empty public constructor
    }

    int[] listviewImage = new int[]{
            R.mipmap.num6,
            R.mipmap.num7,
            R.mipmap.num8,
            R.mipmap.num9,
            R.mipmap.num10,
            R.mipmap.num11,
            R.mipmap.num12,
            R.mipmap.flower,
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_one, container, false);

//        title = (TextView)view.findViewById(R.id.title);
//        subtitle = (TextView)view.findViewById(R.id.subtitle);
//        item1 = (TextView)view.findViewById(R.id.item1);
//        item2 = (TextView)view.findViewById(R.id.item2);
//        item3 = (TextView)view.findViewById(R.id.item3);
//        item4 = (TextView)view.findViewById(R.id.item4);

        ArrayList<String> listviewTitle = new ArrayList<String>();
        listviewTitle.add("สัปดาห์ที่ 6");
        listviewTitle.add("สัปดาห์ที่ 7");
        listviewTitle.add("สัปดาห์ที่ 8");
        listviewTitle.add("สัปดาห์ที่ 9");
        listviewTitle.add("สัปดาห์ที่ 10");
        listviewTitle.add("สัปดาห์ที่ 11");
        listviewTitle.add("สัปดาห์ที่ 12");
        listviewTitle.add("โภชนาการสำหรับไตรมาสที่ 1");

        ArrayList<String> myArrList = new ArrayList<String>();
        for(int x=0; x<listviewTitle.size(); x++){
            myArrList.add("หมวด : ปฏิทินการตั้งครรภ์");
        }

        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < listviewTitle.size(); i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("title", listviewTitle.get(i));
            //hm.put("description", myArrList.get(i));
            hm.put("image", Integer.toString(listviewImage[i])); //listviewImage[i]
            aList.add(hm);
        }

        String[] from = {"image", "title"}; // "description"
        int[] to = {R.id.listview_image, R.id.listview_item_title}; // R.id.description

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
        if(position == 7){
            title = title;
        }else{
            title = "อายุครรภ์"+title;
        }
            MaterialDialog dialog = new MaterialDialog.Builder(getContext())
                    .title(title)
                    .customView(R.layout.week6, true)
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
            subtitle = (TextView)dialog.getCustomView().findViewById(R.id.subtitle);
            item1 = (TextView)dialog.getCustomView().findViewById(R.id.item1);
            item2 = (TextView)dialog.getCustomView().findViewById(R.id.item2);
            item3 = (TextView)dialog.getCustomView().findViewById(R.id.item3);
            item4 = (TextView)dialog.getCustomView().findViewById(R.id.item4);
            dataimg = (ImageView)dialog.getCustomView().findViewById(R.id.img);

            String fetched_datas[] = intializedata(position);
            Integer draw_img = Integer.valueOf(fetched_datas[6]);
            dataimg.setBackgroundResource(draw_img);
            //mytitle.setText(fetched_datas[0]);
            subtitle.setText(fetched_datas[1]);
            item1.setText(fetched_datas[2]);
            item2.setText(fetched_datas[3]);
            item3.setText(fetched_datas[4]);
            item4.setText(fetched_datas[5]);
            dialog.show();
    }


    //public void intializedata(String d_title, String d_subtitle, String d_item1, String d_item2, String d_item3){
    public String[] intializedata(int position){
        String title = null;
        String subtitle = null;
        String item1 = null;
        String item2 = null;
        String item3 = null;
        String item4 = null;
        int draw = 0;
        switch (position){
            case 0:
                title = "อายุครรภ์สัปดาห์ที่ 6";
                subtitle = "พัฒนาการของลูกน้อยในครรภ์";
                item1 = "๐ ตอนนี้ลูกมีความสูงประมาณ 8 มิลลิเมตร. มีขนาดเท่ากับลูกอ๊อดตัวเล็กๆ";
                item2 = "๐ หัวใจเริ่มเต้นประมาณ 100–130 ครั้งต่อนาที เลือดเริ่มไหลเวียน";
                item3 = "๐ ปุ่มเล็กๆที่จะกลายเป็นแขนขาเริ่มเป็นรูปร่าง";
                item4 = "๐ สามารถตรวจคลื่นสมองได้ตั้งแต่วันที่ 40 เนื้อเยื่อสมองเติบโตขึ้นอย่างรวดเร็ว";
                draw = R.drawable.traimas1;
                break;
            case 1:
                title = "อายุครรภ์สัปดาห์ที่ 7";
                subtitle = "พัฒนาการของลูกน้อยในครรภ์";
                item1 = "๐ ขณะนี้ลูกมีส่วนสูงประมาณ 12 มิลลิเมตร";
                item2 = "๐ ระบบการย่อยอาหารกำลังพัฒนา ทางเดินหายใจเล็กๆ เริ่มปรากฏ แขนขาเริ่มเป็นรูปร่าง เริ่มดูเป็นเด็กทารกขึ้นอีกเล็กน้อย";
                item3 = "๐ เปลือกตา นิ้วเท้า และนิ้วมือก็เริ่มเป็นรูปร่างแม้จะมีพังผืดหนาๆ เชื่อมอยู่";
                item4 = "๐ เริ่มเห็นรูจมูกชัดเจนขึ้น และเริ่มเห็นปลายจมูก";
                draw = R.drawable.traimas1;
                break;
            case 2:
                title = "อายุครรภ์สัปดาห์ที่ 8";
                subtitle = "พัฒนาการของลูกน้อยในครรภ์";
                item1 = "๐ ตอนนี้ลูกมีความสูงประมาณ 1.6 เซนติเมตร";
                item2 = "๐ ตา ปาก และจมูกจะเริ่มชัดเจนยิ่งขึ้น";
                item3 = "๐ ปุ่มฟันน้ำนมเริ่มเป็นรูปร่าง เซลล์กระดูกก็เริ่มเป็นรูปร่างขึ้นมาแทนที่กระดูกอ่อน ข้อต่อเล็กๆ เริ่มเจริญเติบโตขึ้น";
                item4 = "๐ อวัยวะทุกส่วนจะเริ่มเข้าที่ และลูกจะเริ่มมีลายนิ้วมือในระยะนี้ ";
                draw = R.drawable.traimas1;
                break;
            case 3:
                title = "อายุครรภ์สัปดาห์ที่ 9";
                subtitle = "พัฒนาการของลูกน้อยในครรภ์";
                item1 = "๐ ตอนนี้ลูกจะสูงประมาณ 2 เซนติเมตร. แขนและขาของลูกจะเจริญเติบโตอย่างรวดเร็วและเคลื่อนไหวไปมาได้แล้ว";
                item2 = "๐ เปลือกตาเริ่มเป็นรูปร่าง (แต่จะยังเปิดตาไม่ได้) และเริ่มเห็นการเจริญเติบโตของหูมากขึ้น";
                item3 = "๐ นิ้วของลูกจะแยกจากกัน ปุ่มฟันเติบโต";
                item4 = "๐ ลูกจะเคลื่อนไหวมากขึ้นโดยหันหัว งอนิ้วเท้า และเปิดปิดปากน้อยๆ ของเขา ";
                draw = R.drawable.traimas1;
                break;
            case 4:
                title = "อายุครรภ์สัปดาห์ที่ 10";
                subtitle = "พัฒนาการของลูกน้อยในครรภ์";
                item1 = "๐ ตอนนี้ลูกจะมีความสูงประมาณ 3 เซนติเมตร";
                item2 = "๐ ศีรษะดูมีขนาดใหญ่มากเนื่องจากสมองของลูกเติบโตรวดเร็วกว่าส่วนอื่นๆของร่างกาย";
                item3 = "๐ นิ้วเท้าเริ่มแยกเป็นนิ้ว ลูกเคลื่อนไหวมากขึ้นในแต่ละวัน และจะเริ่มหลับๆตื่นๆพร้อมทั้งบริหารกล้ามเนื้อของตน";
                item4 = "๐ แขนจะงอบริเวณที่เป็นข้อศอก และจะเห็นกระดูกสันหลังของลูกอย่างชัดเจนผ่านผิวหนัง";
                draw = R.drawable.traimas1;
                break;
            case 5:
                title = "อายุครรภ์สัปดาห์ที่ 11";
                subtitle = "พัฒนาการของลูกน้อยในครรภ์";
                item1 = "๐ ตอนนี้ลูกสูงประมาณ 4 เซนติเมตร";
                item2 = "๐ สมอง ปอด ตับ และไตจะยังมีขนาดเล็กมาก";
                item3 = "๐ ลูกจะดูด กลืน และหาวได้แล้ว นอกจากนี้ยังสามารถ‘หายใจ’เอาน้ำคร่ำเข้าไปและถ่ายปัสสาวะด้วย";
                item4 = "๐ นิ้วมือของลูกแยกกันแล้ว และในไม่ช้าก็จะกำและแบมือได้";
                draw = R.drawable.traimas1;
                break;
            case 6:
                title = "อายุครรภ์สัปดาห์ที่ 12";
                subtitle = "พัฒนาการของลูกน้อยในครรภ์";
                item1 = "๐ ตอนนี้ลูกมีอวัยวะครบ และกำลังสร้างอวัยวะเพศภายใน";
                item2 = "๐ ลำตัวยาวประมาณ 6 เซนติเมตร . ใบหน้าเริ่มมีเค้าโครงมากขึ้น และตาเริ่มเคลื่อนไปอยู่ในตำแหน่งบนใบหน้าและหูเริ่มอยู่ด้านข้างของศีรษะ";
                item3 = "๐ ผมของลูกเริ่มยาวขึ้น และมีเล็บอ่อนๆขึ้นที่นิ้วมือและนิ้วเท้า ในขณะที่ปากมีปุ่มเล็กๆ 20 ปุ่มซึ่งจะกลายเป็นฟันในอนาคต";
                item4 = "๐ ลูกอาจจะเริ่มดูดนิ้วโป้งและเคลื่อนไหวมากขึ้น เส้นเสียงสมบูรณ์พร้อม แม้ว่าจะยังไม่เปล่งเสียงจนกว่าจะออกมาสู่โลกภายนอกใน 2-3วินาทีแรก";
                draw = R.drawable.traimas1;
                break;
            case 7:
                title = "==================================";
                subtitle = "ไตรมาสที่ 1 อาหารสร้างเนื้อเยื่อ และการเจริญเติบโตของลูกน้อย";
                item1 = "๐  ช่วง 1-3 เดือนแรกของการตั้งครรภ์ มีความสำคัญที่หญิงตั้งครรภ์ต้องปรับเปลี่ยนพฤติกรรมการกิน เพื่อเตรียมพร้อมสำหรับไตรมาสต่อไป อาหารจำเป็นสำหรับไตรมาสแรกนี้ จึงควรเป็นอาหารที่เสริมสร้างเนื้อเยื่อและการเจริญเติบโตของลูกน้อย ได้แก่ โปรตีน แคลเซียม ธาตุเหล็ก และโฟเลต";
                item2 = "๐ กินไข่วันละ 1 ฟอง เลือกเป็นไข่ต้ม ไข่ตุ๋น ถ้าเป็นไข่เจียว หรือไข่ดาว ควรปรุงเองโดยใช้น้ำมันน้อย ๆ";
                item3 = "๐ ดื่มนมวัวอุ่น ๆ สลับกับนมถั่วเหลือง ดื่มวันละ 1-2 แก้ว เพื่อเพิ่มแคลเซียม";
                item4 = "๐ เลือกกินอาหารที่มีธาตุเหล็กเป็นประจำทุกวัน เช่น เนื้อสัตว์สีแดง ตับสัตว์ ไข่แดง งาดำ ถั่วเมล็ดแห้ง เช่น ถั่วเขียว ถั่วแดง ถั่วดำ ผลไม้ เช่น ทับทิม พรุน ลูกเกด กล้วยตาก และผักใบเขียวเข้ม พวกคะน้า ตำลึง ผักหวาน บรอกโคลี และใบยอ" +
                        "\n ๐ กินโฟเลต มีมากในตับสัตว์ ผักใบเขียว ผลไม้สีเหลืองส้ม อย่าง แคนตาลูป ส้ม มะละกอ หน่อไม้ฝรั่ง และธัญพืชไม่ขัดสี เช่น ข้าวซ้อมมือ ขนมปังโฮลวีท ควรกินผักวันละ 2-3 ทัพพี และผลไม้ 2-3 ชนิด" +
                        "\n   หญิงตั้งครรภ์ควรได้รับโปรตีน แคลเซียม และธาตุเหล็กอย่างเพียงพอในทุกไตรมาส เพราะสำคัญต่อการเจริญเติบโตและการส่งผ่านออกซิเจนและอาหารไปสู่ลูก" +
                        "\n\n เมนูแนะนำ : หมูผัดบรอกโคลีใส่น้ำมันหอย ปรุงรสนิดหน่อย ก็ได้ความอร่อยและประโยชน์";
                draw = R.drawable.traimas1_food;
                break;
            default:
                title = " ";
                subtitle = " ";
                item1 = " ";
                item2 = " ";
                item3 = " ";
                item4 = " ";
        }

        return new String[] {title, subtitle, item1, item2, item3, item4,String.valueOf(draw)};

    }

}
