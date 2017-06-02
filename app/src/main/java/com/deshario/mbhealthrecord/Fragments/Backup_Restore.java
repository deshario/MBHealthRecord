package com.deshario.mbhealthrecord.Fragments;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.deshario.mbhealthrecord.R;
import com.deshario.mbhealthrecord.api.RetroClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * A simple {@link Fragment} subclass.
 */
public class Backup_Restore extends Fragment {

    Button logout_btn,backup_btn,restore_btn;
    TextView name_text,email_text,pass_text,status_text;
    String name,email,pass;
    Boolean status;
    SharedPreferences prfs;
    private static final String CONFIG_NAME = "logged_in_or_not";
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final String APPDIRNAME = "บันทึกสุขภาพแม่และเด็ก";
    File currentDB,backupDB;
    SweetAlertDialog swbackedup;
    boolean upload_status = false;


    public Backup_Restore() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_backup__restore, container, false);
        verifyStoragePermissions(getActivity());
        logout_btn = (Button)view.findViewById(R.id.logout_btn);
        backup_btn = (Button)view.findViewById(R.id.backup_btn);
        restore_btn = (Button)view.findViewById(R.id.restore_btn);

        name_text = (TextView)view.findViewById(R.id.name_1);
        //email_text = (TextView)view.findViewById(R.id.email_1);
        //pass_text = (TextView)view.findViewById(R.id.pass_1);

       prfs = getActivity().getSharedPreferences(CONFIG_NAME, Context.MODE_PRIVATE);
        name = prfs.getString("login_user", "");
        email = prfs.getString("login_email", "");
        pass = prfs.getString("login_pass", "");

        name_text.setText("ยินดีต้อนรับคุณ"+name);
        //email_text.setText("Email : "+email);
        //pass_text.setText("Pass : "+pass);

        backup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backupdata();
            }
        });

        restore_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"restore",Toast.LENGTH_SHORT).show();
//                backupdata();
            }
        });

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prfs .edit().clear().commit();
                alertlogout();
            }
        });
        return view;
    }

    public void alertlogout(){
        SweetAlertDialog log_out = new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE);
        log_out.setTitleText("ออกจากระบบสำเร็จ");
        log_out.setConfirmText("ตกลง");
        log_out.setCancelable(false);
        log_out.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                CloudFragment log_out_frag = new CloudFragment();
                ft.addToBackStack(null);
                ft.hide(Backup_Restore.this);
                ft.replace(R.id.containerView, log_out_frag);
                ft.commit();
                sweetAlertDialog.dismiss();
            }
        });
        log_out.show();
        WindowManager.LayoutParams lp = log_out.getWindow().getAttributes();
        lp.dimAmount = 0.8f;
        log_out.getWindow().setAttributes(lp);
        log_out.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
    }

    public void backupdata(){
        chkdir();
        Boolean access = verifyStoragePermissions(getActivity());
        if(access == true){
            backup(APPDIRNAME);
        }else{
            Toast.makeText(getActivity()," Permission Denied\n Please Try Again",Toast.LENGTH_SHORT).show();
            //backupdata();
        }
    }

    public void chkdir(){
        File folder = new File(Environment.getExternalStorageDirectory() + "/"+APPDIRNAME);
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdir();
        }
    }

    public static boolean verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
            return true;
        }
        return true;
    }

    private void backup(String foldername){
        try {
            File external_storage = Environment.getExternalStorageDirectory();
            final File data = Environment.getDataDirectory();

            if (external_storage.canWrite()) {
                String currentDBPath = "/data/"+ "com.deshario.mbhealthrecord" +"/databases/"+"weeks.db";
                // String backupDBPath = "/"+foldername+"/"+"database.db";
                // String backupDBPath = "/"+foldername+"/"+"55"+current_datetime+""; //.db
                String backupDBPath = "/"+foldername+"/"+"MomnBabyData"; //.db
                currentDB = new File(data, currentDBPath);
                backupDB = new File(external_storage, backupDBPath);
                // File backupDB = new File();
                String str = backupDB.toString();

                if (backupDB.exists()) { // currentdb
                    swbackedup = new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("ตรวจพบการสำรองข้อมูลเก่า")
                            .setContentText("ข้อมูลเก่าที่สำรองเอาไว้จะถูกเขียนทับด้วยข้อมูลปัจจุบัน")
                            .setConfirmText("ตกลง")
                            .setCancelText("ยกเลิก")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    try{
                                        FileChannel src = new FileInputStream(currentDB).getChannel();
                                        FileChannel dst = new FileOutputStream(backupDB,false).getChannel();
                                        dst.transferFrom(src, 0, src.size());
                                        src.close();
                                        dst.close();
                                      //  confirmok(1);
                                        sDialog.dismiss();
                                    }catch (Exception e){
                                        sDialog.dismissWithAnimation();
                                    }
                                }
                            })
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                }
                            });
                    swbackedup.show();
                    WindowManager.LayoutParams lp = swbackedup.getWindow().getAttributes();
                    lp.dimAmount = 1f;
                    swbackedup.getWindow().setAttributes(lp);
                    swbackedup.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                    //uploaddb(str);
                }else{
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                    // Toast.makeText(getActivity(), "สำรองข้อมูลสำเร็จ", Toast.LENGTH_SHORT).show();
                    //confirmok(0);
                    //uploaddb(str);
                }
            }
        } catch (Exception e) {
        }

    }

//    private void uploaddb(String filename) {
//        final SweetAlertDialog progressDialog;
//        // final ProgressDialog pDialog;
//
//        progressDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
//        progressDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//        progressDialog.setTitleText("กำลังสำรองข้อมูล");
//        progressDialog.setCancelable(false);
//        progressDialog.show();
//        WindowManager.LayoutParams lp = progressDialog.getWindow().getAttributes();
//        lp.dimAmount = 1f;
//        progressDialog.getWindow().setAttributes(lp);
//        progressDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
//
//        //progressDialog = new ProgressDialog(getActivity());
//        //progressDialog.setMessage("กำลังกู้คืน");
//        //progressDialog.show();
//        //Create Upload Server Client
//        com.deshario.mbhealthrecord.api.ApiService service = RetroClient.getApiService();
//        //String imagePath = "/storage/9016-4EF8/hello.txt";
//        File file = new File(filename);
//
//        // create RequestBody instance from file
//        okhttp3.RequestBody requestFile = okhttp3.RequestBody.create(MediaType.parse("multipart/form-data"), file);
//        MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file", file.getName(), requestFile);
//
//        Call<Result> resultCall = service.uploadImage(body);
//        // finally, execute the request
//        resultCall.enqueue(new Callback<Result>() {
//            @Override
//            public void onResponse(Call<Result> call, retrofit2.Response<Result> response) {
//                progressDialog.dismiss();
//                // Response Success or Fail
//                if(response.isSuccessful()) {
//                    if(response.body().getResult().equals("success")){
//                        Toast.makeText(getActivity(),"สำรองข้อมูลสำเร็จ",Toast.LENGTH_SHORT).show();
//                    }else{
//                        Toast.makeText(getActivity(),"สำรองข้อมูลล้มเหลว",Toast.LENGTH_SHORT).show();
//                    }
//                }else{
//                    Toast.makeText(getActivity(),"สำรองข้อมูลล้มเหลว",Toast.LENGTH_SHORT).show();
//                }
//                // imagePath = "";
//            }
//            @Override
//            public void onFailure(Call<Result> call, Throwable t) {
//                progressDialog.dismiss();
//            }
//        });
//    }

    public void confirmok(final int execute_from){
        final SweetAlertDialog confi = new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("สำรองข้อมูลสำเร็จ")
                .setConfirmText("ตกลง");
        confi.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                if(execute_from == 1){
                    if (swbackedup.isShowing()){
                        swbackedup.dismiss();
                    }
                }
                sweetAlertDialog.dismiss();
            }
        });
        confi.show();
        WindowManager.LayoutParams lp = confi.getWindow().getAttributes();
        lp.dimAmount = 1f;
        confi.getWindow().setAttributes(lp);
        confi.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
    }



}
