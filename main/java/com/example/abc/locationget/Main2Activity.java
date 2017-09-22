package com.example.abc.locationget;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;

public class Main2Activity extends AppCompatActivity {


TextView txtid,txtname,txtplace_id,txtrefrence,txtlat,txtlng;
    ImageView   imageView;
    MapObject mo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);
        ButterKnife.bind(this);



        imageView=(ImageView)findViewById(R.id.Limgicon);
        txtlng=(TextView)findViewById(R.id.Llon);
        txtlat=(TextView)findViewById(R.id.Llat);
        txtrefrence=(TextView)findViewById(R.id.Lreference);
        txtid=(TextView)findViewById(R.id.Lid);
        txtplace_id=(TextView)findViewById(R.id.Lplace_id);
        txtname=(TextView)findViewById(R.id.Lname);
        init();
    }
    private void init(){


        try {


            Bundle bsingleitem = getIntent().getExtras();

            MapObject mo = (MapObject) bsingleitem.get("address");

            Log.e("here","Data=>"+mo);

            txtid.setText("Id :"+mo.getId());

            txtname.setText("Name :"+mo.getName());


            txtplace_id.setText("Place Id :"+mo.getPlace_id());

            txtrefrence.setText("Refrence :"+mo.getReference());


            txtlat.setText("Latitude :"+mo.getLat());

            txtlng.setText("Longitude :"+mo.getLon());

            Picasso.with(this).load(mo.getIcon()).into(imageView);

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
