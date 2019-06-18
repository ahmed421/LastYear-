package com.example.bankbloodproject.Home.Patient;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bankbloodproject.R;
import com.example.bankbloodproject.model.patientModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class SendRequestActivity extends AppCompatActivity {
    private Spinner bloodGroupSpinner;
    EditText bloodQuantity;
    private String mResponse;
    private DatabaseReference mFirebaseDatabase;
    Button sendRequest;
    private int blood;
    private String bloodGroup,key1;
    private com.example.bankbloodproject.model.patientModel patientModel;
    private String bloodgroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_request);
        //get object from firebase
        mFirebaseDatabase =  FirebaseDatabase.getInstance().getReference().child("patient");
        //get id from xml
        bloodGroupSpinner=findViewById(R.id.spinnerblood);
        bloodQuantity=findViewById(R.id.bloodQuantity1);
        sendRequest=findViewById(R.id.btnsearchdonner);
        // get data from user.....
        bloodgroup = bloodGroupSpinner.getSelectedItem().toString().trim();

        try {
            blood = Integer.parseInt(bloodQuantity.getText().toString().trim());
            Log.i("blood",""+blood);
        }
        catch (Exception ex)
        {
            Log.i("blood",ex.getMessage());
        }



        sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (!TextUtils.isEmpty(bloodGroup) &&bloodQuantity.length()>0) {
                    //get key..
                    SharedPreferences sharedPreferences = getSharedPreferences("patient", MODE_PRIVATE);
                    key1 = sharedPreferences.getString("key", "null");
                    Log.i("key", key1);
//                SharedPreferences sharedPreferences = getSharedPreferences("userid", MODE_PRIVATE);
//                key1 = sharedPreferences.getString("cid", "null");
//                Log.i("key",key1);
                    //get patient object...
                    try {
                        File f = new File(SendRequestActivity.this.getFilesDir().getPath() + "/" + "patient.json");
                        //check whether file exists
                        FileInputStream is = new FileInputStream(f);
                        int size = is.available();
                        byte[] buffer = new byte[size];
                        is.read(buffer);
                        is.close();
                        mResponse = new String(buffer);
                    } catch (IOException e) {
                        Log.e("TAG", "Error in Reading: " + e.getLocalizedMessage());

                    }
                    Log.i("class", mResponse);
                    Gson gson = new Gson();
                    patientModel = gson.fromJson(mResponse, patientModel.class);
                    // Log.i("class2",patientModel.toString());

                    //get patient bloodgroup
                    //get bloodQuantity and save it in firebase....
                    // blood = Long.parseLong(bloodQuantity.getText().toString());


                    patientModel.setBloodQuantity(10);
                    patientModel.setBloodGroup(bloodgroup);
                    mFirebaseDatabase.child(key1).setValue(patientModel);
                    Toast.makeText(SendRequestActivity.this, "Request Send Successfuly....", Toast.LENGTH_SHORT).show();

              //  }
//                else {
//                    Toast.makeText(SendRequestActivity.this, "all data are requird ..", Toast.LENGTH_SHORT).show();
//                }
            }
        });

    }
}
