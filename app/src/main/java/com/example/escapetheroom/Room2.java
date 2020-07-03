package com.example.escapetheroom;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Room2 extends AppCompatActivity implements View.OnClickListener{

    //Constants
    private static final String SHARED_PREFS = "sharedPreferences";

    //Variables (widgets)
    private Button moveLeft;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        setContentView(R.layout.room2);

        moveLeft = findViewById(R.id.move_left);
        moveLeft.setOnClickListener(this);

        loadData();
    }

    public void loadData(){

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.move_left:
                Intent intent = new Intent(this,Room1.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        overridePendingTransition(0,0);
    }
}

