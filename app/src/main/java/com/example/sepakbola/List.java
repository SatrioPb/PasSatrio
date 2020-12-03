package com.example.sepakbola;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class List extends AppCompatActivity {

     RecyclerView recyclerView;
     DataAdapter adapter;
     ArrayList<Model> DataArrayList;
     ImageView tambah_data;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        recyclerView = (RecyclerView) findViewById(R.id.rvdata);
        dialog = new ProgressDialog(List.this);
        //addData();
addDataOnline();
        dialog.setMessage("Sedang memproses data");
        dialog.show();

    }

    void addData() {
        //offline, isi data offline dulu
        DataArrayList = new ArrayList<>();
        Model data1 = new Model();
        data1.strTeam("Judul Film");
        data1.strTeamBadge("https://image.tmdb.org/t/p/w500/k68nPLbIST6NP96JmTxmZijEvCA.jpg");
        data1.strDescriptionEN("Deskripsi Film disini");
        data1.setVote_count(100);
        DataArrayList.add(data1);


        adapter = new DataAdapter(DataArrayList, new DataAdapter.Callback() {
            @Override
            public void onClick(int position) {

            }

            @Override
            public void test() {

            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(List.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);




    }
    void addDataOnline(){
        AndroidNetworking.get("https://www.thesportsdb.com/api/v1/json/1/search_all_teams.php?l=English%20Premier%20League")
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("hasiljson","onResponse: "+response.toString());
                        DataArrayList  = new ArrayList<>();
                        Model modelku;
                        try {
                            Log.d("hasiljson", "onResponse: " + response.toString());
                            JSONArray jsonArray = response.getJSONArray("teams");
                            Log.d("hasiljson2", "onResponse: " + jsonArray.toString());
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                modelku=new Model();
                                modelku.setIdTeam(jsonObject.getInt("idTeam"));
                                modelku.setStrTeam(jsonObject.getString("strTeam"));
                                modelku.setStrDescriptionEN(jsonObject.getString("strDescriptionEN"));
                                modelku.setStrStadium(jsonObject.getString("strStadium"));
                                modelku.setStrStadiumThumb(jsonObject.getString("strStadiumThumb"));
                                modelku.setStrTeamBadge(jsonObject.getString("strTeamBadge"));
                                DataArrayList.add(modelku);
                            }

                            adapter = new DataAdapter(DataArrayList, new DataAdapter.Callback() {
                                @Override
                                public void onClick(int position) {
                                    Model team = DataArrayList.get(position);
                                    Intent intent = new Intent(getApplicationContext(), DataMovie.class);
                                    intent.putExtra("id",team.idTeam);
                                    intent.putExtra("nama",team.strTeam);
                                    intent.putExtra("stadion",team.strStadium);
                                    intent.putExtra("foto stadion", team.strStadiumThumb);
                                    intent.putExtra("deskripsi",team.strDescriptionEN);
                                    intent.putExtra("logo",team.strTeamBadge);
                                    startActivity(intent);
                                    Toast.makeText(List.this, ""+position, Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void test() {

                                }
                            });
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(List.this);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(adapter);
                            if (dialog.isShowing()) {
                                dialog.dismiss();
                            }
                        } catch ( JSONException e) {
                            e.printStackTrace();
                            if (dialog.isShowing()) {
                                dialog.dismiss();
                            }
                        }
                    }



                    @Override
                    public void onError(ANError error) {
                        Log.d("myerror","onError errorcode: "+ error.getErrorCode());
                        Log.d("myerror","onError errorcode: " +error.getErrorBody());
                        Log.d("myerror","onError errorcode: "+ error.getErrorDetail());

                    }
                });



    }
}