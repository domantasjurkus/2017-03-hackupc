package edu.salleurl.ls30394.crowdie;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private Location userLocation;

    private BroadcastReceiver locReceiver;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        initLocationReceiver();

    }

    private void initLocationReceiver() {

        final Context c = this;

        locReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle b = intent.getBundleExtra("Location");
                userLocation = b.getParcelable("Location");
                Toast.makeText(c, "Location Updated", Toast.LENGTH_SHORT).show();
                onUserLocationUpdate();
            }
        };

    }

    private void onUserLocationUpdate() {

        LatLng userLatLng = new LatLng(userLocation.getLatitude(), userLocation.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 17.0f));

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.map_style_json_night));

            if (!success) {
                Log.e("MAP", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("MAP", "Can't find style. Error: ", e);
        }

        // Add a marker in Sydney and move the camera
        LatLng barcelona = new LatLng(41.39, 2.15);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(barcelona, 17.0f));
    }
}
