package com.example.sepakbola;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView masuk;
    TextView dft;
    ImageView img;
    TextView pass;
    TextView nama;
    Toast toast;
    Button btn;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        masuk = findViewById(R.id.masuk);
        dft = findViewById(R.id.dft);
        img = findViewById(R.id.img);
        nama = findViewById(R.id.nama);
        pass = findViewById(R.id.pass);
        pref = getSharedPreferences("login", MODE_PRIVATE);
        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nama.getText().toString().equalsIgnoreCase("satrio")|| pass.getText().toString().equalsIgnoreCase("satrio")){
                    Toast.makeText(MainActivity.this, "Anda berhasil login", Toast.LENGTH_SHORT).show();
                    editor = pref.edit();
                    editor.putString("userid", nama.getText().toString());
                    editor.apply();
                    Intent in = new Intent(MainActivity.this, menu.class);
                    in.putExtra("name", nama.getText().toString());
                    startActivity(in);
                    finish();

                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("username atau passs anda salah")
                            .setNegativeButton("Retry", null).create().show();
                }
            }
        });
    }
}