package com.example.healthyfoodandroid;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class finderpage extends AppCompatActivity implements OnMapReadyCallback {
    private final int fineCode = 1;
    private Location currentLoc;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private GoogleMap map;
    private LocationRequest locationRequest;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finderpage);

        // Set up edge-to-edge display
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
        getWindow().setNavigationBarColor(android.graphics.Color.TRANSPARENT);
        getWindow().setStatusBarColor(android.graphics.Color.TRANSPARENT);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.getApplicationContext());
        getLastLocation();

        // Button for healthy food
        Button healthy = findViewById(R.id.healthy);
        healthy.setOnClickListener(view -> {
            map.clear();
            String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?" +
                    "query=healthy+food" +
                    "&location=" + currentLoc.getLatitude() + "," + currentLoc.getLongitude() +
                    "&radius=1000" +
                    "&type=restaurant" +
                    "&key=AIzaSyDnTeUoEPsCLg0aVfRZhpv7Fc4_J-Sh2-o";

            fetchData fetchData = new fetchData();
            fetchData.FetchData(map, url);
            fetchData.execute();
        });

        // Button for keto food
        Button keto = findViewById(R.id.keto);
        keto.setOnClickListener(view -> {
            map.clear();
            String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?" +
                    "query=keto+restaurants" +
                    "&location=" + currentLoc.getLatitude() + "," + currentLoc.getLongitude() +
                    "&radius=1000" +
                    "&type=restaurant" +
                    "&key=AIzaSyDnTeUoEPsCLg0aVfRZhpv7Fc4_J-Sh2-o";

            fetchData fetchData = new fetchData();
            fetchData.FetchData(map, url);
            fetchData.execute();
        });

        // Button for vegetarian food
        Button vegie = findViewById(R.id.vegie);
        vegie.setOnClickListener(view -> {
            map.clear();
            String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?" +
                    "query=vegetarian+food" +
                    "&location=" + currentLoc.getLatitude() + "," + currentLoc.getLongitude() +
                    "&radius=1000" +
                    "&type=restaurant" +
                    "&key=AIzaSyDnTeUoEPsCLg0aVfRZhpv7Fc4_J-Sh2-o";

            fetchData fetchData = new fetchData();
            fetchData.FetchData(map, url);
            fetchData.execute();
        });

        // Search functionality
        SearchView search = findViewById(R.id.search);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String location) {
                map.clear();
                Toast.makeText(finderpage.this, "Search submitted: " + location, Toast.LENGTH_SHORT).show();

                String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?" +
                        "query=" + location +
                        "&location=" + currentLoc.getLatitude() + "," + currentLoc.getLongitude() +
                        "&radius=1000" +
                        "&type=restaurant" +
                        "&key=AIzaSyDnTeUoEPsCLg0aVfRZhpv7Fc4_J-Sh2-o";

                fetchData fetchData = new fetchData();
                fetchData.FetchData(map, url);
                fetchData.execute();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, fineCode);
            return;
        }
        locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
                .setWaitForAccurateLocation(false)
                .setMinUpdateIntervalMillis(5000)
                .setMaxUpdateDelayMillis(10000)
                .build();

        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                // Handle location updates here
            }
        };
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);

        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(location -> {
            if (location != null) {
                currentLoc = location;

                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapView);
                if (mapFragment != null) {
                    mapFragment.getMapAsync(finderpage.this);
                }
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        Log.d("MapReady", "Google Map is ready");
        this.map = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        map.setMyLocationEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);

        LatLng defaultLocation = new LatLng(currentLoc.getLatitude(), currentLoc.getLongitude());
        map.addMarker(new MarkerOptions().position(defaultLocation).title("You are here"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 12));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == fineCode) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                Toast.makeText(this, "Location Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
