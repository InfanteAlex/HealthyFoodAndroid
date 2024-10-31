package com.example.healthyfoodandroid;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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

import java.io.IOException;
import java.util.List;


public class finderpage extends AppCompatActivity implements OnMapReadyCallback {
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
        setContentView(R.layout.activity_finderpage);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button healthy = findViewById(R.id.healthy);
        healthy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                map.clear();
                String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?" + "query=healthy+food" +
                        "&location=" + currentLoc.getLatitude() + "," + currentLoc.getLongitude() +
                        "&radius=1000" +
                        "&type=restaurant" +
                        "&key=AIzaSyDnTeUoEPsCLg0aVfRZhpv7Fc4_J-Sh2-o";

                fetchData.FetchData(map, url);
                fetchData.execute();
            }
        });
        Button keto = findViewById(R.id.keto);
        keto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchData fetchData = new fetchData();
                map.clear();
                String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?" + "query=keto+restaurants" +
                        "&location=" + currentLoc.getLatitude() + "," + currentLoc.getLongitude() +
                        "&radius=1000" +
                        "&type=restaurant" +
                        "&key=AIzaSyDnTeUoEPsCLg0aVfRZhpv7Fc4_J-Sh2-o";
                fetchData.FetchData(map, url);
                fetchData.execute();
            }
        });
        Button vegie = findViewById(R.id.vegie);
        vegie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                map.clear();
                fetchData fetchData = new fetchData();

                String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?" + "query=vegetarian+Food" +
                        "&location=" + currentLoc.getLatitude() + "," + currentLoc.getLongitude() +
                        "&radius=1000" +
                        "&type=restaurant" +
                        "&key=AIzaSyDnTeUoEPsCLg0aVfRZhpv7Fc4_J-Sh2-o";

                fetchData.FetchData(map, url);
                fetchData.execute();
//                LatLng defaultLocation = new LatLng(currentLoc.getLatitude(), currentLoc.getLongitude());
//                map.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 15));
            }
        });
        SearchView search = findViewById(R.id.search);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String location) {
                map.clear();
                Toast.makeText(finderpage.this, "Search submitted: " + location, Toast.LENGTH_SHORT).show();

                fetchData fetchData = new fetchData();

                String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?" + "query="+ location +
                        "&location=" + currentLoc.getLatitude() + "," + currentLoc.getLongitude() +
                        "&radius=1000" +
                        "&type=restaurant" +
                        "&key=AIzaSyDnTeUoEPsCLg0aVfRZhpv7Fc4_J-Sh2-o";

                fetchData.FetchData(map, url);
                fetchData.execute();
//                location = search.getQuery().toString();
//                Geocoder geocoder = new Geocoder(finderpage.this);
//
//
//                String finalLocation = location;
//                geocoder.getFromLocationName(location, 1, new Geocoder.GeocodeListener() {
//                    @Override
//                    public void onGeocode(@Nullable List<Address> addresses) {
//                        if (addresses != null && !addresses.isEmpty()) {
//                            Address address = addresses.get(0);
//                            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
//                            map.addMarker(new MarkerOptions().position(latLng).title(finalLocation));
//                            map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
//                        } else {
//                            Toast.makeText(finderpage.this, "Location not found", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    public void onError(@NonNull Throwable error) {
//                        Toast.makeText(finderpage.this, "Geocoding failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });

//                if(location !=null){
//                    Geocoder geocoder = new Geocoder(finderpage.this);
//
//                    try{
//                        addressList = geocoder.getFromLocationName(currentLoc.getLatitude(),currentLoc.getLongitude(),1, new Geocoder.GeocodeListener());
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                    assert addressList != null;
//                    Address address = addressList.get(0);
//                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
//                    map.addMarker(new MarkerOptions().position(latLng).title(location));
//                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
//
//                }

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
//                Toast.makeText(getApplicationContext(), "location result=" + locationResult, Toast.LENGTH_LONG).show();
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
                    mapFragment.getMapAsync(finderpage.this);


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

        // Check permissions before enabling location
//        fetchData fetchData = new fetchData();

//        String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?" + "query=healthy+food" +
//                "&location=" + currentLoc.getLatitude() + "," + currentLoc.getLongitude() +
//                "&radius=1500" +
//                "&key=AIzaSyDnTeUoEPsCLg0aVfRZhpv7Fc4_J-Sh2-o";
//
//        fetchData.FetchData(map, url);
//        fetchData.execute();

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
