package com.project.quiz.quizapp26082017;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Oldornew extends AppCompatActivity {
Button pre,neww;
String category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oldornew);
        pre=findViewById(R.id.prequestion);
        neww =findViewById(R.id.newquestion);
        Intent in = getIntent();
        Bundle b= in.getExtras();
        category=b.getString("category");

        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences("logs",0);
                SharedPreferences.Editor et = sp.edit();
                et.putInt("logout", 0); //insert counter value as 1
                et.commit();
                Intent in = new Intent(Oldornew.this,ResumeActivity.class);
                in.putExtra("category",category);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(in);

            }
        });
        neww.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences("logs",0);
                SharedPreferences.Editor et = sp.edit();
                et.putInt("logout", 0); //insert counter value as 1
                et.commit();
                Intent in = new Intent(Oldornew.this,ComputerTestActivity.class);
                in.putExtra("category",category);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(in);


            }
        });

    }
}
