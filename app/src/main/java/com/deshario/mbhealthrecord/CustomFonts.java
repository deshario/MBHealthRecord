package com.deshario.mbhealthrecord;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Deshario on 12/24/2016.
 */

public class CustomFonts extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Kanit.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
    }
}
