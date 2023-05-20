package com.example.wayhome;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wayhome.ui.main.MainFragment;
import com.google.firebase.FirebaseApp;
import com.yandex.mapkit.MapKitFactory;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String MAPKIT_API_KEY = "4c56caeb-091c-4b65-ad09-2bede93dc754";
        MapKitFactory.setApiKey(MAPKIT_API_KEY);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        // Java

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }
}