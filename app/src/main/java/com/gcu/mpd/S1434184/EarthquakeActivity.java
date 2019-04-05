package com.gcu.mpd.S1434184;


import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 *  * Name                 Niklas Olsson
 *  * Student ID           S1434184
 *  * Programme of Study   Computing
 *  *
 *  * @author Niklas
 *  * @version 1.0
 */
public class EarthquakeActivity extends MainActivity implements OnMapReadyCallback {
    private static final String TAG = "EarthquakeActivity";
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    private MapView mapView;
    private Resources resources;
    private TextView tvMagnitude;
    private double geoLat;
    private double geoLong;


    /**
     * Creates EarthquakeActivity. Bundle is used to get the intent from EarthquakeAdapter view
     * which contains the earthquake values needed to set the TextView values. A toolbar is also
     * set to enable a user to return back to the previous MainActivity. Using the values from the
     * intent, a MapView using GoogleMaps API is created using the coordinates of the earthquake
     * selected.
     *
     * @param savedInstanceState Loads activity state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquake);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        TextView tvLocation = findViewById(R.id.tvLocation);
        tvMagnitude = findViewById(R.id.tvMagnitude);
        TextView tvPubDate = findViewById(R.id.tvPubDate);
        TextView tvDepth = findViewById(R.id.tvDepth);
        TextView tvCoordinates = findViewById(R.id.tvCoordinates);
        TextView tvLink = findViewById(R.id.tvLink);
        resources = getResources();

        Bundle bundle = getIntent().getExtras();
        String location = bundle.getString("location");
        Double magnitude = bundle.getDouble("magnitude");
        String pubDate = bundle.getString("pubDate");
        int depth = bundle.getInt("depth", 0);
        String link = bundle.getString("link");
        geoLat = bundle.getDouble("geoLat");
        geoLong = bundle.getDouble("geoLong");

        Log.d(TAG, "onCreate: strings pulled though");

        tvLocation.setText(location);
        tvMagnitude.setText(String.format("M %s", magnitude));
        tvPubDate.setText(pubDate);
        tvDepth.setText(String.format("Depth: %s km", depth));
        tvCoordinates.setText(String.format("Coordinates: %s, %s", geoLat, geoLong));
        tvLink.setText(String.format("Link: %s", link));
        setColor(magnitude);

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        Log.d(TAG, "onCreate: MapView object created");
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: EarthquakeActivity finished, return to MainActivity");
                finish();
            }
        });
    }

    /**
     * Method to save the state of EarthquakeActivity. The mapViewBundle is saved so that a new
     * MapView does not need to be created and can be reused, using only the new coorindates of
     * a selected earthquake.
     *
     *
     * @param outState Bundle
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }

    /**
     * Inherited methods of OnMapReadyCallback for when MapView is resumed
     *
     */
    @Override
    protected void onResume() {
        mapView.onResume();
        super.onResume();
    }

    /**
     * Inherited methods of OnMapReadyCallback for when MapView is paused
     *
     */
    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    /**
     * Inherited methods of OnMapReadyCallback for when MapView is destroyed
     *
     */
    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    /**
     * Inherited methods of OnMapReadyCallback for when MapView is on low memory
     *
     */
    @Override
    public void onLowMemory() {
        mapView.onLowMemory();
        super.onLowMemory();
    }

    /**
     * Method used to set MapView on a certain geographical position based of coordinates from
     * earthquake. Map is also set with custom map style to remove unnecessary map details.
     *
     * @param googleMap GoogleMap object
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady: started");
        GoogleMap gmap = googleMap;
        gmap.setMapStyle(MapStyleOptions.loadRawResourceStyle(
                this, R.raw.style_earthquake));
        gmap.setMinZoomPreference(5);
        LatLng position = new LatLng(geoLat,geoLong);

        gmap.moveCamera(CameraUpdateFactory.newLatLng(position));
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(position);
        gmap.addMarker(markerOptions);
        Log.d(TAG, "onMapReady: Map coordinates set to "+geoLat+", "+geoLong);

    }

    /**
     * Method used to style the magnitude TextView. If a certain magnitude is given, the drawable
     * chosen will reflect the severity of the earthquake, grey being severe and red being most
     * severe.
     *
     * @param magnitude Magnitude used to dertermine which drawable style to use
     */
    private void setColor(double magnitude){

        if(magnitude < 0.0){
            tvMagnitude.setBackground(resources.getDrawable(R.drawable.rounded0));
        }
        else if(magnitude <=0.9){
            tvMagnitude.setBackground(resources.getDrawable(R.drawable.rounded1));
        }
        else if(magnitude <=1.9){
            tvMagnitude.setBackground(resources.getDrawable(R.drawable.rounded2));
        }
        else if(magnitude <=2.9){
            tvMagnitude.setBackground(resources.getDrawable(R.drawable.rounded3));
        }
        else if(magnitude <=3.9){
            tvMagnitude.setBackground(resources.getDrawable(R.drawable.rounded4));
        }
        else if(magnitude <=4.9){
            tvMagnitude.setBackground(resources.getDrawable(R.drawable.rounded5));
        }
        else if(magnitude <=5.9){
            tvMagnitude.setBackground(resources.getDrawable(R.drawable.rounded6));
        }
        else if(magnitude <=6.9){
            tvMagnitude.setBackground(resources.getDrawable(R.drawable.rounded7));
        }
        else{
            tvMagnitude.setBackground(resources.getDrawable(R.drawable.rounded8));
        }
    }
}
