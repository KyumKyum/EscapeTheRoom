package com.example.escapetheroom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Vault extends AppCompatActivity implements View.OnClickListener {

    private static final int ANSWER = 3942;
    private static final int ANSWER2 = 4521;
    private static final String FINAL_ANSWER = "final";
    private static final String ANSWER1 = "answer1";
    private static final String RET_CODE = "Vault1";
    private static final String RET_CODE2 = "Vault2";


    private Button one, two, three, four, five, six, seven, eight, nine, zero, clear, apply;
    private TextView textViewSafe;
    private Button ret;

    public Boolean openOrNot;
    public Boolean finalVault;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.safe1);

        Intent intent = getIntent();
        finalVault = intent.getBooleanExtra(FINAL_ANSWER,false);

        textViewSafe = findViewById(R.id.text_view_safe1);
        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        four = findViewById(R.id.four);
        five = findViewById(R.id.five);
        six = findViewById(R.id.six);
        seven = findViewById(R.id.seven);
        eight = findViewById(R.id.eight);
        nine = findViewById(R.id.nine);
        zero = findViewById(R.id.zero);
        clear = findViewById(R.id.clear);
        apply = findViewById(R.id.apply);
        ret = findViewById(R.id.button_return);

        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        seven.setOnClickListener(this);
        eight.setOnClickListener(this);
        nine.setOnClickListener(this);
        zero.setOnClickListener(this);
        clear.setOnClickListener(this);
        apply.setOnClickListener(this);
        ret.setOnClickListener(this);

        openOrNot = false;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.one:
                if(textViewSafe.length() <= 4){ textViewSafe.append("1"); }
                break;

            case R.id.two:
                if(textViewSafe.length() <= 4){ textViewSafe.append("2"); }
                break;

            case R.id.three:
                if(textViewSafe.length() <= 4){ textViewSafe.append("3"); }
                break;

            case R.id.four:
                if(textViewSafe.length() <= 4){ textViewSafe.append("4"); }
                break;

            case R.id.five:
                if(textViewSafe.length() <= 4){ textViewSafe.append("5"); }
                break;

            case R.id.six:
                if(textViewSafe.length() <= 4){ textViewSafe.append("6"); }
                break;

            case R.id.seven:
                if(textViewSafe.length() <= 4){ textViewSafe.append("7"); }
                break;

            case R.id.eight:
                if(textViewSafe.length() <= 4){ textViewSafe.append("8"); }
                break;

            case R.id.nine:
                if(textViewSafe.length() <= 4){ textViewSafe.append("9"); }
                break;

            case R.id.zero:
                if(textViewSafe.length() <= 4){ textViewSafe.append("0"); }
                break;

            case R.id.clear:
                textViewSafe.setText("");
                break;

            case R.id.apply:
                String input = textViewSafe.getText().toString();

                int yourAnswer = Integer.parseInt(input);

                if(yourAnswer == ANSWER && !finalVault){
                    openOrNot = true;
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra(RET_CODE,true);
                    setResult(RESULT_OK,returnIntent);
                    finish();

                }else if(yourAnswer == ANSWER2 && finalVault){
                    openOrNot = true;
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra(RET_CODE2,true);
                    setResult(RESULT_OK,returnIntent);
                    finish();
                }
                else{
                    openOrNot = false;
                    Toast.makeText(getApplicationContext(), "여전히 굳게 닫혀있다.\n아마 다르게 접근을 해야 하나보군.", Toast.LENGTH_SHORT).show();
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra(RET_CODE,false);
                    setResult(RESULT_OK,returnIntent);
                    finish();

                }
                break;

            case R.id.button_return:
                Intent returnIntent = new Intent();
                returnIntent.putExtra(RET_CODE,false);
                setResult(RESULT_OK,returnIntent);
                finish();

        }
    }
}
