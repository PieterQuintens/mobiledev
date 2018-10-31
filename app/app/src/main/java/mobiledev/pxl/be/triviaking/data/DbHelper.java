package mobiledev.pxl.be.triviaking.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "TriviaKing.db";

    private static final int DATABASE_VERSION = 0;

    public DbHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_QUIZ_TABLE = "CREATE TABLE " + DatabaseContract.Quiz.TABLE_NAME + " (" +
                DatabaseContract.Quiz._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DatabaseContract.Quiz.CREATED + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                DatabaseContract.Quiz.CATEGORY + " TEXT NOT NULL," +
                DatabaseContract.Quiz.DATA + " TEXT NOT NULL,"+
                DatabaseContract.Quiz.QUESTIONS + " INTEGER NOT NULL" +
                ")";
        db.execSQL(SQL_CREATE_QUIZ_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.Quiz.TABLE_NAME);
        onCreate(db);
    }
}
