package com.project.quiz.quizapp26082017;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ResultActivity extends AppCompatActivity {
TextView ques,anss,timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ques=findViewById(R.id.question);
        anss=findViewById(R.id.answer);
        timer=findViewById(R.id.timer);
        Intent in = getIntent();
        Bundle b = in.getExtras();
        int quest=b.getInt("question");
        int quest2=quest-1;
        int ans=b.getInt("answer");
        String tim=b.getString("timer");
        ques.setText(""+quest);
        anss.setText(""+ans);
        timer.setText(""+tim);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent in =new Intent(ResultActivity.this,DashBord.class);

        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(in);
    }



    public void email(View view) {
        Intent intent5 = new Intent();
        intent5.setAction(Intent.ACTION_SEND);
        intent5.setType("text/plain"); //or use "message/rfc822" also
        intent5.putExtra(Intent.EXTRA_EMAIL, "info@techpalle.com");
        intent5.putExtra(Intent.EXTRA_CC, "enquiry@techpalle.com");
        intent5.putExtra(Intent.EXTRA_SUBJECT, "Hello");
        intent5.putExtra(Intent.EXTRA_TEXT, "Hello, how are you info.");
        startActivity(Intent.createChooser(intent5, "SEND EMAIL"));
    }
}
