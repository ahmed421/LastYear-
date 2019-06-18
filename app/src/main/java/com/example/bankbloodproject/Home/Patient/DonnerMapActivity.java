package com.example.bankbloodproject.Home.Patient;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bankbloodproject.R;
import com.example.bankbloodproject.model.DonnerModel;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DonnerMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DatabaseReference reference;
    private DonnerModel childValue;
    private List<DonnerModel> doonerModelList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_donner_map);
        reference = FirebaseDatabase.getInstance().getReference();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
//        {
//            checkMapPermission();
//        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }
    private void checkMapPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},0);
            }

        }

    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if(grantResults!=null && grantResults.length>0){
//            if(grantResults[0]!=PackageManager.PERMISSION_GRANTED || grantResults[1]!=PackageManager.PERMISSION_GRANTED){
//                return;
//            }
//        }
//    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //get location...
        reference.child("donner").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                {
                     doonerModelList=new ArrayList<>();
                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                    for (DataSnapshot child:children) {
                        childValue = child.getValue(DonnerModel.class);
                        Log.i("c",childValue.toString());
                        String latidute = childValue.getLatidute();
                        String longtude = childValue.getLongtude();
                        Log.i("lat",latidute);
                        Log.i("long",longtude);
                        LatLng latLng = new LatLng(Double.parseDouble(latidute),Double.parseDouble(longtude));
                        addMyMarker(latLng);
                        // LatLng latLng = new LatLng(37.42199803188567,-122.08399968221784);
                        // LatLng latLng2 = new LatLng(37.42224619516356,-122.08660310134292);

                        //addMyMarker(latLng);
                        //addMyMarker(latLng2);
                        doonerModelList.add(childValue);


                        Log.i("dataSnapshot",dataSnapshot.getValue().toString());
                    }
//


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("cancel", "onCancelled: dataBase " );

            }
        });

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }


    private void addMyMarker(LatLng position) {
        MarkerOptions options = new MarkerOptions();
        options.title(childValue.getName())
                .snippet(childValue.getPhone_number())
                // .draggable(true)
                //.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_donner))
                // .snippet(childValue.getBloodGroup())
                .position(position);
        mMap.addMarker(options);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(position,16f);
        mMap.moveCamera(cameraUpdate);
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                LatLng latlng = marker.getPosition();
                double ln = latlng.longitude;
                double la = latlng.latitude;
                //show donner details.....
                AlertDialog.Builder builder = new AlertDialog.Builder(DonnerMapActivity.this);
                builder.setView(R.layout.donner_view);
                AlertDialog dialog = builder.show();
                ImageView img=dialog.findViewById(R.id.imageView);
                TextView name= dialog.findViewById(R.id.nametv);
                TextView phone= dialog.findViewById(R.id.phonetv);
                //TextView city= dialog.findViewById(R.id.citytv);
                TextView blood= dialog.findViewById(R.id.bloodgrouptv);
                // TextView age= dialog.findViewById(R.id.agetv);
                img.setImageResource(R.drawable.ic_donner);
                for (int i=0;i<doonerModelList.size();i++)
                {
                    DonnerModel donnerModel = doonerModelList.get(i);
                    double lati = Double.parseDouble(donnerModel.getLatidute());
                    double longi = Double.parseDouble(donnerModel.getLongtude());
                    Log.i("lat",""+lati);
                    Log.i("lat",""+longi);
                    Log.i("lat",""+ln);
                    Log.i("lat",""+la);
                    if (ln==longi&&la==lati) {
                        name.setText(donnerModel.getName());
                        phone.setText(donnerModel.getPhone_number());
                        blood.setText(donnerModel.getBloodGroup());
                    }

                }

//                name.setText(childValue.getName());
//                phone.setText(childValue.getPhone_number());
//                blood.setText(childValue.getBloodGroup());
                //age.setText(""+childValue.getAge());



                return false;
            }
        });
    }
}
