package com.example.sepakbola;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class DataMovie extends AppCompatActivity {

    Realm realm;
    RealmHelper realmHelper;
    ModelRealm movieModel;

    Bundle extras;
    String title;
    String date;
    String deskripsi;
    String path;
    String id;

    TextView Moviejudul;
    ImageView imgmovie;
    TextView Moviedesc;
    Button Movietanda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_movie);
        extras = getIntent().getExtras();
        Moviejudul = findViewById(R.id.Moviejudul);
        Moviedesc = findViewById(R.id.moviedesc);
        imgmovie = findViewById(R.id.imgmovie);
        Movietanda = findViewById(R.id.Movietanda);



        if (extras != null) {
            title = extras.getString("judul");
            id = extras.getString("id");
            date = extras.getString("date");
            deskripsi = extras.getString("deskripsi");
            path = extras.getString("path");
            Moviejudul.setText(title);
            Moviedesc.setText(deskripsi);
            Glide.with(DataMovie.this)
                    .load(path)
                    .override(Target.SIZE_ORIGINAL)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(imgmovie);

        }
        Realm.init(DataMovie.this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(configuration);

        Movietanda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieModel = new ModelRealm();
                movieModel.setStrDescriptionEN(deskripsi);
                movieModel.setStrTeam(title);
                movieModel.setStrTeamBadge(path);


                realmHelper = new RealmHelper(realm);
                realmHelper.save(movieModel);

            }
        });
    }


}

