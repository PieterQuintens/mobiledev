package mobiledev.pxl.be.triviaking;

import android.annotation.SuppressLint;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        try {
            getCategories();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        findViewById(R.id.main_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pieter = getString(R.string.hey_pieter);
                String dary = getString(R.string.hey_dary);

                TextView view = findViewById(R.id.main_textview);

                if (view.getText().equals(pieter)) {
                    view.setText(dary);
                } else {
                    view.setText(pieter);
                }
            }
        });
    }

    public void getCategories() throws InterruptedException {


        final ApiQueryTask task = new ApiQueryTask();

        synchronized (task){
            task.execute("https://opentdb.com/api_category.php");

            if(task == null){
                task.wait();
            } else {
                Log.i("test " ,task.getJsonObject().toString());
            }
        }

//        try {
//            JSONArray array = new JSONArray(task.getJsonObject());
//
//            for(int i = 0 ; i < array.length() ; i++){
//                Log.i("getCategories: ", array.getJSONObject(i).getString("name"));
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }


    }
}
