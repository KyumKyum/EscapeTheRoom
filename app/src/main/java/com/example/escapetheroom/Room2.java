package com.example.escapetheroom;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Room2 extends AppCompatActivity implements View.OnClickListener{

    //Constants
    private static final String SHARED_PREFS = "sharedPreferences";
    private static final String HAMMER = "hammer";
    private static final String LIT_VASE_BROKEN = "littlevase";
    private static final String REMOTE = "remote";
    private static final String DARK = "dark";

    //Variables (widgets)
    private Button moveLeft;
    private TextView itemList;
    private Button vase;
    private ImageView dark;

    //Variables (data)
    public Boolean hammerAvailable;
    public Boolean vaseBroken;
    public Boolean remoteAvailable;
    public Boolean isDark;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        setContentView(R.layout.room2);

        moveLeft = findViewById(R.id.move_left);
        vase = findViewById(R.id.button_little_vase);
        dark = findViewById(R.id.light_off_img);

        itemList = findViewById(R.id.item_list);


        moveLeft.setOnClickListener(this);
        vase.setOnClickListener(this);

        vaseBroken = false;
        isDark = false;

        updateData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateData();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        updateData();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.move_left:
                Intent intent = new Intent(this,Room1.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;

            case R.id.button_little_vase:
                if(vaseBroken){
                    if(!remoteAvailable){
                        Toast.makeText(this, "리모컨을 얻었다!\nTV에 사용해볼만할 가치가 있겠는걸!", Toast.LENGTH_SHORT).show();
                    }

                    remoteAvailable = true;

                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putBoolean(REMOTE,true);

                    editor.apply();

                    updateData();

                } else{

                    if(!hammerAvailable){
                        Toast.makeText(this, "작은 물병들이다. 안에 무언가 있지만 나오지를 않는다.\n이거, '망치'같은걸로 깨뜨려야겠는데.", Toast.LENGTH_SHORT).show();
                    } else{
                        Toast.makeText(this, "'쨍그랑!'\n안에 뭔가가 있어!...뭔가 둔탁한데", Toast.LENGTH_SHORT).show();
                        vaseBroken = true;

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean(LIT_VASE_BROKEN,true);
                        editor.apply();

                        updateData();
                    }
                }

        }
    }

    public void updateData(){
        sharedPreferences  = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        hammerAvailable = sharedPreferences.getBoolean(HAMMER,false);
        vaseBroken = sharedPreferences.getBoolean(LIT_VASE_BROKEN,false);
        remoteAvailable = sharedPreferences.getBoolean(REMOTE,false);
        isDark = sharedPreferences.getBoolean(DARK,false);


        String list = itemList.getText().toString();

        if(hammerAvailable){
            if(!(list.contains("망치 "))){
                list += "망치 ";
                itemList.setText(list);
            }
        } else {
            if(list.contains("망치 ")){
                list.replace("망치 ","");
                itemList.setText(list);
            }
        }

        if(remoteAvailable){
            if(!(list.contains("리모컨 "))){
                list += "리모컨 ";
                itemList.setText(list);
            }
        } else {
            if(list.contains("리모컨 ")){
                list.replace("리모컨 ","");
                itemList.setText(list);
            }
        }

        if(isDark){
            dark.setVisibility(View.VISIBLE);
        } else {
            dark.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

        overridePendingTransition(0,0);
    }
}

