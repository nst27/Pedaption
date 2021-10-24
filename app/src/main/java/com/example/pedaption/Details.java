package com.example.pedaption;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static java.lang.System.load;

public class Details extends AppCompatActivity {
    ImageView imgview;
    TextView nameview,description,type,gender,address,userName,phoneNo;
    Button btn;
    Model model = null;
    Geocoder geocoder;
    List<Address> addresses;
    double latitude;
    double longitude;
    String result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        geocoder = new Geocoder(this, Locale.getDefault());

        nameview = findViewById(R.id.displayName);
        imgview = findViewById(R.id.displayImg);
        description = findViewById(R.id.displayDesc);
        type = findViewById(R.id.type);
        gender = findViewById(R.id.gender);
        address = findViewById(R.id.address);
        btn = findViewById(R.id.btn);
        userName = findViewById(R.id.userName);
        phoneNo = findViewById(R.id.userphone);



        final Object object = getIntent().getSerializableExtra("title");
        if(object instanceof Model){
            model = (Model) object;
        }

        if(model != null){
            nameview.setText(model.getName());
            description.setText(model.getPetDescription());
            type.setText(model.getType());
            gender.setText(model.getPetGender());
            address.setText(model.getPetAddress());
            phoneNo.setText(model.getPhone());
            userName.setText(model.getFullName());
            String imgURL = model.getImageUrl();
            Picasso.get().load(imgURL).into(imgview);

            String val = model.getPetAddress();

            try {
                addresses = geocoder.getFromLocationName(val.trim(),1);
                //Log.d("dasd",""+addresses.get(0));
                if (addresses != null && addresses.size() > 0) {
                    Address address = addresses.get(0);
                    StringBuilder sb = new StringBuilder();
                    latitude = (address.getLatitude());
                    longitude = (address.getLongitude());

                    Log.d("latitude",""+latitude);
                    Log.d("Longitute",""+longitude);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),MapsActivity.class);
                Toast.makeText(getApplicationContext(),"click",Toast.LENGTH_SHORT);
                String lan,lon;
                lan = Double.toString(latitude);
                lon = Double.toString(longitude);
                i.putExtra("latitude",lan);
                i.putExtra("longitude",lon);
                startActivity(i);
            }
        });
    }
}