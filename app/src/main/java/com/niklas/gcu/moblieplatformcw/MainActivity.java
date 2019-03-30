package com.niklas.gcu.moblieplatformcw;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    private ListView listEarthquakes;
    private EarthquakeAdapter earthquakeAdapter;
    private DatePicker datePicker;
    public ParseEarthquakes parseEarthquakes;
    private ArrayList<Earthquake> masterList;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activateToolbar(false);
        listEarthquakes = (ListView) findViewById(R.id.list_earthquakes);

        Log.d(TAG, "onCreate: Starting AsyncTak");
        DownloadData downloadData = new DownloadData();
        downloadData.execute("http://quakes.bgs.ac.uk/feeds/MhSeismology.xml");
        Log.d(TAG, "onCreate: done");
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.pullToRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData(); // your code
                swipeRefreshLayout.setRefreshing(false);
            }
        });


    }

    public void refreshData(){
        DownloadData downloadData = new DownloadData();
        downloadData.execute("http://quakes.bgs.ac.uk/feeds/MhSeismology.xml");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu: created");
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                if (TextUtils.isEmpty(s)) {
                    earthquakeAdapter.filter("");
                    listEarthquakes.clearTextFilter();
                }
                else {
                    earthquakeAdapter.filter(s);
                }
                return true;
            }
        });
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected: Begin");
        switch (item.getItemId()){
            case R.id.action_date:
                Log.d(TAG, "onOptionsItemSelected: Date selected");
                MainActivity.this.showDatePicker(this.datePicker);
            case R.id.action_All:
                Log.d(TAG, "onOptionsItemSelected: All selected");
                earthquakeAdapter = new EarthquakeAdapter(MainActivity.this, R.layout.list_record, masterList);
                listEarthquakes.setAdapter(earthquakeAdapter);
                earthquakeAdapter.notifyDataSetChanged();
                break;
            case R.id.action_A_z:
                ArrayList<Earthquake> AzList = new ArrayList<>(masterList);
                Collections.sort(AzList, Earthquake.locationCompare);
                earthquakeAdapter = new EarthquakeAdapter(MainActivity.this, R.layout.list_record, AzList);
                listEarthquakes.setAdapter(earthquakeAdapter);
                earthquakeAdapter.notifyDataSetChanged();
                break;
            case R.id.action_magnitude:
                ArrayList<Earthquake> magList = new ArrayList<>(masterList);
                Collections.sort(magList, Earthquake.magnitudeCompare);
                earthquakeAdapter = new EarthquakeAdapter(MainActivity.this, R.layout.list_record, magList);
                listEarthquakes.setAdapter(earthquakeAdapter);
                earthquakeAdapter.notifyDataSetChanged();
                break;
            default:
                Log.d(TAG, "onOptionsItemSelected: End");
        }
        return true;
    }

    public void setMasterList(ArrayList listToSet){
        this.masterList = new ArrayList<>();
        masterList.addAll(listToSet);
    }
    /**
     * Handles the button click to create a new date picker fragment and
     * show it.
     *
     * @param view View that was clicked
     */
    public void showDatePicker(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(),
                getString(R.string.datepicker));
    }

    /**
     * Process the date picker result into strings that can be displayed in
     * a Toast.
     *
     * @param year Chosen year
     * @param month Chosen month
     * @param day Chosen day
     */
    public void processDatePickerResult(int year, int month, int day) {
        String month_string = Integer.toString(month + 1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);
        String dateMessage = (month_string +
                "/" + day_string +
                "/" + year_string);

        Toast.makeText(this, "Date: " + dateMessage,
                Toast.LENGTH_SHORT).show();
    }



    private class DownloadData extends AsyncTask<String, Void, String> {
        private static final String TAG = "DownloadXML";

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d(TAG, "onPostExecute: parameter is " + s);
            parseEarthquakes = new ParseEarthquakes();
            parseEarthquakes.parse(s);
            setMasterList(parseEarthquakes.getEarthquakes());
            earthquakeAdapter = new EarthquakeAdapter(MainActivity.this, R.layout.list_record, parseEarthquakes.getEarthquakes());
            listEarthquakes.setAdapter(earthquakeAdapter);


        }

        @Override
        protected String doInBackground(String... strings) {
            Log.d(TAG, "doInBackground: starts with " + strings[0]);
            String rssFeed = downLoadXML(strings[0]);
            if (rssFeed == null) {
                Log.e(TAG, "doInBackground: Error downloading");
            }

            return rssFeed;
        }

        private String downLoadXML(String urlPath) {
            StringBuilder xmlResult = new StringBuilder();

            try {
                URL url = new URL(urlPath);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                int response = connection.getResponseCode();
                Log.d(TAG, "downloadXML: Response code " + response);
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                int charsRead;
                char[] inputBuffer = new char[500];
                while (true) {
                    charsRead = reader.read(inputBuffer);
                    if (charsRead < 0) {
                        break;
                    }
                    if (charsRead > 0) {
                        xmlResult.append(String.copyValueOf(inputBuffer, 0, charsRead));
                    }
                }
                reader.close();
                return xmlResult.toString();
            } catch (MalformedURLException e) {
                Log.e(TAG, "downloadXML: Invalid URL " + e.getMessage());
            } catch (IOException e) {
                Log.e(TAG, "downloadXML: IO exception reading data " + e.getMessage());
            }
            return null;
        }
    }
}
