package com.example.bankbloodproject.Home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.example.bankbloodproject.Home.Patient.DonnerListActivity;
import com.example.bankbloodproject.Home.Patient.AddDonnerDetailsActivity;
import com.example.bankbloodproject.Home.Patient.SendRequestActivity;
import com.example.bankbloodproject.R;
import com.example.bankbloodproject.auth.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    Button patientbtn,sendrequestbtn,searchbtn,donnerbtn;
    Spinner spinnerbloodgroup;





    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        firebaseAuth=FirebaseAuth.getInstance();
        patientbtn=findViewById(R.id.patientbtn);
        sendrequestbtn=findViewById(R.id.btnsendrequest);
        donnerbtn=findViewById(R.id.donnerbtn);
        searchbtn=findViewById(R.id.btnseaerch);
        SharedPreferences sharedPreferences = getSharedPreferences("usertype", MODE_PRIVATE);
        boolean b = sharedPreferences.getBoolean("ut", false);
//        if (b)
//        {
//            //display donner tool and hide patient tool
//
//                searchdonnerbtn.setVisibility(View.INVISIBLE);
//                sendrequestbtn.setVisibility(View.INVISIBLE);
//
//
//        }
//        else
//        {
//
//                donnertxt.setVisibility(View.INVISIBLE);
//
//
//        }

        patientbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

          startActivity(new Intent(HomeActivity.this, AddDonnerDetailsActivity.class));

            }
        });
        sendrequestbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(HomeActivity.this, SendRequestActivity.class));

            }
        });


//        donnerbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(HomeActivity.this, HomeDonnerActivity.class));
//
//            }
//        });
        donnerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, HomeDonnerActivity.class));
            }
        });
        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, DonnerListActivity.class));

            }
        });


        //log out
//        logout=findViewById(R.id.btnlogout);
//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                firebaseAuth.signOut();
//                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
//                finish();
//            }
//        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.signout:
                firebaseAuth.signOut();
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                finish();
                break;


        }



        return super.onOptionsItemSelected(item);
    }
}
