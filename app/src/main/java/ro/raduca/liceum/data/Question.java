package ro.raduca.liceum.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.ArrayMap;

import java.util.ArrayList;

public class Question {
    private int id;
    private String question;
    private ArrayMap<String, String> options;
    private String correctOption;

    public Question(int id, String question, ArrayMap<String, String> options, String correctOption) {
        this.id=id;
        this.question=question;
        this.options=options;
        this.correctOption=correctOption;
    }

    public static ArrayList<Question> getAllCategory(SQLiteDatabase sqlite, String categoryName) {
        Cursor cursor = sqlite.rawQuery("SELECT id, question, option_a, option_b, option_c, option_d, answer FROM "+categoryName, null);
        ArrayList<Question> questions = new ArrayList<>();
        while(cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String question = cursor.getString(cursor.getColumnIndex("question"));
            ArrayMap <String, String> options = new ArrayMap<>();
            options.put("A", cursor.getString(cursor.getColumnIndex("option_a")));
            options.put("B", cursor.getString(cursor.getColumnIndex("option_b")));
            options.put("C", cursor.getString(cursor.getColumnIndex("option_c")));
            options.put("D", cursor.getString(cursor.getColumnIndex("option_d")));
            String correctOption = cursor.getString(cursor.getColumnIndex("answer"));
            Question currentQuestion = new Question(id, question, options, correctOption);
            questions.add(currentQuestion);
        }
        cursor.close();
        return questions;
    }

    public int getId() {
        return this.id;
    }

    public String getQuestion(){
        return this.question;
    }

    public String getAnswer() {
        return this.correctOption;
    }

    public String getOption(String option) {
        return options.get(option);
    }
}
