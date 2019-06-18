package com.example.bankbloodproject.auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.bankbloodproject.Home.HomeActivity;
import com.example.bankbloodproject.Home.HomeAdmin2;
import com.example.bankbloodproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mauth;
    EditText emailText ,passText;
    Button btnReg;
    private ProgressBar progressBar;
    private FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mauth = FirebaseAuth.getInstance();

        emailText=findViewById(R.id.editEmailog);
        passText=findViewById(R.id.editPass);
        progressBar =  findViewById(R.id.progressBar);

        btnReg =findViewById(R.id.btnReg);
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

    }
    public void sign(View view) {


        //login email an pass
        String Email = emailText.getText().toString();
        String pass = passText.getText().toString();

        if (TextUtils.isEmpty(Email)){
            Toast.makeText(LoginActivity.this, "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(LoginActivity.this, "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }


        progressBar.setVisibility(View.VISIBLE);




        if(Email.equals("admin@admin.com") &&pass.equals("admin123")){

            mauth.signInWithEmailAndPassword(Email,pass).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {


                    progressBar.setVisibility(View.GONE);

                    // If sign in fails, display a message to the user.

                    if (!task.isSuccessful()) {
                        
                        // If Authentication failed.
                        Toast.makeText(LoginActivity.this, "Invalid Login", Toast.LENGTH_LONG).show();
                    }else {

                     startActivity(new Intent(LoginActivity.this, HomeAdmin2.class));
                        finish();
                    }
                }
            });
        }else {

            //authenticate user
            mauth.signInWithEmailAndPassword(Email,pass).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {


                    progressBar.setVisibility(View.GONE);

                    // If sign in fails, display a message to the user.

                    if (!task.isSuccessful()) {
                        // If Authentication failed.

                        Toast.makeText(LoginActivity.this, "Invalid Login", Toast.LENGTH_LONG).show();
                    }else {

                        currentUser= mauth.getCurrentUser();
                        String uid = currentUser.getUid();
                        SharedPreferences userid = getSharedPreferences("u", MODE_PRIVATE);
                        userid.edit().putString("cid",uid).commit();
                        Log.i("id2",uid);
                        String cid = userid.getString("cid", null);

                        Log.i("id2",cid);
                       startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                        finish();
                    }
                }
            });

        }

    }



}
