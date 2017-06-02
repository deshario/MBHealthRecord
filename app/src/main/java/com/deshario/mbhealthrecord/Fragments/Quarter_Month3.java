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
public class Quarter_Month3 extends Fragment {

    View positiveAction;
    TextView mytitle,subtitle,item1,item2,item3,item4;
    ImageView dataimg;
    int[] listviewImage = new int[]{
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
            R.mipmap.flower,
    };

    public Quarter_Month3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_one, container, false);
        ArrayList<String> listviewTitle = new ArrayList<String>();
        listviewTitle.add("สัปดาห์ที่ 26");
        listviewTitle.add("สัปดาห์ที่ 27");
        listviewTitle.add("สัปดาห์ที่ 28");
        listviewTitle.add("สัปดาห์ที่ 29");
        listviewTitle.add("สัปดาห์ที่ 30");
        listviewTitle.add("สัปดาห์ที่ 31");
        listviewTitle.add("สัปดาห์ที่ 32");
        listviewTitle.add("สัปดาห์ที่ 33");
        listviewTitle.add("สัปดาห์ที่ 34");
        listviewTitle.add("สัปดาห์ที่ 35");
        listviewTitle.add("สัปดาห์ที่ 36");
        listviewTitle.add("สัปดาห์ที่ 37");
        listviewTitle.add("สัปดาห์ที่ 38");
        listviewTitle.add("สัปดาห์ที่ 39");
        listviewTitle.add("สัปดาห์ที่ 40");
        listviewTitle.add("โภชนาการสำหรับไตรมาสที่ 3");

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
        if(position == 15){
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

    public String[] intializedata(int position) {
        String title = null;
        String subtitle = null;
        String item1 = null;
        String item2 = null;
        String item3 = null;
        String item4 = null;
        int draw = 0;
        switch (position) {
            case 0:
                title = "อายุครรภ์สัปดาห์ที่ 26";
                subtitle = "พัฒนาการของลูกน้อยในครรภ์";
                item1 = "๐ อวัยวะที่สำคัญของลูกเริ่มมีการพัฒนาไปอย่างรวดเร็ว และจะเปลี่ยนแปลงจนถึงสัปดาห์ที่ 29";
                item2 = "๐ ในขณะนี้ ลูกมีลำตัวยาวประมาณ 36 เซนติเมตร. และหนักประมาณ 650 กรัม";
                item3 = "๐ คุณแม่จะรู้สึกถึงการเคลื่อนไหวของลูกได้ทุกวัน ลูกตื่นและนอนเป็นเวลาแล้ว";
                item4 = "๐ เริ่มสร้างไขมันใต้ผิวหนังที่จะช่วยรักษาระดับอุณหภูมิในร่างกาย \n ๐ หากลูกคลอดก่อนกำหนด ในระยะนี้ปอดก็พร้อมที่จะทำงานแล้ว แต่ยังต้องพัฒนาอีกมาก";
                draw = R.drawable.pregnant_26;
                break;
            case 1:
                title = "อายุครรภ์สัปดาห์ที่ 27";
                subtitle = "พัฒนาการของลูกน้อยในครรภ์";
                item1 = "๐ ในขณะนี้ลูกน้อยของคุณมีลำตัวยาวประมาณ 37 เซนติเมตร. และหนักประมาณ 900 กรัม เป็นอีกช่วงที่น้ำหนักตัวลูกน้อยจะเพิ่มค่อนข้างเร็ว";
                item2 = "๐ ลูกลืมตาได้มากขึ้นสามารถมองเห็นแสงที่ผ่านมาทางหน้าท้องคุณแม่";
                item3 = "๐ ไตรมาสสุดท้ายนี้ ลูกจะเริ่มได้รับภูมิต้านทานโรคที่ถ่ายทอดผ่านสายสะดือ ซึ่งจะถูกกระตุ้นจากการให้ลูกกินนมแม่และสามารถคุ้มกันโรคจนลูกน้อยมีอายุ 2-3 เดือน";
                item4 = "";
                draw = R.drawable.pregnant_27;
                break;
            case 2:
                title = "อายุครรภ์สัปดาห์ที่ 28";
                subtitle = "พัฒนาการของลูกน้อยในครรภ์";
                item1 = "๐ ขณะนี้ลูกมีลำตัวยาวประมาณ 38 เซนติเมตร. และหนัก 990 กรัม";
                item2 = "๐ อัตราการเต้นของหัวใจ 150 ครั้งต่อนาที";
                item3 = "๐ ลูกสามารถมองไปรอบๆ รวมทั้งเปิดปิดดวงตาได้แล้ว ";
                item4 = "๐ อวัยวะต่างๆพัฒนาขึ้นครบสมบูรณ์แล้ว ประสาทสัมผัสอื่นๆก็เกือบจะสมบูรณ์แล้ว ลูกสามารถรับรู้รสสัมผัสและจดจำเสียงของคุณได้โดยง่าย \n ๐ ในขณะที่ลูกโตขึ้นเรื่อยๆ พื้นที่ว่างในครรภ์ก็ลดน้อยลงทุกขณะ แต่ลูกยังคงเตะครรภ์เมื่อรู้สึกว่ามีสิ่งที่น่าตื่นเต้น และจะหดแขนและขาทั้งสองมาไว้แนบหน้าอกในท่าขดตัวเพื่ออยู่ในท่าที่สบาย ";
                draw = R.drawable.pregnant_28;
                break;
            case 3:
                title = "อายุครรภ์สัปดาห์ที่ 29";
                subtitle = "พัฒนาการของลูกน้อยในครรภ์";
                item1 = "๐ ขณะนี้ลูกน้อยของคุณมีลำตัวยาวเกือบ 40 เซนติเมตร. และมีน้ำหนักประมาณ 1000 กรัม.";
                item2 = "๐ ในไตรมาสที่ 3 นี้ เด็กแต่ละคนจะมีขนาดและน้ำหนักตัวที่ค่อนข้างต่างกันมาก โดยลูกอาจมีน้ำหนักตัวเพิ่มขึ้นถึง 3 เท่าเมื่อเข้าสู่สัปดาห์ที่ 40 ";
                item3 = "๐ พัฒนาการด้านต่างๆโดยส่วนใหญ่ครบถ้วนสมบูรณ์แล้ว อวัยวะทุกส่วนครบถ้วน แต่ยังต้องพัฒนาให้เจริญเติบโตเต็มที่ต่อไปเพื่อให้พร้อมออกมาดูโลก ";
                item4 = "๐ รอบตัวลูกมีไขมันเคลือบอยู่ ถุงลมปอดพัฒนาอย่างสมบูรณ์ สายตาเริ่มปรับโฟกัสได้แล้ว ";
                draw = R.drawable.pregnant_29;
                break;
            case 4:
                title = "อายุครรภ์สัปดาห์ที่ 30";
                subtitle = "พัฒนาการของลูกน้อยในครรภ์";
                item1 = "๐ ลูกมีขนาดลำตัวยาวประมาณ 40 เซนติเมตร. และหนักประมาณ 1100 กรัม. แล้ว";
                item2 = "๐ ช่วงเวลานี้ ลูกจะใช้เวลากว่าร้อยละ 80 ของการนอนอยู่ในระยะหลับฝัน (Rapid Eye Movement หรือ REM)";
                item3 = "๐ สมองและเครือข่ายเส้นใยประสาทมีการเจริญเติบโตและพัฒนาอย่างรวดเร็ว ทำให้สัญญาณประสาทจากสมองเดินทางได้รวดเร็วยิ่งขึ้น ช่วยเพิ่มความสามารถในการเรียนรู้ของลูกเมื่อลืมตาดูโลก";
                item4 = "";
                draw = R.drawable.pregnant_30;
                break;
            case 5:
                title = "อายุครรภ์สัปดาห์ที่ 31";
                subtitle = "พัฒนาการของลูกน้อยในครรภ์";
                item1 = "๐ ลูกมีขนาดลำตัวยาวประมาณ 41 เซนติเมตร. และเริ่มมีน้ำหนักตัวเพิ่มขึ้นอย่างรวดเร็วโดยหนัก 1500 กรัม.แล้ว";
                item2 = "๐ ในขณะนี้ อวัยวะต่างครบถ้วนสมบูรณ์เต็มที่ ปอดแข็งแรงขึ้น ส่วนต่างๆของสมองที่ทำหน้าที่ในการจำเริ่มพัฒนา";
                item3 = "๐ จากการศึกษาพบว่าเด็กทารกที่เคยได้ยินเสียงเพลงหลักที่ใช้ประกอบละครในขณะที่อยู่ในครรภ์มารดาตอบสนองต่อเสียงเพลงดังกล่าวเมื่อคลอดออกมา ซึ่งต่างจากเด็กทารกที่ไม่เคยได้ยินเพลงนั้นมาก่อน";
                item4 = "";
                //draw = R.drawable.pregnant_31;
                break;
            case 6:
                title = "อายุครรภ์สัปดาห์ที่ 32";
                subtitle = "พัฒนาการของลูกน้อยในครรภ์";
                item1 = "๐ ลูกมีขนาดลำตัวยาวประมาณ 42 เซนติเมตร. และหนักประมาณ 1600 กรัม.";
                item2 = "๐ ระบบการทำงานทั้งหมดของร่างกายพัฒนาโดยสมบูรณ์แล้ว ยกเว้นปอดกับทางเดินอาหารซึ่งยังคงต้องใช้เวลาในการเจริญเติบโตอีก 2-3 สัปดาห์";
                item3 = "๐ กล้ามเนื้อของลูกแข็งแรงมากขึ้น ลูกค่อนข้างเคลื่อนไหวเป็นเวลา";
                item4 = "๐ เมื่อลูกตื่นขึ้นมา เขาจะลืมตาและทำสิ่งต่างๆตามธรรมชาติโดยทั่วไปของเด็กแรกเกิด เช่น จับเท้าและดูดนิ้วหัวแม่มือ ลูกน้อยของคุณยังแสดงความรู้สึกออกมาทางใบหน้าได้อีกด้วย";
                draw = R.drawable.pregnant_32;
                break;
            case 7:
                title = "อายุครรภ์สัปดาห์ที่ 33";
                subtitle = "พัฒนาการของลูกน้อยในครรภ์";
                item1 = "๐ เมื่อเข้าสู่สัปดาห์ที่ 33 ระบบภูมิต้านทานโรคของลูกน้อยเริ่มแข็งแรงมากขึ้น";
                item2 = "๐ ผิวหนังจะเริ่มหนาขึ้นและรอยเหี่ยวย่นก็เริ่มลดลงมาก";
                item3 = "๐ น้ำหนักตัวประมาณ 1900 กรัม.และยาวประมาณ 44 เซนติเมตร.";
                item4 = "๐ ไขมันที่เคลือบตัวลูกหนาขึ้น ผมและเล็บยาวขึ้น ผิวเรียบขึ้นแต่ยังย่นอยู่เล็กน้อย \n ๐ ปอดพัฒนาอย่างเต็มที่เตรียมพร้อมออกมาสู่โลกภายนอก";
                draw = R.drawable.pregnant_33;
                break;
            case 8:
                title = "อายุครรภ์สัปดาห์ที่ 34";
                subtitle = "พัฒนาการของลูกน้อยในครรภ์";
                item1 = "๐ ใกล้ครบกำหนดคลอดเข้ามาทุกที เหลืออีกแค่ 6 สัปดาห์เท่านั้น ขณะนี้ลูกสูงประมาณ 45 เซนติเมตร. และหนักประมาณ 2000 กรัม. และเป็นช่วงทำน้ำหนักอีกช่วง";
                item2 = "๐ สำหรับเด็กผู้ชาย ลูกอัณฑะลงมาอยู่ที่ขาหนีบแล้ว";
                item3 = "๐ ขนคิ้วและขนตาขึ้นเต็มเรียบร้อย";
                item4 = "๐ ผิวหนังเริ่มหนาตัวขึ้นโดยมีชั้นไขมันสะสมอยู่ใต้ผิวหนัง \n ๐ ลูกน้อยของคุณได้รับแอนติบอดี้ที่ช่วยเสริมสร้างภูมิต้านทานโรคจากคุณอย่างต่อเนื่อง เพื่อเสริมสร้างความแข็งแรงพร้อมที่จะออกมาสู่โลกกว้างแล้ว";
                draw = R.drawable.pregnant_34;
                break;
            case 9:
                title = "อายุครรภ์สัปดาห์ที่ 35";
                subtitle = "พัฒนาการของลูกน้อยในครรภ์";
                item1 = "๐ ในขณะนี้ ลูกน้อยสามารถตอบสนองต่อแสง เสียง และความเจ็บปวดได้แล้ว";
                item2 = "๐ ในขณะที่ลูกเติบโตขึ้นปริมาณน้ำคร่ำก็จะลดลง";
                item3 = "๐ ภายในลำไส้ของลูกจะเต็มไปด้วยของเหลวสีเขียวเข้มที่เรียกว่า 'เมโคเนียม' (Meconium) ซึ่งเกิดจากของเสียที่ขับออกมาจากตับและลำไส้ โดยลูกอาจถ่ายของเสียเหล่านี้ออกมาระหว่างการคลอดหากรู้สึกเครียด แต่หากไม่ถ่ายออกมา ของเสียเหล่านี้ก็จะรวมอยู่ในผ้าอ้อมผืนแรกที่คุณจะต้องเปลี่ยน";
                item4 = "";
                draw = R.drawable.pregnant_35;
                break;
            case 10:
                title = "อายุครรภ์สัปดาห์ที่ 36";
                subtitle = "พัฒนาการของลูกน้อยในครรภ์";
                item1 = "๐ ลูกน้อยอ้วนท้วนขึ้นทุกวัน มีน้ำหนักเพิ่มขึ้น 140 กรัมต่อสัปดาห์ในช่วงนี้";
                item2 = "๐ ในขณะที่ลูกเติบโตขึ้นปริมาณน้ำคร่ำก็จะลดลง";
                item3 = "๐ ลูกมีไขมันสะสมใต้ผิวหนังมากขึ้น ลำไส้กำลังผลิตขี้เทา ระบบประสาทพร้อมจะสั่งการหลังคลอดทั้งเรื่องการดูดกลืนและการหาว";
                item4 = "๐ ขณะนี้ลูกมีขนาดลำตัวยาวประมาณ 47 เซนติเมตร. และอาจมีน้ำหนักประมาณ 2320 กก. ดังนั้นจึงแทบจะไม่มีที่ว่างเหลืออยู่ในครรภ์แล้ว";
                //draw = R.drawable.pregnant_36;
                break;
            case 11:
                title = "อายุครรภ์สัปดาห์ที่ 37";
                subtitle = "พัฒนาการของลูกน้อยในครรภ์";
                item1 = "๐ ลูกอาจจะหนักประมาณ 2700 กก. ตามเกณฑ์เฉลี่ย ลำตัวยาวประมาณ 49 เซนติเมตร.";
                item2 = "๐ การเคลื่อนไหวจึงเป็นการบิดตัวไปมามากกว่าการเตะท้องเพราะมีพื้นที่น้อยลง";
                item3 = "๐ ลูกกำลังฝึกหายใจเพื่อเตรียมพร้อมที่จะออกมาหายใจภายนอก";
                item4 = "๐ ส่วนใหญ่แล้วทารกจะกลับหัวลงมาอยู่ในอุ้งเชิงกรานแล้ว \n ๐ ใบหน้าของทารกจะเริ่มอ้วนขึ้นและมีขนตา คอก็จะเริ่มหนาขึ้นและสามารถเปิดปิดตาได้ง่ายขึ้นแล้ว ";
                draw = R.drawable.pregnant_37;
                break;
            case 12:
                title = "อายุครรภ์สัปดาห์ที่ 38";
                subtitle = "พัฒนาการของลูกน้อยในครรภ์";
                item1 = "๐ ใกล้ถึงกำหนดคลอด ลูกน้อยของคุณเติบโตเต็มที่แล้วและกำลังใช้เวลา 2 สัปดาห์สุดท้ายเพื่อสะสมไขมัน ด้วยความยาวประมาณ 49 หรือ 50 เซนติเมตร. น้ำหนักเกือบ 2900 กรัม.";
                item2 = "๐ ช่วงนี้ ลูกจะได้ยินเสียงคุณพูดคุยและร้องเพลงให้เขาฟัง";
                item3 = "๐ ผิวหนังเป็นสีชมพูแล้ว ผมยาวประมาณ 5 เซนติเมตร เล็บยาวขึ้น ไขมันที่หุ้มตัวเริ่มลอกออกไปบ้าง";
                item4 = "๐ หากลูกคลอดออกมาในช่วงนี้ ก็ไม่ถือว่าผิดปกติแต่อย่างใด";
                draw = R.drawable.pregnant_38;
                break;
            case 13:
                title = "อายุครรภ์สัปดาห์ที่ 39";
                subtitle = "พัฒนาการของลูกน้อยในครรภ์";
                item1 = "๐ ประมาณ 1 สัปดาห์ก่อนคลอด ทารกจะเอาศีรษะลงไปในอุ้งเชิงกราน โดยหากเป็นลูกคนแรกเด็กจะกลับหัวลงอยู่ก่อนแล้ว";
                item2 = "๐ กะโหลกศีรษะของลูกจะพร้อมสำหรับการคลอด และสามารถเลื่อนเข้ามาเกยกัน เพื่อให้ศีรษะลอดผ่านปากมดลูกได้";
                item3 = "๐ ขณะนี้ ลูกจะมีลำตัวยาวประมาณ 50-51 เซนติเมตร. และมีน้ำหนักเพิ่มวันละ 28 กรัม และทุกส่วนในร่างกายลูกพัฒนาสมบูรณ์เท่าที่ธรรมชาติเอื้ออำนวยให้เจริญเติบโตในท้องแม่แล้ว";
                item4 = "";
                draw = R.drawable.pregnant_39;
                break;
            case 14:
                title = "อายุครรภ์สัปดาห์ที่ 40";
                subtitle = "พัฒนาการของลูกน้อยในครรภ์";
                item1 = "๐ เป็นระยะเวลากว่า 9 เดือนที่ลูกของคุณเติบโตจากเซลล์ๆ เดียวเป็นมนุษย์ตัวน้อย เมื่อแรกเกิดน้ำหนักโดยเฉลี่ยของเด็กจะอยู่ที่ 3400 กรัม. และมีความยาวจากหัวถึงเท้า 51 เซนติเมตร. ซึ่งลูกโตเกือบเต็มพื้นที่ในท้องของคุณแล้ว จึงมีที่ว่างในครรภ์จำกัดเพียงให้ขยับตัวไปมาเท่านั้น";
                item2 = "๐ ตอนนี้ลูกกลับศีรษะแล้ว อยู่ตอนล่างของมดลูกในท่าขดตัวแน่น เตรียมพร้อมสำหรับการคลอด";
                item3 = "";
                item4 = "";
                draw = R.drawable.pregnant_40;
                break;
            case 15:
                title = "=====================================";
                subtitle = "ไตรมาสที่ 3 อาหารบำรุงเซลล์สมองลูก";
                item1 = "๐ ไตรมาสสุดท้ายคือช่วง 7-9 เดือน ยังคงต้องการพลังงานเพิ่มขึ้นวันละ 300 กิโลแคลอรี่ แม่ท้องที่มีน้ำหนักเกิน ควรงดขนมหวาน และเครื่องดื่มที่มีน้ำตาลสูง และควรจำกัดปริมาณน้ำมันปรุงอาหารให้ได้วันละ 2-3 ช้อนโต๊ะ เพื่อป้องกันน้ำหนักตัวเพิ่มขึ้น โดยสารอาหารที่แม่ท้องควรคำนึงถึง คือสารอาหารที่ช่วยบำรุงสมอง เพราะเซลล์สมองของลูกจะพัฒนาสูงสุดในไตรมาสนี้ พวกไขมันโอเมก้า 3และ6 น้ำ เลซิติน และสังกะสี";
                item2 = "๐ ไขมันไม่อิ่มตัวชนิดไลโนเลอิก และไลโนเลนิก ซึ่งก็คือโอเมก้า 3 และ 6 นั่นเอง จะช่วยพัฒนาและเสริมสร้างการเจริญเติบโตของเซลล์สมองและจอประสาทตาของลูก ถ้าแม่กินอาหารที่มีไขมันดีลูกจะได้รับ DHA และ ARA สะสมไว้อย่างเพียงพอ ทำให้ลูกมีพัฒนาการและการเรียนรู้ดี \n    แต่เนื่องจากร่างกายไม่สามารถสร้างกรดไขมันชนิดนี้ได้ต้องได้รับจากอาหารนั้น โดยเฉพาะน้ำมันมะกอก น้ำมันคาโนลา น้ำมันรำข้าว เมล็ดทานตะวัน อะโวคาโด และเนื้อปลา";
                item3 = "๐ ดื่มน้ำวันละ 8-10 แก้ว น้ำช่วยให้การส่งผ่านข้อมูลของสมองทำงานได้เต็มประสิทธิภาพ ถ้าดื่มน้ำน้อยหรือร่างกายมีภาวะขาดน้ำขนาดของเส้นใยสมองจะเล็กลง ทำให้การส่งข้อมูลทำได้ช้า ส่งผลให้การทำงานของสมองและความจำลดประสิทธิภาพลง";
                item4 = "๐ เลซิตินหาได้จาก ปลา นมวัว ตับสัตว์ ไข่แดง ถั่วเหลือง ดอกกะหล่ำ ผักกาดหอม Brewer Yeast ธัญพืชต่าง ๆ เลซิตินเป็นสารประกอบหลักของโคลีนซึ่งเป็นสารในกลุ่มวิตามินบี มีบทบาทต่อความจำ การเรียนรู้ของสมองและสร้างสารสื่อประสาท ช่วยเสริมสร้างความจำเพื่อใช้ในการติดต่อสื่อสารระหว่างเซลล์ประสาทต่าง ๆ ปริมาณโคลีนที่เพียงพอในสมองจะช่วยป้องกันภาวะความจำเสื่อมได้" +
                        "\n ๐ สารสังกะสีช่วยสังเคราะห์โปรตีน เป็นส่วนหนึ่งของเอ็นไซม์ คาร์บอร์นิคแอนไฮเดรส (Carbonic Anhydrase) ที่ช่วยให้ระบบประสาททำงานได้ดี มีความสำคัญต่อการเจริญเติบโต ช่วยให้สมองผ่อนคลาย แหล่งอาหารที่มี ได้แก่ หอยนางรม ถั่วลิสงงา เนื้อวัว ชีส จมูกข้าวสาลี กุ้ง ปู ไก่งวง เป็นต้น" +
                        "\n\n เมนูแนะนำ : ยำแซลมอนแสนอร่อย นำปลาแซลมอนต้ม";
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
        return new String[] {title, subtitle, item1, item2, item3, item4, String.valueOf(draw)};

        }



}
