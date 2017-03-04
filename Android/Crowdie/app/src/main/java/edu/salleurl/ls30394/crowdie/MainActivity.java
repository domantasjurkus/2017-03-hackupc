package edu.salleurl.ls30394.crowdie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


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
    }

    @Override
    protected void onStart() {
        super.onStart();
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

        //nextActivity = new Intent(this, SettingsActivity.class);
        //startActivity(nextActivity);

    }

    public void OnEmergency(View view) {

        //nextActivity = new Intent(this, AreYouInjuredActivity.class);
        startActivity(nextActivity);
    }

    public void onProfileSelected(View view) {

        nextActivity = new Intent(this, ProfileActivity.class);
        startActivity(nextActivity);

    }
}
