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
import java.util.Objects;

import ro.raduca.liceum.data.Category;
import ro.raduca.liceum.data.Database;
import ro.raduca.liceum.data.Question;

public class Questions extends AppCompatActivity {

    public int visibility = 0;
    public int currentCategoryIndex, questionListIndex = 0, questionIndex=0, currentScore = 0;
    boolean categoryFinished = true;
    int maxQuestions = 10;
    TextView ques;
    Button OptA, OptB, OptC, OptD;
    Button play_button;
    String selectedCategoru;
    ProgressBar progressBar;
    ArrayList<Category> categories;
    Question currentQuestion;
    String currentAnswer = null, currentOptionA, currentOptionB, currentOptionC, currentOptionD;
    ArrayList<Integer> list = new ArrayList<>();
    Toast toast;
    long timerDuration = 10000;
    long timerStep = 1000;
    CountDownTimer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        for (currentCategoryIndex = 1; currentCategoryIndex <= maxQuestions; currentCategoryIndex++) {
            list.add(currentCategoryIndex);
        }
        Collections.shuffle(list);
//        list = new ArrayList<>(list.subList(0, 3));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(100);
        progressBar.setKeepScreenOn(true);


//        SharedPreferences shared = getSharedPreferences("Score", Context.MODE_PRIVATE);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        selectedCategoru = intent.getStringExtra(Navigation.Message);
        toast = new Toast(this);

        Database database = new Database(this);
        database.createDatabase();
        database.openDatabase();
        database.getWritableDatabase();

        categories = Category.getAll(database.sqlite);

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

        if (selectedCategoru.equals("c1") && shared.getInt("Computer", 0) < currentScore)
            editor.putInt("Computer", currentScore * 10).apply();
        else if (selectedCategoru.equals("c2") && shared.getInt("Sports", 0) < 1)
            editor.putInt("Sports", currentScore * 10).apply();
        else if (selectedCategoru.equals("c3") && shared.getInt("Inventions", 0) < 1)
            editor.putInt("Inventions", currentScore * 10).apply();
        else if (selectedCategoru.equals("c4") && shared.getInt("General", 0) < 1)
            editor.putInt("General", currentScore * 10).apply();
        else if (selectedCategoru.equals("c5") && shared.getInt("Science", 0) < 1)
            editor.putInt("Science", currentScore * 10).apply();
        else if (selectedCategoru.equals("c6") && shared.getInt("English", 0) < 1)
            editor.putInt("English", currentScore * 10).apply();
        else if (selectedCategoru.equals("c7") && shared.getInt("Books", 0) < 1)
            editor.putInt("Books", currentScore * 10).apply();
        else if (selectedCategoru.equals("c8") && shared.getInt("Maths", 0) < 1)
            editor.putInt("Maths", currentScore * 10).apply();
        else if (selectedCategoru.equals("c9") && shared.getInt("Capitals", 0) < 1)
            editor.putInt("Capitals", currentScore * 10).apply();
        else if (selectedCategoru.equals("c10") && shared.getInt("Currency", 0) < 1)
            editor.putInt("Currency", currentScore * 10).apply();
        else if (selectedCategoru.equals("c11") && shared.getInt("ANT", 0) < 1)
            editor.putInt("ANT", currentScore * 10).apply();
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
            switch (selectedCategoru) {
                case "c1":
                    setUIQuestionElements(categories.get(0));
                    break;
                case "c2":
                    setUIQuestionElements(categories.get(1));
                    break;
                case "c3":
                    setUIQuestionElements(categories.get(2));
                    break;
                case "c4":
                    setUIQuestionElements(categories.get(3));
                    break;
                case "c5":
                    setUIQuestionElements(categories.get(4));
                    break;
                case "c6":
                    setUIQuestionElements(categories.get(5));
                    break;
                case "c7":
                    setUIQuestionElements(categories.get(6));
                    break;
                case "c8":
                    setUIQuestionElements(categories.get(7));
                    break;
                case "c9":
                    setUIQuestionElements(categories.get(8));
                    break;
                case "c10":
                    setUIQuestionElements(categories.get(9));
                    break;
                case "c11":
                    setUIQuestionElements(categories.get(10));
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

    private void setUIQuestionElements(Category category) {
        addTimer();

        currentAnswer = category.getQuestion(list.get(questionListIndex)).getAnswer();
        currentOptionA = category.getQuestion(list.get(questionListIndex)).getOption("A");
        currentOptionB = category.getQuestion(list.get(questionListIndex)).getOption("B");
        currentOptionC = category.getQuestion(list.get(questionListIndex)).getOption("C");
        currentOptionD = category.getQuestion(list.get(questionListIndex)).getOption("D");
        currentQuestion = category.getQuestion(list.get(questionListIndex++));

        ques.setText(currentQuestion.getQuestion());
        OptA.setText(currentQuestion.getOption("A"));
        OptB.setText(currentQuestion.getOption("B"));
        OptC.setText(currentQuestion.getOption("C"));
        OptD.setText(currentQuestion.getOption("D"));
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