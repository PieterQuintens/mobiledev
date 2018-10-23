package mobiledev.pxl.be.triviaking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.json.JSONObject;

import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.WriterException;

import org.json.JSONObject;

import mobiledev.pxl.be.triviaking.support.QRCodeSupporter;


public class MainActivity extends AppCompatActivity implements CallbackInterface {
    private JSONObject result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        findViewById(R.id.new_quiz).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNewQuiz();
            }
        });

        findViewById(R.id.scan_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onScanButton();
            }
        });

        findViewById(R.id.history).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onHistoryButton();
            }
        });
    }

    private void onHistoryButton() {
        Intent i = new Intent(this, HistoryActivity.class);
        startActivity(i);
    }

    private void onScanButton() {
        Intent i = new Intent(this, CodeScanActivity.class);
        startActivity(i);
    }

    private void onNewQuiz() {
        Intent i = new Intent(this, NewQuizActivity.class);
        startActivity(i);
    }

    @Override
    public void processFinish(JSONObject result) {
        this.result = result;
        Log.i("Tagtag", result.toString());
    }
}
