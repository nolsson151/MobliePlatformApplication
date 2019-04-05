package com.gcu.mpd.S1434184;



import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

/**
 *  * Name                 Niklas Olsson
 *  * Student ID           S1434184
 *  * Programme of Study   Computing
 *  *
 *  * @author Niklas
 *  * @version 1.0
 */
public class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
    static final String EARTHQUAKE_QUERY = "EARTHQUAKE_QUERY";

    /**
     * Method that implements an action bar, used within the MainActivity to hold an options menu for searching
     * and sorting.
     * @param enableHome boolean for turning the toolbar on or off
     */
    public void activateToolbar(boolean enableHome) {
        Log.d(TAG, "activateToolbar: starts");
        ActionBar actionBar = getSupportActionBar();
        if(actionBar == null) {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

            if(toolbar != null) {
                setSupportActionBar(toolbar);
                actionBar = getSupportActionBar();
            }
        }

        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(enableHome);
        }
    }
}