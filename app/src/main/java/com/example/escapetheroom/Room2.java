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
    private static final String GLITTER_FOUND = "glitterfound";
    private static final String ANSWER2 = "answer2";
    private static final String FINAL_ANSWER = "final";
    private static final String RET_CODE2 = "Vault2";

    //Variables (widgets)
    private Button moveLeft;
    private TextView itemList;
    private Button vase;
    private ImageView dark;
    private Button glitter;
    private Button invalidDoor;
    private Button vault;

    //Variables (data)
    public Boolean hammerAvailable;
    public Boolean vaseBroken;
    public Boolean remoteAvailable;
    public Boolean isDark;
    public Boolean glitterFound;
    public Boolean terminateAction;
    public Boolean correct;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        setContentView(R.layout.room2);

        moveLeft = findViewById(R.id.move_left);
        vase = findViewById(R.id.button_little_vase);
        dark = findViewById(R.id.light_off_img);
        glitter = findViewById(R.id.button_glitter);
        invalidDoor = findViewById(R.id.door_invalid);
        vault = findViewById(R.id.button_vault);

        itemList = findViewById(R.id.item_list);


        moveLeft.setOnClickListener(this);
        vase.setOnClickListener(this);
        glitter.setOnClickListener(this);
        invalidDoor.setOnClickListener(this);
        vault.setOnClickListener(this);

        vaseBroken = false;
        isDark = false;
        glitterFound = false;
        terminateAction = false;
        correct = false;

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

                limitedAction();
                if(terminateAction) {
                    terminateAction = false;
                    break;}

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
                break;

            case R.id.button_glitter:
                if(isDark){
                    glitterFound = true;
                    Toast.makeText(this, "무언가가 반짝이고 있다!", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(GLITTER_FOUND,true);
                    editor.apply();
                } else{
                    if(glitterFound){
                        FamilyPic3 familyPic3 = new FamilyPic3(this);
                        familyPic3.show();
                        Toast.makeText(this, "...모두 데려갔다니...?\n젠장. 꽤 험한 일에 휘말린 것 같군.", Toast.LENGTH_SHORT).show();
                    }
                }

                break;

            case R.id.door_invalid:

                limitedAction();
                if(terminateAction) {
                    terminateAction = false;
                    break;}

                Hint3 hint3 = new Hint3(this);
                hint3.show();
                Toast.makeText(this, "의미를 알 수 없는 문장의 나열...\n내가 이해하지 못하는 건가?", Toast.LENGTH_SHORT).show();
                break;

            case R.id.button_vault:
                limitedAction();
                if(terminateAction) {
                    terminateAction = false;
                    break;}

                if(!correct){

                    Intent openVault = new Intent(this, Vault.class);
                    openVault.putExtra(FINAL_ANSWER,true);
                    startActivityForResult(openVault,102);

                    Toast.makeText(this, "4자리 수를 입력할 수 있는 금고다.\n이걸 열어야지만 탈출할 수 있다는 느낌이 들어.", Toast.LENGTH_LONG).show();
                }


        }
    }

    public void updateData(){
        sharedPreferences  = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        hammerAvailable = sharedPreferences.getBoolean(HAMMER,false);
        vaseBroken = sharedPreferences.getBoolean(LIT_VASE_BROKEN,false);
        remoteAvailable = sharedPreferences.getBoolean(REMOTE,false);
        isDark = sharedPreferences.getBoolean(DARK,false);
        glitterFound = sharedPreferences.getBoolean(GLITTER_FOUND,false);
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

        if(isDark || correct){
            glitter.setVisibility(View.VISIBLE);
            dark.setVisibility(View.VISIBLE);
        } else {
            if(glitterFound){ glitter.setVisibility(View.VISIBLE); }
            dark.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

        overridePendingTransition(0,0);
    }

    public void limitedAction() {
        if (isDark) {
            Toast.makeText(this, "어두워서 아무것도 못하겠군.", Toast.LENGTH_SHORT).show();
            terminateAction = true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 102 && resultCode == RESULT_OK){
            assert data != null;
            boolean openOrNot =  data.getBooleanExtra(RET_CODE2,false);
            if(openOrNot){
                Toast.makeText(getApplicationContext(), "! 열리는 소리가 들렸...전등이?\n다행히 금고가 야광이어서 눈에 보이는군.", Toast.LENGTH_SHORT).show();
                correct = true;

                //SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putBoolean(ANSWER2,true);

                editor.apply();

                updateData();
            }
        }
    }

}

