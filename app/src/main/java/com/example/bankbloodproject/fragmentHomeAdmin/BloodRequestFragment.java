package com.example.bankbloodproject.fragmentHomeAdmin;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bankbloodproject.R;
import com.example.bankbloodproject.model.patientModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BloodRequestFragment extends Fragment {

    private bloodRequstAdapter adapter;
    private DatabaseReference reference;

    public BloodRequestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        reference = FirebaseDatabase.getInstance().getReference();

        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_blood_request, container, false);
        // Inflate the layout for this fragment

        RecyclerView recycle = view.findViewById(R.id.recyclerView);
        recycle.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter=new bloodRequstAdapter(getContext());
        recycle.setAdapter(adapter);
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        viewAllPatient();
    }
    private void viewAllPatient() {
        reference.child("patient").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<patientModel> patientModelList=new ArrayList<>();
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child:children) {
                    patientModel childValue = child.getValue(patientModel.class);
                    patientModelList.add(childValue);
                }


                adapter.setDataSource(patientModelList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("cancel", databaseError.toString() );
                Toast.makeText(getContext(), databaseError.toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }

}
