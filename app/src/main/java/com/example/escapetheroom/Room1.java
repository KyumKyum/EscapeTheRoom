package com.example.escapetheroom;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Room1 extends AppCompatActivity implements View.OnClickListener{

    //Constants
    private static final String SHARED_PREFS = "sharedPreferences";
    private static final String INTRO = "intro";
    private static final String DOORKEY = "doorkey";


    //Variables (widgets)
    private Button moveRight;
    private Button doorExit;
    private Button pic;

    //Variables (data)
    public Boolean introOnOff;
    public Boolean doorKey;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        setContentView(R.layout.room1);

        moveRight = findViewById(R.id.move_right);
        doorExit = findViewById(R.id.button_exit);
        pic = findViewById(R.id.button_pic);

        moveRight.setOnClickListener(this);
        doorExit.setOnClickListener(this);
        pic.setOnClickListener(this);

        loadData();

        if(introOnOff) { intro(); }
    }

    public void intro(){
        Toast.makeText(this, "으윽...머리가 깨질 것 같아...", Toast.LENGTH_SHORT).show();
        SharedPreferences sharedPreferences  = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(INTRO,false);

        editor.apply();
    }

    public void loadData(){
        SharedPreferences sharedPreferences  = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        introOnOff = sharedPreferences.getBoolean(INTRO,true);
        doorKey = sharedPreferences.getBoolean(DOORKEY,false);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.move_right:
                Intent intent = new Intent(this,Room2.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;

            case R.id.button_exit:
                if(!doorKey){
                    Toast.makeText(this, "굳게 잠겨있다.\nEXIT이라고 써져있는 것을 보니, 여기로 나가는거겠지.", Toast.LENGTH_LONG).show();
                }

                break;

            case R.id.button_pic:
                FamilyPic1 familyPic1 = new FamilyPic1(this);
                familyPic1.show();
                Toast.makeText(this, "어린아이가 그린것 같은 가족그림이다.\n순수함이 느껴지는군.", Toast.LENGTH_LONG).show();
        }

    }
}

