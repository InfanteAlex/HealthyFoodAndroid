package com.example.healthyfoodandroid;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
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

public class bank extends AppCompatActivity implements OnMapReadyCallback {
    private final int fineCode = 1;
    Location currentLoc;
    FusedLocationProviderClient fusedLocationProviderClient;
    private GoogleMap map;
    private LocationRequest locationRequest;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        fetchData fetchData = new fetchData();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.getApplicationContext());
        //  Button healthy, keto, vegie;
        getLastLocation();
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bank);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button healthy = findViewById(R.id.findBanks);
        healthy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                map.clear();
                String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?" + "query=food+banks" +
                        "&location=" + currentLoc.getLatitude() + "," + currentLoc.getLongitude() +
                        "&radius=1000" +
                        "&key=AIzaSyDnTeUoEPsCLg0aVfRZhpv7Fc4_J-Sh2-o";

                fetchData.FetchData(map, url);
                fetchData.execute();
            }
        });

        SearchView search = findViewById(R.id.search);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String location) {
                map.clear();
                Toast.makeText(bank.this, "Search submitted: " + location, Toast.LENGTH_SHORT).show();

                fetchData fetchData = new fetchData();

                String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?" + "query="+ location +
                        "&location=" + currentLoc.getLatitude() + "," + currentLoc.getLongitude() +
                        "&radius=1000" +
                        "&type=restaurant" +
                        "&key=AIzaSyDnTeUoEPsCLg0aVfRZhpv7Fc4_J-Sh2-o";

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

    private void getLastLocation(){
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},fineCode);
            return;
        }
        locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY,1000)
                .setWaitForAccurateLocation(false)
                .setMinUpdateIntervalMillis(5000)  // Fastest update interval
                .setMaxUpdateDelayMillis(10000)
                .build();// Regular update interval.build();
        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {

            }
        };
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback,null);
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    currentLoc = location;

                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapView);
                    assert mapFragment != null;
                    mapFragment.getMapAsync(bank.this);


                }
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        Log.d("MapReady", "Google Map is ready");
        this.map = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        map.setMyLocationEnabled(true);
        // Enable zoom controls, etc.
        map.getUiSettings().setZoomControlsEnabled(true);

        // Set a default location
        LatLng defaultLocation = new LatLng(currentLoc.getLatitude(), currentLoc.getLongitude());
        map.addMarker(new MarkerOptions().position(defaultLocation).title("Orlando"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 12));


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == fineCode){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLastLocation();

            }else{
                Toast.makeText(this, "Location Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

}