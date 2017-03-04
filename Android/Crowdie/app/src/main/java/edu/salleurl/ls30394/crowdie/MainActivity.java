package edu.salleurl.ls30394.crowdie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWidgets();
    }

    private void initWidgets() {

        getSupportActionBar().hide();

    }

    public void onLiveMapSelected(View view) {
    }

    public void OnSettingsSelected(View view) {
    }

    public void OnEmergency(View view) {
    }
}
