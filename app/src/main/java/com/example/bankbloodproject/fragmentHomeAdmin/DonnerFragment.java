package com.example.bankbloodproject.fragmentHomeAdmin;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bankbloodproject.R;
import com.example.bankbloodproject.model.DonnerModel;
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
public class DonnerFragment extends Fragment {

    private DoonerAdapterRecyclerView adapter;
    private DatabaseReference reference;

    public DonnerFragment() {

        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        reference = FirebaseDatabase.getInstance().getReference();



        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_donner, container, false);
        // Inflate the layout for this fragment

        RecyclerView recycle = view.findViewById(R.id.recyclerView);
        recycle.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter=new DoonerAdapterRecyclerView(getActivity());
        recycle.setAdapter(adapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
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


}
