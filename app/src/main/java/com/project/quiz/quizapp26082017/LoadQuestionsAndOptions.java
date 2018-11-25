package com.project.quiz.quizapp26082017;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Map;

public class LoadQuestionsAndOptions extends AppCompatActivity {
/*this will get every question and there 4-option with hint as well creste the object
* and store it in object later add it to the arraylist*/
    ArrayList<pojoclass> questionlist = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ArrayList<pojoclass> getQuestionsvalues(Map<String, Object> value) {

        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : value.entrySet()){

            //Get user map
            Map singleUser = (Map) entry.getValue();
            String quest=(String) singleUser.get("question");
            String opt1=(String) singleUser.get("Option1");
            String opt2=(String) singleUser.get("Option2");
            String opt3=(String) singleUser.get("Option3");

            String opt4=(String) singleUser.get("Option4");
            String ans=(String) singleUser.get("Answer");
            String hint=(String) singleUser.get("Hint");


            pojoclass p= new pojoclass(quest,opt1,opt2,opt3,opt4,ans,hint);
            questionlist.add(p);
        }

        return questionlist;


    }



}

