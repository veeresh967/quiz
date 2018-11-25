package com.project.quiz.quizapp26082017;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ResumeActivity extends AppCompatActivity {
    FirebaseDatabase databaseArtists;
    public int questionCounter;
    public  int previousCounter;
    public int questionCountTotal;
    public int previousbtcount=0;
    TextView questioncount;
    TextView questionsnew;
    Button opt1,opt2,opt3,opt4,sub,pre,skip;
    LinearLayout hint;
    TextView timer;
    Chronometer simpleChronometer;
    public int buttonpressed;
    public int questinattemptedcounter;
    public int correctanscounter;
    public int previousbuttoncounter;
    public String selectedans;
    public Boolean selectanyans=false;
    public String TotaltimeElapsed;
    public  Boolean skippedques=false;

    SharedPreferences shared;


    String quest;
    String option11;
    String option22;
    String option33;
    String option44;
    String totalstring;




    public  static String TAG="MainActivity";
    ArrayList<pojoclass> mylist = new ArrayList<>();
    //    ArrayList<String> Questions = new ArrayList<>();
//    ArrayList<String> Answers = new ArrayList<>();
//    ArrayList<String> Options1 = new ArrayList<>();
//    ArrayList<String> Options2 = new ArrayList<>();
//    ArrayList<String> Options3 = new ArrayList<>();
//    ArrayList<String> Options4 = new ArrayList<>();
    ArrayList<String> SelectedAns=new ArrayList<>();
    ArrayList<String> AnsweredQuest=new ArrayList<>();
    ArrayList<pojoclass> Skippedquestions=new ArrayList<>();

    ArrayList<String> savedansweredquest = new ArrayList<String>();
    ArrayList<String> savedselectedans = new ArrayList<String>();
    ArrayList<String> savedskippedquestions = new ArrayList<String>();


    ArrayList<pojoclass> lstArrayList;

    pojoclass p;

    public Boolean bt1pressed=false;
    public Boolean bt2pressed=false;
    public Boolean bt3pressed=false;
    public Boolean bt4pressed=false;


    @SuppressLint("LongLogTag")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume);
        simpleChronometer = (Chronometer) findViewById(R.id.simpleChronometer);
        questioncount=findViewById(R.id.questioncount);
        questionsnew=(TextView)findViewById(R.id.question);
        hint=findViewById(R.id.hintid);
        opt1=findViewById(R.id.option1);
        opt2=findViewById(R.id.option2);
        opt3=findViewById(R.id.option3);
        opt4=findViewById(R.id.option4);
        sub=findViewById(R.id.submit);
        pre=findViewById(R.id.previous);
        skip=findViewById(R.id.skip);
        shared = getSharedPreferences("App_settings", MODE_PRIVATE);
        ComputerTestActivity computerTestActivity = new ComputerTestActivity();
        Gson gson = new Gson();

        String response=shared.getString("mylist" , "");
        String answeredquestjson=shared.getString("answeredquest" , "");
        String selectedquestjson=shared.getString("selectedans" , "");
        //String skippedquestjson=shared.getString("skippedquest" , "");
        questionCounter=shared.getInt("counter" , 0);

        lstArrayList= gson.fromJson(response,
                new TypeToken<List<pojoclass>>(){}.getType());


        if (answeredquestjson.isEmpty()) {
            savedansweredquest = new ArrayList<String>();
        } else {
            Type type = new TypeToken<List<String>>() {
            }.getType();
            savedansweredquest = gson.fromJson(answeredquestjson, type);
        }


        if (selectedquestjson.isEmpty()) {
            savedselectedans = new ArrayList<String>();
        } else {
            Type type = new TypeToken<List<String>>() {
            }.getType();
            savedselectedans = gson.fromJson(selectedquestjson, type);
        }


//        if (skippedquestjson.isEmpty()) {
//            savedskippedquestions = new ArrayList<String>();
//        } else {
//            Type type = new TypeToken<List<String>>() {
//            }.getType();
//            savedskippedquestions = gson.fromJson(skippedquestjson, type);
//        }
//

        for(int i=0;i<mylist.size();i++) {
            pojoclass p = lstArrayList.get(i);
            // Log.d("retrivesharedPreferences", "" + p.getQuestion());
        }
        //  Log.d("retrivesharedPreferencesquest", "" + savedCollage);
           Log.d("retrivesharedPreferences savedansweredquest", "" + savedansweredquest);
         Log.d("retrivesharedPreferences savedselectedans", "" + savedselectedans);
        // Log.d("retrivesharedPreferences skipped", "" + savedskippedquestions);

        Log.d("retrivesharedPreferences savedselectedans", "" +mylist);
        Toast.makeText(ResumeActivity.this, ""+mylist, Toast.LENGTH_SHORT).show();




        sub.setEnabled(false);
        // timer=findViewById(R.id.timer);
        hint =findViewById(R.id.hintid);
        Intent in = getIntent();
        Bundle b= in.getExtras();
        String category=b.getString("category");

        simpleChronometer.start();

        //---- getting question from firebase-----------------
        // datastore = new Datastore();

        //   databaseArtists = re.getInstance("").getReference();

        // databaseArtists.setValue("Hello, joee!");
        opt1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                buttonpressed=1;
                sub.setEnabled(true);
                bt1pressed=true;
                questinattemptedcounter++;


                checkanswer();
            }
        });
        opt2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                sub.setEnabled(true);
                buttonpressed=2;
                questinattemptedcounter++;

                bt2pressed=true;
                checkanswer();
            }
        });
        opt3.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                sub.setEnabled(true);
                buttonpressed=3;
                bt3pressed=true;
                questinattemptedcounter++;

                checkanswer();
            }
        });
        opt4.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                sub.setEnabled(true);
                buttonpressed=4;
                bt4pressed=true;
                questinattemptedcounter++;

                checkanswer();
            }
        });
        sub.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                pre.setEnabled(true);
                opt1.setEnabled(true);
                opt2.setEnabled(true);
                opt3.setEnabled(true);
                opt4.setEnabled(true);

                opt1.setBackgroundColor(Color.parseColor("#D3D3D3"));


                opt2.setBackgroundColor(Color.parseColor("#D3D3D3"));



                opt3.setBackgroundColor(Color.parseColor("#D3D3D3"));



                opt4.setBackgroundColor(Color.parseColor("#D3D3D3"));


                sub.setEnabled(false);
                questionCountTotal=lstArrayList.size();
                questionCounter++;

                loadquestions(lstArrayList);

            }
        });

        pre.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                previousbuttoncounter=questionCounter;
                questionCounter--;
                opt1.setBackgroundColor(Color.parseColor("#D3D3D3"));


                opt2.setBackgroundColor(Color.parseColor("#D3D3D3"));



                opt3.setBackgroundColor(Color.parseColor("#D3D3D3"));



                opt4.setBackgroundColor(Color.parseColor("#D3D3D3"));
                p=lstArrayList.get(questionCounter);
                quest= p.getQuestion();
                option11= p.getOption1();
                option22=p.getOption2();
                option33= p.getOption3();
                option44= p.getOption4();
                questionsnew.setText(quest);
                opt1.setText(option11);
                opt2.setText(option22);
                opt3.setText(option33);
                opt4.setText(option44);

                previousbtcount=questionCounter;

                questioncount.setText("Question: " +questionCounter+"/"+questionCountTotal);
                String presentquest=p.getQuestion();
                // Create List of users that you want to save
                //Toast.makeText( this, ""+AnsweredQuest.toString(), Toast.LENGTH_SHORT).show();


                if(questionCounter==0)
                {
                    pre.setEnabled(false);
                }

//checking previous questions


                checkpreviousandnextquest(presentquest);











                //Toast.makeText(ComputerTestActivity.this, "" + questionCounter, Toast.LENGTH_SHORT).show();


            }
        });




        questionCountTotal=lstArrayList.size();

loadquestions(lstArrayList);

    }


    private void checkpreviousandnextquest(String presentquest)
    {
        p=lstArrayList.get(questionCounter);
        if (savedansweredquest.toString().contains(presentquest)) {


            //   Toast.makeText(ComputerTestActivity.this, "aNSWERED", Toast.LENGTH_SHORT).show();
//                         SharedPreferences prefs2 = getSharedPreferences("User", Context.MODE_PRIVATE);
//                         Gson gson2 = new Gson();
//                         String json2 = prefs2.getString("sachinaa", null);
//                         Type type = new TypeToken<ArrayList<TemporaryDbModal>>() {}.getType();
//
//
//                         s=gson2.fromJson(json2, type);
//
//
//
//                         TemporaryDbModal temporaryDbModal = s.get(questionCounter);
//                         String question = temporaryDbModal.getQuestion();
//                         String option1 =temporaryDbModal.getOption1();
//                         String option2 =temporaryDbModal.getOption2();
//                         String option3 =temporaryDbModal.getOption3();
//                         String option4 =temporaryDbModal.getOption4();
//                         String selectedans=temporaryDbModal.getSelectedans();
//                         questionsnew.setText(question);
//                         opt1.setText(option1);
//                         opt2.setText(option2);
//                         opt3.setText(option3);
//                         opt4.setText(option4);
            ArrayList<String> nonDupList = new ArrayList<String>();

            Iterator<String> dupIter = savedselectedans.iterator();
            while (dupIter.hasNext()) {
                String dupWord = dupIter.next();
                if (nonDupList.contains(dupWord)) {
                    dupIter.remove();
                } else {
                    nonDupList.add(dupWord);
                }
            }


            String ans = p.getAnswer();

            // Toast.makeText(ComputerTestActivity.this, "aNSWERED" + nonDupList.toString(), Toast.LENGTH_SHORT).show();
            String finalans = nonDupList.get(questionCounter);

            if (finalans.matches(ans)) {
                if (finalans.matches(option11)) {
                    opt1.setBackground(getDrawable(R.drawable.buttonselectorgreen));
                    opt2.setEnabled(false);
                    opt3.setEnabled(false);
                    opt4.setEnabled(false);
                    sub.setEnabled(true);


                }
                if (finalans.matches(option22)) {
                    opt2.setBackground(getDrawable(R.drawable.buttonselectorgreen));
                    opt1.setEnabled(false);
                    opt3.setEnabled(false);
                    opt4.setEnabled(false);
                    sub.setEnabled(true);


                }
                if (finalans.matches(option33)) {
                    opt3.setBackground(getDrawable(R.drawable.buttonselectorgreen));
                    opt1.setEnabled(false);
                    opt2.setEnabled(false);
                    opt4.setEnabled(false);
                    sub.setEnabled(true);

                }
                if (finalans.matches(option44))

                {
                    opt4.setBackground(getDrawable(R.drawable.buttonselectorgreen));
                    opt1.setEnabled(false);
                    opt2.setEnabled(false);
                    opt3.setEnabled(false);
                    sub.setEnabled(true);

                }


            } else {
                if (finalans.matches(option11)) {
                    opt1.setBackground(getDrawable(R.drawable.button_selector_effect));
                    opt2.setEnabled(false);
                    opt3.setEnabled(false);
                    opt4.setEnabled(false);
                    sub.setEnabled(true);

                }
                if (finalans.matches(option22)) {
                    opt2.setBackground(getDrawable(R.drawable.button_selector_effect));
                    opt1.setEnabled(false);
                    opt3.setEnabled(false);
                    opt4.setEnabled(false);
                    sub.setEnabled(true);

                }
                if (finalans.matches(option33)) {
                    opt3.setBackground(getDrawable(R.drawable.button_selector_effect));
                    opt1.setEnabled(false);
                    opt2.setEnabled(false);
                    opt4.setEnabled(false);
                    sub.setEnabled(true);
                }
                if (finalans.matches(option44))

                {
                    opt4.setBackground(getDrawable(R.drawable.button_selector_effect));
                    opt1.setEnabled(false);
                    opt2.setEnabled(false);
                    opt3.setEnabled(false);
                    sub.setEnabled(true);

                }


            }

        }




    }






    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void checkanswer() {
        p=lstArrayList.get(questionCounter);
        //  Toast.makeText(this, ""+Answers.get(questionCounter-1), Toast.LENGTH_SHORT).show();
        switch (buttonpressed)
        {

            case 1:
                selectedans=opt1.getText().toString();
                savedansweredquest.add(p.getQuestion());
                savedselectedans.add(opt1.getText().toString());
                if(opt1.getText().toString().contains(p.getAnswer()))
                {
                    correctanscounter++;


                    // Toast.makeText(this, "correct", Toast.LENGTH_SHORT).show();
                    opt1.setBackground(getDrawable(R.drawable.buttonselectorgreen));
                    opt2.setEnabled(false);
                    opt3.setEnabled(false);
                    opt4.setEnabled(false);

                }else
                {

                    //   Toast.makeText(this, "Incorrect", Toast.LENGTH_SHORT).show();
                    opt1.setBackground(getDrawable(R.drawable.button_selector_effect));
                    opt2.setEnabled(false);
                    opt3.setEnabled(false);
                    opt4.setEnabled(false);
                    showsolution();
                }

                break;

            case 2:
                selectedans=opt2.getText().toString();
                savedansweredquest.add(p.getQuestion());
                savedselectedans.add(opt2.getText().toString());
                if(opt2.getText().toString().contains(p.getAnswer()))
                {
                    correctanscounter++;
                    opt2.setBackground(getDrawable(R.drawable.buttonselectorgreen));
                    //Toast.makeText(this, "correct", Toast.LENGTH_SHORT).show();
                    opt1.setEnabled(false);
                    opt3.setEnabled(false);
                    opt4.setEnabled(false);

                }else
                {
                    //Toast.makeText(this, "Incorrect", Toast.LENGTH_SHORT).show();
                    opt2.setBackground(getDrawable(R.drawable.button_selector_effect));
                    opt1.setEnabled(false);
                    opt3.setEnabled(false);
                    opt4.setEnabled(false);
                    showsolution();
                }
                break;
            case 3:
                selectedans=opt3.getText().toString();
                savedansweredquest.add(p.getQuestion());
                savedselectedans.add(opt3.getText().toString());
                if(opt3.getText().toString().contains(p.getAnswer()))
                {
                    correctanscounter++;
                    opt3.setBackground(getDrawable(R.drawable.buttonselectorgreen));
                    //Toast.makeText(this, "correct", Toast.LENGTH_SHORT).show();
                    opt1.setEnabled(false);
                    opt2.setEnabled(false);
                    opt4.setEnabled(false);

                }else
                {
                    // Toast.makeText(this, "Incorrect", Toast.LENGTH_SHORT).show();
                    opt3.setBackground(getDrawable(R.drawable.button_selector_effect));
                    opt1.setEnabled(false);
                    opt2.setEnabled(false);
                    opt4.setEnabled(false);
                    showsolution();


                }
                break;
            case 4:
                selectedans=opt4.getText().toString();

                savedselectedans.add(opt4.getText().toString());
                savedansweredquest.add(p.getQuestion());

                if(opt4.getText().toString().contains(p.getAnswer()))
                {
                    correctanscounter++;
                    opt4.setBackground(getDrawable(R.drawable.buttonselectorgreen));
                    //Toast.makeText(this, "correct", Toast.LENGTH_SHORT).show();
                    opt1.setEnabled(false);
                    opt2.setEnabled(false);
                    opt3.setEnabled(false);

                }else
                {
                    // Toast.makeText(this, "Incorrect", Toast.LENGTH_SHORT).show();
                    opt4.setBackground(getDrawable(R.drawable.button_selector_effect));
                    opt1.setEnabled(false);
                    opt2.setEnabled(false);
                    opt3.setEnabled(false);
                    showsolution();


                }
                break;


        }




    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void showsolution() {
        p=lstArrayList.get(questionCounter);

        String gettextfromopt1=opt1.getText().toString();
        String gettextfromopt2=opt2.getText().toString();
        String gettextfromopt3=opt3.getText().toString();
        String gettextfromopt4=opt4.getText().toString();

        if(gettextfromopt1.matches(p.getAnswer()))
        {

            opt1.setBackground(getDrawable(R.drawable.buttonselectorgreen));
        }else if(gettextfromopt2.matches(p.getAnswer()))
        {
            opt2.setBackground(getDrawable(R.drawable.buttonselectorgreen));
        }
        else if(gettextfromopt3.matches(p.getAnswer()))
        {
            opt3.setBackground(getDrawable(R.drawable.buttonselectorgreen));

        }else if(gettextfromopt4.matches(p.getAnswer()))
        {
            opt4.setBackground(getDrawable(R.drawable.buttonselectorgreen));


        }

    }




    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void loadquestions(ArrayList<pojoclass> list) {

        if(questionCounter<questionCountTotal)
        {
            pojoclass p = lstArrayList.get(questionCounter);

            quest= p.getQuestion();
            option11=p.getOption1();
            option22= p.getOption2();
            option33= p.getOption3();
            option44= p.getOption4();
            questionsnew.setText(quest);
            opt1.setText(option11);
            opt2.setText(option22);
            opt3.setText(option33);
            opt4.setText(option44);

            previousbtcount=questionCounter;

            questioncount.setText("Question: " +questionCounter+"/"+questionCountTotal);
            String presentquest=p.getQuestion();
            // Create List of users that you want to save
            //Toast.makeText( this, ""+AnsweredQuest.toString(), Toast.LENGTH_SHORT).show();

            if(questionCounter!=0) {

               checkpreviousandnextquest(presentquest);

            }

//             Toast.makeText(this, "Present"+presentquest, Toast.LENGTH_SHORT).show();



        }else
        {



            Toast.makeText(this, ""+Skippedquestions.toString(), Toast.LENGTH_SHORT).show();
            simpleChronometer.stop();
            TotaltimeElapsed=simpleChronometer.getText().toString();
            Intent in =new Intent(ResumeActivity.this,ResultActivity.class);
            in.putExtra("question",questinattemptedcounter);
            in.putExtra("answer",correctanscounter);
            in.putExtra("timer",TotaltimeElapsed);
            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(in);



        }

    }









    @Override
    public void onBackPressed() {
        Intent in = new Intent(ResumeActivity.this,DashBord.class);
        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(in);
    }







}
