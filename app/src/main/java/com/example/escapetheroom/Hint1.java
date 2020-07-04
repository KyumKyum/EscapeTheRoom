package com.example.escapetheroom;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import androidx.annotation.NonNull;

public class Hint1 extends Dialog implements View.OnClickListener {

    ImageView img;

    public Hint1(@NonNull Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.hint1);

        img = findViewById(R.id.img);
        img.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        dismiss();
    }
}
