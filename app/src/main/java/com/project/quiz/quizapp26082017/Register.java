package com.project.quiz.quizapp26082017;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    Button register;
    TextView agreementTextView;
    EditText email,password,username;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().hide();

        email=findViewById(R.id.emailr);
        username=findViewById(R.id.userNamer);
        password=findViewById(R.id.passwordr);
        agreementTextView=findViewById(R.id.agreementTextView);
        agreementTextView.setPaintFlags(agreementTextView.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        register = findViewById(R.id.registerbutton);
        email=findViewById(R.id.emailr);
        password=findViewById(R.id.passwordr);

        // navigation for terms and condition page
        agreementTextView.setOnClickListener( new View.OnClickListener(){

            @Override
            public void onClick(View v) {
               // Intent intent=new Intent(getApplication(),TermsAndConditions.class);
               // startActivity(intent);
                Toast.makeText(getApplicationContext(),"agreemet text is clicked", Toast.LENGTH_SHORT).show();
            }
        });
//        public void onclicktc(View v)
//        {
//            Toast.makeText(getApplicationContext(),"agreemet text is clicked", Toast.LENGTH_SHORT).show();
//        }



        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (username.getText().toString().length() == 0) {
                    username.setError("UserName is required");
                } else if ((email.getText().toString().length() == 0)) {
                    email.setError("email is requierd");
                } else if (password.getText().toString().length() == 0) {
                    password.setError("password is required");
                } else {
                    progressDialog.setMessage("Please Wait...");
                    progressDialog.show();
                    registerUser();
                }
            }
        });
    }
    private void registerUser(){

        //getting email and password from edit texts
        String emailnew = email.getText().toString().trim();
        String passwordnew =password.getText().toString().trim();

        //checking if email and passwords are empty


        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(emailnew, passwordnew)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){
                            //display some message here
                            Toast.makeText(Register.this,"Successfully registered",Toast.LENGTH_LONG).show();
                            Intent in =new Intent(Register.this,DashBord.class);
                            startActivity(in);
                        }else{
                            //display some message here
                            Toast.makeText(Register.this,"Registration Error",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }
}
