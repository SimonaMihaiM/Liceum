package ro.raduca.liceum.data;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class Database extends SQLiteOpenHelper {

    public SQLiteDatabase sqlite;
    private final String databasePath;
    private final String databaseName;
    private final Context context;

    public Database(Context context) {
        super(context, "liceum", null, 1);
        this.context = context;
        this.databaseName = "liceum.sqlite";
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

    public void createDatabase() {
        this.getReadableDatabase();
        copyDbFromResource();
    }
}
