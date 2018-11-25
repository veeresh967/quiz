package com.project.quiz.quizapp26082017;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DashBord extends AppCompatActivity {
Button computer,sports,inventions,generalknowledge,science,english,authors,mathematics,capital,currency;
    DatabaseReference databaseArtists;
    int logs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_bord);
        databaseArtists = FirebaseDatabase.getInstance().getReference("artists").child("tests");
        computer = findViewById(R.id.computer);
     //   sports = findViewById(R.id.sports);
      //  inventions = findViewById(R.id.inventions);
        generalknowledge = findViewById(R.id.general);
        science = findViewById(R.id.science);
       // english = findViewById(R.id.science);
        authors = findViewById(R.id.authors);
        mathematics = findViewById(R.id.mathematics);
        capital = findViewById(R.id.capitals);
        currency = findViewById(R.id.currency);
        SharedPreferences sp = getSharedPreferences("logs",0);
        logs = sp.getInt("logout",0);
        Toast.makeText(this, ""+logs, Toast.LENGTH_SHORT).show();



        //inventions=findViewById(R.id.)

        computer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(logs==1)
                {
                    Intent in = new Intent(DashBord.this,Oldornew.class);
                    in.putExtra("category","Computers");
                    startActivity(in);
                }else
                    {
                        Intent in =new Intent(DashBord.this,ComputerTestActivity.class);
                        in.putExtra("category","Computers");
                        startActivity(in);

                    }



            }
        });


        generalknowledge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in =new Intent(DashBord.this,DisplayQuestions.class);
                in.putExtra("category","General");
                startActivity(in);
            }
        });
        science.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in =new Intent(DashBord.this,DisplayQuestions.class);
                in.putExtra("category","Science");
                startActivity(in);
            }
        });

authors.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent in =new Intent(DashBord.this,DisplayQuestions.class);
        in.putExtra("category","Authors");
        startActivity(in);
    }
});
mathematics.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent in =new Intent(DashBord.this,DisplayQuestions.class);
        in.putExtra("category","Mathematics");
        startActivity(in);
    }
});
capital.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent in =new Intent(DashBord.this,DisplayQuestions.class);
        in.putExtra("category","Capitals");
        startActivity(in);
    }
});
currency.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent in =new Intent(DashBord.this,DisplayQuestions.class);
        in.putExtra("category","Currency");
        startActivity(in);
    }
});


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menudashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.item1:
              //  Toast.makeText(getApplicationContext(),"logout",Toast.LENGTH_LONG).show();
                SharedPreferences sp = getSharedPreferences("logs",0);
                SharedPreferences.Editor et = sp.edit();
                et.putInt("counter", 0); //insert counter value as 1
                et.putInt("logout",1);
                et.commit();
                Intent in =new Intent(DashBord.this,LoginActivity.class);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(in);

                return true;
            case R.id.item2:
//                Toast.makeText(getApplicationContext(),"Item 2 Selected",Toast.LENGTH_LONG).show();
//                String id2 = databaseArtists.push().getKey();
//                String name = "veeresh";
//                String genre="male";
//
//                //creating an Artist Object
//                Artists artist = new Artists(id2, name, genre);
//
//                //Saving the Artist
//                databaseArtists.child(id2).setValue(artist);

 Intent in2 =new Intent(DashBord.this,AdminPanel.class);
startActivity(in2);


                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
