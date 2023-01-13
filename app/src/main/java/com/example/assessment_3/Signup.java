package com.example.assessment_3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.assessment_3.databinding.ActivityMainBinding;
import com.example.assessment_3.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signup extends AppCompatActivity {
    private ActivitySignupBinding binding;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        auth = FirebaseAuth.getInstance();
        Button registerButton=findViewById(R.id.addButton);
        EditText emailEditText= findViewById(R.id.emailEditText);
        EditText passwordEditText= findViewById(R.id.passwordEditText);
        //menu view binding
        Button backButton=findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Signup.this, MainActivity.class);
                startActivity(intent);
            }
        });
        //check password and email validation
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_txt = emailEditText.getText().toString();
                String password_txt = passwordEditText.getText().toString();
                boolean numCheck = false;
                boolean checkAt = false;
                boolean checkDot = false;
                for (int i = 0; i < password_txt.length(); i++) {
                    if (Character.isDigit(password_txt.charAt(i))) {
                        numCheck= true;
                    }
                }
                for (int i = 0; i < email_txt.length() - 1; i++) {
                    if (email_txt.charAt(i) == '@') {
                        checkAt = true;
                    }
                    if (email_txt.charAt(i) == '.') {
                        checkDot = true;
                    }
                }
                if (TextUtils.isEmpty(email_txt) || TextUtils.isEmpty(password_txt)) {
                    String msg = "Empty Username or Password";
                    toastMsg(msg);
                } else if (password_txt.length() < 6) {
                    String msg = "Password is too short";
                    toastMsg(msg);
                } else if (numCheck == false){
                    String msg = "Password needs at least 1 number";
                    toastMsg(msg);
                } else if (checkAt == false){
                    String msg = "Invalid Email";
                    toastMsg(msg);
                } else if (checkDot == false){
                    String msg = "Invalid Email";
                    toastMsg(msg);
                } else {
                    registerUser(email_txt, password_txt);
                }
            }
        });
    }
    //check for already registered email
    private void registerUser(String email_txt, String password_txt) {
        // To create username and password
        auth.createUserWithEmailAndPassword(email_txt,password_txt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    String msg = "Registration Successful";
                    toastMsg(msg);
                    startActivity(new Intent(Signup.this, MainActivity.class));
                }else {
                    String msg = "Email already registered";
                    toastMsg(msg);
                }
            }
        });
    }
    public void toastMsg(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}