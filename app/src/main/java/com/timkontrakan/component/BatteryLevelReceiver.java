package com.timkontrakan.component;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.BatteryManager;
import android.widget.ImageView;
import android.widget.TextView;

public class BatteryLevelReceiver extends BroadcastReceiver {


    public BatteryLevelReceiver() {
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onReceive(Context context, Intent intent) {

//        TextView statusLabel = ((MainActivity) context).findViewById(R.id.statusLabel);
//        TextView statusPercentage = ((MainActivity) context).findViewById(R.id.batteryPercentage);
//        ImageView batteryImage = ((MainActivity) context).findViewById(R.id.batteryImage);

        String action = intent.getAction();
        if (action != null && action.equals(Intent.ACTION_BATTERY_CHANGED)) {

            //
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            String message = "";
            switch (status) {
                case BatteryManager.BATTERY_STATUS_FULL:
                    message = "Full";
                    break;
                case BatteryManager.BATTERY_STATUS_CHARGING:
                    message = "Charging";
                    break;
                case BatteryManager.BATTERY_STATUS_DISCHARGING:
                    message = "Discharging";
                    break;
                case BatteryManager.BATTERY_STATUS_UNKNOWN:
                    message = "Unknown";
                    break;
            }
//            statusLabel.setText(message);
//
//            //Percentage
//            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
//            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
//            int percentage = level * 100 / scale;
//            statusPercentage.setText(percentage + "%");
//
//            //Image
//            Resources resources = context.getResources();
//            if (percentage >= 90) {
//                batteryImage.setImageDrawable(resources.getDrawable(R.drawable.b100));
//            } else if (90 > percentage && percentage >= 65) {
//                batteryImage.setImageDrawable(resources.getDrawable(R.drawable.b75));
//            } else if (65 > percentage && percentage >= 40) {
//                batteryImage.setImageDrawable(resources.getDrawable(R.drawable.b50));
//            } else if (40 > percentage && percentage >= 15) {
//                batteryImage.setImageDrawable(resources.getDrawable(R.drawable.b25));
//            }else {
//                batteryImage.setImageDrawable(resources.getDrawable(R.drawable.b0));
//            }
        }
    }


}
