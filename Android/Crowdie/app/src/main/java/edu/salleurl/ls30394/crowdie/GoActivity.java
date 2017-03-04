package edu.salleurl.ls30394.crowdie;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class GoActivity extends AppCompatActivity {

    private EditText etName;
    private Button btnGo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if(isLogged()){
            gotoMainActivity();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go);

        initWidgets();
    }

    private boolean isLogged() {
        SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);

        return prefs.getBoolean("Logged", false);
    }

    private void initWidgets() {
        getSupportActionBar().hide();

        etName = (EditText) findViewById(R.id.activity_go_nameInput);
        btnGo = (Button) findViewById(R.id.activity_go_btn);
    }

    public void OnGO(View view) {

        if("".equals(etName.getText().toString())){
            Toast.makeText(this, "Please introduce your name", Toast.LENGTH_SHORT).show();
        }else{

            SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean("Logged", true);
            editor.putString("UserName", etName.getText().toString());

            editor.apply();

            gotoMainActivity();
        }

    }

    private void gotoMainActivity() {

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
