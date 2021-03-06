package mobiledev.pxl.be.triviaking;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.WriterException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import mobiledev.pxl.be.triviaking.data.DatabaseContract;
import mobiledev.pxl.be.triviaking.data.DbHelper;
import mobiledev.pxl.be.triviaking.support.ApiQueryTask;
import mobiledev.pxl.be.triviaking.support.CallbackInterface;
import mobiledev.pxl.be.triviaking.support.QRCodeSupporter;
import mobiledev.pxl.be.triviaking.support.Remembrance;

public class PrestartActivity extends AppCompatActivity implements CallbackInterface {
    JSONArray quizResult;
    String uriString;
    private boolean loaded = false;
    private boolean errorResponse = false;
    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pre_start);
        String scanUri = getIntent().getStringExtra("uri");

        if(scanUri == null) {
            ApiQueryTask task = new ApiQueryTask();
            task.delegate = this;
            StringBuilder uri = new StringBuilder("https://opentdb.com/api.php?amount=");
            uri.append(Remembrance.questions);
            uri.append("&category=");
            uri.append(Remembrance.categoryId);
            if (!Remembrance.difficulty.equalsIgnoreCase("any")) {
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
        } else {
            Remembrance.questions = Integer.parseInt(scanUri.substring(scanUri.indexOf('=')+1, scanUri.indexOf('&')));
            ApiQueryTask task = new ApiQueryTask();
            task.delegate = this;
            uriString = scanUri;
            task.execute(scanUri);
        }

        findViewById(R.id.start_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(loaded){
                    openQuestionActivity();
                } else if (errorResponse){
                    showNewSettingsToast();
                } else {
                    showWaitToast();
                }
            }
        });
    }

    @Override
    public void processFinish(JSONObject result) {
        try {
            int responseCode = result.getInt("response_code");
            if(responseCode!=0){
                errorResponse = true;
                return;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
        ImageView view = findViewById(R.id.share_qr);
        try {
            QRCodeSupporter.encodeAsBitmap(uriString, view);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        try {
            quizResult = result.getJSONArray("results");
            Remembrance.quiz = quizResult;
            loaded = true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showWaitToast() {
        Toast.makeText(this,"Please wait until the quiz is loaded...", Toast.LENGTH_SHORT).show();
    }

    private void showNewSettingsToast() {
        Toast.makeText(this, "Unable to find enough questions for current settings! Change the settings!", Toast.LENGTH_LONG).show();
    }

    private void openQuestionActivity() {
        Intent i = new Intent(this, QuestionActivity.class);
        Remembrance.questionNumber = 0;
        Remembrance.score = 0;
        startActivity(i);
    }
}
