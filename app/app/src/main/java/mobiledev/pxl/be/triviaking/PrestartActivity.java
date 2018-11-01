package mobiledev.pxl.be.triviaking;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.google.zxing.WriterException;

import org.json.JSONObject;

import mobiledev.pxl.be.triviaking.data.DatabaseContract;
import mobiledev.pxl.be.triviaking.data.DbHelper;
import mobiledev.pxl.be.triviaking.support.ApiQueryTask;
import mobiledev.pxl.be.triviaking.support.CallbackInterface;
import mobiledev.pxl.be.triviaking.support.QRCodeSupporter;
import mobiledev.pxl.be.triviaking.support.Remembrance;

public class PrestartActivity extends AppCompatActivity implements CallbackInterface {
    JSONObject quizResult;
    String uriString;
    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pre_start);

        ApiQueryTask task = new ApiQueryTask();
        task.delegate = this;
        StringBuilder uri = new StringBuilder("https://opentdb.com/api.php?amount=");
        uri.append(Remembrance.questions);
        uri.append("&category=");
        uri.append(Remembrance.categoryId);
        if(!Remembrance.difficulty.equalsIgnoreCase("any")) {
            uri.append("&difficulty=");
            uri.append(Remembrance.difficulty);
        }
        uriString = uri.toString();
        Log.i("uri", uriString);
        task.execute(uriString);

        DbHelper helper = new DbHelper(this);

        mDb = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.Quiz.CATEGORY, Remembrance.category);
        values.put(DatabaseContract.Quiz.DATA, uriString);
        values.put(DatabaseContract.Quiz.QUESTIONS, Remembrance.questions);
        values.put(DatabaseContract.Quiz.DIFFICULTY, Remembrance.difficulty);

        mDb.insert(DatabaseContract.Quiz.TABLE_NAME, null, values);
    }

    @Override
    public void processFinish(JSONObject result) {
        ImageView view = findViewById(R.id.share_qr);
        try {
            QRCodeSupporter.encodeAsBitmap(uriString, view);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        quizResult = result;


    }
}
