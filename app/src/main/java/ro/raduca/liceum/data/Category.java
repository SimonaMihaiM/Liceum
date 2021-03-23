package ro.raduca.liceum.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Category {
    private int id;
    private String name;
    private String translatedName;
    private ArrayList<Question> questions;

    public Category(int id, String name, String translatedName) {
        this.id = id;
        this.name = name;
        this.translatedName = translatedName;
    }

    public static ArrayList<Category> getAll(SQLiteDatabase sqlite) {
        Cursor cursor = sqlite.rawQuery("SELECT id, table_name, translated_name FROM contents ORDER BY screen_order", null);
        ArrayList<Category> categories = new ArrayList<>();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("table_name"));
            String translatedName = cursor.getString(cursor.getColumnIndex("translated_name"));
            Category currentCategory = new Category(id, name, translatedName);
            ArrayList<Question> questions = Question.getAllCategory(sqlite, name);
            currentCategory.setQuestions(questions);
            categories.add(currentCategory);
        }
        cursor.close();

        return categories;
    }

    public String getTranslatedName() {
        return this.translatedName;
    }

    public String getName() {
        return this.name;
    }

    public Question getQuestion(int index) {
        for (Question question : this.questions) {
            if (question.getId() == index) {
                return question;
            }
        }
        return this.questions.get(0);
    }

    public ArrayList<Question> getQuestions() {
        return this.questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public List<Integer> getRandomizedQuestionIds(int maxQuestions) {
        List list = new ArrayList<Integer>();
        for (Question question : this.questions) {
            list.add(question.getId());
        }
        Collections.shuffle(list);
        return list.subList(0, maxQuestions);
    }
}
