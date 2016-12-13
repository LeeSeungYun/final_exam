package com.example.igx.problem1;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

public class GPSlocation extends Service implements LocationListener {
    private final Context mContext;
    boolean isGPS = false;
    boolean isNetwork = false;
    boolean isGetLocation = false;

    Location location;
    double lat = 37.609921;
    double lon = 126.997072;
    private static final long UPDATE_TIME = 1;
    private static final long UPDATE_DISTANCE = 1;

    protected LocationManager locationManager;

    public GPSlocation(Context context){
        this.mContext = context;
        getLocation();
    }

    public Location getLocation() {
        {
            try {
                locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
                isGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                isNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                this.isGetLocation = true;

                if (isNetwork) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, UPDATE_TIME, UPDATE_DISTANCE, this);
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            lat = location.getLatitude();
                            lon = location.getLongitude();
                        }
                    }
                }

                if (isGPS) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, UPDATE_TIME, UPDATE_DISTANCE, this);
                        if (locationManager != null) {
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                lat = location.getLatitude();
                                lon = location.getLongitude();
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return location;
    }

    public void stopGPS(){
        if(locationManager != null) {}
    }

    public double getLatitude(){
        if(location != null){
            lat = location.getLatitude();
        }
        return lat;
    }

    public double getLongitude(){
        if(location != null){
            lon = location.getLongitude();
        }
        return lon;
    }

    public IBinder onBind(Intent arg0) {
        return null;
    }
    public void onLocationChanged(Location location) {
    }
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
    public void onProviderEnabled(String provider) {
    }
    public void onProviderDisabled(String provider) {
    }
}
