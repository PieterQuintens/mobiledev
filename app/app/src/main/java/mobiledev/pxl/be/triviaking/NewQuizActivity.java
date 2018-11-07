package mobiledev.pxl.be.triviaking;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import mobiledev.pxl.be.triviaking.support.ApiQueryTask;
import mobiledev.pxl.be.triviaking.support.CallbackInterface;
import mobiledev.pxl.be.triviaking.support.Remembrance;

public class NewQuizActivity extends AppCompatActivity implements CallbackInterface {

    private JSONObject result;
    private HashMap<String,Integer> categories;
    private Spinner categorySpinner;
    private String[] difficulties = { "any" , "easy" , "medium" , "hard"};
    private Spinner diffSpinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getCategories();

        diffSpinner = findViewById(R.id.difficulty_spinner);
        diffSpinner.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, difficulties));

        findViewById(R.id.generate_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Remembrance.category = categorySpinner.getSelectedItem().toString();
                Remembrance.categoryId = categories.get(Remembrance.category);
                Remembrance.difficulty = diffSpinner.getSelectedItem().toString();
                int numQuestions = Integer.parseInt(((EditText) findViewById(R.id.num_questions_setup)).getText().toString());
                if(numQuestions > 30 || numQuestions == 0) {
                    showToast(numQuestions);
                } else {
                    generateQuiz(numQuestions);
                }
            }
        });
    }

    public void getCategories() {
        final ApiQueryTask task = new ApiQueryTask();
        task.delegate = this;
        task.execute("https://opentdb.com/api_category.php");
    }

    @Override
    public void processFinish(JSONObject result) {
        this.result = result;
        try {
            Log.i("Tagtag", result.toString());
            JSONArray array = result.getJSONArray("trivia_categories");
            categories = new HashMap<>();

            for (int i = 0 ; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                categories.put(object.getString("name"), object.getInt("id"));
                Log.i("Tagtag", array.getJSONObject(i).getString("name"));
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, categories.keySet().toArray(new String[categories.size()]));

            categorySpinner = (Spinner) findViewById(R.id.category_spinner);
            categorySpinner.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("Tagtag", "Weer JsonError");
        }
    }

    private void showToast(int numQuestions) {
        if(numQuestions == 0) {
            Toast.makeText(this, "Please set number of questions.", Toast.LENGTH_LONG).show();
            return;
        }
        Toast.makeText(this, "Please set number of questions to lower than thirty", Toast.LENGTH_LONG).show();
    }

    private void generateQuiz(int numQuestions){
        Remembrance.questions = numQuestions;
        Remembrance.score = 0;
        Intent i = new Intent(this, PrestartActivity.class);
        startActivity(i);

    }
}
