package com.deshario.mbhealthrecord;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.deshario.mbhealthrecord.Database.DBHelper;
import com.deshario.mbhealthrecord.Fragments.CloudFragment;
import com.deshario.mbhealthrecord.Fragments.Dontdoit_fragment;
import com.deshario.mbhealthrecord.Fragments.DueDateFragment;
import com.deshario.mbhealthrecord.Fragments.EventCalendar;
import com.deshario.mbhealthrecord.Fragments.Excercise_Fragment;
import com.deshario.mbhealthrecord.Fragments.GraphTabFragment;
import com.deshario.mbhealthrecord.Fragments.MainFragment;
import com.deshario.mbhealthrecord.Fragments.TabFragment;
import com.deshario.mbhealthrecord.api.RetroClient;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity{

    private static Toast toast;
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    int goback = 1;

    String u_weight,u_height,u_week;
    View positiveAction;
    EditText user_date,user_height,user_weeks;
    String duedate_length,delete_weekselect,data;
    String current_datetime;
    boolean permission = false;

    Fragment fragment_to_open;

    boolean month1,month2,month3,month4,month5,month6,month7,month8,month9;
    private static final String CONFIG_NAME = "userfirsttime";
    private static final String APPDIRNAME = "บันทึกสุขภาพแม่และเด็ก";
    private SharedPreferences SP;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        deleteCache(getApplicationContext());


        // Setup the DrawerLayout and NavigationView
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.navigation);

        // Current Date
        //DateFormat df_medium = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT); // DATETIME
        DateFormat mydate = DateFormat.getDateInstance(DateFormat.LONG); // DATE

        Calendar myCalendar = Calendar.getInstance();
        DateFormat aaa = new SimpleDateFormat("yyyy-MM-dd");
        String today_date = aaa.format(myCalendar.getTime());

        String sDate = "2013-08-12";
        System.out.println("Date Thai : " + dateThai(sDate));

        View header = mNavigationView.getHeaderView(0);
        TextView mother_name = (TextView) header.findViewById(R.id.headertxt);
        TextView title = (TextView) header.findViewById(R.id.datenow);

        title.setText(" "+dateThai(today_date));
        Calendar cal = Calendar.getInstance();
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        //current_datetime = sdf.format(cal.getTime());

        SharedPreferences prfs = getSharedPreferences(CONFIG_NAME, Context.MODE_PRIVATE);
        String mom_name = prfs.getString("Nickname", "");
        String mom_first_week = prfs.getString("Week", "");
        prfs.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                Toast.makeText(MainActivity.this,"data = "+s,Toast.LENGTH_LONG).show();
            }
        });

        //mother_name.setText(mom_name);//+" (สัปดาห์ที่ "+mom_first_week+")");



        mother_name.setText(mom_name);



        // title.setText(""+formattedDate);
        // title.setText("Welcome to Mom and baby health record application");

        // Inflate the very first fragment
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView, new MainFragment());
        mFragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        mFragmentTransaction.addToBackStack(null);
        //mFragmentTransaction.addToBackStack("MainFrag");
        mFragmentTransaction.commit();//MainFragment


        // Setup Navigation Drawer Toggle of the Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                // Parameters = Activity, Drawerlayout, Toolbar, Opendrawer, Closedrawer
                this,
                mDrawerLayout,
                toolbar,
                R.string.app_name,
                R.string.app_name
        );
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        // Listener to Navigation View Items.
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers(); // Close or Hide Navdrawer
                int selected_menu = menuItem.getItemId();
                FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                switch (selected_menu) {
                    case R.id.pregnant_calendar:
                        //setActionBarTitle("ปฏิทินสำหรับหญิงตั้งครรภ์");
                        fragment_to_open = new TabFragment();
                        break;
                    case R.id.pregnant_excercise:
                        //setActionBarTitle("การออกกำลังกาย");
                        fragment_to_open = new Excercise_Fragment();
                        break;
                    case R.id.pregnant_dontdoit:
                        //setActionBarTitle("ข้อห้ามสำหรับหญิงตั้งครรภ์");
                        fragment_to_open = new Dontdoit_fragment();
                        break;
                    case R.id.graph_menu:
                        //setActionBarTitle("บันทึกสุขภาพประจำสัปดาห์");
                        fragment_to_open = new GraphTabFragment();
                        break;
                    case R.id.doctor_schedule_menu:
                        // setActionBarTitle("ตารางนัดหมอ");
                        fragment_to_open = new EventCalendar();
                        break;
                    case R.id.deliverydate_menu:
                        //setActionBarTitle("คำนวณวันคลอด");
                        fragment_to_open = new DueDateFragment();
                        break;
                    //case R.id.cloud_menu:
                       //  fragment_to_open = new CloudFragment();
                        //startActivity(new Intent(MainActivity.this,OKHdTTP.class));
//                        Calendar c = Calendar.getInstance();
//                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                        current_datetime = sdf.format(c.getTime());
//                       chkdir();
//                         permission = verifyStoragePermissions(MainActivity.this);
//                        if(permission == true){
//                            backup(APPDIRNAME);
//                            //restore(APPDIRNAME);
//                        }
                       // break;
                    case R.id.myprofile:
                        // setActionBarTitle("ข้อมูลของฉัน");
                        fragment_to_open = new MainFragment();
                        break;
                    /*case R.id.cloud_menu:
                       // setActionBarTitle("ซิงค์ข้อมูล");
                        fragment_to_open = new CloudFragment();
                        break;*/
                    /*case R.id.chkforupdate:
                        new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("ข้ออภัย")
                                .setContentText("เราจะแจ้งให้คูณทราบเมือมีเวอร์ชันใหม่")
                                .setConfirmText("ตกลง")
                                .show();
                        break;*/
                    case R.id.about_app:
                        MaterialDialog aboutdialog = new MaterialDialog.Builder(MainActivity.this)
                                .title("ข้อตกลงการใช้งาน")
                                .customView(R.layout.license_agreement, true)
                                .positiveText("ปิด")
                                .titleColorRes(R.color.primary_bootstrap)
                                .positiveColorRes(R.color.colorPrimaryDark)
                                .negativeColorRes(R.color.primary_bootstrap)
                                .canceledOnTouchOutside(false)
                                .cancelable(false)
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        dialog.dismiss();
                                    }
                                })
                                .build();

                        positiveAction = aboutdialog.getActionButton(DialogAction.POSITIVE);
                        aboutdialog.show();
                        WindowManager.LayoutParams lp = aboutdialog.getWindow().getAttributes();
                        lp.dimAmount = 1f;
                        aboutdialog.getWindow().setAttributes(lp);
                        aboutdialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                        break;
                    case R.id.exit_app:
                        moveTaskToBack(true);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);
                        break;
                    default:
                        break;
                }
                if(fragment_to_open != null){
                    fragmentTransaction.replace(R.id.containerView, fragment_to_open);
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
                return false;
            }

        });

        // fab();
    }



    @Override
    public void onResume() {
        super.onResume();
        //getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    public static String dateThai(String strDate){
//        String Months[] = {
//                "ม.ค", "ก.พ", "มี.ค", "เม.ย",
//                "พ.ค", "มิ.ย", "ก.ค", "ส.ค",
//                "ก.ย", "ต.ค", "พ.ย", "ธ.ค"};
        String Months[] = {
                "มกราคม", "กุมภาพันธ์", "มีนาคม", "เมษายน",
                "พฤษภาคม", "มิถุนายน", "กรกฎาคม", "สิงหาคม",
                "กันยายน", "ตุลาคม", "พฤศจิกายน", "ธันวาคม"};

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        int year=0,month=0,day=0;
        try {
            Date date = df.parse(strDate);
            Calendar c = Calendar.getInstance();
            c.setTime(date);

            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DATE);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return String.format("%s %s %s", day,Months[month],year+543);
    }

    // Cache Start
    private void initializeCache() {
        long size = 0;
        size += getDirSize(this.getCacheDir());
        size += getDirSize(this.getExternalCacheDir());
        Toast.makeText(MainActivity.this,""+size+" Bytes",Toast.LENGTH_LONG).show();
    }

    public long getDirSize(File dir){
        long size = 0;
        for (File file : dir.listFiles()) {
            if (file != null && file.isDirectory()) {
                size += getDirSize(file);
            } else if (file != null && file.isFile()) {
                size += file.length();
            }
        }
        return size;
    }

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    // Cache End

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
            File data = Environment.getDataDirectory();

            if (external_storage.canWrite()) {
                String currentDBPath = "/data/"+ "com.deshario.mbhealthrecord" +"/databases/"+"weeks.db";
                // String backupDBPath = "/"+foldername+"/"+"database.db";
               // String backupDBPath = "/"+foldername+"/"+"55"+current_datetime+""; //.db
                String backupDBPath = "/"+foldername+"/"+"MomnBabyData"; //.db
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(external_storage, backupDBPath);
               // File backupDB = new File();
                String str = backupDB.toString();

                if (backupDB.exists()) { // currentdb
//                    FileChannel src = new FileInputStream(currentDB).getChannel();
//                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
//                    dst.transferFrom(src, 0, src.size());
//                    src.close();
//                    dst.close();
//                     Toast.makeText(getApplicationContext(), "สำรองข้อมูลสำเร็จ", Toast.LENGTH_SHORT).show();
 ///                  Toast.makeText(getApplicationContext(), "str : "+str, Toast.LENGTH_LONG).show();
                    SweetAlertDialog swbackedup = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Database already exists")
                            .setContentText("Won't be able to recover this file!")
                            .setConfirmText("Yes,delete it!")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                }
                            })
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {

                                }
                            });
                    swbackedup.show();
                    String aa = "1";
                    if(aa.equals("1")){
                        Toast.makeText(MainActivity.this," yes 1",Toast.LENGTH_LONG).show();
                        FileChannel src = new FileInputStream(currentDB).getChannel();
                        FileChannel dst = new FileOutputStream(backupDB,false).getChannel();
                        dst.transferFrom(src, 0, src.size());
                        src.close();
                        dst.close();
                    }else{
                        Toast.makeText(MainActivity.this," no 1",Toast.LENGTH_LONG).show();
                    }
//                    uploaddb(str);
                }else{
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                    Toast.makeText(getApplicationContext(), "สำรองข้อมูลสำเร็จ", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
        }

    }

//    private void uploaddb(String filename) {
//        final ProgressDialog progressDialog;
//        progressDialog = new ProgressDialog(MainActivity.this);
//        progressDialog.setMessage("กำลังกู้คืน");
//        progressDialog.show();
//        //Create Upload Server Client
//        com.deshario.mbhealthrecord.api.ApiService service = RetroClient.getApiService();
//        //String imagePath = "/storage/9016-4EF8/hello.txt";
//        File file = new File(filename);
//
//        // create RequestBody instance from file
//        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//        MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file", file.getName(), requestFile);
//
//        Call<Result> resultCall = service.uploadImage(body);
//        // finally, execute the request
//        resultCall.enqueue(new Callback<Result>() {
//            @Override
//            public void onResponse(Call<Result> call, Response<Result> response) {
//                progressDialog.dismiss();
//                // Response Success or Fail
//                if(response.isSuccessful()) {
//                    if(response.body().getResult().equals("success")){
//                        Toast.makeText(MainActivity.this,"Backup success",Toast.LENGTH_SHORT).show();
//                    }else{
//                        Toast.makeText(MainActivity.this,"Backup Fail",Toast.LENGTH_SHORT).show();
//                    }
//                }else{
//                    Toast.makeText(MainActivity.this,"Backup Fail",Toast.LENGTH_SHORT).show();
//                }
//                // imagePath = "";
//            }
//            @Override
//            public void onFailure(Call<Result> call, Throwable t) {
//                progressDialog.dismiss();
//            }
//        });
//    }
//
//    private void restore(String foldername){
//        try {
//            File external_storage = Environment.getExternalStorageDirectory();
//            File data = Environment.getDataDirectory();
//
//            if (external_storage.canWrite()) {
//                String currentDBPath = "/data/"+ "com.deshario.mbhealthrecord" +"/databases/"+"weeks.db";
//                String backupDBPath = "/"+foldername+"/"+current_datetime+""; //.db
//                File currentDB = new File(data, currentDBPath);
//                File backupDB = new File(external_storage, backupDBPath);
//
//                if (currentDB.exists()) {
//                    FileChannel src = new FileInputStream(backupDB).getChannel();
//                    FileChannel dst = new FileOutputStream(currentDB).getChannel();
//                    dst.transferFrom(src, 0, src.size());
//                    src.close();
//                    dst.close();
//                    Toast.makeText(getApplicationContext(), "กู้คืนข้อมูลสำเร็จ", Toast.LENGTH_SHORT).show();
//                }
//            }
//        } catch (Exception e) {
//        }
//
//    }




    // Set ActionBar Title
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            //clearBackStack(0);

        }else {
            super.onBackPressed();
            clearBackStack(goback);
            // deleteCache(getApplicationContext());
        }
//        int goback = 1;
//       if(goback == 1){
//            clearBackStack(goback);
//        }else{
//           // showToast("Else Goback : "+goback);
//       }
    }

    // CLEAR BACK STACK.
    private void clearBackStack(int firsttime) {
        final FragmentManager fragmentManager = getSupportFragmentManager();
//        showToast("fragment : "+fragmentManager.getBackStackEntryCount());
        while(fragmentManager.getBackStackEntryCount() != 0) {
            fragmentManager.popBackStackImmediate();
        }
//        for(int fragclose = 0; fragclose < fragmentManager.getBackStackEntryCount(); fragclose++){
//            fragmentManager.popBackStackImmediate();
//        }

//        if(fragmentManager.getBackStackEntryCount() >0){
            mFragmentManager = getSupportFragmentManager();
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.replace(R.id.containerView, new MainFragment());
            mFragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            mFragmentTransaction.disallowAddToBackStack();
            //mFragmentTransaction.addToBackStack("MainFrag");
            mFragmentTransaction.commit();//MainFragment
            setActionBarTitle("บันทึกสุขภาพแม่และเด็ก");
//        }


        goback++;
    }

    @Override
    protected void attachBaseContext(Context newBase) { // Set Default Font
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }




    public void showToast(String message) {
        //private Toast toast;
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public boolean validweek(int validthisweek){
        DBHelper dbHandler;
        dbHandler = new DBHelper(MainActivity.this, null, null, 1);
        boolean check = dbHandler.uniqueweek(validthisweek);
        return check;
    }


}
