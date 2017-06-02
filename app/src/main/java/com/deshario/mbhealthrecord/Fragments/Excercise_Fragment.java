package com.deshario.mbhealthrecord.Fragments;


import android.graphics.Color;
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
public class Excercise_Fragment extends Fragment {

    View positiveAction;
    TextView item1;
    ImageView dataimg;
    int[] listviewImage = new int[]{
            R.mipmap.num1,
            R.mipmap.num2,
            R.mipmap.num3,
            R.mipmap.num4,
            R.mipmap.num5,
            R.mipmap.num6
    };

    public Excercise_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_one, container, false);
        ((MainActivity) getActivity()).setActionBarTitle("ท่าบริหารร่างกาย");
        ArrayList<String> listviewTitle = new ArrayList<String>();
        listviewTitle.add("ท่าบริหารร่างกายท่าที่ 1");
        listviewTitle.add("ท่าบริหารร่างกายท่าที่ 2");
        listviewTitle.add("ท่าบริหารร่างกายท่าที่ 3");
        listviewTitle.add("ท่าบริหารร่างกายท่าที่ 4");
        listviewTitle.add("ท่าบริหารร่างกายท่าที่ 5");
//        listviewTitle.add("ท่ายืน");
//        listviewTitle.add("ท่านั่ง");
//        listviewTitle.add("ท่านอน");

        ArrayList<String> myArrList = new ArrayList<String>();
        for(int x=0; x<listviewTitle.size(); x++){
            myArrList.add("หมวด : ออกกำลังกาย");
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
                .customView(R.layout.excercise_list, true)
                .positiveText("ปิด")
                .titleColorRes(R.color.primary_bootstrap)
                //.titleColor(Color.DKGRAY)
               // .contentColor(Color.GRAY) // notice no 'res' postfix for literal color
              //  .linkColorAttr(R.attr.my_link_color_attr)  // notice attr is used instead of none or res for attribute resolving
                //.dividerColorRes(R.color.danger_bootstrap)
                //.backgroundColorRes(R.color.info_bootstrap)
                .positiveColorRes(R.color.colorPrimaryDark)
                .negativeColorRes(R.color.danger_bootstrap)
                .widgetColorRes(R.color.info_bootstrap)
                .buttonRippleColorRes(R.color.orange)

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
                item1 = "เริ่มจากนอนราบ ศีรษะหนุนหมอน ชันเข่าทั้ง 2 ข้าง ให้เท้าวางห่างกันเท่าช่วงสะโพก วางแขนข้างลำตัว เริ่มขมิบช่องคลอดและทวารหนัก เกร็งค้างไว้ประมาณ 8 – 10 วินาที (หรือนับ 1 – 10 ช้าๆ) แล้วผ่อนคลายออก ทำ 25 ครั้งแล้วหยุดพัก\n" +
                        "\n ข้อควรระวัง : การนอนหงายเหมาะกับอายุครรภ์ไม่เกิน 4 เดือนเท่าเท่านั้น หากอายุครรภ์มากกว่านี้ ควรทำในท่ายืนหรือนั่งบนเก้าอี้แทน";
                draw = R.drawable.exer1;
                break;
            case 1:
                item1 = "เป็นการบริหารกล้ามเนื้ออุ้งเชิงกราน ให้นอนราบเหมือนกับในท่าแรก พยายามกดให้หลังส่วนล่างแนบกับพื้นและผ่อนคลายกล้ามเนื้อกระดูกสันหลัง นับ 1 – 10 ช้าๆ แล้วผ่อนคลาย ทำซ้ำหลายๆ ครั้ง" +
                        "\n ข้อควรระวัง: การนอนหงายเหมาะกับอายุครรภ์ไม่เกิน 4 เดือนเท่าเท่านั้น หากอายุครรภ์มากกว่านี้ ควรทำในท่ายืนโดยให้เอาหลังพิงกำแพงแทน";
                draw = R.drawable.excer2;
                break;
            case 2:
                item1 = "เป็นการบริหารกล้ามเนื้อส่วนหลังและหน้าท้อง มีประโยชน์ในการคลอดลูกมาก เพราะช่วยผ่อนน้ำหนักกดทับของมดลูกต่อส่วนหลัง เริ่มด้วยการคุกเข่ากับพื้น (ท่าคลานสี่ขา) ให้มือทั้ง 2 ข้างและเข่าทั้ง 2 ข้างรับน้ำหนักเท่าๆ กัน ให้ศีรษะอยู่ในระดับเดียวกับลำตัวจากนั้นค่อยๆ ดันสะโพกไปทางด้านหน้า แขม่วท้อง และโก่งหลังขึ้นไปให้โค้งมากที่สุด (เหมือนแมวกำลังโก่งตัวเวลาตกใจ) ขณะเดียวกันก็ก้มศีรษะลงจนคางจรดอกแล้วค้างไว้ นับ 1 – 10 แล้วคลายออกช้าๆ ทำซ้ำหลายๆ ครั้ง ทั้งนี้จะโก่งหลังมากแค่ไหนนั้น ขอให้โก่งแค่พอรู้สึกว่าตึงก็พอ ไม่จำเป็นต้องฝืนโก่งมากเกินไปในช่วงแรก ฝึกไปเรื่อยๆ จะสามารถโก่งหลังให้สูงมากขึ้นได้เอง";
                draw = R.drawable.excer3;
                break;
            case 3:
                item1 = "เป็นการบริหารกล้ามเนื้อส่วนคอ เนื่องจากคุณแม่ที่ตั้งครรภ์มักมีอาการตึงและปวดเมื่อยเสมอ ท่านี้จะช่วยให้คลายอาการปวดต้นคอได้ เริ่มจากนั่งในท่าสบายๆ ผ่อนคลาย หลับตา ก้มศีรษะลงเล็กน้อย ค่อยๆ หมุนคอเป็นวงกลมช้าๆ เพื่อผ่อนคลายกล้ามเนื้อทั้งหมด ไม่เกร็งกล้ามเนื้อ ทำ 4 – 5 รอบแล้วเปลี่ยนทิศการหมุน";
                draw = R.drawable.excer4;
                break;
            case 4:
                item1 = "เป็นการบริหารกล้ามเนื้อหน้าท้อง เริ่มจากการนั่งขัดสมาธิ (หากไม่สามารถนั่งขัดสมาธิได้ ให้นั่งบนพื้นหรือเก้าอี้ที่มีพื้นเรียบ) หรือกรณีที่ไม่สะดวกในการนั่งก็สามารถใช้การยืนตรงธรรมดา (ทรงตัวให้มั่น) ยกแขนขึ้นตรงๆ พยายามยืดแขนข้างหนึ่งให้สูงเหมือนกำลังจะเอื้อมจับเพดาน หรือกำลังเอื้อมมือไปคว้าของ จากนั้นค้างไว้สักครู่ (นับ 1 – 10 ในใจ) ค่อยลดแขนลง จากนั้นเปลี่ยนเป็นยกแขนอีกข้างหนึ่ง ทำสลับกันหลายๆ ครั้ง โดยไม่ยกก้นขึ้นจากพื้น ท่านี้จะเป็นการบริหารกล้ามเนื้อส่วนท้องและบริเวณด้านข้างลำตัว รูปด้านบนจะเป็นรูปตัวอย่างแบบยืน";
                draw = R.drawable.excer5;
                break;
            default:
                item1 = "";
        }
        return new String[] {item1, String.valueOf(draw)};
    }

}
