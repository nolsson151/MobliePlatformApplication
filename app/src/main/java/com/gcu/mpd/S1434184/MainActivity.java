package com.gcu.mpd.S1434184;

//
// Name                 Niklas Olsson
// Student ID           S1434184
// Programme of Study   Computing
//

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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    private ListView listEarthquakes;
    private EarthquakeAdapter earthquakeAdapter;
    private DatePicker datePicker;
    public ParseEarthquakes parseEarthquakes;
    private ArrayList<Earthquake> masterList;
    private ArrayList<Earthquake> sortedList;
    private SwipeRefreshLayout swipeRefreshLayout;

    /**
     * Creates MainActivity. Toolbar is implemented from BaseActivity which will contain search and sort options
     * in the toolbar. Method implements asynchronous task DownloadData to download earthquake XML from BGS Seismology
     * which will be used in the application, one of the most vital tasks. This method also contains a listener for a
     * user swiping down to refresh the data.
     *
     * @param savedInstanceState loads activity state
     */
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


    /**
     * Method that is called from a swipeRefreshLayoutListener in the onCreate method that carries out the asynchronous method
     * of DownloadData once again. This will download the data once again and add any new earthquakes that may have occured.
     * Method will test if there is any data in the masterList array, clear any elements if present to prepare for new ones,
     * or simply attempt to download the earthquake data. This is in case of no internet connection or server side faults.
     */
    public void refreshData(){

        if(masterList == null){
            DownloadData downloadData = new DownloadData();
            downloadData.execute("http://quakes.bgs.ac.uk/feeds/MhSeismology.xml");
        } else if(masterList.size() == 0){
            DownloadData downloadData = new DownloadData();
            downloadData.execute("http://quakes.bgs.ac.uk/feeds/MhSeismology.xml");
        } else {
            masterList.clear();
            DownloadData downloadData = new DownloadData();
            downloadData.execute("http://quakes.bgs.ac.uk/feeds/MhSeismology.xml");
        }
    }

    /**
     * Method that creates a SearchView used to search earthquakes by location in the toolbar as well as inflate options menu used
     * for sorting arrayAdapter. Menu is inflated from menu_main.xml and a hint 'Enter Location' is prompted to the user. Method
     * implements two required methods used to search the custom arrayAdapter.
     *
     * @param menu Menu to inflate
     * @return     True on created options menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu: created");
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setQueryHint(getResources().getString(R.string.searchable_hint));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            /**
             * Required method of setOnQueryTextListener, used to search for a single given string. It is not used in this
             * implementation and is set to return false.
             * @param s String to search
             * @return False
             */
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            /**
             *  Overridden required method of setOnQueryTextListener, that takes a string input from the user and filters custom
             * arrayAdapter of earthquakes if the string matches the filter. If the string is empty the filter is set to ""
             * to indicate that no filter is used, else every letter given is used in a new query and the filter is then
             * applied to each new character given.
             *
             * @param s String of characters to search
             * @return  True always
             */
            @Override
            public boolean onQueryTextChange(String s) {

                if (TextUtils.isEmpty(s)) {
                    earthquakeAdapter.filter("");
                    listEarthquakes.clearTextFilter();
                }
                else {
                    Log.d(TAG, "onQueryTextChange: Query submitted "+s);
                    earthquakeAdapter.filter(s);
                }
                return true;
            }
        });
        return true;
    }

    /**
     * Method used to determine the action taken on which option item is selected by the user. A switch statement retrieves the menu
     * item by it's R.id and
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected: Begin");
        switch (item.getItemId()){
            case R.id.action_date:
                Log.d(TAG, "onOptionsItemSelected: Date selected");
                showDatePicker(this.datePicker);
                break;

            case R.id.action_recent:
                Log.d(TAG, "onOptionsItemSelected: Most Recent selected");
                earthquakeAdapter = new EarthquakeAdapter(MainActivity.this, R.layout.list_record, masterList);
                listEarthquakes.setAdapter(earthquakeAdapter);
                earthquakeAdapter.notifyDataSetChanged();
                break;

            case R.id.action_Az:
                Log.d(TAG, "onOptionsItemSelected: Sort A-z selected");
                this.sortedList = new ArrayList<>(masterList);
                Collections.sort(sortedList, Earthquake.locationCompare_A_z);
                earthquakeAdapter = new EarthquakeAdapter(MainActivity.this, R.layout.list_record, sortedList);
                listEarthquakes.setAdapter(earthquakeAdapter);
                earthquakeAdapter.notifyDataSetChanged();
                break;

            case R.id.action_Za:
                Log.d(TAG, "onOptionsItemSelected: Sort A-z selected");
                this.sortedList = new ArrayList<>(masterList);
                Collections.sort(sortedList, Earthquake.locationCompare_Z_a);
                earthquakeAdapter = new EarthquakeAdapter(MainActivity.this, R.layout.list_record, sortedList);
                listEarthquakes.setAdapter(earthquakeAdapter);
                earthquakeAdapter.notifyDataSetChanged();
                break;

            case R.id.action_highest:
                Log.d(TAG, "onOptionsItemSelected: Sort Magnitude selected");
                this.sortedList = new ArrayList<>(masterList);
                Collections.sort(sortedList, Earthquake.magnitudeCompare_HighLow);
                earthquakeAdapter = new EarthquakeAdapter(MainActivity.this, R.layout.list_record, sortedList);
                listEarthquakes.setAdapter(earthquakeAdapter);
                earthquakeAdapter.notifyDataSetChanged();
                break;

            case R.id.action_lowest:
                Log.d(TAG, "onOptionsItemSelected: Sort Magnitude selected");
                this.sortedList = new ArrayList<>(masterList);
                Collections.sort(sortedList, Earthquake.magnitudeCompare_LowHigh);
                earthquakeAdapter = new EarthquakeAdapter(MainActivity.this, R.layout.list_record, sortedList);
                listEarthquakes.setAdapter(earthquakeAdapter);
                earthquakeAdapter.notifyDataSetChanged();
                break;

            case R.id.action_deep:
                Log.d(TAG, "onOptionsItemSelected: Sort Depth selected");
                this.sortedList = new ArrayList<>(masterList);
                Collections.sort(sortedList, Earthquake.depthCompare_HighLow);
                earthquakeAdapter = new EarthquakeAdapter(MainActivity.this, R.layout.list_record, sortedList);
                listEarthquakes.setAdapter(earthquakeAdapter);
                earthquakeAdapter.notifyDataSetChanged();
                break;

            case R.id.action_shallow:
                Log.d(TAG, "onOptionsItemSelected: Sort Depth selected");
                this.sortedList = new ArrayList<>(masterList);
                Collections.sort(sortedList, Earthquake.depthCompare_LowHigh);
                earthquakeAdapter = new EarthquakeAdapter(MainActivity.this, R.layout.list_record, sortedList);
                listEarthquakes.setAdapter(earthquakeAdapter);
                earthquakeAdapter.notifyDataSetChanged();
                break;

            case R.id.action_north:
                Log.d(TAG, "onOptionsItemSelected: Sort Most northerly selected");
                this.sortedList = new ArrayList<>(masterList);
                Collections.sort(sortedList, Earthquake.positionCompare_North);
                earthquakeAdapter = new EarthquakeAdapter(MainActivity.this, R.layout.list_record, sortedList);
                listEarthquakes.setAdapter(earthquakeAdapter);
                earthquakeAdapter.notifyDataSetChanged();
                break;
            case R.id.action_south:
                Log.d(TAG, "onOptionsItemSelected: Sort Most northerly selected");
                sortedList = new ArrayList<>(masterList);
                Collections.sort(sortedList, Earthquake.positionCompare_South);
                earthquakeAdapter = new EarthquakeAdapter(MainActivity.this, R.layout.list_record, sortedList);
                listEarthquakes.setAdapter(earthquakeAdapter);
                earthquakeAdapter.notifyDataSetChanged();
                break;
            case R.id.action_east:
                Log.d(TAG, "onOptionsItemSelected: Sort Most northerly selected");
                sortedList = new ArrayList<>(masterList);
                Collections.sort(sortedList, Earthquake.positionCompare_East);
                earthquakeAdapter = new EarthquakeAdapter(MainActivity.this, R.layout.list_record, sortedList);
                listEarthquakes.setAdapter(earthquakeAdapter);
                earthquakeAdapter.notifyDataSetChanged();
                break;
            case R.id.action_west:
                Log.d(TAG, "onOptionsItemSelected: Sort Most northerly selected");
                sortedList = new ArrayList<>(masterList);
                Collections.sort(sortedList, Earthquake.positionCompare_West);
                earthquakeAdapter = new EarthquakeAdapter(MainActivity.this, R.layout.list_record, sortedList);
                listEarthquakes.setAdapter(earthquakeAdapter);
                earthquakeAdapter.notifyDataSetChanged();
                break;

            default:
                Log.d(TAG, "onOptionsItemSelected: End");
        }
        return true;
    }

    /**
     *
     * @param listToSet
     */
    public void setMasterList(ArrayList listToSet){
        this.masterList = new ArrayList<>();
        masterList.addAll(listToSet);
    }

    /**
     *
     * @param view
     */
    public void showDatePicker(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), getString(R.string.datepicker));
    }

    /**
     *
     * @param year
     * @param month
     * @param day
     */
    public void processDatePickerResult(int year, int month, int day) {
        Log.d(TAG, "processDatePickerResult: "+day+" "+month+" "+year);
        sortedList = new ArrayList<>();
        String date = new GregorianCalendar(year,month, day).toZonedDateTime().format(DateTimeFormatter.ofPattern("dd MMM uuuu"));

        for(Earthquake e : masterList){
            if(e.getPubDate().equals(date)){
                sortedList.add(e);
            }
        }
        if(sortedList.size() == 0){
            Toast.makeText(this, "No earthquakes found for: "+ date, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "processDatePickerResult: No results found on "+ date);
        }
        else{
            earthquakeAdapter = new EarthquakeAdapter(MainActivity.this, R.layout.list_record, sortedList);
            listEarthquakes.setAdapter(earthquakeAdapter);
            earthquakeAdapter.notifyDataSetChanged();
        }
        Log.d(TAG, "processDatePickerResult: End");
    }

    private class DownloadData extends AsyncTask<String, Void, String> {
        private static final String TAG = "DownloadXML";

        /**
         *
         * @param s
         */
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d(TAG, "onPostExecute: parameter is " + s);
            if (s == null){
                Toast.makeText(MainActivity.this,
                        "Whoops, there was a connection error. Please check your connection", Toast.LENGTH_SHORT).show();
            }
            else{
                parseEarthquakes = new ParseEarthquakes();
                parseEarthquakes.parse(s);
                setMasterList(parseEarthquakes.getEarthquakes());
                earthquakeAdapter = new EarthquakeAdapter(MainActivity.this, R.layout.list_record, parseEarthquakes.getEarthquakes());
                listEarthquakes.setAdapter(earthquakeAdapter);
            }
        }

        /**
         *
         * @param strings
         * @return
         */
        @Override
        protected String doInBackground(String... strings) {
            Log.d(TAG, "doInBackground: starts with " + strings[0]);
            String rssFeed = downLoadXML(strings[0]);
            if (rssFeed == null) {
                Log.e(TAG, "doInBackground: Error downloading");
            }
            return rssFeed;
        }

        /**
         *
         * @param urlPath
         * @return
         */
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