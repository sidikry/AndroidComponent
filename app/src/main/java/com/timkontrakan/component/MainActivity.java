package com.timkontrakan.component;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private BatteryLevelReceiver batteryLevelReceiver = new BatteryLevelReceiver();
    private IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        registerReceiver(batteryLevelReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(batteryLevelReceiver);
        super.onPause();
    }
}