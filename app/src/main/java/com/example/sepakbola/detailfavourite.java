package com.example.sepakbola;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import io.realm.Realm;

public class detailfavourite extends AppCompatActivity {


    Realm realm;
    RealmHelper realmHelper;
    ModelRealm movieModel;

    Bundle extras;
    String title;
    String date;
    String deskripsi;
    String path;

    TextView Moviejudul;
    ImageView imgmovie;
    TextView Moviedesc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_movie);
        extras = getIntent().getExtras();
        Moviejudul = findViewById(R.id.Moviejudul);
        Moviedesc = findViewById(R.id.moviedesc);
        imgmovie = findViewById(R.id.imgmovie);



        if (extras != null) {
            title = extras.getString("judul");
            date = extras.getString("date");
            deskripsi = extras.getString("deskripsi");
            path = extras.getString("path");
            Moviejudul.setText(title);
            Moviedesc.setText(deskripsi);
            Glide.with(com.example.sepakbola.detailfavourite.this)
                    .load(path)
                    .override(Target.SIZE_ORIGINAL)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(imgmovie);

        }



    }
}

