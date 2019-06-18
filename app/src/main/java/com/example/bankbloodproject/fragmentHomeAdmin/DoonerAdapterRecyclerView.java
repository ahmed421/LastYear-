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

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class DoonerAdapterRecyclerView extends RecyclerView.Adapter<DoonerAdapterRecyclerView.DoonerAdapterRecyclerViewHolder> {
    private List<DonnerModel> doonerModelList;
    private Context context;
    private String countryName;

    public DoonerAdapterRecyclerView(Context context) {
        this.context=context;
    }

    @NonNull
    @Override
    public DoonerAdapterRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new DoonerAdapterRecyclerViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_dooner,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull DoonerAdapterRecyclerViewHolder doonerAdapterRecyclerViewHolder, int i) {

        final DonnerModel donnerModel=doonerModelList.get(i);
        Log.i("onBind",donnerModel.getName());

        doonerAdapterRecyclerViewHolder.txtname.setText(donnerModel.getName());
        doonerAdapterRecyclerViewHolder.txtAge.setText(donnerModel.getAge());
        doonerAdapterRecyclerViewHolder.txtBloodGroup.setText(donnerModel.getBloodGroup());
        String latidute = donnerModel.getLatidute();
        String longtude = donnerModel.getLongtude();
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
        doonerAdapterRecyclerViewHolder.txtCity.setText(countryName);

            Log.i("city", countryName.toString());
        }
        if(donnerModel.getPhone_number()!=null){
        doonerAdapterRecyclerViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = ("tel:" + donnerModel.getPhone_number());
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
        doonerAdapterRecyclerViewHolder.txtPhone.setText(donnerModel.getPhone_number());
    }
    else{
        doonerAdapterRecyclerViewHolder.imageView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return doonerModelList!=null?doonerModelList.size():0;
    }

    public void setDataSource(List<DonnerModel> doonerModelList) {
        this.doonerModelList=doonerModelList;
    }

    class DoonerAdapterRecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView txtname,txtAge,txtPhone,txtBloodGroup,txtCity;
        ImageView imageView;
public DoonerAdapterRecyclerViewHolder(View view){
    super(view);
        txtname=itemView.findViewById(R.id.tvNameDooner);
        txtAge=itemView.findViewById(R.id.tvAgeDooner);
        txtBloodGroup=itemView.findViewById(R.id.tvBloodGroup);
    txtCity=itemView.findViewById(R.id.tvcity);
    txtPhone=itemView.findViewById(R.id.tvphone);
    imageView=itemView.findViewById(R.id.imvphone);



}

    }
}
