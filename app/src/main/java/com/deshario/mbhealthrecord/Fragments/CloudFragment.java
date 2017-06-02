package com.deshario.mbhealthrecord.Fragments;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.deshario.mbhealthrecord.LoggedIN;
import com.deshario.mbhealthrecord.MainActivity;
import com.deshario.mbhealthrecord.Models.AppSingleton;
import com.deshario.mbhealthrecord.R;
import com.deshario.mbhealthrecord.api.RetroClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;


/**
 * A simple {@link Fragment} subclass.
 */
public class CloudFragment extends Fragment {

    EditText username,email,password,password_again,login_username,login_password;
    String name,e_mail,pass,pass_again;
    View positiveAction;
    Button login,register,reset;
    boolean closepopup = false;
    Button back_up,re_store;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final String APPDIRNAME = "บันทึกสุขภาพแม่และเด็ก";
    File currentDB,backupDB;
    SweetAlertDialog swbackedup;
    private static final String CONFIG_NAME = "userfirsttime";
    MaterialDialog dialog;

    private ProgressDialog pDialog;
    StringRequest strReq;
    //private static final String TAG = RegisterActivity.class.getSimpleName();

    private static final String TAG = "RegisterActivity";
    //private static final String URL_FOR_REGISTRATION = "http://10.0.2.2/android_login_api/register.php";
    private static final String URL_FOR_REGISTRATION = "http://momandbaby.000webhostapp.com/android_login_api/register.php";

    //private static final String URL_FOR_LOGIN = "http://10.0.2.2/android_login_api/login.php";
    private static final String URL_FOR_LOGIN = "http://momandbaby.000webhostapp.com/android_login_api/login.php";
    ProgressDialog progressDialog;
    SweetAlertDialog sweetdialog,sw_register;

    private LoggedIN prefManager;


    public CloudFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_cloud, container, false);

        prefManager = new LoggedIN(getActivity());
        if (!prefManager.isloggedin()) {
            //Toast.makeText(getActivity(),"Logged in",Toast.LENGTH_SHORT).show();
            //launchHomeScreen();
            //finish();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            Backup_Restore fragname = new Backup_Restore();
            ft.addToBackStack(null);
            ft.hide(CloudFragment.this);
            ft.replace(R.id.containerView, fragname);
            ft.commit();
        }else{
            //Toast.makeText(getActivity(),"Not logged in",Toast.LENGTH_SHORT).show();
            //View view = inflater.inflate(R.layout.fragment_cloud, container, false);
            ((MainActivity) getActivity()).setActionBarTitle("ซิงค์ข้อมูล");
            login_username = (EditText)view.findViewById(R.id.log_user);
            login_password = (EditText)view.findViewById(R.id.log_pass);

            login = (Button)view.findViewById(R.id.loginbtn);
            register = (Button)view.findViewById(R.id.registerbtn);
            reset = (Button)view.findViewById(R.id.resetbtn);

            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setCancelable(false);

            sweetdialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
            sweetdialog.setCancelable(false);
            sw_register = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
            sw_register.setCancelable(false);


            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String user = login_username.getText().toString();
                    String pass = login_password.getText().toString();

                    // Check for empty data in the form
                    if (!user.isEmpty() && !pass.isEmpty()) {
                        loginUser(user, pass);
                        //registerUser(user, pass);
                    } else {
                        Toast.makeText(getActivity(), " กรุณากรอกข้อมูลให้ครบ\n แล้วลองใหม่อีกครั้ง", Toast.LENGTH_LONG).show();
                    }

                    //alertdata();
                }
            });
            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    register();
                }
            });
            reset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    login_username.setText("");
                    login_password.setText("");
                }
            });
            //return view;
        }
        return view;
    }


    private void loginUser( final String email, final String password) {
        // Tag used to cancel the request
        String cancel_req_tag = "login";
        sweetdialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        sweetdialog.setTitleText("กำลังเข้าสู่ระบบ");
        showDialog();
        WindowManager.LayoutParams lp = sweetdialog.getWindow().getAttributes();
        lp.dimAmount = 1f;
        sweetdialog.getWindow().setAttributes(lp);
        sweetdialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_FOR_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        final String user = jObj.getJSONObject("user").getString("name");
                       // Toast.makeText(getActivity(),"sss Success",Toast.LENGTH_SHORT).show();

                        SweetAlertDialog login_success = new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE);
                        login_success.setTitleText("เข้าสู่ระบบสำเร็จ");
                        login_success.setConfirmText("ตกลง");
                        login_success.setCancelable(false);
                        login_success.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                // Toast.makeText(getActivity(),"HW",Toast.LENGTH_SHORT).show();
                                sweetAlertDialog.dismiss();

                                LoggedIN fd = new LoggedIN(getActivity());
                                fd.setLoginData(user,email,password);
                                fd.setLogin(false);
                                //startActivity(new Intent(get.this, MainActivity.class));

                                FragmentManager fm = getFragmentManager();
                                FragmentTransaction ft = fm.beginTransaction();
                                Backup_Restore fragname = new Backup_Restore();
                                ft.addToBackStack(null);
                                ft.hide(CloudFragment.this);
                                ft.replace(R.id.containerView, fragname);
                                Bundle args = new Bundle();
                                args.putString("index", "From Activity");
                                fragname.setArguments(args);
                                ft.commit();
                            }
                        });
                        login_success.show();
                        WindowManager.LayoutParams lp = login_success.getWindow().getAttributes();
                        lp.dimAmount = 1f;
                        login_success.getWindow().setAttributes(lp);
                        login_success.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                        // Launch User activity
//                        Intent intent = new Intent(
//                                LoginActivity.this,
//                                UserActivity.class);
//                        intent.putExtra("username", user);
//                        startActivity(intent);
//                        finish();
                    } else {

                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getActivity(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }

        };
        // Adding request to request queue
        AppSingleton.getInstance(getActivity()).addToRequestQueue(strReq,cancel_req_tag);
    }

    private void showDialog() {
        if (!sweetdialog.isShowing())
            sweetdialog.show();
    }
    private void hideDialog() {
        if (sweetdialog.isShowing())
            sweetdialog.dismiss();
    }

    public void register(){
        boolean wrapInScrollView = true;
        MaterialDialog dialog = new MaterialDialog.Builder(getContext())
                .title("ลงทะเบียนผู้ใช้งานใหม่")
                .customView(R.layout.register_account, true)
                .positiveText("ตกลง")
                .negativeText("ยกเลิก")
                .titleColorRes(R.color.primary_bootstrap)
                .positiveColorRes(R.color.primary_bootstrap)
                .negativeColorRes(R.color.primary_bootstrap)
                .canceledOnTouchOutside(false)
                .cancelable(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        name = username.getText().toString();
                        e_mail = email.getText().toString();
                        pass = password.getText().toString();
                        pass_again = password_again.getText().toString();

                        if (TextUtils.isEmpty(e_mail) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(pass_again)){
                            Toast.makeText(getActivity(),"กรุณากรอกข้อมูลให้ครบ !\n แล้วลองใหม่อีกครั้ง",Toast.LENGTH_SHORT).show();
                        }else{
                            String validated_pass = validate_pass(pass,pass_again);

                            if(validated_pass == null){
                                Toast.makeText(getActivity(),"รหัสผ่านไม่ตรงกัน",Toast.LENGTH_SHORT).show();
                            }else{
                                reg_account(name,e_mail,pass);
                            }
                        }
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .build();

        positiveAction = dialog.getActionButton(DialogAction.POSITIVE);
        username = (EditText)dialog.getCustomView().findViewById(R.id.user_name);
        email = (EditText)dialog.getCustomView().findViewById(R.id.user_email);
        password = (EditText)dialog.getCustomView().findViewById(R.id.user_pass);
        password_again = (EditText)dialog.getCustomView().findViewById(R.id.user_pass_confirm);
        SharedPreferences user_dat = getActivity().getSharedPreferences(CONFIG_NAME, Context.MODE_PRIVATE);
        String mom_name = user_dat.getString("Nickname", "");
        username.setText(mom_name);
        username.requestFocus(username.length());


        dialog.show();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.dimAmount = 1f;
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

    }

    public String validate_pass(String pass, String confirm_pass){
        if(!pass.equals(confirm_pass)){
            return null;
        }
        return pass;
    }


    private void reg_account(final String name,  final String email, final String password) {
        // Tag used to cancel the request
        String cancel_req_tag = "register";

       // progressDialog.setMessage("Adding you ...");
        sw_register.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        sw_register.setTitleText("กำลังทำการสมัครบัญชี");
        //showDialog();
        if (!sw_register.isShowing()){
            sw_register.show();
            WindowManager.LayoutParams lp = sw_register.getWindow().getAttributes();
            lp.dimAmount = 1f;
            sw_register.getWindow().setAttributes(lp);
            sw_register.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        }
        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_FOR_REGISTRATION, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                //hideDialog();
                if (sw_register.isShowing()){
                    sw_register.dismiss();
                    WindowManager.LayoutParams lp = sw_register.getWindow().getAttributes();
                    lp.dimAmount = 1f;
                    sw_register.getWindow().setAttributes(lp);
                    sw_register.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                }
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        String user = jObj.getJSONObject("user").getString("name");
                        SweetAlertDialog reg_ok = new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE);
                        reg_ok.setTitleText("ลงทะเบียนสำเร็จ");
                        reg_ok.setContentText("ยินดีต้อนรับคุณ"+user);
                        reg_ok.setConfirmText("ตกลง");
                        reg_ok.setCancelable(false);
                        reg_ok.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                            }
                        });
                        reg_ok.show();
                        WindowManager.LayoutParams lp = reg_ok.getWindow().getAttributes();
                        lp.dimAmount = 1f;
                        reg_ok.getWindow().setAttributes(lp);
                        reg_ok.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                       // Toast.makeText(getActivity(), "Hi " + user +", You are successfully Added!", Toast.LENGTH_SHORT).show();
                        // Launch login activity
//                        Intent intent = new Intent( RegisterActivity.this, LoginActivity.class);
//                        startActivity(intent);
//                        finish();
                    } else {
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getActivity(),errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        // Adding request to request queue
        AppSingleton.getInstance(getActivity()).addToRequestQueue(strReq, cancel_req_tag);
    }



    public void alertdata(){
        MaterialDialog dialog = new MaterialDialog.Builder(getContext())
                .title("สำรองหรือกู้คืนข้อมูล")
                .customView(R.layout.backedup_restore, true)
                .positiveText("ตกลง")
                .negativeText("ยกเลิก")
                .titleColorRes(R.color.primary_bootstrap)
                .positiveColorRes(R.color.primary_bootstrap)
                .negativeColorRes(R.color.primary_bootstrap)
                .canceledOnTouchOutside(false)
                .cancelable(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        //onBackPressed();
                    }
                })
                .build();

        positiveAction = dialog.getActionButton(DialogAction.POSITIVE);
        back_up = (Button)dialog.getCustomView().findViewById(R.id.backup);
        re_store = (Button)dialog.getCustomView().findViewById(R.id.restore);
        back_up.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
              // backupdata();
           }
       });
        re_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        dialog.show();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.dimAmount = 1f;
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
    }

//    public void backupdata(){
//        chkdir();
//        Boolean access = verifyStoragePermissions(getActivity());
//        if(access == true){
//            backup(APPDIRNAME);
//        }else{
//            Toast.makeText(getActivity()," Permission Denied\n Please Try Again",Toast.LENGTH_SHORT).show();
//            //backupdata();
//        }
//    }
//
//    public void chkdir(){
//        File folder = new File(Environment.getExternalStorageDirectory() + "/"+APPDIRNAME);
//        boolean success = true;
//        if (!folder.exists()) {
//            success = folder.mkdir();
//        }
//    }

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

//    private void backup(String foldername){
//        try {
//            File external_storage = Environment.getExternalStorageDirectory();
//            final File data = Environment.getDataDirectory();
//
//            if (external_storage.canWrite()) {
//                String currentDBPath = "/data/"+ "com.deshario.mbhealthrecord" +"/databases/"+"weeks.db";
//                // String backupDBPath = "/"+foldername+"/"+"database.db";
//                // String backupDBPath = "/"+foldername+"/"+"55"+current_datetime+""; //.db
//                String backupDBPath = "/"+foldername+"/"+"MomnBabyData"; //.db
//                currentDB = new File(data, currentDBPath);
//                backupDB = new File(external_storage, backupDBPath);
//                // File backupDB = new File();
//                String str = backupDB.toString();
//
//                if (backupDB.exists()) { // currentdb
//                    swbackedup = new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
//                            .setTitleText("ตรวจพบการสำรองข้อมูลเก่า")
//                            .setContentText("ข้อมูลเก่าที่สำรองเอาไว้จะถูกเขียนทับด้วยข้อมูลปัจจุบัน")
//                            .setConfirmText("ตกลง")
//                            .setCancelText("ยกเลิก")
//                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                                @Override
//                                public void onClick(SweetAlertDialog sDialog) {
//                                    try{
//                                        FileChannel src = new FileInputStream(currentDB).getChannel();
//                                        FileChannel dst = new FileOutputStream(backupDB,false).getChannel();
//                                        dst.transferFrom(src, 0, src.size());
//                                        src.close();
//                                        dst.close();
//                                        confirmok(1);
//                                    }catch (Exception e){
//                                        sDialog.dismissWithAnimation();
//                                    }
//                                }
//                            })
//                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                                @Override
//                                public void onClick(SweetAlertDialog sweetAlertDialog) {
//                                    sweetAlertDialog.dismiss();
//                                }
//                            });
//                                swbackedup.show();
//                                WindowManager.LayoutParams lp = swbackedup.getWindow().getAttributes();
//                                lp.dimAmount = 1f;
//                                swbackedup.getWindow().setAttributes(lp);
//                                swbackedup.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
//                       uploaddb(str);
//                }else{
//                    FileChannel src = new FileInputStream(currentDB).getChannel();
//                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
//                    dst.transferFrom(src, 0, src.size());
//                    src.close();
//                    dst.close();
//                   // Toast.makeText(getActivity(), "สำรองข้อมูลสำเร็จ", Toast.LENGTH_SHORT).show();
//                    confirmok(0);
//                    uploaddb(str);
//                }
//            }
//        } catch (Exception e) {
//        }
//
//    }
//
//    private void uploaddb(String filename) {
//        final SweetAlertDialog progressDialog;
//       // final ProgressDialog pDialog;
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
//                        //Toast.makeText(getActivity(),"สำรองข้อมูลสำเร็จ",Toast.LENGTH_SHORT).show();
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
