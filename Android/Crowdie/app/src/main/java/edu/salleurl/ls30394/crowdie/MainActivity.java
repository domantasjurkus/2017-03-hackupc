package edu.salleurl.ls30394.crowdie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import edu.salleurl.ls30394.crowdie.repositories.GpsTrackerRepository;

public class MainActivity extends AppCompatActivity {

    private Button btLiveMap;

    private Button btSettings;

    private Button btEmergency;

    private Intent nextActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWidgets();
        initLocator();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void initLocator() {

        startService(new Intent(this, GpsTrackerRepository.class));
        Toast.makeText(this, "Locator initiated", Toast.LENGTH_SHORT).show();

    }

    private void initWidgets() {

        getSupportActionBar().hide();

        btLiveMap = (Button) findViewById(R.id.main_activity_liveMap_btn);
        btEmergency = (Button) findViewById(R.id.activity_main_emergency_btn);
        btSettings = (Button) findViewById(R.id.main_activity_settings_btn);

    }

    public void onLiveMapSelected(View view) {

        nextActivity = new Intent(this, MapActivity.class);
        startActivity(nextActivity);

    }

    public void OnSettingsSelected(View view) {

        nextActivity = new Intent();

    }

    public void OnEmergency(View view) {

        nextActivity = new Intent(this, AreYouInjuredActivity.class);
        startActivity(nextActivity);
    }
}
