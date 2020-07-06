package com.example.escapetheroom;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Room1 extends AppCompatActivity implements View.OnClickListener {

    //Constants
    private static final String TAG = "system";
    private static final String SHARED_PREFS = "sharedPreferences";
    private static final String INTRO = "intro";
    private static final String DOORKEY = "doorkey";
    private static final String ANSWER1 = "answer1";
    private static final String RET_CODE = "Vault1";
    private static final String HAMMER = "hammer";
    private static final String BROKEN = "vasebroken";
    private static final String REMOTE = "remote";
    private static final String DARK = "dark";
    private static final String FINAL_ANSWER = "final";
    private static final String ANSWER2 = "answer2";

    //Variables (widgets)
    private Button moveRight;
    private Button doorExit;
    private Button pic;
    private Button books;
    private Button safe1;
    private Button hammer;
    private Button tv;
    private Button vase;

    private TextView itemList;
    private ImageView dark;

    //Variables (data)
    public Boolean introOnOff;
    public Boolean doorKey;
    public Boolean answer1;
    public Boolean hammerAvailable;
    public Boolean vaseBroken;
    public Boolean remoteAvailable;
    public Boolean isDark;
    public Boolean terminateAction;
    public Boolean correct;

    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        setContentView(R.layout.room1);

        sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);


        moveRight = findViewById(R.id.move_right);
        doorExit = findViewById(R.id.button_exit);
        pic = findViewById(R.id.button_pic);
        books = findViewById(R.id.button_books);
        safe1 = findViewById(R.id.button_safe1);
        hammer = findViewById(R.id.button_hammer);
        tv = findViewById(R.id.button_tv);
        vase = findViewById(R.id.button_vase);
        dark = findViewById(R.id.light_off_img);

        itemList = findViewById(R.id.item_list);
        vaseBroken = false;
        hammerAvailable = false;
        remoteAvailable = false;
        isDark = false;
        terminateAction = false;
        correct = false;

        moveRight.setOnClickListener(this);
        doorExit.setOnClickListener(this);
        pic.setOnClickListener(this);
        books.setOnClickListener(this);
        safe1.setOnClickListener(this);
        hammer.setOnClickListener(this);
        tv.setOnClickListener(this);
        vase.setOnClickListener(this);

        updateData();

        if(introOnOff) { intro(); }
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
                    Toast.makeText(this, "굳게 잠겨있다.\nEXIT이라고 써져있는 것을 보니, 여기로 나가는거겠지.", Toast.LENGTH_SHORT).show();
                } else if(isDark){
                    Toast.makeText(this, "어두워서 아무것도 못하겠군.", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.button_pic:
                limitedAction();
                if(terminateAction) {
                    terminateAction = false;
                    break;}
                FamilyPic1 familyPic1 = new FamilyPic1(this);
                familyPic1.show();
                Toast.makeText(this, "어린아이가 그린것 같은 가족그림이다.\n순수함이 느껴지는군.", Toast.LENGTH_SHORT).show();
                break;

            case R.id.button_books:
                limitedAction();
                if(terminateAction) {
                    terminateAction = false;
                    break;}

                Hint1 hint1 = new Hint1(this);
                hint1.show();
                Toast.makeText(this, "책들 사이에 메모가 끼어져 있다.\n무슨 뜻이지?", Toast.LENGTH_SHORT).show();
                break;

            case R.id.button_safe1:

                limitedAction();
                if(terminateAction) {
                    terminateAction = false;
                    break;}

                if(!answer1){

                    Intent openVault = new Intent(this, Vault.class);
                    openVault.putExtra(FINAL_ANSWER,false);
                    startActivityForResult(openVault,101);

                    Toast.makeText(this, "4자리 수를 입력할 수 있는 금고다.\n이걸 열어야지 탈출할 실마리를 얻을 수 있겠군.", Toast.LENGTH_LONG).show();
                } else {
                    FamilyPic2 familyPic2 = new FamilyPic2(this);
                    familyPic2.show();
                    Toast.makeText(this, "검은 요정?\n...일단 '망치'라는 물건에 집중하도록 하자.", Toast.LENGTH_LONG).show();
                }

                break;

            case R.id.button_hammer:

                limitedAction();
                if(terminateAction) {
                    terminateAction = false;
                    break;}

                if(answer1){
                    if(!hammerAvailable){
                        Toast.makeText(this, "망치를 얻었다! 뭔갈 '깰 수 있을 것' 같은데.", Toast.LENGTH_SHORT).show();
                    }

                    hammerAvailable = true;

                    //SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putBoolean(HAMMER,true);

                    editor.apply();

                    updateData();
                } else {
                    Toast.makeText(this, "'W 형태'의 특이한 책장이다.\n무언가가 있는 것 같은데, 나중에 찾아봐야겠군.", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.button_tv:

                if(correct){
                    Toast.makeText(this, "뭐야..뭐냐고...왜 불이 안켜지는거야..", Toast.LENGTH_SHORT).show();
                    break;
                }

                if(!remoteAvailable){
                    Toast.makeText(this, "...왜 'TV가 전등하고 연결'되어 있지?\n켤 수 있는 스위치도 없어.", Toast.LENGTH_SHORT).show();
                } else {
                    isDark = !isDark;

                    if(isDark){
                        dark.setVisibility(View.VISIBLE);
                    } else {
                        dark.setVisibility(View.INVISIBLE);
                    }

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(DARK,isDark);
                    editor.apply();
                }
                break;

            case R.id.button_vase:

                limitedAction();
                if(terminateAction) {
                    terminateAction = false;
                    break;}

                if(vaseBroken){
                    Hint2 hint2 = new Hint2(this);
                    hint2.show();
                    Toast.makeText(this, "......진정하자\n분명 숨겨진 의미가 있거나, 나중에 도움이 되는 단서가 될꺼야.", Toast.LENGTH_LONG).show();
                } else{

                    if(!hammerAvailable){
                        Toast.makeText(this, "물병들이다. 안에 무언가 있지만 나오지를 않는다.\n이거, '망치'같은걸로 깨뜨려야겠는데.", Toast.LENGTH_SHORT).show();
                    } else{
                        Toast.makeText(this, "'쨍그랑!'\n안에 쪽지가 있어!...뭔가 불길한데", Toast.LENGTH_SHORT).show();
                        vaseBroken = true;

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean(BROKEN,true);
                        editor.apply();

                        updateData();
                    }
                }


        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 101 && resultCode == RESULT_OK){
            assert data != null;
            boolean openOrNot =  data.getBooleanExtra(RET_CODE,false);
            if(openOrNot){
                Toast.makeText(getApplicationContext(), "! 열리는 소리가 들렸다!", Toast.LENGTH_SHORT).show();
                answer1 = true;

                //SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
               SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putBoolean(ANSWER1,true);

                editor.apply();

                updateData();
            }
        }
    }

    public void intro(){
        Toast.makeText(this, "으윽...머리가 깨질 것 같아...", Toast.LENGTH_SHORT).show();
        //SharedPreferences sharedPreferences  = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(INTRO,false);

        editor.apply();
    }

    public void updateData(){
        //SharedPreferences sharedPreferences  = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        introOnOff = sharedPreferences.getBoolean(INTRO,true);
        doorKey = sharedPreferences.getBoolean(DOORKEY,false);
        answer1 = sharedPreferences.getBoolean(ANSWER1,false);
        hammerAvailable = sharedPreferences.getBoolean(HAMMER,false);
        vaseBroken = sharedPreferences.getBoolean(BROKEN,false);
        remoteAvailable = sharedPreferences.getBoolean(REMOTE,false);
        isDark = sharedPreferences.getBoolean(DARK,false);
        correct = sharedPreferences.getBoolean(ANSWER2,false);

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

        if(isDark || correct ){
            dark.setVisibility(View.VISIBLE);
        } else {
            dark.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        updateData();
    }

    public void limitedAction(){
        if(isDark){
            Toast.makeText(this, "어두워서 아무것도 못하겠군.", Toast.LENGTH_SHORT).show();
            terminateAction = true;
        }
    }
}

