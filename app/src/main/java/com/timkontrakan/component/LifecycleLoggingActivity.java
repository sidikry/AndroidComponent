package com.timkontrakan.component;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

public class LifecycleLoggingActivity extends Activity {

    /**
     * Debugging tag used by the Android logger.
     */
    private final String TAG =
            getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            // The activity is being re-created. Use the
            // savedInstanceState bundle for initializations either
            // during onCreate or onRestoreInstanceState().
            Log.d(TAG, "onCreate(): activity re-created");

        } else {
            // Activity is being created anew. No prior saved
            // instance state information available in Bundle object.
            Log.d(TAG, "onCreate(): activity created anew");
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() - the activity is about to become visible");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,
                "onResume() - the activity has become visible (it is now " +
                        "\"resumed\")");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,
                "onPause() - another activity is taking focus (this activity " +
                        "is about to be \"paused\")");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart() - the activity is about to be restarted()");
    }

    /**
     * Hook method that gives a final chance to release resources and
     * stop spawned threads. onDestroy() may not always be called-when
     * system kills hosting process
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() - the activity is about to be destroyed");
    }

}
