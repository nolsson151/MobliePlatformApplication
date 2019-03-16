package com.niklas.gcu.moblieplatformcw;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

public class EarthquakeActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "EarthquakeActivity";
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    private MapView mapView;
    private GoogleMap gmap;
    private String geoLat;
    private String geoLong;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquake);
        Log.d(TAG, "onCreate: started");
        TextView tvLocation = findViewById(R.id.tvLocation);
        TextView tvMagnitude = findViewById(R.id.tvMagnitude);
        TextView tvPubDate = findViewById(R.id.tvPubDate);
        TextView TvDetails = findViewById(R.id.tvDetails);
        Resources resources = getResources();

        Bundle bundle = getIntent().getExtras();
        String location = bundle.getString("location");
        String magnitude = bundle.getString("magnitude");
        String pubDate = bundle.getString("pubDate");
        String details = bundle.getString("details");
        geoLat = bundle.getString("geoLat");
        geoLong = bundle.getString("geoLong");
        Log.d(TAG, "onCreate: strings pulled though");

        tvLocation.setText(location);
        tvMagnitude.setText(resources.getString(R.string.magnitude_symbol, magnitude));
        tvPubDate.setText(pubDate);
        TvDetails.setText(details);

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        Log.d(TAG, "onCreate: mapview object created");
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);

    }
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
    @Override
    protected void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        mapView.onLowMemory();
        super.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady: started");
        gmap = googleMap;
        gmap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_earthquake));
        gmap.setMinZoomPreference(6);
        LatLng position = makeLatLng(geoLat,geoLong);
        gmap.moveCamera(CameraUpdateFactory.newLatLng(position));
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(position);
        gmap.addMarker(markerOptions);

    }

    public LatLng makeLatLng(String geoLat, String geoLong){
        Log.d(TAG, "makeLatLng: start with coordinates: "+geoLat+", "+ geoLong);

        double latitude = Double.parseDouble(geoLat);
        double longitude = Double.parseDouble(geoLong);
        LatLng latLng = new LatLng(latitude, longitude);

        return latLng;
    }
}
