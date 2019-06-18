package com.example.bankbloodproject.Home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Geocoder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import  android.location.Address;

import com.example.bankbloodproject.R;
import com.example.bankbloodproject.model.DonnerDetails;
import com.example.bankbloodproject.model.DonnerModel;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;

public class HomeDonnerActivity extends AppCompatActivity {
    private Spinner bloodGroupSpinner;
    // private EditText donnerLocation;
    TextView showlocation;
    Button btnsave,btnpicker;
    int place_picker_request=1;
    private DatabaseReference mFirebaseDatabase;
    private String latitude,longtude;
    private String bloodgroup;
    private SharedPreferences sharedPreferences,sharedPreferences2;
    private String donnerObject;
    private String mResponse;
    private String countryName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_donner);
        mFirebaseDatabase =  FirebaseDatabase.getInstance().getReference().child("donner");

        //  FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        //  final String uid = currentUser.getUid();


        bloodGroupSpinner=findViewById(R.id.spinnerblood);
        // donnerLocation=findViewById(R.id.edtlocation);

        //textview to show the lat and long for donner location
        showlocation=findViewById(R.id.edtlocation);
        //get donner location using place picker
        btnpicker=findViewById(R.id.btn_picker);
        btnpicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PlacePicker.IntentBuilder builder=new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(HomeDonnerActivity.this),place_picker_request);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });




        //save donner location and its blood group...
        btnsave=findViewById(R.id.btnsave);
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get bloodtype from spinner
                 bloodgroup = bloodGroupSpinner.getSelectedItem().toString();
                 Log.i("blood",bloodgroup);
                //get donnerlocation from edittext
               // String donnerlocation = donnerLocation.getText().toString();
                if (!TextUtils.isEmpty(bloodgroup)&& !TextUtils.isEmpty(showlocation.getText().toString()))
                {

                    // get key of current donner from sharedprefrences....
//                     sharedPreferences = getSharedPreferences("u", MODE_PRIVATE);
//                     String key1 = sharedPreferences.getString("cid", "null");
//                     Log.i("key",key1);
                    sharedPreferences = getSharedPreferences("donner", MODE_PRIVATE);
                    String key1 = sharedPreferences.getString("key", "null");
                    Log.i("key",key1);



                    try {
                        File f = new File(HomeDonnerActivity.this.getFilesDir().getPath() + "/" + "donner.json");
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





                    Log.i("object",mResponse);
                    // convert donnerObject from String to object....
                    Gson gson=new Gson();
                    DonnerModel donnerModel = gson.fromJson(mResponse, DonnerModel.class);
                    //set data into class.....
                   donnerModel.setBloodGroup(bloodgroup);
                    donnerModel.setLatidute(latitude);
                    donnerModel.setLongtude(longtude);
                   // DonnerModel donnerModel=new DonnerModel(bloodgroup,latitude,longtude);
                    //set data into firebase...
                    mFirebaseDatabase.child(key1).setValue(donnerModel);

                    Toast.makeText(HomeDonnerActivity.this, "Data added....", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(HomeDonnerActivity.this, "all data require..", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    //get donner location..


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==place_picker_request)
        {
            if (resultCode==RESULT_OK)
            {
                Place place=PlacePicker.getPlace(data,this);
                StringBuilder stringBuilder=new StringBuilder();
                 latitude = String.valueOf(place.getLatLng().latitude);
                 longtude = String.valueOf(place.getLatLng().longitude);
                stringBuilder.append("LATITUDE :");
                stringBuilder.append(latitude);
                stringBuilder.append("\n");
                stringBuilder.append("LONGTUDE :");
                stringBuilder.append(longtude);

                //get address from lat and long....
                String address=null;
                try
                {
                    Geocoder geocoder=new Geocoder(this, Locale.getDefault());
                    List<android.location.Address> addressList=  geocoder.getFromLocation(Double.parseDouble(latitude)
                            ,Double.parseDouble(longtude),1);
                    if (addressList.size()>0)
                    {
                        Address address1 = addressList.get(0);
                        address=address1.getAddressLine(0);

                        //show cit....
                         countryName = address1.getLocality();




                    }
                }

                catch (IOException ex)
                {
                      ex.printStackTrace();
                }
                showlocation.setText(countryName);


            }
        }
    }
}
