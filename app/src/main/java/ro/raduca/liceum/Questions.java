package ro.raduca.liceum;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;

import ro.raduca.liceum.data.Books;
import ro.raduca.liceum.data.Capitals;
import ro.raduca.liceum.data.Computer;
import ro.raduca.liceum.data.Currency;
import ro.raduca.liceum.data.English;
import ro.raduca.liceum.data.General;
import ro.raduca.liceum.data.Inventions;
import ro.raduca.liceum.data.Maths;
import ro.raduca.liceum.data.Science;
import ro.raduca.liceum.data.Sports;

public class Questions extends AppCompatActivity {

    int variable = 0;
    TextView ques;
    Button OptA, OptB, OptC, OptD;
    Button play_button;
    String get;
    ProgressBar progressBar;

    // obiecte din diferite clase
    Books books;
    Sports sports;
    Currency currency;
    Computer computer;
    Capitals capitals;
    English english;
    General general;
    Inventions inventions;
    Maths maths;
    Science science;

    public int visibility = 0, c1 = 0, c2 = 0, c3 = 0, c4 = 0, c5 = 0, c6 = 0, c7 = 0, c8 = 0, c9 = 0, c10 = 0;
    public int currentCategoryIndex, questionListIndex = 0, numberQuestions, currentScore = 0;
    String currentAnswer = null, currentQuestion, currentOptionA, currentOptionB, currentOptionC, currentOptionD;
    ArrayList<Integer> list = new ArrayList<>();
    Toast toast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(100);
        progressBar.setKeepScreenOn(true);

        SharedPreferences shared = getSharedPreferences("Score", Context.MODE_PRIVATE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // primirea intentului trimis de activitatea Navigation
        Intent intent = getIntent();
        get = intent.getStringExtra(Navigation.Message);
        toast = new Toast(this);

        // legarea bazelor de date cu clasa Questions

        books = new Books(this);
        books.createDatabase();
        books.openDatabase();
        books.getWritableDatabase();

        capitals = new Capitals(this);
        capitals.createDatabase();
        capitals.openDatabase();
        capitals.getWritableDatabase();

        computer = new Computer(this);
        computer.createDatabase();
        computer.openDatabase();
        computer.getWritableDatabase();

        currency = new Currency(this);
        currency.createDatabase();
        currency.openDatabase();
        currency.getWritableDatabase();

        english = new English(this);
        english.createDatabase();
        english.openDatabase();
        english.getWritableDatabase();

        general = new General(this);
        general.createDatabase();
        general.openDatabase();
        general.getWritableDatabase();

        inventions = new Inventions(this);
        inventions.createDatabase();
        inventions.openDatabase();
        inventions.getWritableDatabase();

        maths = new Maths(this);
        maths.createDatabase();
        maths.openDatabase();
        maths.getWritableDatabase();

        science = new Science(this);
        science.createDatabase();
        science.openDatabase();
        science.getWritableDatabase();

        sports = new Sports(this);
        sports.createDatabase();
        sports.openDatabase();
        sports.getWritableDatabase();

        OptA = findViewById(R.id.OptionA);
        OptB = findViewById(R.id.OptionB);
        OptC = findViewById(R.id.OptionC);
        OptD = findViewById(R.id.OptionD);
        ques = findViewById(R.id.Questions);
        play_button = findViewById(R.id.play_button);
    }

    public void onClick(View v) {

        final SharedPreferences shared = getSharedPreferences("Score", Context.MODE_PRIVATE);
        numberQuestions++;

        if (visibility == 0) {
            // afisarea butoanelor care erau invizibile
            OptA.setVisibility(View.VISIBLE);
            OptB.setVisibility(View.VISIBLE);
            OptC.setVisibility(View.VISIBLE);
            OptD.setVisibility(View.VISIBLE);
            play_button.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            visibility = 1;

            new CountDownTimer(200000, 1000) {
                int progressBarValue = 100;

                @Override
                public void onTick(long millisUntilFinished) {
                    progressBarValue = progressBarValue - 5;
                    progressBar.setProgress(progressBarValue);
                }

                @Override
                public void onFinish() {
                    toast.cancel();
                    SharedPreferences.Editor editor = shared.edit();
                    editor.putInt("Questions", numberQuestions).apply();

                    if (get.equals("c1") && shared.getInt("Computer", 0) < currentScore)
                        editor.putInt("Computer", currentScore * 10).apply();
                    else if (get.equals("c2") && shared.getInt("Sports", 0) < 1)
                        editor.putInt("Sports", currentScore * 10).apply();
                    else if (get.equals("c3") && shared.getInt("Inventions", 0) < 1)
                        editor.putInt("Inventions", currentScore * 10).apply();
                    else if (get.equals("c4") && shared.getInt("General", 0) < 1)
                        editor.putInt("General", currentScore * 10).apply();
                    else if (get.equals("c5") && shared.getInt("Science", 0) < 1)
                        editor.putInt("Science", currentScore * 10).apply();
                    else if (get.equals("c6") && shared.getInt("English", 0) < 1)
                        editor.putInt("English", currentScore * 10).apply();
                    else if (get.equals("c7") && shared.getInt("Books", 0) < 1)
                        editor.putInt("Books", currentScore * 10).apply();
                    else if (get.equals("c8") && shared.getInt("Maths", 0) < 1)
                        editor.putInt("Maths", currentScore * 10).apply();
                    else if (get.equals("c9") && shared.getInt("Capitals", 0) < 1)
                        editor.putInt("Capitals", currentScore * 10).apply();
                    else if (get.equals("c10") && shared.getInt("Currency", 0) < 1)
                        editor.putInt("Currency", currentScore * 10).apply();

                    progressBar.setProgress(0);

                    if (variable == 0) {
                        Intent intent = new Intent(Questions.this, Result.class);
                        intent.putExtra("correct", currentScore);
                        intent.putExtra("attemp", numberQuestions);
                        startActivity(intent);
                        finish();
                    }
                }
            }.start();
        }

        if (currentAnswer != null) {
            switch (currentAnswer) {
                case "A":
                    if (v.getId() == R.id.OptionA) {
                        Snackbar.make(v, "         Correct ☺", Snackbar.LENGTH_SHORT).show();
                        currentScore++;
                    } else {
                        Snackbar.make(v, "Incorrect\t      Answer : " + currentOptionA + "", Snackbar.LENGTH_SHORT).show();
                    }
                    break;
                case "B":
                    if (v.getId() == R.id.OptionB) {
                        //Here we use the snackbar because if we use the toast then they will be stacked an user cannot idetify which questions answer is it showing
                        Snackbar.make(v, "         Correct ☺", Snackbar.LENGTH_SHORT).show();

                        currentScore++;
                    } else {
                        Snackbar.make(v, "Incorrect\t      Answer : " + currentOptionB + "", Snackbar.LENGTH_SHORT).show();
                    }
                    break;
                case "C":
                    if (v.getId() == R.id.OptionC) {
                        //Here we use the snackbar because if we use the toast then they will be stacked an user cannot idetify which questions answer is it showing
                        Snackbar.make(v, "         Correct ☺", Snackbar.LENGTH_SHORT).show();

                        currentScore++;
                    } else {
                        Snackbar.make(v, "Incorrect\t      Answer : " + currentOptionC + "", Snackbar.LENGTH_SHORT).show();
                    }
                    break;
                case "D":
                    if (v.getId() == R.id.OptionD) {
                        //Here we use the snackbar because if we use the toast then they will be stacked an user cannot idetify which questions answer is it showing
                        Snackbar.make(v, "         Correct ☺", Snackbar.LENGTH_SHORT).show();

                        currentScore++;
                    } else {
                        Snackbar.make(v, "Incorrect\t      Answer : " + currentOptionD + "", Snackbar.LENGTH_SHORT).show();
                    }
                    break;
            }
        }

        switch (get) {
            case "c1":

                if (c1 == 0) {
                    for (currentCategoryIndex = 1; currentCategoryIndex <= 20; currentCategoryIndex++) {
                        list.add(currentCategoryIndex);
                    }
                    Collections.shuffle(list);
                    c1 = 1;
                }

                currentQuestion = computer.readQuestion(list.get(questionListIndex));
                currentOptionA = computer.readOptionA(list.get(questionListIndex));
                currentOptionB = computer.readOptionB(list.get(questionListIndex));
                currentOptionC = computer.readOptionC(list.get(questionListIndex));
                currentOptionD = computer.readOptionD(list.get(questionListIndex));
                currentAnswer = computer.readAnswer(list.get(questionListIndex++));
                break;
            case "c2":

                if (c2 == 0) {
                    for (currentCategoryIndex = 1; currentCategoryIndex < 20; currentCategoryIndex++) {
                        list.add(currentCategoryIndex);
                    }
                    Collections.shuffle(list);
                    c2 = 1;
                }

                currentQuestion = sports.readQuestion(list.get(questionListIndex));
                currentOptionA = sports.readOptionA(list.get(questionListIndex));
                currentOptionB = sports.readOptionB(list.get(questionListIndex));
                currentOptionC = sports.readOptionC(list.get(questionListIndex));
                currentOptionD = sports.readOptionD(list.get(questionListIndex));
                currentAnswer = sports.readAnswer(list.get(questionListIndex++));
                break;
            case "c3":

                if (c3 == 0) {
                    for (currentCategoryIndex = 1; currentCategoryIndex < 20; currentCategoryIndex++) {
                        list.add(currentCategoryIndex);
                    }
                    Collections.shuffle(list);
                    c3 = 1;
                }

                currentQuestion = inventions.readQuestion(list.get(questionListIndex));
                currentOptionA = inventions.readOptionA(list.get(questionListIndex));
                currentOptionB = inventions.readOptionB(list.get(questionListIndex));
                currentOptionC = inventions.readOptionC(list.get(questionListIndex));
                currentOptionD = inventions.readOptionD(list.get(questionListIndex));
                currentAnswer = inventions.readAnswer(list.get(questionListIndex++));
                break;
            case "c4":

                if (c4 == 0) {
                    for (currentCategoryIndex = 1; currentCategoryIndex < 40; currentCategoryIndex++) {
                        list.add(currentCategoryIndex);
                    }
                    Collections.shuffle(list);
                    c4 = 1;
                }

                currentQuestion = general.readQuestion(list.get(questionListIndex));
                currentOptionA = general.readOptionA(list.get(questionListIndex));
                currentOptionB = general.readOptionB(list.get(questionListIndex));
                currentOptionC = general.readOptionC(list.get(questionListIndex));
                currentOptionD = general.readOptionD(list.get(questionListIndex));
                currentAnswer = general.readAnswer(list.get(questionListIndex++));
                break;
            case "c5":

                if (c5 == 0) {
                    for (currentCategoryIndex = 1; currentCategoryIndex < 20; currentCategoryIndex++) {
                        list.add(currentCategoryIndex);
                    }
                    Collections.shuffle(list);
                    c5 = 1;
                }

                currentQuestion = science.readQuestion(list.get(questionListIndex));
                currentOptionA = science.readOptionA(list.get(questionListIndex));
                currentOptionB = science.readOptionB(list.get(questionListIndex));
                currentOptionC = science.readOptionC(list.get(questionListIndex));
                currentOptionD = science.readOptionD(list.get(questionListIndex));
                currentAnswer = science.readAnswer(list.get(questionListIndex++));
                break;
            case "c6":
                // romana
                if (c6 == 0) {
                    // 60
                    for (currentCategoryIndex = 1; currentCategoryIndex <= 3; currentCategoryIndex++) {
                        list.add(currentCategoryIndex);
                    }
                    Collections.shuffle(list);
                    c6 = 1;
                }

                currentQuestion = english.readQuestion(list.get(questionListIndex));
                currentOptionA = english.readOptionA(list.get(questionListIndex));
                currentOptionB = english.readOptionB(list.get(questionListIndex));
                currentOptionC = english.readOptionC(list.get(questionListIndex));
                currentOptionD = english.readOptionD(list.get(questionListIndex));
                currentAnswer = english.readAnswer(list.get(questionListIndex++));
                break;
            case "c7":

                if (c7 == 0) {
                    for (currentCategoryIndex = 1; currentCategoryIndex < 20; currentCategoryIndex++) {
                        list.add(currentCategoryIndex);
                    }
                    Collections.shuffle(list);
                    c7 = 1;
                }

                currentQuestion = books.readQuestion(list.get(questionListIndex));
                currentOptionA = books.readOptionA(list.get(questionListIndex));
                currentOptionB = books.readOptionB(list.get(questionListIndex));
                currentOptionC = books.readOptionC(list.get(questionListIndex));
                currentOptionD = books.readOptionD(list.get(questionListIndex));
                currentAnswer = books.readAnswer(list.get(questionListIndex++));
                break;
            case "c8":

                if (c8 == 0) {
                    for (currentCategoryIndex = 1; currentCategoryIndex < 20; currentCategoryIndex++) {
                        list.add(currentCategoryIndex);
                    }
                    Collections.shuffle(list);
                    c8 = 1;
                }

                currentQuestion = maths.readQuestion(list.get(questionListIndex));
                currentOptionA = maths.readOptionA(list.get(questionListIndex));
                currentOptionB = maths.readOptionB(list.get(questionListIndex));
                currentOptionC = maths.readOptionC(list.get(questionListIndex));
                currentOptionD = maths.readOptionD(list.get(questionListIndex));
                currentAnswer = maths.readAnswer(list.get(questionListIndex++));
                break;
            case "c9":

                if (c9 == 0) {
                    for (currentCategoryIndex = 1; currentCategoryIndex < 20; currentCategoryIndex++) {
                        list.add(currentCategoryIndex);
                    }
                    Collections.shuffle(list);
                    c9 = 1;
                }

                currentQuestion = capitals.readQuestion(list.get(questionListIndex));
                currentOptionA = capitals.readOptionA(list.get(questionListIndex));
                currentOptionB = capitals.readOptionB(list.get(questionListIndex));
                currentOptionC = capitals.readOptionC(list.get(questionListIndex));
                currentOptionD = capitals.readOptionD(list.get(questionListIndex));
                currentAnswer = capitals.readAnswer(list.get(questionListIndex++));
                break;
            case "c10":

                if (c10 == 0) {
                    for (currentCategoryIndex = 1; currentCategoryIndex < 20; currentCategoryIndex++) {
                        list.add(currentCategoryIndex);
                    }
                    Collections.shuffle(list);
                    c10 = 1;
                }

                currentQuestion = currency.readQuestion(list.get(questionListIndex));
                currentOptionA = currency.readOptionA(list.get(questionListIndex));
                currentOptionB = currency.readOptionB(list.get(questionListIndex));
                currentOptionC = currency.readOptionC(list.get(questionListIndex));
                currentOptionD = currency.readOptionD(list.get(questionListIndex));
                currentAnswer = currency.readAnswer(list.get(questionListIndex++));
                break;
        }

        ques.setText("" + currentQuestion);
        OptA.setText(currentOptionA);
        OptB.setText(currentOptionB);
        OptC.setText(currentOptionC);
        OptD.setText(currentOptionD);
    }

    @Override
    protected void onPause() {
        super.onPause();
        variable = 1;
        SharedPreferences sp = getSharedPreferences("Score", Context.MODE_PRIVATE);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        variable = 1;
        SharedPreferences sp = getSharedPreferences("Score", Context.MODE_PRIVATE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        variable = 1;
        finish();
    }
}