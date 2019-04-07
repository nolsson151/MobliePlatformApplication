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
import android.widget.EditText;
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

/**
 *  * Name                 Niklas Olsson
 *  * Student ID           S1434184
 *  * Programme of Study   Computing
 *  *
 *  * @author Niklas
 *  * @version 1.0
 */
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
     * @param savedInstanceState Loads activity state
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
                Log.d(TAG, "onQueryTextChange: Search option selected");

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
     * item by it's R.id, each case then instantiates a new ArrayList of earthquakes called sortedList containing all elements of the
     * masterList of earthquakes. The sortedList is then sorted using the Collections framework .sort() and a custom Comparator of the value
     * to sorted, either location, magnitude, depth, or coordinate. The sortedList is then applied to the custom arrayAdapter to display the
     * sorted results.
     *
     * In the case of R.id.action_recent, the masterList is applied to the custom arrayAdapter as this will contain the most recent
     * earthquakes. R.id.action_date creates a DialogFragment in which the lifecycle will complete after the switch statement case.
     *
     * @param item Options item selected
     * @return     True if option item selected.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected: Begin");
        switch (item.getItemId()){
            case R.id.action_date:
                Log.d(TAG, "onOptionsItemSelected: Date selected");
                showDatePicker();
                break;

            case R.id.action_recent:
                Log.d(TAG, "onOptionsItemSelected: Most Recent selected");
                earthquakeAdapter = new EarthquakeAdapter(MainActivity.this, R.layout.list_record, masterList);
                listEarthquakes.setAdapter(earthquakeAdapter);
                earthquakeAdapter.notifyDataSetChanged();
                break;

            case R.id.action_Az:
                Log.d(TAG, "onOptionsItemSelected: Sort A-Z selected");
                this.sortedList = new ArrayList<>(masterList);
                Collections.sort(sortedList, Earthquake.locationCompare_A_z);
                earthquakeAdapter = new EarthquakeAdapter(MainActivity.this, R.layout.list_record, sortedList);
                listEarthquakes.setAdapter(earthquakeAdapter);
                earthquakeAdapter.notifyDataSetChanged();
                break;

            case R.id.action_Za:
                Log.d(TAG, "onOptionsItemSelected: Sort Z-A selected");
                this.sortedList = new ArrayList<>(masterList);
                Collections.sort(sortedList, Earthquake.locationCompare_Z_a);
                earthquakeAdapter = new EarthquakeAdapter(MainActivity.this, R.layout.list_record, sortedList);
                listEarthquakes.setAdapter(earthquakeAdapter);
                earthquakeAdapter.notifyDataSetChanged();
                break;

            case R.id.action_highest:
                Log.d(TAG, "onOptionsItemSelected: Sort Magnitude High-Low selected");
                this.sortedList = new ArrayList<>(masterList);
                Collections.sort(sortedList, Earthquake.magnitudeCompare_HighLow);
                earthquakeAdapter = new EarthquakeAdapter(MainActivity.this, R.layout.list_record, sortedList);
                listEarthquakes.setAdapter(earthquakeAdapter);
                earthquakeAdapter.notifyDataSetChanged();
                break;

            case R.id.action_lowest:
                Log.d(TAG, "onOptionsItemSelected: Sort Magnitude Low-High selected");
                this.sortedList = new ArrayList<>(masterList);
                Collections.sort(sortedList, Earthquake.magnitudeCompare_LowHigh);
                earthquakeAdapter = new EarthquakeAdapter(MainActivity.this, R.layout.list_record, sortedList);
                listEarthquakes.setAdapter(earthquakeAdapter);
                earthquakeAdapter.notifyDataSetChanged();
                break;

            case R.id.action_deep:
                Log.d(TAG, "onOptionsItemSelected: Sort Depth High-Low selected");
                this.sortedList = new ArrayList<>(masterList);
                Collections.sort(sortedList, Earthquake.depthCompare_HighLow);
                earthquakeAdapter = new EarthquakeAdapter(MainActivity.this, R.layout.list_record, sortedList);
                listEarthquakes.setAdapter(earthquakeAdapter);
                earthquakeAdapter.notifyDataSetChanged();
                break;

            case R.id.action_shallow:
                Log.d(TAG, "onOptionsItemSelected: Sort Depth Low-High selected");
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
                Log.d(TAG, "onOptionsItemSelected: Sort Most southerly selected");
                sortedList = new ArrayList<>(masterList);
                Collections.sort(sortedList, Earthquake.positionCompare_South);
                earthquakeAdapter = new EarthquakeAdapter(MainActivity.this, R.layout.list_record, sortedList);
                listEarthquakes.setAdapter(earthquakeAdapter);
                earthquakeAdapter.notifyDataSetChanged();
                break;
            case R.id.action_east:
                Log.d(TAG, "onOptionsItemSelected: Sort Most easterly selected");
                sortedList = new ArrayList<>(masterList);
                Collections.sort(sortedList, Earthquake.positionCompare_East);
                earthquakeAdapter = new EarthquakeAdapter(MainActivity.this, R.layout.list_record, sortedList);
                listEarthquakes.setAdapter(earthquakeAdapter);
                earthquakeAdapter.notifyDataSetChanged();
                break;
            case R.id.action_west:
                Log.d(TAG, "onOptionsItemSelected: Sort Most westerly selected");
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
     * Simple method used to copy the elements of one array to the masterList. Used during DownloadData to store parsed values
     * into the masterList.
     *
     * @param arrayList List to be copied
     */
    public void setMasterList(ArrayList arrayList){
        this.masterList = new ArrayList<>();
        masterList.addAll(arrayList);
        Log.d(TAG, "setMasterList: Earthquakes added to masterList");
    }

    /**
     * Method to create a DatePickerDialog fragment used for to select a date to search on which earthquakes occurred.
     *
     */
    public void showDatePicker() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), getString(R.string.datepicker));
    }

    /**
     * Method to process the results of DatePickerDialog fragment. Values given are formatted using GregorianCalendar into a sting
     * to match the pubDate of an Earthquake. A for-loop is used to iterate through the masterList, if the formatted date string
     * matches an earthquake pubDate it is added to an instantiation of sortedList. sortedList is then applied to the custom
     * arrayAdapter to display the earthquakes that having matching dates. If no dates are found, a toast message is given to
     * the user to indicate the date given matched no earthquake pubDate.
     *
     * @param year int of year
     * @param month int of month
     * @param day int of day
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

    /**
     * Private class used to implement asynchronous task of downloading XML data from BGS Seismology.
     */
    private class DownloadData extends AsyncTask<String, Void, String> {
        private static final String TAG = "DownloadXML";

        /**
         * Method to be executed after doInBackGround has completed the download of XML data. Data is passed to parseEarthquakes
         * that will parse the XML data and create an ArrayList of Earthquake classes from the data that is then set to the
         * masterList in the MainActivity. The masterList is then applied to the custom ArrayAdapter and set to the ListView
         * listEarthquakes. If no data was found  a toast message is given to the user to indicate that the data was not
         * downloaded and to check connection.
         *
         * @param string String of XML data downloaded.
         */
        @Override
        protected void onPostExecute(String string) {
            super.onPostExecute(string);
            Log.d(TAG, "onPostExecute: parameter is " + string);
            if (string == null){
                Toast.makeText(MainActivity.this,
                        "Whoops, there was a connection error. Please check your connection", Toast.LENGTH_SHORT).show();
            }
            else{
                parseEarthquakes = new ParseEarthquakes();
                parseEarthquakes.parse(string);

                setMasterList(parseEarthquakes.getEarthquakes());
                earthquakeAdapter = new EarthquakeAdapter(MainActivity.this, R.layout.list_record, parseEarthquakes.getEarthquakes());
                listEarthquakes.setAdapter(earthquakeAdapter);
                Log.d(TAG, "onPostExecute: Earthquakes applied to ListView");
            }
        }

        /**
         * Method of AsyncTask that will run in a separate thread that that takes URL and implements downloadXML from
         * BGS Seismology.
         * @param strings URL to download from
         * @return        XML as String from downloadXML
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
         * Method that reads XML data from BGS Seismology using BufferedReader to create a concatenated String of XML data.
         *
         * @param urlPath URL to perform download from
         * @return        XML data as String
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
