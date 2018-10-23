package mobiledev.pxl.be.triviaking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
        Log.i("log","Entered On Create");
        setContentView(R.layout.activity_main);
       // getCategories();


       /* findViewById(R.id.main_button).setOnClickListener(new View.OnClickListener() {
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

                ImageView imageView = findViewById(R.id.qrImageView);
                try{

                    QRCodeSupporter.encodeAsBitmap(pieter, imageView);
                } catch (WriterException exception){
                    Log.i("log",exception.getMessage());
                    imageView.setImageDrawable(getDrawable(R.drawable.ic_launcher_background));
                }
            }


        });*/


    }

    public void getCategories() {


        final ApiQueryTask task = new ApiQueryTask();
        task.delegate = this;
        task.execute("https://opentdb.com/api_category.php");
        try {
            Log.i("Tag", result.getString("name"));
        } catch (Exception e) {
            Log.e("Exception", "Json");
        }

    }

    @Override
    public void processFinish(JSONObject result) {
        this.result = result;
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
