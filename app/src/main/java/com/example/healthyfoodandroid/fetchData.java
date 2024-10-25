package com.example.healthyfoodandroid;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class fetchData  {

    String googleNear;
    GoogleMap googleMap;
    String url;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    // Handler for posting results to the main thread
    private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());

    public void FetchData(GoogleMap map, String requestUrl) {
        this.googleMap = map;
        this.url = requestUrl;
    }

    public void execute(){
        executorService.execute(()->{
            String data = doInBackground();

            mainThreadHandler.post(()-> onPostExecute(data));
        });
    }

    private String doInBackground() {
        try {
            Log.d("fetchData", "Fetching URL: " + url);
            DownloadUrl downloadUrl = new DownloadUrl();
            googleNear = downloadUrl.getURL(url);
            Log.d("fetchData", "Data received: " + googleNear);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return googleNear;
    }

    private void onPostExecute(String result) {
        try{
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("results");

            for(int i = 0; i<jsonArray.length(); i++){
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                JSONObject getLocation = jsonObject1.getJSONObject("geometry").getJSONObject("location");

                String lat = getLocation.getString("lat");
                String lng = getLocation.getString("lng");

                JSONObject getName = jsonArray.getJSONObject(i);
                String name = getName.getString("name");

                LatLng latLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.title(name);
                markerOptions.position(latLng);
                googleMap.addMarker(markerOptions);
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                mainThreadHandler.post(() -> {
                    googleMap.addMarker(markerOptions);
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                    Log.d("fetchData", "Marker added for: " + name + " at location: " + latLng);
                });
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

}





