package mobiledev.pxl.be.triviaking.fragmentsupport;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mobiledev.pxl.be.triviaking.data.DatabaseContract;
import mobiledev.pxl.be.triviaking.data.DbHelper;

public class QuizContent {
    private static SQLiteDatabase mDb;

    public static Cursor ITEMS;

    public static final Map<String, Quiz> ITEM_MAP = new HashMap<String, Quiz>();

    public static final void  loadItems(Context context){
        DbHelper helper = new DbHelper(context);

        mDb = helper.getReadableDatabase();
        ITEMS = mDb.query(DatabaseContract.Quiz.TABLE_NAME, null, null, null, null, null, null, null);

    }
}
