package com.example.lab5;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class InitScene extends Activity {
    int mode = 0;
    Button enter;
    Button reg;
    TextView textView;
    TextView collection;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.init_scene);
        /*Typeface type = Typeface.createFromAsset(getAssets(),"assets/fonts/CoopBlack_Cyrillic_0.ttf");
        textView.setTypeface(type);*/

        enter = findViewById(R.id.enterButton);
        reg = findViewById(R.id.regButton);

        Intent intent = new Intent(this, RegScene.class);

        Bundle flag = new Bundle();

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mode = 0;
                flag.putInt("mode", mode);
                intent.putExtras(flag);
                startActivity(intent);
            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mode = 1;
                flag.putInt("mode", mode);
                intent.putExtras(flag);
                startActivity(intent);
            }
        });
    }
}
