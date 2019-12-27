package com.iulia.proiecttrivia;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;


public class ScoreCard extends AppCompatActivity {

    TextView a1, a2, a3, a4, a5, a6, a7, a8, a9, a10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_card);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences sharedPreferences = getSharedPreferences("Score", Context.MODE_PRIVATE);

        a1 = findViewById(R.id.computer);
        a2 = findViewById(R.id.sports);
        a3 = findViewById(R.id.inventions);
        a4 = findViewById(R.id.general);
        a5 = findViewById(R.id.science);
        a6 = findViewById(R.id.english);
        a7 = findViewById(R.id.books);
        a8 = findViewById(R.id.maths);
        a9 = findViewById(R.id.capitals);
        a10 = findViewById(R.id.currency);

        try {
            a1.setText(" " + sharedPreferences.getInt("Computer", 0));
            a2.setText(" " + sharedPreferences.getInt("Sports", 0));
            a3.setText(" " + sharedPreferences.getInt("Inventions", 0));
            a4.setText(" " + sharedPreferences.getInt("General", 0));
            a5.setText(" " + sharedPreferences.getInt("Science", 0));
            a6.setText(" " + sharedPreferences.getInt("English", 0));
            a7.setText(" " + sharedPreferences.getInt("Books", 0));
            a8.setText(" " + sharedPreferences.getInt("Maths", 0));
            a9.setText(" " + sharedPreferences.getInt("Capitals", 0));
            a10.setText(" " + sharedPreferences.getInt("Currency", 0));
        } catch (Exception e) {
            Toast.makeText(ScoreCard.this, "" + e, Toast.LENGTH_SHORT).show();
        }

    }
}
