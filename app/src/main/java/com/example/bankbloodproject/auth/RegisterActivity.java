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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.bankbloodproject.Home.HomeActivity;
import com.example.bankbloodproject.Home.HomeDonnerActivity;
import com.example.bankbloodproject.R;
import com.example.bankbloodproject.model.DonnerModel;
import com.example.bankbloodproject.model.patientModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    private DatabaseReference mFirebaseDatabasepatient,mFirebaseDatabasedonner;
    private FirebaseAuth auth;
    FirebaseUser currentUser;
    private EditText name1,email1,passwd,repasswd,contact,age;
    private ProgressBar mprogress;
    private RadioGroup radioGroupgen,radioGrouptype;
    private RadioButton radioButtongender,radioButtontype;
    private int radiotypeId;
    private String typetxt;
    private SharedPreferences sharedPreferencesdonner,sharedPreferencespatient;
    private String idonner,idpatient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mFirebaseDatabasepatient =  FirebaseDatabase.getInstance().getReference("patient");
        mFirebaseDatabasedonner =  FirebaseDatabase.getInstance().getReference("donner");

        name1 =  findViewById(R.id.editName);
        email1 =  findViewById(R.id.editEmail);
        passwd =  findViewById(R.id.editPassreg);
        contact =  findViewById(R.id.editCont);
        age=findViewById(R.id.editage);
        repasswd=findViewById(R.id.editrepass);

        radioGroupgen =  findViewById(R.id.radioGen);
        radioGrouptype =  findViewById(R.id.radiotype);

        auth = FirebaseAuth.getInstance();
        mprogress =  findViewById(R.id.progressBarreg);


    }

    public void signUp(View view) {

        register();
    }

    private void register() {

        final String name = name1.getText().toString().trim();
        final String email = email1.getText().toString().trim();
        final String password = passwd.getText().toString().trim();
        final String repassword = repasswd.getText().toString().trim();
        final String phone_number = contact.getText().toString().trim();
        final String age1 = age.getText().toString().trim();


        int radiogenId=radioGroupgen.getCheckedRadioButtonId();
        radioButtongender=findViewById(radiogenId);
        String gendertxt = (String) radioButtongender.getText();

        radiotypeId=radioGrouptype.getCheckedRadioButtonId();
        radioButtontype=findViewById(radiotypeId);
        typetxt = (String) radioButtontype.getText();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)||TextUtils.isEmpty(repassword)|| !password.equals(repassword) || TextUtils.isEmpty(phone_number) ||TextUtils.isEmpty(age1)
         || radiogenId==-1 ||radiotypeId==-1 )
        {
            Toast.makeText(RegisterActivity.this, "Please check  all  data.", Toast.LENGTH_SHORT).show();
            repasswd.setError("Error RePassword");
        }

        else


        {

            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        currentUser= auth.getCurrentUser();
                        String uid = currentUser.getUid();
                        SharedPreferences userid = getSharedPreferences("u", MODE_PRIVATE);
                        userid.edit().putString("cid",uid).commit();
                        Log.i("id",uid);
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        Toast.makeText(RegisterActivity.this, "Register success", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else {
                        Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(RegisterActivity.this, "message"+e.getMessage(), Toast.LENGTH_LONG).show();

                }
            });
            //register...
            switch (typetxt)
            {
                case "Donner":
                    //save user type
//                    SharedPreferences sharedPreferences = getSharedPreferences("usertype", MODE_PRIVATE);
//                    sharedPreferences.edit().putBoolean("ut",true).commit();
                    //save key of current donner in sharedprefrences.....
                     idonner = mFirebaseDatabasedonner.push().getKey();
                     Log.i("key",idonner);
                     sharedPreferencesdonner = getSharedPreferences("donner", MODE_PRIVATE);
                     sharedPreferencesdonner.edit().putString("key",idonner).commit();
//                    sharedPreferencesdonner = getSharedPreferences("u", MODE_PRIVATE);
//                    idonner = sharedPreferencesdonner.getString("cid", "null");
//                    Log.i("key",idonner);

                    //convert object to string using Gson and save it in files...
                    DonnerModel donnerModel=new DonnerModel(idonner,name,email,password,phone_number,age1,gendertxt );
                    Gson gson=new Gson();
                    String donnerObject = gson.toJson(donnerModel);
                    Log.i("name",donnerObject);


                    try {
                        FileWriter file = new FileWriter(this.getFilesDir().getPath() + "/" + "donner.json");
                        file.write(donnerObject);
                        file.flush();
                        file.close();
                    } catch (IOException e) {
                        Log.e("TAG", "Error in Writing: " + e.getLocalizedMessage());
                    }


                    //set data into firebase....
                    mFirebaseDatabasedonner.child(idonner).setValue(donnerModel);

                    Toast.makeText(this, "register successfly for donner..", Toast.LENGTH_SHORT).show();
                    Log.i("register",donnerModel.toString());

                    break;
                case "Patient":
                    //save user type
                    SharedPreferences sharedPreferences2 = getSharedPreferences("usertype", MODE_PRIVATE);
                    sharedPreferences2.edit().putBoolean("ut",false).commit();
                    //get key...
                    idpatient = mFirebaseDatabasepatient.push().getKey();
                    Log.i("key",idpatient);
                    sharedPreferencespatient = getSharedPreferences("patient", MODE_PRIVATE);
                    sharedPreferencespatient.edit().putString("key",idpatient).commit();
                    sharedPreferencespatient = getSharedPreferences("userid", MODE_PRIVATE);
                    idonner = sharedPreferencespatient.getString("cid", "null");
                    Log.i("key",idonner);

                    patientModel patientModel=new patientModel(idpatient,name,email,password,phone_number,age1,gendertxt );
                    Gson gsonPAtient=new Gson();
                    String patientObject = gsonPAtient.toJson(patientModel);


                    try {
                        FileWriter file = new FileWriter(this.getFilesDir().getPath() + "/" + "patient.json");
                        file.write(patientObject);
                        file.flush();
                        file.close();
                    } catch (IOException e) {
                        Log.e("TAG", "Error in Writing: " + e.getLocalizedMessage());
                    }


                    mFirebaseDatabasepatient.child(idpatient).setValue(patientModel);
                    Toast.makeText(this, "register successfly for patient..", Toast.LENGTH_SHORT).show();
                    break;

            }

        }







    }

}
