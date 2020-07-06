package com.example.escapetheroom;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class FamilyPic4 extends Dialog implements View.OnClickListener{

    ImageView img;

    public FamilyPic4(@NonNull Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pic4);

        img = findViewById(R.id.img);
        img.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        Toast.makeText(getContext(), "사진 뒷편에 열쇠가 있다!\n이제 나갈 수 있겠어!", Toast.LENGTH_SHORT).show();
        dismiss();
    }
}
