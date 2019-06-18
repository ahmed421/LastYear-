package com.example.bankbloodproject.Home;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bankbloodproject.R;
import com.example.bankbloodproject.auth.LoginActivity;
import com.example.bankbloodproject.fragmentHomeAdmin.BloodRequestFragment;
import com.example.bankbloodproject.fragmentHomeAdmin.DonnerFragment;
import com.example.bankbloodproject.fragmentHomeAdmin.PatientFragment;
import com.example.bankbloodproject.fragmentHomeAdmin.ProfileFragment;
import com.example.bankbloodproject.model.BloodModel;
import com.example.bankbloodproject.model.DonnerModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class HomeAdmin2 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private DatabaseReference reference;
    private FirebaseAuth firebaseAuth;
    private Spinner blgrp;
    EditText edtcountity;
    private DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowAddDoonerDialog();
            }
        });

         drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.flContent,new ProfileFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_profile);
        }



        firebaseAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("Blood");
    }
    private void ShowAddDoonerDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(HomeAdmin2.this);
        builder.setView(R.layout.add_donner_dialog).setPositiveButton("Add Blood ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SaveDataBase();
                Toast.makeText(HomeAdmin2.this, "Added", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = builder.show();
         blgrp = (Spinner) dialog.findViewById(R.id.spinnerblood);
         edtcountity=dialog.findViewById(R.id.edtcountity); }
    private void SaveDataBase() { final String bloodl = blgrp.getSelectedItem().toString().trim();
        int count = Integer.parseInt(edtcountity.getText().toString().trim());
        BloodModel bloodModel=new BloodModel();
        if (bloodl.equals("A+")) {
            reference.child("A+").setValue(count);
            Toast.makeText(this, "Added Blood", Toast.LENGTH_SHORT).show();
            Log.i("blood",bloodl);
        }
        else if (bloodl.equals("B+")) {
            reference.child("B+").setValue(count);
        } else if (bloodl.equals("A-")) {
            reference.child("A-").setValue(count);
        } else if (bloodl.equals("B-")) {
            reference.child("B-").setValue(count);
        }       else if (bloodl.equals("O+")) {
            reference.child("O+").setValue(count);
        }       else if (bloodl.equals("O-")) {
            reference.child("O-").setValue(count);
        }      else if (bloodl.equals("AB+")) {
            reference.child("AB+").setValue(count);
        }        else if (bloodl.equals("AB-")) {
            reference.child("AB-").setValue(count);

        }else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            Log.i("message", String.valueOf(count));
        }
        }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_admin2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.


        switch (item.getItemId()) {
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.flContent,new ProfileFragment()).commit();

                break;

            case R.id.nav_bloodrequest:
                getSupportFragmentManager().beginTransaction().replace(R.id.flContent,new BloodRequestFragment()).commit();

                break;
            case R.id.nav_donner:
                getSupportFragmentManager().beginTransaction().replace(R.id.flContent,new DonnerFragment()).commit();

                break;

            case R.id.patient:
                getSupportFragmentManager().beginTransaction().replace(R.id.flContent,new PatientFragment()).commit();

                break;

            case R.id.nav_signpout:
                firebaseAuth.signOut();
                startActivity(new Intent(HomeAdmin2.this, LoginActivity.class));
                finish();


                break;

        }
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}
