package edu.salleurl.ls30394.crowdie.repositories;

import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

/**
 * Created by avoge on 04/03/2017.
 * @src http://stackoverflow.com/questions/37297905/how-to-get-device-location-in-android
 */

public class GpsTrackerRepository extends Service implements LocationListener {

    private final Context mContext;

    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    boolean canGetLocation = false;

    Location location;
    double latitude;
    double longitude;

    private final long MIN_DISTANCE = 10;
    private final long MIN_TIME = 30;
    protected LocationManager locationManager;


    public GpsTrackerRepository(Context context) {
        this.mContext = context;
        getLocation();
    }


    public Location getLocation() {
        try {

            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider enabled
            } else {

                this.canGetLocation = true;

                // first get location from network provider
                if (isNetworkEnabled) {
                    if (ActivityCompat.checkSelfPermission(
                            this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat
                                .checkSelfPermission(
                                        this,
                                        android.Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                        //    ActivityCompat#requestPermissions
                        //    here to request the missing permissions, and then overriding
                        //    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return null;
                    }
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }

                // if GPS enabled get lat/long using GPS provider

                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
                        Log.d("GPS provider", "GPS provider");

                        if (locationManager != null) {
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();

                            }
                        }

                    }
                }


            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    /* Stop using GPS listener
     * calling this function will stop using GPS in your app*/

    public void stopUsingGPS() {
        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.removeUpdates(GpsTrackerRepository.this);
        }
    }


    // functions to get latitude and longitude

    public double getLatitude() {
        if(location != null){
            latitude = location.getLatitude();
        }
        return latitude;
    }


    public double getLongitude() {
        if(location != null){
            longitude = location.getLongitude();
        }
        return longitude;
    }


    // function to check whether GPS/WiFi is enabled

    public boolean canGetLocation() {

        return this.canGetLocation;
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {

        sendLocationToActivities(location, "User location");

    }

    private void sendLocationToActivities(Location loc, String msg) {

        Intent i = new Intent("Location Updates");

        Bundle b = new Bundle();

        b.putParcelable("Location", loc);
        i.putExtra("Location", b);

        LocalBroadcastManager.getInstance(mContext).sendBroadcast(i);
    }

    @Override
    public void onProviderDisabled(String arg0) {

    }

    @Override
    public void onProviderEnabled(String arg0) {

    }

    @Override
    public void onStatusChanged(String arg0, int arg1, Bundle arg2) {

    }

}
