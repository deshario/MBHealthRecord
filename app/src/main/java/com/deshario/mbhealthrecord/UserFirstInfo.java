package com.deshario.mbhealthrecord;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.deshario.mbhealthrecord.FirstTimeData;
import com.mikhaellopez.circularimageview.CircularImageView;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Deshario on 1/28/2017.
 */

public class UserFirstInfo extends AppCompatActivity {

    EditText nickname,weight,height;
    String u_nickname,u_weight,u_height,u_week;
    Boolean status = false;
    Button btn;
    CircularImageView profile_image;
    private FirstTimeData prefManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_first_info);

        prefManager = new FirstTimeData(this);
        if (!prefManager.isFirstTimeLaunch()) {
            launchHomeScreen();
            finish();
        }

        profile_image = (CircularImageView)findViewById(R.id.profile);
        nickname = (EditText)findViewById(R.id.input_nickname);
        weight = (EditText)findViewById(R.id.input_weight);
        height = (EditText)findViewById(R.id.input_height);
        btn = (Button)findViewById(R.id.save_data);
    }

    public void save_form(View view){
        boolean chk = scan();
        if(chk == true){
            //Toast.makeText(UserFirstInfo.this, "  ! \n ", Toast.LENGTH_SHORT).show();
            FirstTimeData fd = new FirstTimeData(this);
            fd.setFirstTimedata(u_nickname,u_weight,u_height,u_week);
            fd.setFirstTimeLaunch(false);
            startActivity(new Intent(UserFirstInfo.this, MainActivity.class));
            //finish();
        }else{
            Toast.makeText(UserFirstInfo.this, " กรุณากรอกข้อมูลให้ครบ ! \n แล้วลองใหม่อีกครั้ง", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean scan(){
        u_nickname = nickname.getText().toString();
        u_weight = weight.getText().toString();
        u_height = height.getText().toString();
        u_week = "1";
        if (TextUtils.isEmpty(u_weight) || TextUtils.isEmpty(u_height) || TextUtils.isEmpty(u_nickname)){
            return false;
        }else{
            return true;
        }
    }

    private void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(UserFirstInfo.this, MainActivity.class));
        finish();
    }

    @Override
    protected void attachBaseContext(Context newBase) { // Set Default Font
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
