package mobiledev.pxl.be.triviaking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.json.JSONObject;

import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import mobiledev.pxl.be.triviaking.support.CallbackInterface;
import mobiledev.pxl.be.triviaking.support.Remembrance;


public class MainActivity extends AppCompatActivity implements CallbackInterface, View.OnClickListener {
    private JSONObject result;
    private IntentIntegrator qrScan;

    @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_main);

        qrScan = new IntentIntegrator(this);

        findViewById(R.id.new_quiz).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNewQuiz();
            }
        });

        findViewById(R.id.scan_button).setOnClickListener(this);

        findViewById(R.id.history).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onHistoryButton();
            }
        });
    }

    private void onHistoryButton() {
        Intent i = new Intent(this, QuizListActivity.class);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                try {
                    Toast.makeText(this,result.getContents(), Toast.LENGTH_LONG).show();
                    Intent i = new Intent(this, PrestartActivity.class);
                    String scanResult = result.getContents();
                    i.putExtra("uri", scanResult);
                    startActivity(i);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    @Override
    public void onClick(View view) {
        qrScan.initiateScan();
    }
}
