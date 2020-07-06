package com.example.escapetheroom;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Keys
    private static final String SHARED_PREFS = "sharedPreferences";
    private static final String INTRO = "intro";
    private static final String DOORKEY = "doorkey";
    private static final String ANSWER1 = "answer1";
    private static final String HAMMER = "hammer";
    private static final String BROKEN = "vasebroken";
    private static final String REMOTE = "remote";
    private static final String LIT_VASE_BROKEN = "littlevase";
    private static final String DARK = "dark";
    private static final String GLITTER_FOUND = "glitterfound";
    private static final String FINAL_ANSWER = "final";
    private static final String ANSWER2 = "answer2";
    private static final String EXIT_KEY = "exitkey";


    private Button startGame;
    private Button reset;
    private Button information;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startGame = findViewById(R.id.button_start_game);
        reset = findViewById(R.id.button_reset);
        information = findViewById(R.id.button_info);

        Animation mAnimation = new AlphaAnimation(1.0f, 0.0f);
        mAnimation.setDuration(800);
        mAnimation.setInterpolator(new AccelerateInterpolator());
        mAnimation.setRepeatCount(Animation.INFINITE);
        mAnimation.setRepeatMode(Animation.REVERSE);


        startGame.startAnimation(mAnimation);


        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Room1.class);
                startActivity(intent);

                overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Reset Game?");
                builder.setMessage("게임을 초기화하시겠습니까? (진행상황이 모두 사라집니다!)");
                builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putBoolean(INTRO,true);
                        editor.putBoolean(DOORKEY,false);
                        editor.putBoolean(ANSWER1,false);
                        editor.putBoolean(HAMMER,false);
                        editor.putBoolean(REMOTE,false);
                        editor.putBoolean(BROKEN,false);
                        editor.putBoolean(DARK,false);
                        editor.putBoolean(LIT_VASE_BROKEN,false);
                        editor.putBoolean(GLITTER_FOUND,false);
                        editor.putBoolean(FINAL_ANSWER,false);
                        editor.putBoolean(ANSWER2,false);
                        editor.putBoolean(EXIT_KEY,false);

                        editor.apply();

                        Toast.makeText(MainActivity.this, "RESET COMPLETE!", Toast.LENGTH_SHORT).show();
                    }

                });
                builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Info info = new Info(MainActivity.this);
                info.setCanceledOnTouchOutside(false);
                info.show();
            }
        });

    }

}
