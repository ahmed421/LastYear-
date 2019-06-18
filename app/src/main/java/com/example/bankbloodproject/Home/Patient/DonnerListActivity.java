package com.example.bankbloodproject.Home.Patient;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.bankbloodproject.R;
import com.example.bankbloodproject.fragmentHomeAdmin.DoonerAdapterRecyclerView;
import com.example.bankbloodproject.model.DonnerModel;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class DonnerListActivity extends AppCompatActivity  {
    private MapView mMapView;
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";

    private DoonerAdapterRecyclerView adapter;
    private DatabaseReference reference;
    private Button btnMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donner_list);
//        initGoogleMap(savedInstanceState);
        btnMap=findViewById(R.id.btnmap);


        reference = FirebaseDatabase.getInstance().getReference();

        RecyclerView recycle = findViewById(R.id.user_list_recycler_view);
        recycle.setLayoutManager(new LinearLayoutManager(this));
        adapter=new DoonerAdapterRecyclerView(this);
        recycle.setAdapter(adapter);

       //go to map ...
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DonnerListActivity.this,DonnerMapActivity.class));
            }
        });
    }

//    private void initGoogleMap(Bundle savedInstanceState)
//    {
//        // *** IMPORTANT ***
//        // MapView requires that the Bundle you pass contain _ONLY_ MapView SDK
//        // objects or sub-Bundles.
//        Bundle mapViewBundle = null;
//        if (savedInstanceState != null) {
//            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
//        }
//        mMapView =  findViewById(R.id.user_list_map);
//        mMapView.onCreate(mapViewBundle);
//
//        mMapView.getMapAsync(this);
//    }
//
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//
//        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
//        if (mapViewBundle == null) {
//            mapViewBundle = new Bundle();
//            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
//        }
//
//        mMapView.onSaveInstanceState(mapViewBundle);
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        mMapView.onResume();
//    }
//
    @Override
    public void onStart() {
        super.onStart();
//        mMapView.onStart();
        viewAllDooner();

    }
    private void viewAllDooner() {
        reference.child("donner").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                {
                    List<DonnerModel> doonerModelList=new ArrayList<>();
                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                    for (DataSnapshot child:children) {
                        DonnerModel childValue = child.getValue(DonnerModel.class);
                        String latidute = childValue.getLatidute();
                        String longtude = childValue.getLongtude();
//                        Log.i("lat",latidute);
//                        Log.i("long",longtude);
                        doonerModelList.add(childValue);

                        Log.i("dataSnapshot",dataSnapshot.getValue().toString());
                    }
//

                    adapter.setDataSource(doonerModelList);
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("cancel", "onCancelled: dataBase " );

            }
        });
    }

//
//    @Override
//    public void onStop() {
//        super.onStop();
//        mMapView.onStop();
//    }
//
//    @Override
//    public void onMapReady(GoogleMap map) {
//      //  map.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
//    }
//
//    @Override
//    public void onPause() {
//        mMapView.onPause();
//        super.onPause();
//    }
//
//    @Override
//    public void onDestroy() {
//        mMapView.onDestroy();
//        super.onDestroy();
//    }
//
//    @Override
//    public void onLowMemory() {
//        super.onLowMemory();
//        mMapView.onLowMemory();
//    }
//
//    @Override
//    public void onPointerCaptureChanged(boolean hasCapture) {
//
//    }
//}
}