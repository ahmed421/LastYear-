package com.example.bankbloodproject.fragmentHomeAdmin;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bankbloodproject.R;
import com.example.bankbloodproject.model.DonnerModel;
import com.example.bankbloodproject.model.patientModel;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class PatientAdapterRecyclerView extends RecyclerView.Adapter<PatientAdapterRecyclerView.PatientAdapterRecyclerViewHolder> {
    private List<patientModel> patientModelList ;
    private Context context;
    private String countryName;

    public PatientAdapterRecyclerView(Context context) {
this.context=context;
    }

    @NonNull
    @Override
    public PatientAdapterRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PatientAdapterRecyclerViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_dooner,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PatientAdapterRecyclerViewHolder patientAdapterRecyclerViewHolder, int i) {
        final patientModel patientModel=patientModelList.get(i);
       Log.i("onBind",patientModel.getName());
        patientAdapterRecyclerViewHolder.txtname.setText(patientModel.getName());
        patientAdapterRecyclerViewHolder.txtAge.setText(patientModel.getAge());
        patientAdapterRecyclerViewHolder.txtBloodGroup.setText(patientModel.getBloodGroup());
        String latidute = patientModel.getLatidute();
        String longtude = patientModel.getLongtude();
        //get address from lat and long....
        if (latidute!=null && longtude!=null) {
            String address = null;
            try {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                List<android.location.Address> addressList = geocoder.getFromLocation(Double.parseDouble(latidute)
                        , Double.parseDouble(longtude), 1);
                if (addressList.size() > 0) {
                    Address address1 = addressList.get(0);
                    address = address1.getAddressLine(0);

                    //show cit....
                    countryName = address1.getLocality();


                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if (countryName!=null) {
            patientAdapterRecyclerViewHolder.txtCity.setText(countryName);

            Log.i("city", countryName.toString());
        }
        if(patientModel.getPhone_number()!=null){
            patientAdapterRecyclerViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String number = ("tel:" + patientModel.getPhone_number());
                    Intent mIntent = new Intent(Intent.ACTION_CALL);
                    mIntent.setData(Uri.parse(number));
// Here, thisActivity is the current activity
                    if (ContextCompat.checkSelfPermission(context,
                            Manifest.permission.CALL_PHONE)
                            != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions((Activity) context,
                                new String[]{Manifest.permission.CALL_PHONE},
                                1);

                        // MY_PERMISSIONS_REQUEST_CALL_PHONE is an
                        // app-defined int constant. The callback method gets the
                        // result of the request.
                    } else {
                        //You already have permission
                        try {
                            context.startActivity(mIntent);
                        } catch(SecurityException e) {
                            e.printStackTrace();
                        }
                    }
                }

            });
            patientAdapterRecyclerViewHolder.txtPhone.setText(patientModel.getPhone_number());
        }
        else{
            patientAdapterRecyclerViewHolder.imageView.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return patientModelList!=null?patientModelList.size():0;
    }

    public void setDataSource(List<patientModel> patientModelList) {
        this.patientModelList=patientModelList;
    }

    class PatientAdapterRecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView txtname,txtAge,txtPhone,txtBloodGroup,txtCity;
        ImageView imageView;
        public PatientAdapterRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            txtname=itemView.findViewById(R.id.tvNameDooner);
            txtAge=itemView.findViewById(R.id.tvAgeDooner);
            txtBloodGroup=itemView.findViewById(R.id.tvBloodGroup);
            txtCity=itemView.findViewById(R.id.tvcity);
            txtPhone=itemView.findViewById(R.id.tvphone);
            imageView=itemView.findViewById(R.id.imvphone);

        }
    }
}
