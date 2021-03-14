package ro.raduca.liceum;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Objects;

import ro.raduca.liceum.data.Category;
import ro.raduca.liceum.data.Database;


public class ScoreCard extends AppCompatActivity {

    private ArrayList<TextView> labels, values;
    private ArrayList<Category> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_card);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        SharedPreferences sharedPreferences = getSharedPreferences("Score", Context.MODE_PRIVATE);

        labels = new ArrayList<>();
        values = new ArrayList<>();

        Database database = new Database(this);
        database.createDatabase();
        database.openDatabase();
        database.getWritableDatabase();

        categories = Category.getAll(database.sqlite);

        database.close();
        try {
            labels.add((TextView) findViewById(R.id.score_label_1));
            labels.add((TextView) findViewById(R.id.score_label_2));
            labels.add((TextView) findViewById(R.id.score_label_3));
            labels.add((TextView) findViewById(R.id.score_label_4));
            labels.add((TextView) findViewById(R.id.score_label_5));
            labels.add((TextView) findViewById(R.id.score_label_6));
            labels.add((TextView) findViewById(R.id.score_label_7));
            labels.add((TextView) findViewById(R.id.score_label_8));
            labels.add((TextView) findViewById(R.id.score_label_9));
            labels.add((TextView) findViewById(R.id.score_label_10));
            labels.add((TextView) findViewById(R.id.score_label_11));

            values.add((TextView) findViewById(R.id.score_value_1));
            values.add((TextView) findViewById(R.id.score_value_2));
            values.add((TextView) findViewById(R.id.score_value_3));
            values.add((TextView) findViewById(R.id.score_value_4));
            values.add((TextView) findViewById(R.id.score_value_5));
            values.add((TextView) findViewById(R.id.score_value_6));
            values.add((TextView) findViewById(R.id.score_value_7));
            values.add((TextView) findViewById(R.id.score_value_8));
            values.add((TextView) findViewById(R.id.score_value_9));
            values.add((TextView) findViewById(R.id.score_value_10));
            values.add((TextView) findViewById(R.id.score_value_11));

            for (int i=0; i<categories.size(); i++){
                labels.get(i).setText(categories.get(i).getTranslatedName());
                values.get(i).setText(String.valueOf(sharedPreferences.getInt(categories.get(i).getName(),0)));
            }

        } catch (Exception e) {
            Toast.makeText(ScoreCard.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}
