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
public class Quarter_Month2 extends Fragment {

    View positiveAction;
    TextView mytitle,subtitle,item1,item2,item3,item4;
    ImageView dataimg;
    int[] listviewImage = new int[]{
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
            R.mipmap.flower,
    };

    public Quarter_Month2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_one, container, false);
        ArrayList<String> listviewTitle = new ArrayList<String>();
        listviewTitle.add("สัปดาห์ที่ 13");
        listviewTitle.add("สัปดาห์ที่ 14");
        listviewTitle.add("สัปดาห์ที่ 15");
        listviewTitle.add("สัปดาห์ที่ 16");
        listviewTitle.add("สัปดาห์ที่ 17");
        listviewTitle.add("สัปดาห์ที่ 18");
        listviewTitle.add("สัปดาห์ที่ 19");
        listviewTitle.add("สัปดาห์ที่ 20");
        listviewTitle.add("สัปดาห์ที่ 21");
        listviewTitle.add("สัปดาห์ที่ 22");
        listviewTitle.add("สัปดาห์ที่ 23");
        listviewTitle.add("สัปดาห์ที่ 24");
        listviewTitle.add("สัปดาห์ที่ 25");
        listviewTitle.add("โภชนาการสำหรับไตรมาสที่ 2");

        ArrayList<String> myArrList = new ArrayList<String>();
        for(int x=0; x<listviewTitle.size(); x++){
            myArrList.add("หมวด : ปฏิทินการตั้งครรภ์");
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
        if(position == 13){
            title = title;
        }else{
            title = "อายุครรภ์"+title;
        }
            MaterialDialog dialog = new MaterialDialog.Builder(getContext())
                    .title(title)
                    .customView(R.layout.week13, true)
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
           // dataimg.setBackgroundResource(R.drawable.pregnancy_women);
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
                title = "อายุครรภ์สัปดาห์ที่ 13";
                subtitle = "พัฒนาการของลูกน้อยในครรภ์";
                item1 = "๐ กระดูกอ่อนค่อยๆเจริญเติบโตแล้ว ตอนนี้ขากรรไกรมีเหง้าฟันครบ 32 เหง้า ";
                item2 = "๐ ความสูงประมาณ 8 เซนติเมตร. โดยจะเคลื่อนไหวและยืดตัวอย่างสม่ำเสมอ รวมทั้งเตะและหมุนตัวด้วย ";
                item3 = "๐ ไตของลูกจะเริ่มทำงานและเริ่มส่งปัสสาวะไปยังกระเพาะปัสสาวะ";
                item4 = "๐ ลูกเริ่มดูดปากกลืนน้ำคร่ำและปล่อยเป็นปัสสาวะออกมา เล็บและผมยาวขึ้น";
                draw = R.drawable.pregnant_13;
                break;
            case 1:
                title = "อายุครรภ์สัปดาห์ที่ 14";
                subtitle = "พัฒนาการของลูกน้อยในครรภ์";
                item1 = "๐ ตอนนี้ลูกมีความสูงประมาณ 9 เซนติเมตร";
                item2 = "๐ เริ่มฝึกจังหวะการหายใจ เริ่มมีคาง หน้าผาก และจมูกชัดเจนขึ้น สามารถหันศีรษะ และทำหน้าผากย่นได้";
                item3 = "๐ รับรู้เสียงและตอบสนองต่อการสัมผัสได้";
                item4 = "๐ แขนขาเห็นเป็นรูปร่างชัดเจน ไตเริ่มทำงาน";
                draw = R.drawable.pregnant_14;
                break;
            case 2:
                title = "อายุครรภ์สัปดาห์ที่ 15";
                subtitle = "พัฒนาการของลูกน้อยในครรภ์";
                item1 = "๐ ตอนนี้ลูกน้อยจะมีความยาวประมาณ 12 เซนติเมตร";
                item2 = "๐ กระดูกจะเริ่มแข็งขึ้นและมีขนอ่อนที่เรียกว่าลานูโก (lanugo) ปรากฏบนผิวหนังซึ่งยังบางและใสมาก ";
                item3 = "๐ ลูกจะมีปุ่มรับรสและจะเริ่มแสดงสีหน้าต่างๆ เนื่องจากกล้ามเนื้อใบหน้าเจริญเติบโตขึ้น ขนตาและขนคิ้วก็เริ่มเป็นรูปร่างแล้ว ดูเป็นเด็กทารกมากขึ้น ";
                item4 = "๐ ช่วงนี้ ลูกจะดูดกลืนน้ำคร่ำซึ่งช่วยให้ปอดเจริญเติบโต ส่วนขาจะยาวกว่าแขนและสามารถเคลื่อนไหวข้อต่อได้ทั้งหมดเพื่อให้สามารถขยับตัวได้ในครรภ์ ";
                draw = R.drawable.pregnant_15;
                break;
            case 3:
                title = "อายุครรภ์สัปดาห์ที่ 16";
                subtitle = "พัฒนาการของลูกน้อยในครรภ์";
                item1 = "๐ ตอนนี้ลูกมีความสูงประมาณ 15 เซนติเมตร แล้ว";
                item2 = "๐ มีเล็บมือและเล็บเท้าเล็กๆที่ปลายนิ้วมือและเท้าซึ่งกำลังเติบโตขึ้นเรื่อยๆ";
                item3 = "๐ ช่วงนี้ลูกสามารถหมุนศีรษะและบริหารกล้ามเนื้อ 40 มัดได้แล้วแต่ยังเร็วไปที่คุณแม่จะรู้สึก";
                item4 = "๐ เปลือกตาของลูกจะยังปิดอยู่ แต่เขาสามารถมองเห็นแสงภายนอกได้ด้วย";
                draw = R.drawable.pregnant_16;
                break;
            case 4:
                title = "อายุครรภ์สัปดาห์ที่ 17";
                subtitle = "พัฒนาการของลูกน้อยในครรภ์";
                item1 = "๐ ตอนนี้ลูกน้อยมีลำตัวยาวประมาณ 17 เซนติเมตร";
                item2 = "๐ เริ่มขมวดคิ้วได้ แขนและขาสมบูรณ์มากขึ้นและเริ่มมีผิวหนังและกล้ามเนื้อ";
                item3 = "๐ ไตทำงานได้ดีแล้ว จึงปัสสาวะหลายครั้งในแต่ละวัน ปัสสาวะของลูกจะถูกกรองกลับไปที่ตัวคุณแม่ผ่านรก ร่างกายของคุณแม่จะจัดการขับของเสียนั้น";
                item4 = "๐ การได้ยินของลูกก็ดีขึ้นมาก คุณแม่จะสังเกตุเห็นว่าลูกตอบสนองต่อเสียงจากโลกภายนอกได้";
                draw = R.drawable.pregnant_17;
                break;
            case 5:
                title = "อายุครรภ์สัปดาห์ที่ 18";
                subtitle = "พัฒนาการของลูกน้อยในครรภ์";
                item1 = "๐ ตอนนี้ลำตัวยาวประมาณ 18 เซนติเมตร แล้ว";
                item2 = "๐ ลูกดูดหัวแม่มือได้แล้วและกำลังฝึกหายใจเอาน้ำคร่ำเข้าปอดและหายใจออกโดยการปล่อยน้ำคร่ำออกมา";
                item3 = "๐ ผิวหนังของลูกยังย่นอยู่เพราะยังไม่มีการสร้างไขมันใต้ผิวหนัง";
                item4 = "๐ ลูกเริ่มมีพัฒนาการของนิสัยในการนอน";
                draw = R.drawable.pregnant_18;
                break;
            case 6:
                title = "อายุครรภ์สัปดาห์ที่ 19";
                subtitle = "พัฒนาการของลูกน้อยในครรภ์";
                item1 = "๐ สัปดาห์นี้ลูกจะมีน้ำหนักเพิ่มขึ้นค่อนข้างเร็ว";
                item2 = "๐ เซลล์สมองเพิ่มจำนวนขึ้นอย่างรวดเร็ว ประสาทไขสันหลังเริ่มหนาตัวขึ้น";
                item3 = "๐ ต่อมรับรสของลูกเจริญเต็มที่และเริ่มรับรู้รสหวานของน้ำคร่ำ";
                item4 = "๐ การได้ยินพัฒนาขึ้นอย่างรวดเร็วและไวพอที่จะได้ยินเสียงที่สูงหรือต่ำเกินกว่าที่ผู้ใหญ่จะได้ยินอีกด้วย ลูกมีลำตัวยาวประมาณ 19 เซนติเมตร แล้ว";
                draw = R.drawable.pregnant_19;
                break;
            case 7:
                title = "อายุครรภ์สัปดาห์ที่ 20";
                subtitle = "พัฒนาการของลูกน้อยในครรภ์";
                item1 = "๐ สมองลูกส่วนที่ควบคุมความรู้สึกเติบโตขึ้นอย่างรวดเร็ว";
                item2 = "๐ การเต้นของหัวใจตรวจพบได้ง่าย";
                item3 = "๐ ผิวของลูกจะมีเมือกสีขาวหนาปกคลุมหรือที่เรียกว่าไขมันเคลือบผิว เพื่อป้องกันผิวอันบอบบางระหว่างการตั้งครรภ์";
                item4 = "๐ ตอนนี้ลูกมีความสูงประมาณ 22 เซนติเมตร";
                draw = R.drawable.pregnant_20;
                break;
            case 8:
                title = "อายุครรภ์สัปดาห์ที่ 21";
                subtitle = "พัฒนาการของลูกน้อยในครรภ์";
                item1 = "๐ ขณะนี้ลูกมีขนาดลำตัวยาวประมาณ 26 เซนติเมตร. น้ำหนักประมาณ 226 กรัม";
                item2 = "๐ ลูกสามารถได้ยินเสียงพูดและเสียงเพลงที่คุณแม่ร้อง ดังนั้น จึงเป็นช่วงเวลาที่ดีที่จะเริ่มพูดคุยกับลูกถ้าหากว่าคุณยังไม่เคยพูดคุยกับลูกมาก่อน";
                item3 = "๐ ไตของทารกเริ่มทำงานแล้ว ลูกจึงเริ่มมีกระบวนการขับถ่ายของเสียด้วยตัวเอง";
                item4 = "๐ ช่วงนี้ลูกจะเคลื่อนไหวมากขึ้นจนคุณแม่รู้สึกได้ว่าลูกเตะหน้าท้องเบาๆ บ่อยๆ";
                draw = R.drawable.pregnant_21;
                break;
            case 9:
                title = "อายุครรภ์สัปดาห์ที่ 22";
                subtitle = "พัฒนาการของลูกน้อยในครรภ์";
                item1 = "๐ ตอนนี้ลำตัวยาวถึง 27-28 เซนติเมตร. น้ำหนักประมาณ 255 กรัม รูปร่างสมบูรณ์ ";
                item2 = "๐ การได้ยินและการรับรู้ก็พัฒนาขึ้นตามลำดับ ลูกเริ่มตอบสนองต่อเสียงของคุณแม่มากยิ่งขึ้น ดังนั้นดนตรีจะช่วยกระตุ้นการทำงานของเซลล์สมองของลูก";
                item3 = "๐ รูปร่างหน้าตาคล้ายคลึงกับแรกเกิดทั่วไปมาก ถึงแม้ว่าผิวหนังจะยังคงโปร่งแสงและยังไม่มีชั้นไขมันมาห่อหุ้มก็ตาม";
                item4 = "";
                draw = R.drawable.pregnant_22;
                break;
            case 10:
                title = "อายุครรภ์สัปดาห์ที่ 23";
                subtitle = "พัฒนาการของลูกน้อยในครรภ์";
                item1 = "๐ ในขณะนี้ ลูกกำลังฝึกหายใจโดยการหายใจเอาน้ำคร่ำเข้าสู่ปอดที่กำลังพัฒนา ";
                item2 = "๐ ศีรษะได้สัดส่วนพอเหมาะกับลำตัว แขนขาพัฒนาเต็มที่";
                item3 = "๐ ในเด็กผู้ชายถุงอัณฑะพัฒนาเกือบสมบูรณ์แล้ว ส่วนเด็กผู้หญิงก็มีการพัฒนาของรังไข่ที่ภายในมีเซลล์ไข่อยู่พร้อมแล้ว";
                item4 = "๐ ต่อมไขมันและต่อมเหงื่อที่ผิวหนังจะเริ่มทำงาน \n ๐ ลูกมีขนาดลำตัวยาวอย่างน้อย 29 เซนติเมตร. และมีน้ำหนักประมาณ 480 กรัม ";
                draw = R.drawable.pregnant_23;
                break;
            case 11:
                title = "อายุครรภ์สัปดาห์ที่ 24";
                subtitle = "พัฒนาการของลูกน้อยในครรภ์";
                item1 = "๐ ลูกเริ่มแยกแยะเสียงได้แล้ว ลืมตาได้";
                item2 = "๐ ผิวหนังหนาขึ้นเล็กน้อยแต่ยังคงโปร่งใสมองเห็นเส้นเลือดได้อยู่";
                item3 = "๐ วัดอัตราการเต้นของหัวใจได้ ถุงลมในปอดพัฒนาสมบูรณ์แล้วแต่การทำงานเกี่ยวกับระบบทางเดินหายใจยังไม่สมบูรณ์นัก";
                item4 = "๐ ลูกมีขนาดลำตัวยาวประมาณ 30 เซนติเมตร. และมีน้ำหนักประมาณ 540 กรัม \n ๐ หากลูกคลอดออกมาในขณะนี้ เขา ‘สามารถมีชีวิตอยู่ได้’ ในหน่วยอภิบาลเด็กแรกคลอดพิเศษ";
                draw = R.drawable.pregnant_24;
                break;
            case 12:
                title = "อายุครรภ์สัปดาห์ที่ 25";
                subtitle = "พัฒนาการของลูกน้อยในครรภ์";
                item1 = "๐ ในขณะนี้ลูกมีลำตัวยาวประมาณ 34 เซนติเมตร. และหนักประมาณ 620 กรัม";
                item2 = "๐ ลูกอาจจดจำเสียงได้ด้วย คุณอาจเปิดเพลงเบาๆให้ลูกฟัง เพราะสมองของลูกพร้อมที่จะเรียนรู้สิ่งต่างๆ นั่นคือ ลูกน้อยของคุณแม่เริ่มจดจำสิ่งต่างๆได้ตั้งแต่อยู่ในครรภ์ ";
                item3 = "๐ กระดูกส่วนกลางลำตัวของลูกเริ่มแข็งมากขึ้น";
                item4 = "๐ ลูกกำลังฝึกการหายใจอย่างขะมักเขม้นจนบางครั้งคุณแม่อาจรู้สึกได้ว่าลูกสะอึก";
                draw = R.drawable.pregnant_25;
                break;
            case 13:
                title = "=====================================";
                subtitle = "ไตรมาสที่ 2 อาหารเพิ่มพลังงาน และลดท้องผูก";
                item1 = "๐ คุณแม่ที่ตั้งครรภ์ได้ 4-6 เดือน ส่วนใหญ่มักปรับตัวได้ อาการแพ้ท้องหายไป ความอยากอาหารมีมากขึ้น ลูกน้อยเจริญเติบโตมากขึ้น จึงต้องการพลังงานจากอาหารเพิ่มขึ้นวันละ 300 กิโลแคลอรี่";
                item2 = "\n  แต่เมื่อลูกน้อยมีขนาดเพิ่มขึ้น ท้องของแม่ก็เริ่มขยายใหญ่ อาจทำให้มีอาการท้องผูก ท้องอืด ท้องเฟ้อได้ จึงควรกินผักและผลไม้เป็นประจำ เพื่อเพิ่มเส้นใยอาหารและต้องจำกัดปริมาณน้ำตาล ไม่เกินวันละ 6 ช้อนชา แบ่งอาหารเป็นมื้อเล็ก ๆ มีอาหารว่างระหว่างมื้อ เพื่อป้องกันอาการอึดอัดแน่นท้อง และภาวะกรดไหลย้อน โดยควรได้รับคาร์โบไฮเดรตเชิงซ้อน เส้นใยอาหาร และไอโอดีน ซึ่งได้จาก";
                item3 = "๐ ข้าวซ้อมมือ ขนมปังโฮลวีท มันเทศ ฟักทอง และข้าวโพด เมื่อย่อยสลายแล้วจะเปลี่ยนเป็นกลูโคส ซึ่งเป็นแหล่งพลังงานที่สำคัญของสมอง ช่วยสร้างความจำ และรักษาระดับน้ำตาลในเลือดให้คงที่และยังทำให้หญิงตั้งครรภ์อารมณ์ดีด้วย";
                item4 = "๐ อาหารที่มีเส้นใยหาได้จากผักและผลไม้สด หลีกเลี่ยงการดื่มน้ำผลไม้ หรือผลไม้รสหวานจัด เพราะจะทำให้ร่างกายได้รับน้ำตาลมากเกินความต้องการ จนทำให้น้ำหนักตัวเพิ่ม" +
                        "\n ๐ ควรกินอาหารที่มีไอโอดีนอย่างเพียงพอ เช่น ปลา กุ้ง สาหร่ายทะเล โดยกินอย่างน้อยสัปดาห์ละ 2 ครั้ง เพราะไอโอดีนเป็นส่วนประกอบของฮอร์โมนไทรอยด์ ทำหน้าที่ควบคุมการทำงานของเซลล์และอวัยวะต่าง ๆ ของร่างกาย โดยเฉพาะระบบสมองและประสาท ทำให้ลูกน้อยมีการเจริญเติบโตและพัฒนาการสมวัย" +
                        "\n\n  เมนูแนะนำ : ก๋วยเตี๋ยวกุ้ง ต้มกุ้งให้สุกใส่เส้นก๋วยเตี๋ยวและผักคะน้าหรือถั่วงอก แล้วปรุงรสให้อร่อยก็จะได้ก๋วยเตี๋ยวกุ้งสำหรับแม่ท้องแล้ว";
                draw = R.drawable.traimas1_food;
                break;
            default:
                title = " ";
                subtitle = " ";
                item1 = " ";
                item2 = " ";
                item3 = " ";
                item4 = " ";
                draw = 0;
        }

        return new String[] {title, subtitle, item1, item2, item3, item4, String.valueOf(draw)};

    }


}
