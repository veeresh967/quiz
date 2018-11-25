package com.project.quiz.quizapp26082017;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static android.widget.Toast.LENGTH_SHORT;

public class LoginActivity extends AppCompatActivity {
EditText email,password;
Button login,register;
    private FirebaseAuth mAuth;
      private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        SharedPreferences sp = getSharedPreferences("logs",0);
        int counter = sp.getInt("counter",0);
        Toast.makeText(this, "counter"+counter, Toast.LENGTH_SHORT).show();
        if(counter==1)
        {
            Intent in = new Intent(LoginActivity.this,DashBord.class);
            startActivity(in);
        }


        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        email=findViewById(R.id.emaill);
        login=findViewById(R.id.loginbutton);
        password=findViewById(R.id.passwordl);
        register=findViewById(R.id.registerbutton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //------------checking empty fields
                if(email.getText().toString().length()==0)
                {
                    email.setError(" user email is required");
                }
                else if(password.getText().toString().length()==0)
                {
                    password.setError(" password field is required");
                }
                else{
                    progressDialog.setMessage("Logging in Please Wait...");
                    progressDialog.show();
                    loginuser();
                }



            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in =new Intent(LoginActivity.this,Register.class);
                startActivity(in);
            }
        });

    }




    private void loginuser() {
        String emailnew=email.getText().toString().trim();
        String passwordnew=password.getText().toString();
        mAuth.signInWithEmailAndPassword(emailnew, passwordnew)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){
                            //display some message here
                          //  Toast.makeText(LoginActivity.this,"Successfully Login",Toast.LENGTH_LONG).show();
                            Intent in =new Intent(LoginActivity.this,DashBord.class);
                            startActivity(in);
                            SharedPreferences sp = getSharedPreferences("logs",0);
                          //  int counter = sp.getInt("counter", 0);


                                //IF COUNTER IS 0 MEANS, THERE IS NO PREFERENCE FILE, WE CREATED JUST NOW
                                SharedPreferences.Editor et = sp.edit();
                                et.putInt("counter", 1); //insert counter value as 1
                                et.commit();


                        }else{
                            //display some message here
                            Toast.makeText(LoginActivity.this,"Login Error",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });




    }
}
