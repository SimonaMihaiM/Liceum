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
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ro.raduca.liceum.data.Category;
import ro.raduca.liceum.data.Database;
import ro.raduca.liceum.data.Question;

public class Questions extends AppCompatActivity {

    public int visibility = 0;
    public int questionListIndex = 0, questionIndex = 0, currentScore = 0;
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
    List<Integer> list;
    Toast toast;
    long timerDuration = 15000;
    long timerStep = 1000;
    CountDownTimer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(100);
        progressBar.getProgressDrawable().setColorFilter(ContextCompat.getColor(this, R.color.progressOkay), android.graphics.PorterDuff.Mode.SRC_IN);
        progressBar.setKeepScreenOn(true);
        progressBar.setScaleY(3f);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        selectedCategoru = intent.getStringExtra(Navigation.Message);
        toast = new Toast(this);

        Database database = new Database(this);
        database.createDatabase();
        database.openDatabase();
        database.getWritableDatabase();

        categories = Category.getAll(database.sqlite);

        database.close();

        int selectedCategoryIndex = Integer.parseInt(selectedCategoru.substring(1)) - 1;

        list = categories.get(selectedCategoryIndex).getRandomizedQuestionIds(maxQuestions);

        OptA = findViewById(R.id.OptionA);
        OptB = findViewById(R.id.OptionB);
        OptC = findViewById(R.id.OptionC);
        OptD = findViewById(R.id.OptionD);
        ques = findViewById(R.id.Questions);
        play_button = findViewById(R.id.play_button);
    }

    public void addTimer() {
        if (timer != null) {
            timer.cancel();
        }
        timer = new CountDownTimer(timerDuration, timerStep) {
            int progressBarValue = 100;

            @Override
            public void onTick(long millisUntilFinished) {
                progressBarValue = (int) (progressBarValue - 100 * timerStep / timerDuration);
                int progressBarColor = ContextCompat.getColor(getContext(), R.color.progressOkay);
                if (progressBarValue <= 66) {
                    progressBarColor = ContextCompat.getColor(getContext(), R.color.progressWarning);
                }
                if (progressBarValue <= 33) {
                    progressBarColor = ContextCompat.getColor(getContext(), R.color.progressDanger);
                }

                progressBar.getProgressDrawable().setColorFilter(progressBarColor, android.graphics.PorterDuff.Mode.SRC_IN);

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

    public Context getContext() {
        return this;
    }

    public void writeScore() {
        final SharedPreferences shared = getSharedPreferences("Score", Context.MODE_PRIVATE);
        toast.cancel();
        SharedPreferences.Editor editor = shared.edit();
        editor.putInt("Questions", questionIndex).apply();

        if (selectedCategoru.equals("c1") && shared.getInt(categories.get(0).getName(), 0) < currentScore)
            editor.putInt(categories.get(0).getName(), currentScore * 10).apply();
        else if (selectedCategoru.equals("c2") && shared.getInt(categories.get(1).getName(), 0) < 1)
            editor.putInt(categories.get(1).getName(), currentScore * 10).apply();
        else if (selectedCategoru.equals("c3") && shared.getInt(categories.get(2).getName(), 0) < 1)
            editor.putInt(categories.get(2).getName(), currentScore * 10).apply();
        else if (selectedCategoru.equals("c4") && shared.getInt(categories.get(3).getName(), 0) < 1)
            editor.putInt(categories.get(3).getName(), currentScore * 10).apply();
        else if (selectedCategoru.equals("c5") && shared.getInt(categories.get(4).getName(), 0) < 1)
            editor.putInt(categories.get(4).getName(), currentScore * 10).apply();
        else if (selectedCategoru.equals("c6") && shared.getInt(categories.get(5).getName(), 0) < 1)
            editor.putInt(categories.get(5).getName(), currentScore * 10).apply();
        else if (selectedCategoru.equals("c7") && shared.getInt(categories.get(6).getName(), 0) < 1)
            editor.putInt(categories.get(6).getName(), currentScore * 10).apply();
        else if (selectedCategoru.equals("c8") && shared.getInt(categories.get(7).getName(), 0) < 1)
            editor.putInt(categories.get(7).getName(), currentScore * 10).apply();
        else if (selectedCategoru.equals("c9") && shared.getInt(categories.get(8).getName(), 0) < 1)
            editor.putInt(categories.get(8).getName(), currentScore * 10).apply();
        else if (selectedCategoru.equals("c10") && shared.getInt(categories.get(9).getName(), 0) < 1)
            editor.putInt(categories.get(9).getName(), currentScore * 10).apply();
        else if (selectedCategoru.equals("c11") && shared.getInt(categories.get(10).getName(), 0) < 1)
            editor.putInt(categories.get(10).getName(), currentScore * 10).apply();
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
        if (timer != null) {
            timer.cancel();
        }

        OptA.setBackgroundColor(ContextCompat.getColor(this, R.color.buttonBackgroundBase));
        OptB.setBackgroundColor(ContextCompat.getColor(this, R.color.buttonBackgroundBase));
        OptC.setBackgroundColor(ContextCompat.getColor(this, R.color.buttonBackgroundBase));
        OptD.setBackgroundColor(ContextCompat.getColor(this, R.color.buttonBackgroundBase));

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
                        OptA.setBackgroundColor(ContextCompat.getColor(this, R.color.progressOkay));
//                        Snackbar.make(v, "         Correct ☺", Snackbar.LENGTH_SHORT).show();
                        currentScore++;
                    } else {
                        OptA.setBackgroundColor(ContextCompat.getColor(this, R.color.progressOkay));
                        v.setBackgroundColor(ContextCompat.getColor(this, R.color.progressDanger));
//                        Snackbar.make(v, "Incorrect\t      Answer : " + currentOptionA + "", Snackbar.LENGTH_SHORT).show();
                    }
                    break;
                case "B":
                    if (v.getId() == R.id.OptionB) {
                        OptB.setBackgroundColor(ContextCompat.getColor(this, R.color.progressOkay));
                        //Here we use the snackbar because if we use the toast then they will be stacked an user cannot idetify which questions answer is it showing
//                        Snackbar.make(v, "         Correct ☺", Snackbar.LENGTH_SHORT).show();

                        currentScore++;
                    } else {
                        v.setBackgroundColor(ContextCompat.getColor(this, R.color.progressDanger));
                        OptB.setBackgroundColor(ContextCompat.getColor(this, R.color.progressOkay));
//                        Snackbar.make(v, "Incorrect\t      Answer : " + currentOptionB + "", Snackbar.LENGTH_SHORT).show();
                    }
                    break;
                case "C":
                    if (v.getId() == R.id.OptionC) {
                        OptC.setBackgroundColor(ContextCompat.getColor(this, R.color.progressOkay));
                        //Here we use the snackbar because if we use the toast then they will be stacked an user cannot idetify which questions answer is it showing
//                        Snackbar.make(v, "         Correct ☺", Snackbar.LENGTH_SHORT).show();

                        currentScore++;
                    } else {
                        OptC.setBackgroundColor(ContextCompat.getColor(this, R.color.progressOkay));
                        v.setBackgroundColor(ContextCompat.getColor(this, R.color.progressDanger));
//                        Snackbar.make(v, "Incorrect\t      Answer : " + currentOptionC + "", Snackbar.LENGTH_SHORT).show();
                    }
                    break;
                case "D":
                    if (v.getId() == R.id.OptionD) {
                        OptD.setBackgroundColor(ContextCompat.getColor(this, R.color.progressOkay));
                        //Here we use the snackbar because if we use the toast then they will be stacked an user cannot idetify which questions answer is it showing
//                        Snackbar.make(v, "         Correct ☺", Snackbar.LENGTH_SHORT).show();

                        currentScore++;
                    } else {
                        OptD.setBackgroundColor(ContextCompat.getColor(this, R.color.progressOkay));
                        v.setBackgroundColor(ContextCompat.getColor(this, R.color.progressDanger));
//                        Snackbar.make(v, "Incorrect\t      Answer : " + currentOptionD + "", Snackbar.LENGTH_SHORT).show();
                    }
                    break;
            }
            v.postDelayed(new Runnable() {
                @Override
                public void run() {
                    displayNextQuestion();
                }
            }, 2000);
        } else {
            displayNextQuestion();
        }


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