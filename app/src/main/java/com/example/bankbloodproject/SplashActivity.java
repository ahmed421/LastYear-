package com.example.bankbloodproject;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.bankbloodproject.Home.HomeActivity;
import com.example.bankbloodproject.Home.HomeAdmin2;
import com.example.bankbloodproject.auth.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {
    private String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);



//      get the current user
        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();


        new Handler().postDelayed(new Runnable() {
                                      @Override
                                      public void run() {

                                          if (currentUser != null) {


                                                  uid = currentUser.getUid();


                                                  if (uid.equals("XzQyFGL8VVUYBuZCeQpmTg3iMF32"))
                                                      startActivity(new Intent(SplashActivity.this, HomeAdmin2.class));
                                                      else
                                                          startActivity(new Intent(SplashActivity.this,HomeActivity.class));

                                              }
                                              else
                                                  startActivity(new Intent(SplashActivity.this,LoginActivity.class));


                                              finish();

                                          }

                                  }
                ,2000);
    }
}

