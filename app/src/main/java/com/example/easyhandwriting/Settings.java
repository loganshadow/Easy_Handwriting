package com.example.easyhandwriting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        final Switch sw = findViewById(R.id.switch2);
        try {
            sw.setChecked(savedInstanceState.getBoolean("checked"));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        SharedPreferences appSettingPrefs = getSharedPreferences("AppSettingPrefs", 0);
        final SharedPreferences.Editor sharedPrefsEdit = appSettingPrefs.edit();
        boolean isNightModeOn = appSettingPrefs.getBoolean("NightMode", false);

        sw.setChecked(appSettingPrefs.getBoolean("NightMode", false));

        sw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sw.isChecked()) {
                    sharedPrefsEdit.putBoolean("NightMode", true);
                    sharedPrefsEdit.apply();
                    Toast.makeText(getApplicationContext(), "Dark side", Toast.LENGTH_LONG).show();
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    sharedPrefsEdit.putBoolean("NightMode", false);
                    sharedPrefsEdit.apply();
                    Toast.makeText(getApplicationContext(), "Light side", Toast.LENGTH_LONG).show();
                }
                recreate();
            }
        });



        /*sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    Toast.makeText(getApplicationContext(), "Dark side", Toast.LENGTH_LONG).show();
                }
                else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    Toast.makeText(getApplicationContext(), "Light side", Toast.LENGTH_LONG).show();
                }
            }
        });
    }*/
    }
}
