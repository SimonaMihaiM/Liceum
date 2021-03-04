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
    public int i, j = 0, k, l = 0;
    String global = null, Ques, Opta, Optb, Optc, Optd;
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
        k++;

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
                    editor.putInt("Questions", k).apply();

                    if (get.equals("c1") && shared.getInt("Computer", 0) < l)
                        editor.putInt("Computer", l * 10).apply();
                    else if (get.equals("c2") && shared.getInt("Sports", 0) < 1)
                        editor.putInt("Sports", l * 10).apply();
                    else if (get.equals("c3") && shared.getInt("Inventions", 0) < 1)
                        editor.putInt("Inventions", l * 10).apply();
                    else if (get.equals("c4") && shared.getInt("General", 0) < 1)
                        editor.putInt("General", l * 10).apply();
                    else if (get.equals("c5") && shared.getInt("Science", 0) < 1)
                        editor.putInt("Science", l * 10).apply();
                    else if (get.equals("c6") && shared.getInt("English", 0) < 1)
                        editor.putInt("English", l * 10).apply();
                    else if (get.equals("c7") && shared.getInt("Books", 0) < 1)
                        editor.putInt("Books", l * 10).apply();
                    else if (get.equals("c8") && shared.getInt("Maths", 0) < 1)
                        editor.putInt("Maths", l * 10).apply();
                    else if (get.equals("c9") && shared.getInt("Capitals", 0) < 1)
                        editor.putInt("Capitals", l * 10).apply();
                    else if (get.equals("c10") && shared.getInt("Currency", 0) < 1)
                        editor.putInt("Currency", l * 10).apply();

                    progressBar.setProgress(0);

                    if (variable == 0) {
                        Intent intent = new Intent(Questions.this, Result.class);
                        intent.putExtra("correct", l);
                        intent.putExtra("attemp", k);
                        startActivity(intent);
                        finish();
                    }
                }
            }.start();
        }

        if (global != null) {
            if (global.equals("A")) {
                if (v.getId() == R.id.OptionA) {
                    Snackbar.make(v, "         Correct ☺", Snackbar.LENGTH_SHORT).show();
                    l++;
                } else {
                    Snackbar.make(v, "Incorrect\t      Answer : " + Opta + "", Snackbar.LENGTH_SHORT).show();
                }
            } else if (global.equals("B")) {
                if (v.getId() == R.id.OptionB) {
                    //Here we use the snackbar because if we use the toast then they will be stacked an user cannot idetify which questions answer is it showing
                    Snackbar.make(v, "         Correct ☺", Snackbar.LENGTH_SHORT).show();

                    l++;
                } else {
                    Snackbar.make(v, "Incorrect\t      Answer : " + Optb + "", Snackbar.LENGTH_SHORT).show();
                }
            } else if (global.equals("C")) {
                if (v.getId() == R.id.OptionC) {
                    //Here we use the snackbar because if we use the toast then they will be stacked an user cannot idetify which questions answer is it showing
                    Snackbar.make(v, "         Correct ☺", Snackbar.LENGTH_SHORT).show();

                    l++;
                } else {
                    Snackbar.make(v, "Incorrect\t      Answer : " + Optc + "", Snackbar.LENGTH_SHORT).show();
                }
            } else if (global.equals("D")) {
                if (v.getId() == R.id.OptionD) {
                    //Here we use the snackbar because if we use the toast then they will be stacked an user cannot idetify which questions answer is it showing
                    Snackbar.make(v, "         Correct ☺", Snackbar.LENGTH_SHORT).show();

                    l++;
                } else {
                    Snackbar.make(v, "Incorrect\t      Answer : " + Optd + "", Snackbar.LENGTH_SHORT).show();
                }
            }
        }

        if (get.equals("c1")) {

            if (c1 == 0) {
                for (i = 1; i < 3; i++) {
                    list.add(i);
                }
                Collections.shuffle(list);
                c1 = 1;
            }

            Ques = computer.readQuestion(list.get(j));
            Opta = computer.readOptionA(list.get(j));
            Optb = computer.readOptionB(list.get(j));
            Optc = computer.readOptionC(list.get(j));
            Optd = computer.readOptionD(list.get(j));
            global = computer.readAnswer(list.get(j++));
        } else if (get.equals("c2")) {

            if (c2 == 0) {
                for (i = 1; i < 20; i++) {
                    list.add(i);
                }
                Collections.shuffle(list);
                c2 = 1;
            }

            Ques = sports.readQuestion(list.get(j));
            Opta = sports.readOptionA(list.get(j));
            Optb = sports.readOptionB(list.get(j));
            Optc = sports.readOptionC(list.get(j));
            Optd = sports.readOptionD(list.get(j));
            global = sports.readAnswer(list.get(j++));
        } else if (get.equals("c3")) {

            if (c3 == 0) {
                for (i = 1; i < 20; i++) {
                    list.add(i);
                }
                Collections.shuffle(list);
                c3 = 1;
            }

            Ques = inventions.readQuestion(list.get(j));
            Opta = inventions.readOptionA(list.get(j));
            Optb = inventions.readOptionB(list.get(j));
            Optc = inventions.readOptionC(list.get(j));
            Optd = inventions.readOptionD(list.get(j));
            global = inventions.readAnswer(list.get(j++));
        } else if (get.equals("c4")) {

            if (c4 == 0) {
                for (i = 1; i < 40; i++) {
                    list.add(i);
                }
                Collections.shuffle(list);
                c4 = 1;
            }

            Ques = general.readQuestion(list.get(j));
            Opta = general.readOptionA(list.get(j));
            Optb = general.readOptionB(list.get(j));
            Optc = general.readOptionC(list.get(j));
            Optd = general.readOptionD(list.get(j));
            global = general.readAnswer(list.get(j++));
        } else if (get.equals("c5")) {

            if (c5 == 0) {
                for (i = 1; i < 20; i++) {
                    list.add(i);
                }
                Collections.shuffle(list);
                c5 = 1;
            }

            Ques = science.readQuestion(list.get(j));
            Opta = science.readOptionA(list.get(j));
            Optb = science.readOptionB(list.get(j));
            Optc = science.readOptionC(list.get(j));
            Optd = science.readOptionD(list.get(j));
            global = science.readAnswer(list.get(j++));
        } else if (get.equals("c6")) {
            // romana
            if (c6 == 0) {
                // 60
                for (i = 1; i <= 3; i++) {
                    list.add(i);
                }
                Collections.shuffle(list);
                c6 = 1;
            }

            Ques = english.readQuestion(list.get(j));
            Opta = english.readOptionA(list.get(j));
            Optb = english.readOptionB(list.get(j));
            Optc = english.readOptionC(list.get(j));
            Optd = english.readOptionD(list.get(j));
            global = english.readAnswer(list.get(j++));
        } else if (get.equals("c7")) {

            if (c7 == 0) {
                for (i = 1; i < 20; i++) {
                    list.add(i);
                }
                Collections.shuffle(list);
                c7 = 1;
            }

            Ques = books.readQuestion(list.get(j));
            Opta = books.readOptionA(list.get(j));
            Optb = books.readOptionB(list.get(j));
            Optc = books.readOptionC(list.get(j));
            Optd = books.readOptionD(list.get(j));
            global = books.readAnswer(list.get(j++));
        } else if (get.equals("c8")) {

            if (c8 == 0) {
                for (i = 1; i < 20; i++) {
                    list.add(i);
                }
                Collections.shuffle(list);
                c8 = 1;
            }

            Ques = maths.readQuestion(list.get(j));
            Opta = maths.readOptionA(list.get(j));
            Optb = maths.readOptionB(list.get(j));
            Optc = maths.readOptionC(list.get(j));
            Optd = maths.readOptionD(list.get(j));
            global = maths.readAnswer(list.get(j++));
        } else if (get.equals("c9")) {

            if (c9 == 0) {
                for (i = 1; i < 20; i++) {
                    list.add(i);
                }
                Collections.shuffle(list);
                c9 = 1;
            }

            Ques = capitals.readQuestion(list.get(j));
            Opta = capitals.readOptionA(list.get(j));
            Optb = capitals.readOptionB(list.get(j));
            Optc = capitals.readOptionC(list.get(j));
            Optd = capitals.readOptionD(list.get(j));
            global = capitals.readAnswer(list.get(j++));
        } else if (get.equals("c10")) {

            if (c10 == 0) {
                for (i = 1; i < 20; i++) {
                    list.add(i);
                }
                Collections.shuffle(list);
                c10 = 1;
            }

            Ques = currency.readQuestion(list.get(j));
            Opta = currency.readOptionA(list.get(j));
            Optb = currency.readOptionB(list.get(j));
            Optc = currency.readOptionC(list.get(j));
            Optd = currency.readOptionD(list.get(j));
            global = currency.readAnswer(list.get(j++));
        }

        ques.setText("" + Ques);
        OptA.setText(Opta);
        OptB.setText(Optb);
        OptC.setText(Optc);
        OptD.setText(Optd);
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