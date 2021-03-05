package ro.raduca.liceum.data;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class CategoryDatabase extends SQLiteOpenHelper {

    public SQLiteDatabase sqlite;
    private final String databasePath;
    private final String databaseName;
    private final String tableName;
    private final String uid = "_id";
    private final int version = 1;
    private final Context context;

    public CategoryDatabase(Context context, String tableName) {
        super(context, tableName, null, 1);
        this.context = context;
        this.tableName = tableName;
        this.databaseName = tableName + ".db";
        this.databasePath = context.getDatabasePath(this.databaseName).getPath();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void openDatabase() throws SQLException {
        sqlite = SQLiteDatabase.openDatabase(this.databasePath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    private boolean DBexists() {
//        SQLiteDatabase db = null;
//        try {
//            db = SQLiteDatabase.openDatabase(this.databasePath, null, SQLiteDatabase.OPEN_READWRITE);
//            db.setLocale(Locale.getDefault());
//            db.setVersion(1);
//        } catch (SQLException e) {
//            Log.e("SQLite", "Database not found");
//            return false;
//        }
//        if (db != null)
//            db.close();
//        return db != null;
        return false;
    }

    private void copyDbFromResource() {

        InputStream is;
        OutputStream os;

        try {
            is = context.getAssets().open(databaseName);
            os = new FileOutputStream(this.databasePath);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            os.flush();
            is.close();
            os.close();
        } catch (Exception e) {
            throw new Error("Problem copying database file" + e.getMessage());
        }
    }

    private void createDb() {

        boolean dbExists = DBexists();
        if (!dbExists) {
            this.getReadableDatabase();
            copyDbFromResource();
        }
    }

    public void createDatabase() {
        createDb();
    }

    public String readQuestion(int i) {
        String qField;
        // numele coloanei 2
        String question = "Question";
        Cursor cursor = sqlite.rawQuery("SELECT " + question + " FROM " + tableName + " WHERE "
                + uid + " = " + i + "", null);

        if (cursor.moveToFirst()) {
            qField = cursor.getString(0);
            cursor.close();
        } else
            qField = "";
        return qField;
    }

    public String readOptionA(int i) {
        String qField;
        // numele coloanei 3
        String optionA = "OptionA";
        Cursor cursor = sqlite.rawQuery("SELECT " + optionA + " FROM " + tableName + " WHERE "
                + uid + " = " + i + "", null);
        if (cursor.moveToFirst()) {
            qField = cursor.getString(0);
            cursor.close();
        } else
            qField = "";
        return qField;
    }

    public String readOptionB(int i) {
        String qField;
        // numele coloanei 4
        String optionB = "OptionB";
        Cursor cursor = sqlite.rawQuery("SELECT " + optionB + " FROM " + tableName + " WHERE "
                + uid + " = " + i + "", null);
        if (cursor.moveToFirst()) {
            qField = cursor.getString(0);
            cursor.close();
        } else
            qField = "";
        return qField;
    }

    public String readOptionC(int i) {
        String qField;
        // numele coloanei 5
        String optionC = "OptionC";
        Cursor cursor = sqlite.rawQuery("SELECT " + optionC + " FROM " + tableName + " WHERE "
                + uid + " = " + i + "", null);
        if (cursor.moveToFirst()) {
            qField = cursor.getString(0);
            cursor.close();
        } else
            qField = "";
        return qField;
    }

    public String readOptionD(int i) {
        String qField;
        // numele coloanei 6
        String optionD = "OptionD";
        Cursor cursor = sqlite.rawQuery("SELECT " + optionD + " FROM " + tableName + " WHERE "
                + uid + " = " + i + "", null);
        if (cursor.moveToFirst()) {
            qField = cursor.getString(0);
            cursor.close();
        } else
            qField = "";
        return qField;
    }

    public String readAnswer(int i) {
        String qField;
        // numele coloanei 7
        String answer = "Answer";
        Cursor cursor = sqlite.rawQuery("SELECT " + answer + " FROM " + tableName + " WHERE "
                + uid + " = " + i + "", null);
        if (cursor.moveToFirst()) {
            qField = cursor.getString(0);
            cursor.close();
        } else
            qField = "";
        return qField;
    }
}
