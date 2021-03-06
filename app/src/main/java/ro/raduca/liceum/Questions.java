package ro.raduca.liceum;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.ArrayMap;
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

import ro.raduca.liceum.data.CategoryDatabase;

public class Questions extends AppCompatActivity {

    public int visibility = 0;
    public int currentCategoryIndex, questionListIndex = 0, questionIndex=0, currentScore = 0;
    boolean categoryFinished = true;
    int maxQuestions = 10;
    TextView ques;
    Button OptA, OptB, OptC, OptD;
    Button play_button;
    String get;
    ProgressBar progressBar;
    ArrayMap<String, CategoryDatabase> databases;
    String currentAnswer = null, currentQuestion, currentOptionA, currentOptionB, currentOptionC, currentOptionD;
    ArrayList<Integer> list = new ArrayList<>();
    Toast toast;
    long timerDuration = 3000;
    long timerStep = 1000;
    CountDownTimer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        for (currentCategoryIndex = 1; currentCategoryIndex <= this.maxQuestions; currentCategoryIndex++) {
            list.add(currentCategoryIndex);
        }
        Collections.shuffle(list);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(100);
        progressBar.setKeepScreenOn(true);

        SharedPreferences shared = getSharedPreferences("Score", Context.MODE_PRIVATE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        get = intent.getStringExtra(Navigation.Message);
        toast = new Toast(this);

        databases = new ArrayMap<String, CategoryDatabase>();
        databases.put("books", new CategoryDatabase(this, "books"));
        databases.put("capitals", new CategoryDatabase(this, "capitals"));
        databases.put("computer", new CategoryDatabase(this, "computer"));
        databases.put("currency", new CategoryDatabase(this, "currency"));
        databases.put("english", new CategoryDatabase(this, "english"));
        databases.put("general", new CategoryDatabase(this, "general"));
        databases.put("inventions", new CategoryDatabase(this, "inventions"));
        databases.put("maths", new CategoryDatabase(this, "maths"));
        databases.put("science", new CategoryDatabase(this, "science"));
        databases.put("sports", new CategoryDatabase(this, "sports"));

        for (CategoryDatabase database : databases.values()) {
            database.createDatabase();
            database.openDatabase();
            database.getWritableDatabase();
        }

        OptA = findViewById(R.id.OptionA);
        OptB = findViewById(R.id.OptionB);
        OptC = findViewById(R.id.OptionC);
        OptD = findViewById(R.id.OptionD);
        ques = findViewById(R.id.Questions);
        play_button = findViewById(R.id.play_button);
    }

    public void addTimer () {
        if (timer != null) {
            timer.cancel();
        }
        timer = new CountDownTimer(timerDuration, timerStep) {
            int progressBarValue = 100;

            @Override
            public void onTick(long millisUntilFinished) {
                progressBarValue = (int) (progressBarValue - 100*timerStep/timerDuration);
                progressBar.setProgress(progressBarValue);
            }

            @Override
            public void onFinish() {
                progressBar.setProgress(0);

                displayNextQuestion();
            }
        };
        timer.start();
    }

    public void writeScore(){
        final SharedPreferences shared = getSharedPreferences("Score", Context.MODE_PRIVATE);
        toast.cancel();
        SharedPreferences.Editor editor = shared.edit();
        editor.putInt("Questions", questionIndex).apply();

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
    }

    public void displayResults() {
        timer.cancel();
        writeScore();
        Intent intent = new Intent(Questions.this, Result.class);
        intent.putExtra("correct", currentScore);
        intent.putExtra("attemp", questionIndex);
        startActivity(intent);
        finish();
    }

    public void displayNextQuestion() {
        if (questionListIndex == maxQuestions) {
            displayResults();
        } else {

            questionIndex++;
            switch (get) {
                case "c1":
                    setUIQuestionElements("computer");
                    break;
                case "c2":
                    setUIQuestionElements("sports");
                    break;
                case "c3":
                    setUIQuestionElements("inventions");
                    break;
                case "c4":
                    setUIQuestionElements("general");
                    break;
                case "c5":
                    setUIQuestionElements("science");
                    break;
                case "c6":
                    setUIQuestionElements("english");
                    break;
                case "c7":
                    setUIQuestionElements("books");
                    break;
                case "c8":
                    setUIQuestionElements("maths");
                    break;
                case "c9":
                    setUIQuestionElements("capitals");
                    break;
                case "c10":
                    setUIQuestionElements("currency");
                    break;
            }
        }
    }

    public void onClick(View v) {

        if (visibility == 0) {
            OptA.setVisibility(View.VISIBLE);
            OptB.setVisibility(View.VISIBLE);
            OptC.setVisibility(View.VISIBLE);
            OptD.setVisibility(View.VISIBLE);
            play_button.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            visibility = 1;
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

        displayNextQuestion();
    }

    private void setUIQuestionElements(String databaseName) {
        addTimer();

        currentQuestion = databases.get(databaseName).readQuestion(list.get(questionListIndex));
        currentOptionA = databases.get(databaseName).readOptionA(list.get(questionListIndex));
        currentOptionB = databases.get(databaseName).readOptionB(list.get(questionListIndex));
        currentOptionC = databases.get(databaseName).readOptionC(list.get(questionListIndex));
        currentOptionD = databases.get(databaseName).readOptionD(list.get(questionListIndex));
        currentAnswer = databases.get(databaseName).readAnswer(list.get(questionListIndex++));

        ques.setText(currentQuestion);
        OptA.setText(currentOptionA);
        OptB.setText(currentOptionB);
        OptC.setText(currentOptionC);
        OptD.setText(currentOptionD);
    }

    @Override
    protected void onPause() {
        super.onPause();
        categoryFinished = false;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        categoryFinished = false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        categoryFinished = false;
        finish();
    }
}