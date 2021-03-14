package ro.raduca.liceum.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

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

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public static ArrayList<Category> getAll(SQLiteDatabase sqlite) {
        Cursor cursor = sqlite.rawQuery("SELECT id, table_name, translated_name FROM contents ORDER BY screen_order", null);
        ArrayList<Category> categories = new ArrayList<>();

        while(cursor.moveToNext()) {
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

    public Question getQuestion(int index) {
        return this.questions.get(index);
    }
}
