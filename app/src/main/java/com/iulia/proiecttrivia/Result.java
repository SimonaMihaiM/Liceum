package com.iulia.proiecttrivia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Result extends AppCompatActivity {

    TextView correct, incorrect, attempted, score, you;
    int cor = 0, incorr = 0, attempt = 0, scor = 0, yo = 0;
    int x = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        cor = intent.getIntExtra("correct", 0);
        attempt = intent.getIntExtra("attempt", 0);
        incorr = attempt - cor;
        scor = 10 * cor;

        correct = findViewById(R.id.correct);
        incorrect = findViewById(R.id.incorrect);
        attempted = findViewById(R.id.attempted);
        score = findViewById(R.id.score);
        you = findViewById(R.id.you);

        // afisarea valorilor numerice
        attempted.setText(" " + attempt);
        correct.setText(" " + cor);
        incorrect.setText(" " + incorr);
        score.setText("Score : " + scor);

        // afisarea unui mesaj in functie de scorul final
        float x1 = (cor * 100) / attempt;
        if (x1 < 40) {
            you.setText(R.string.improvement);
        } else if (x1 < 70) {
            you.setText(R.string.average);
        } else if (x1 < 90) {
            you.setText(R.string.above_average);
        } else
            you.setText(R.string.amazing);
    }
}
