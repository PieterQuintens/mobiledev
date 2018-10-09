package mobiledev.pxl.be.triviaking;

import android.annotation.SuppressLint;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        final ApiQueryTask task = new ApiQueryTask();
        task.execute("https://opentdb.com/api_token.php?command=request");

        findViewById(R.id.main_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pieter = getString(R.string.hey_pieter);
                String dary = getString(R.string.hey_dary);

                TextView view = findViewById(R.id.main_textview);

                if(view.getText().equals(pieter)){
                    view.setText(task.getToken());
                } else {
                    view.setText(pieter);
                }
            }
        });
    }

}
