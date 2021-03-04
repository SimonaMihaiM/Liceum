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
import ro.raduca.liceum.R;

import ro.raduca.liceum.data.books;
import ro.raduca.liceum.data.capitals;
import ro.raduca.liceum.data.computer;
import ro.raduca.liceum.data.currency;
import ro.raduca.liceum.data.english;
import ro.raduca.liceum.data.general;
import ro.raduca.liceum.data.inventions;
import ro.raduca.liceum.data.maths;
import ro.raduca.liceum.data.science;
import ro.raduca.liceum.data.sports;

import java.util.ArrayList;
import java.util.Collections;

public class Questions extends AppCompatActivity {

    int variable = 0;
    TextView ques;
    Button OptA, OptB, OptC, OptD;
    Button play_button;
    String get;
    ProgressBar progressBar;

    // obiecte din diferite clase
    books Books;
    sports Sports;
    currency Currency;
    computer Computer;
    capitals Capitals;
    english English;
    general General;
    inventions Inventions;
    maths Maths;
    science Science;

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

        Books = new books(this);
        Books.createDatabase();
        Books.openDatabase();
        Books.getWritableDatabase();

        Capitals = new capitals(this);
        Capitals.createDatabase();
        Capitals.openDatabase();
        Capitals.getWritableDatabase();

        Computer = new computer(this);
        Computer.createDatabase();
        Computer.openDatabase();
        Computer.getWritableDatabase();

        Currency = new currency(this);
        Currency.createDatabase();
        Currency.openDatabase();
        Currency.getWritableDatabase();

        English = new english(this);
        English.createDatabase();
        English.openDatabase();
        English.getWritableDatabase();

        General = new general(this);
        General.createDatabase();
        General.openDatabase();
        General.getWritableDatabase();

        Inventions = new inventions(this);
        Inventions.createDatabase();
        Inventions.openDatabase();
        Inventions.getWritableDatabase();

        Maths = new maths(this);
        Maths.createDatabase();
        Maths.openDatabase();
        Maths.getWritableDatabase();

        Science = new science(this);
        Science.createDatabase();
        Science.openDatabase();
        Science.getWritableDatabase();

        Sports = new sports(this);
        Sports.createDatabase();
        Sports.openDatabase();
        Sports.getWritableDatabase();

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

            new CountDownTimer(20000, 1000) {
                int i = 100;

                @Override
                public void onTick(long millisUntilFinished) {
                    i = i - 5;
                    progressBar.setProgress(i);
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
                for (i = 1; i < 20; i++) {
                    list.add(i);
                }
                Collections.shuffle(list);
                c1 = 1;
            }

            Ques = Computer.readQuestion(list.get(j));
            Opta = Computer.readOptionA(list.get(j));
            Optb = Computer.readOptionB(list.get(j));
            Optc = Computer.readOptionC(list.get(j));
            Optd = Computer.readOptionD(list.get(j));
            global = Computer.readAnswer(list.get(j++));
        } else if (get.equals("c2")) {

            if (c2 == 0) {
                for (i = 1; i < 20; i++) {
                    list.add(i);
                }
                Collections.shuffle(list);
                c2 = 1;
            }

            Ques = Sports.readQuestion(list.get(j));
            Opta = Sports.readOptionA(list.get(j));
            Optb = Sports.readOptionB(list.get(j));
            Optc = Sports.readOptionC(list.get(j));
            Optd = Sports.readOptionD(list.get(j));
            global = Sports.readAnswer(list.get(j++));
        } else if (get.equals("c3")) {

            if (c3 == 0) {
                for (i = 1; i < 20; i++) {
                    list.add(i);
                }
                Collections.shuffle(list);
                c3 = 1;
            }

            Ques = Inventions.readQuestion(list.get(j));
            Opta = Inventions.readOptionA(list.get(j));
            Optb = Inventions.readOptionB(list.get(j));
            Optc = Inventions.readOptionC(list.get(j));
            Optd = Inventions.readOptionD(list.get(j));
            global = Inventions.readAnswer(list.get(j++));
        } else if (get.equals("c4")) {

            if (c4 == 0) {
                for (i = 1; i < 40; i++) {
                    list.add(i);
                }
                Collections.shuffle(list);
                c4 = 1;
            }

            Ques = General.readQuestion(list.get(j));
            Opta = General.readOptionA(list.get(j));
            Optb = General.readOptionB(list.get(j));
            Optc = General.readOptionC(list.get(j));
            Optd = General.readOptionD(list.get(j));
            global = General.readAnswer(list.get(j++));
        } else if (get.equals("c5")) {

            if (c5 == 0) {
                for (i = 1; i < 20; i++) {
                    list.add(i);
                }
                Collections.shuffle(list);
                c5 = 1;
            }

            Ques = Science.readQuestion(list.get(j));
            Opta = Science.readOptionA(list.get(j));
            Optb = Science.readOptionB(list.get(j));
            Optc = Science.readOptionC(list.get(j));
            Optd = Science.readOptionD(list.get(j));
            global = Science.readAnswer(list.get(j++));
        } else if (get.equals("c6")) {

            if (c6 == 0) {
                for (i = 1; i < 60; i++) {
                    list.add(i);
                }
                Collections.shuffle(list);
                c6 = 1;
            }

            Ques = English.readQuestion(list.get(j));
            Opta = English.readOptionA(list.get(j));
            Optb = English.readOptionB(list.get(j));
            Optc = English.readOptionC(list.get(j));
            Optd = English.readOptionD(list.get(j));
            global = English.readAnswer(list.get(j++));
        } else if (get.equals("c7")) {

            if (c7 == 0) {
                for (i = 1; i < 20; i++) {
                    list.add(i);
                }
                Collections.shuffle(list);
                c7 = 1;
            }

            Ques = Books.readQuestion(list.get(j));
            Opta = Books.readOptionA(list.get(j));
            Optb = Books.readOptionB(list.get(j));
            Optc = Books.readOptionC(list.get(j));
            Optd = Books.readOptionD(list.get(j));
            global = Books.readAnswer(list.get(j++));
        } else if (get.equals("c8")) {

            if (c8 == 0) {
                for (i = 1; i < 20; i++) {
                    list.add(i);
                }
                Collections.shuffle(list);
                c8 = 1;
            }

            Ques = Maths.readQuestion(list.get(j));
            Opta = Maths.readOptionA(list.get(j));
            Optb = Maths.readOptionB(list.get(j));
            Optc = Maths.readOptionC(list.get(j));
            Optd = Maths.readOptionD(list.get(j));
            global = Maths.readAnswer(list.get(j++));
        } else if (get.equals("c9")) {

            if (c9 == 0) {
                for (i = 1; i < 20; i++) {
                    list.add(i);
                }
                Collections.shuffle(list);
                c9 = 1;
            }

            Ques = Capitals.readQuestion(list.get(j));
            Opta = Capitals.readOptionA(list.get(j));
            Optb = Capitals.readOptionB(list.get(j));
            Optc = Capitals.readOptionC(list.get(j));
            Optd = Capitals.readOptionD(list.get(j));
            global = Capitals.readAnswer(list.get(j++));
        } else if (get.equals("c10")) {

            if (c10 == 0) {
                for (i = 1; i < 20; i++) {
                    list.add(i);
                }
                Collections.shuffle(list);
                c10 = 1;
            }

            Ques = Currency.readQuestion(list.get(j));
            Opta = Currency.readOptionA(list.get(j));
            Optb = Currency.readOptionB(list.get(j));
            Optc = Currency.readOptionC(list.get(j));
            Optd = Currency.readOptionD(list.get(j));
            global = Currency.readAnswer(list.get(j++));
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