package com.example.pedaption;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    EditText mEmail, mPassword;
    Button mLoginBtn;
    TextView mCreateBtn;

    SharedPreferences sharedPreferences;

    public static final String Email = "email";
    public static final String Password = "password";
    public static final String fileName = "login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.password);
        mLoginBtn = findViewById(R.id.loginBtn);
        mCreateBtn = findViewById(R.id.createText);

        sharedPreferences = getSharedPreferences("login",Context.MODE_PRIVATE);

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser(v);
            }
        });

        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });
    }


    private Boolean emailValidate(){
        String email = mEmail.getText().toString().replaceAll("@gmail.com","");
        if(email.isEmpty()){
            mEmail.setError("Field cannot be empty");
            return false;
        }else{
            mEmail.setError(null);
            return true;
        }
    }
    private  Boolean passwordValidate(){
        String password = mPassword.getText().toString();
        if(password.isEmpty()){
            mPassword.setError("Field cannot be empty");
            return false;
        }else{
            mPassword.setError(null);
            return true;
        }
    }

    public void loginUser(View view){
        if(!emailValidate() | !passwordValidate()){
            return;
        }else{
            isUser();
        }
    }
    private void isUser(){
        final String userEntertedEmail = mEmail.getText().toString().replaceAll("@gmail.com","");
        final String userEnteredPassword = mPassword.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

        Query checkuser = reference.orderByChild("email").equalTo(userEntertedEmail);

        checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    mEmail.setError(null);

                    String passwordfromDB = snapshot.child(userEntertedEmail).child("password").getValue(String.class);
                    if(passwordfromDB.equals(userEnteredPassword)){



                        String emergencyphonefromDB = snapshot.child(userEntertedEmail).child("emergencyphone").getValue(String.class);
                        String fullname = snapshot.child(userEntertedEmail).child("fullname").getValue(String.class);
                        Intent intent = new Intent(getApplicationContext(),Post.class);
                        intent.putExtra("emergencyphone",emergencyphonefromDB);
                        intent.putExtra("fullName",fullname);

                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString("phone",emergencyphonefromDB);
                        editor.putString("name",fullname);
                        editor.commit();

                        startActivity(intent);
                    }else{
                        mPassword.setError("Invalid Password");
                        mPassword.requestFocus();
                    }
                }
                else{
                    mEmail.setError("No such user exists");
                    mEmail.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
    }
}