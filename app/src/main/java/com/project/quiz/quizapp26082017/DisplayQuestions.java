package com.project.quiz.quizapp26082017;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class DisplayQuestions extends AppCompatActivity {

    Button btn_option1,btn_option2,btn_option3,btn_option4;
    Button btn_previous,btn_skip,btn_next;
    TextView question;
    TextView questionNum;
    String category;
    Chronometer simpleChronometer;
    LinearLayout hint;
    static int questionCounter=0;
    int questionCountTotal;
    static int  correctans=0;
    public String TotaltimeElapsed;
    ProgressDialog loadingScreen;
    LoadQuestionsAndOptions loadQuestionsAndOptions;
    ArrayList<pojoclass> questionlist = new ArrayList<>();
    ArrayList<AnsweredQue> ansQuestionlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_questions);
        loadQuestionsAndOptions=new LoadQuestionsAndOptions();
        // getting id for options
        btn_option1=findViewById(R.id.option1);
        btn_option2=findViewById(R.id.option2);
        btn_option3=findViewById(R.id.option3);
        btn_option4=findViewById(R.id.option4);
        //----get id for hint button-----
        hint=findViewById(R.id.hintid);
        //getting id for pre-next,skip
        btn_previous=findViewById(R.id.previous);
        btn_next=findViewById(R.id.next);
        btn_skip=findViewById(R.id.skip);
        btn_previous.setEnabled(false);
        btn_next.setEnabled(false);
        // get id for question
        question=findViewById(R.id.question);
        //-----get the id for question number
        questionNum=findViewById(R.id.questioncount);

        //----------get id for timer
        simpleChronometer = (Chronometer) findViewById(R.id.simpleChronometer);
        //----getting category name
        Intent in = getIntent();
        Bundle b= in.getExtras();
        category=b.getString("category");


        // getting data from firevase and show loading screen display
        loadingScreen = new ProgressDialog(DisplayQuestions.this);
        loadingScreen.setMessage("loading");
        loadingScreen.show();
        getQuestions();
    }
       @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.submit:
                // do something
              //  Toast.makeText(DisplayQuestions.this, "Submit clicked", Toast.LENGTH_SHORT).show();
                simpleChronometer.stop();
                TotaltimeElapsed=simpleChronometer.getText().toString();
                Intent in =new Intent(DisplayQuestions.this,ResultActivity.class);
                in.putExtra("question",questionlist.size());
                in.putExtra("answer",correctans);
                in.putExtra("timer",TotaltimeElapsed);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(in);
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }
    private void loadquestions(int counter) {

        if(counter<questionCountTotal)
        {
            int num=counter+1;
            questionNum.setText("Question: " +num+"/"+questionCountTotal);
            pojoclass obj = questionlist.get(questionCounter);


            question.setText(obj.getQuestion());
            btn_option1.setText(obj.getOption1());
            //btn_option1.setText("1234567890,1234567890,1234567890,1234567890,1234567890");
            btn_option2.setText(obj.getOption2());
            btn_option3.setText(obj.getOption3());
            btn_option4.setText(obj.getOption4());
            System.out.println("que"+obj.getQuestion());
            System.out.println("op1"+obj.getOption1());

            //---------------------next,pre,skip button---------------
            btn_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    questionCounter=questionCounter+1;
                    loadquestions(questionCounter);
                    btn_previous.setEnabled(true);

                    //enabling below options for next new question
                    btn_option1.setEnabled(true);
                    //btn_option1.setBackgroundColor(Color.parseColor("#D3D3D3"));
                    btn_option1.setBackgroundResource(R.drawable.buttonshape);

                    btn_option2.setEnabled(true);
                    //btn_option2.setBackgroundColor(Color.parseColor("#D3D3D3"));
                    btn_option2.setBackgroundResource(R.drawable.buttonshape);

                    btn_option3.setEnabled(true);
                   // btn_option3.setBackgroundColor(Color.parseColor("#D3D3D3"));
                    btn_option3.setBackgroundResource(R.drawable.buttonshape);

                    btn_option4.setEnabled(true);
                    //btn_option4.setBackgroundColor(Color.parseColor("#D3D3D3"));
                    btn_option4.setBackgroundResource(R.drawable.buttonshape);
                    btn_skip.setEnabled(true);

                    //-------if its alredy answered question change the option color---------
                    pojoclass obj = questionlist.get(questionCounter);
                    question.setText(obj.getQuestion());
                    prevQuestionoptionBG(obj.getQuestion());

                }
            });

            btn_previous.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //------ displaying the previous answerd question when user press previous button---------
                    questionCounter=questionCounter-1;
                    loadquestions(questionCounter);
                    if(questionCounter==0)
                    {
                        btn_previous.setEnabled(false);
                    }
                    btn_option1.setEnabled(false);
                   // btn_option1.setBackgroundColor(Color.parseColor("#D3D3D3"));
                    btn_option1.setBackgroundResource(R.drawable.buttonshape);

                    btn_option2.setEnabled(false);
                   // btn_option2.setBackgroundColor(Color.parseColor("#D3D3D3"));
                    btn_option2.setBackgroundResource(R.drawable.buttonshape);

                    btn_option3.setEnabled(false);
                   // btn_option3.setBackgroundColor(Color.parseColor("#D3D3D3"));
                    btn_option3.setBackgroundResource(R.drawable.buttonshape);

                    btn_option4.setEnabled(false);
                   // btn_option4.setBackgroundColor(Color.parseColor("#D3D3D3"));
                    btn_option4.setBackgroundResource(R.drawable.buttonshape);
                    btn_skip.setEnabled(false);

                    //-------answered question it is so change the option color---------
                    pojoclass obj = questionlist.get(questionCounter);
                    question.setText(obj.getQuestion());
                    prevQuestionoptionBG(obj.getQuestion());
                    btn_next.setEnabled(true);
                }

            });

            btn_skip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //------- if user skip que then that question display at the end by removing current position-------
                    pojoclass skip_obj=questionlist.remove(questionCounter);
                    questionlist.add(skip_obj);
                    loadquestions(questionCounter);
                }
            });
            //---------------------end of next,pre,skip button fun---------------

            hint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //--------function to display hint for particuler question--------
                    pojoclass p = questionlist.get(questionCounter);
                    String hint = p.getHint();
                    MyAlertDialog myAlertDialog = new MyAlertDialog(hint);
                    myAlertDialog.show(getSupportFragmentManager(), null);
                }
            });

            //---------options selection function-------------
            btn_option1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //----- calling checkAnswer function---- by sending selected option-------
                    if(checkAnswer(btn_option1.getText().toString(),questionCounter))
                    {
                        //-----if it is true option turn into green
                        //btn_option1.setBackgroundColor(Color.GREEN);
                        btn_option1.setBackgroundResource(R.drawable.buttonshapeg);
                        //--------counting correct ans----------
                        correctans++;
                    }
                    else
                    {
                        //-----if it is false turn option as red-----
                        //btn_option1.setBackgroundColor(Color.RED);
                        btn_option1.setBackgroundResource(R.drawable.buttonshaper);
                    }
                    //---- disabling other options once user click on any option----
                    btn_option2.setEnabled(false);
                    btn_option3.setEnabled(false);
                    btn_option4.setEnabled(false);
                    // disbling skip btn after option select
                    btn_skip.setEnabled(false);

                }
            });

            btn_option2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //----- calling checkAnswer function---- by sending selected option-------
                    if(checkAnswer(btn_option2.getText().toString(),questionCounter))
                    {
                        //-----if it is true option turn into green
                        //btn_option2.setBackgroundColor(Color.GREEN);
                        btn_option2.setBackgroundResource(R.drawable.buttonshapeg);
                        //--------counting correct ans----------
                        correctans++;
                    }
                    else
                    {
                        //-----if it is false turn option as red-----
                        //btn_option2.setBackgroundColor(Color.RED);
                        btn_option2.setBackgroundResource(R.drawable.buttonshaper);
                    }
                    //---- disabling other options once user click on any option----
                    btn_option1.setEnabled(false);
                    btn_option3.setEnabled(false);
                    btn_option4.setEnabled(false);
                    // disbling skip btn after option select
                    btn_skip.setEnabled(false);
                }
            });

            btn_option3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //----- calling checkAnswer function---- by sending selected option-------
                    if(checkAnswer(btn_option3.getText().toString(),questionCounter))
                    {
                        //-----if it is true option turn into green
                        //btn_option3.setBackgroundColor(Color.GREEN);
                        btn_option3.setBackgroundResource(R.drawable.buttonshapeg);
                        //--------counting correct ans----------
                        correctans++;
                    }
                    else
                    {
                        //-----if it is false turn option as red-----
                        //btn_option3.setBackgroundColor(Color.RED);
                        btn_option3.setBackgroundResource(R.drawable.buttonshaper);
                    }
                    //---- disabling other options once user click on any option----
                    btn_option1.setEnabled(false);
                    btn_option2.setEnabled(false);
                    btn_option4.setEnabled(false);
                    // disbling skip btn after option select
                    btn_skip.setEnabled(false);
                }
            });

            btn_option4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //----- calling checkAnswer function---- by sending selected option-------
                    if(checkAnswer(btn_option4.getText().toString(),questionCounter))
                    {
                        //-----if it is true option turn into green
                        //btn_option4.setBackgroundColor(Color.GREEN);
                        btn_option4.setBackgroundResource(R.drawable.buttonshapeg);
                        //--------counting correct ans----------
                        correctans++;
                    }
                    else
                    {
                        //-----if it is false turn option as red-----
                        //btn_option4.setBackgroundColor(Color.RED);
                        btn_option4.setBackgroundResource(R.drawable.buttonshaper);
                    }
                    //---- disabling other options once user click on any option----
                    btn_option1.setEnabled(false);
                    btn_option2.setEnabled(false);
                    btn_option3.setEnabled(false);
                    // disbling skip btn after option select
                    btn_skip.setEnabled(false);
                }
            });

            //--------- end of options selection function
        }
        else
        {
            //-----------at the end redirect to result page--------
            simpleChronometer.stop();
            TotaltimeElapsed=simpleChronometer.getText().toString();
            Intent in =new Intent(DisplayQuestions.this,ResultActivity.class);
            in.putExtra("question",questionlist.size());
            in.putExtra("answer",correctans);
            in.putExtra("timer",TotaltimeElapsed);
            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(in);
        }
    }
    //------------------------------------------------------------------------------------------------------------------------

    //-----function to load question with answer-----------
    public void getQuestions()
    {
        // ------get a reference of db-----
        FirebaseDatabase databaseArtists;
        databaseArtists = FirebaseDatabase.getInstance();
        DatabaseReference DBref = databaseArtists.getReference(category).child("test1");

        DBref.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //-------function call to store question details in an object and stor it in array of object------
                questionlist= loadQuestionsAndOptions.getQuestionsvalues((Map<String,Object>) dataSnapshot.getValue());
                questionCountTotal=questionlist.size();
                loadingScreen.dismiss();
                //---- display questions on screen---------
                loadquestions(questionCounter);
                //-----start timer for test-------
                simpleChronometer.start();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //----any error occur to retrive data from db----
                Toast.makeText(DisplayQuestions.this, "error"+databaseError.toException(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean checkAnswer(String option,int counter)
    {
        //------function to check selected option is right or not------
        pojoclass obj = questionlist.get(counter);
        String ans= obj.getAnswer();
        btn_next.setEnabled(true);
       //-----------------adding que,option,ans in ansList---------
        AnsweredQue ansobj=new AnsweredQue();
       ansobj.setQuestion(obj.getQuestion());
       ansobj.setAnswer(ans);
       ansobj.setSelectedOption(option);
       ansQuestionlist.add(ansobj);
       btn_skip.setEnabled(false);
//------------send true if it is correct and false if it is wrong---------
       if(option.equals(ans))
       {
           return true;
       }
       else
       {
           return false;
       }
    }
    //---------------functiuon to put background color for options if user press previous button
    public void prevQuestionoptionBG(String que)
    {
        for(int i=0;i<ansQuestionlist.size();i++)
        {
            AnsweredQue obj=ansQuestionlist.get(i);
            if(que.equals(obj.getQuestion()))
            {
                btn_option1.setEnabled(false);
                btn_option2.setEnabled(false);
                btn_option3.setEnabled(false);
                btn_option4.setEnabled(false);
                btn_next.setEnabled(true);
                btn_skip.setEnabled(false);
                if(obj.getAnswer().equals(obj.getSelectedOption())) {
                    btn_skip.setEnabled(false);
                    // change option color if user gives correct ans
                    if (obj.getAnswer().equals(btn_option1.getText())) {
                       // btn_option1.setBackgroundColor(Color.GREEN);
                        btn_option1.setBackgroundResource(R.drawable.buttonshapeg);
                    } else if (obj.getAnswer().equals(btn_option2.getText())) {
                        //btn_option2.setBackgroundColor(Color.GREEN);
                        btn_option2.setBackgroundResource(R.drawable.buttonshapeg);
                    } else if (obj.getAnswer().equals(btn_option3.getText())) {
                        //btn_option3.setBackgroundColor(Color.GREEN);
                        btn_option3.setBackgroundResource(R.drawable.buttonshapeg);
                    } else {
                        //btn_option4.setBackgroundColor(Color.GREEN);
                        btn_option4.setBackgroundResource(R.drawable.buttonshapeg);
                    }
                }
                //--------------change options red and green for wrong ans
                else
                {
                    btn_option1.setEnabled(false);
                    btn_option2.setEnabled(false);
                    btn_option3.setEnabled(false);
                    btn_option4.setEnabled(false);
                    //-------------change red color if ans is wrong
                    if(obj.getSelectedOption().equals(btn_option1.getText()))
                    {
                        //btn_option1.setBackgroundColor(Color.RED);
                        btn_option1.setBackgroundResource(R.drawable.buttonshaper);
                    }
                    else if (obj.getSelectedOption().equals(btn_option2.getText()))
                    {
                        //btn_option2.setBackgroundColor(Color.RED);
                        btn_option2.setBackgroundResource(R.drawable.buttonshaper);
                    }
                    else if(obj.getSelectedOption().equals(btn_option3.getText()))
                    {
                        //btn_option3.setBackgroundColor(Color.RED);
                        btn_option3.setBackgroundResource(R.drawable.buttonshaper);
                    }
                    else
                    {
                       // btn_option4.setBackgroundColor(Color.RED);
                        btn_option4.setBackgroundResource(R.drawable.buttonshaper);
                    }
                    //---------------------------------------------
                    if (obj.getAnswer().equals(btn_option1.getText())) {
                       // btn_option1.setBackgroundColor(Color.GREEN);
                        btn_option1.setBackgroundResource(R.drawable.buttonshapeg);
                    } else if (obj.getAnswer().equals(btn_option2.getText())) {
                       // btn_option2.setBackgroundColor(Color.GREEN);
                        btn_option2.setBackgroundResource(R.drawable.buttonshapeg);
                    } else if (obj.getAnswer().equals(btn_option3.getText())) {
                        //btn_option3.setBackgroundColor(Color.GREEN);
                        btn_option3.setBackgroundResource(R.drawable.buttonshapeg);
                    } else {
                        //btn_option4.setBackgroundColor(Color.GREEN);
                        btn_option4.setBackgroundResource(R.drawable.buttonshapeg);
                    }
                }
            }
        }

    }
}
