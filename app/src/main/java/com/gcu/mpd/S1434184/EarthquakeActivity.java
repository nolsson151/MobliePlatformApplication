package com.gcu.mpd.S1434184;

//
// Name                 Niklas Olsson
// Student ID           S1434184
// Programme of Study   Computing
//

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

public class EarthquakeActivity extends MainActivity implements OnMapReadyCallback {
    private static final String TAG = "EarthquakeActivity";
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    private MapView mapView;
    private GoogleMap gmap;
    private double geoLat;
    private double geoLong;


    /**
     * @param savedInstanceState
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
        TextView tvMagnitude = findViewById(R.id.tvMagnitude);
        TextView tvPubDate = findViewById(R.id.tvPubDate);
        TextView tvDepth = findViewById(R.id.tvDepth);
        TextView tvCoordinates= findViewById(R.id.tvCoordinates);
        TextView tvLink = findViewById(R.id.tvLink);
        Resources resources = getResources();

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

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        Log.d(TAG, "onCreate: mapview object created");
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: EarthquakeActivity finsihed, return to MainActivity");
                finish();
            }
        });

    }

    /**
     *
     * @param outState
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
     *
     */
    @Override
    protected void onResume() {
        mapView.onResume();
        super.onResume();
    }

    /**
     *
     */
    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    /**
     *
     */
    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    /**
     *
     */
    @Override
    public void onLowMemory() {
        mapView.onLowMemory();
        super.onLowMemory();
    }

    /**
     *
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady: started");
        gmap = googleMap;
        gmap.setMapStyle(MapStyleOptions.loadRawResourceStyle(
                this, R.raw.style_earthquake));
        gmap.setMinZoomPreference(5);
        LatLng position = new LatLng(geoLat,geoLong);

        gmap.moveCamera(CameraUpdateFactory.newLatLng(position));
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(position);
        gmap.addMarker(markerOptions);

    }
}
