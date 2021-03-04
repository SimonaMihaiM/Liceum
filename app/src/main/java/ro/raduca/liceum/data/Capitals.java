package ro.raduca.liceum.data;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;

public class Capitals extends SQLiteOpenHelper {

    private static final String Database_path = "/data/data/ro.raduca.liceum/databases/";
    private static final String Database_name = "capitals.db"; // nume database din assets
    private static final String Table_name = "capitals"; // numele tabelului
    private static final String uid = "_id"; // numele primei coloane
    private static final String Question = "Question"; // numele coloanei 2
    private static final String OptionA = "OptionA"; // numele coloanei 3
    private static final String OptionB = "OptionB"; // numele coloanei 4
    private static final String OptionC = "OptionC"; // numele coloanei 5
    private static final String OptionD = "OptionD"; // numele coloanei 6
    private static final String Answer = "Answer"; // numele coloanei 7
    private static final int version = 1; // versiunea bazei de date
    public SQLiteDatabase sqlite; // obiect de tip SQLiteDatabase
    private Context context; // obiect de tip context pentru a prelua din Questions

    public Capitals(Context context) {
        super(context, Database_name, null, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // deja am creat database-ul
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // nu avem cod aici. database-ul este creat
    }

    public void openDatabase() throws SQLException {
        String myPath = Database_path + Database_name;
        sqlite = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    private boolean DBexists() {
        SQLiteDatabase db = null;
        try {
            String databasePath = Database_path + Database_name;
            db = SQLiteDatabase.openDatabase(databasePath, null, SQLiteDatabase.OPEN_READWRITE);
            db.setLocale(Locale.getDefault());
            db.setVersion(1);
        } catch (SQLException e) {
            Log.e("SQLite", "Database not found");
        }
        if (db != null)
            db.close();
        return db!= null;
    }

    private void copyDbFromResource() {

        InputStream is;
        OutputStream os;
        String filePath = Database_path + Database_name;

        try {
            is = context.getAssets().open(Database_name);
            os = new FileOutputStream(filePath);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            os.flush();
            is.close();
            os.close();
        } catch (IOException e) {
            throw new Error("Problem copying database file");
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

    public String readQuestion(int i){
        String qField;
        Cursor cursor = sqlite.rawQuery("SELECT " + Question + " FROM " + Table_name + " WHERE "
                + uid + " = " + i + "", null);

        if (cursor.moveToFirst()) {
            qField = cursor.getString(0);
            cursor.close();
        }
        else
            qField = "";
        return qField;
    }

    public String readOptionA(int i) {
        String qField;
        Cursor cursor = sqlite.rawQuery("SELECT " + OptionA + " FROM " + Table_name + " WHERE "
                + uid + " = " + i + "", null);
        if (cursor.moveToFirst()) {
            qField = cursor.getString(0);
            cursor.close();
        }
        else
            qField = "";
        return qField;
    }

    public String readOptionB(int i) {
        String qField;
        Cursor cursor = sqlite.rawQuery("SELECT " + OptionB + " FROM " + Table_name + " WHERE "
                + uid + " = " + i + "", null);
        if (cursor.moveToFirst()) {
            qField = cursor.getString(0);
            cursor.close();
        }
        else
            qField = "";
        return qField;
    }

    public String readOptionC(int i) {
        String qField;
        Cursor cursor = sqlite.rawQuery("SELECT " + OptionC + " FROM " + Table_name + " WHERE "
                + uid + " = " + i + "", null);
        if (cursor.moveToFirst()) {
            qField = cursor.getString(0);
            cursor.close();
        }
        else
            qField = "";
        return qField;
    }

    public String readOptionD(int i) {
        String qField;
        Cursor cursor = sqlite.rawQuery("SELECT " + OptionD + " FROM " + Table_name + " WHERE "
                + uid + " = " + i + "", null);
        if (cursor.moveToFirst()) {
            qField = cursor.getString(0);
            cursor.close();
        }
        else
            qField = "";
        return qField;
    }

    public String readAnswer(int i) {
        String qField;
        Cursor cursor = sqlite.rawQuery("SELECT " + Answer + " FROM " + Table_name + " WHERE "
                + uid + " = " + i + "", null);
        if (cursor.moveToFirst()) {
            qField = cursor.getString(0);
            cursor.close();
        }
        else
            qField = "";
        return qField;
    }
}
