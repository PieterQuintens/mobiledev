package mobiledev.pxl.be.triviaking;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class NewQuizActivity extends AppCompatActivity implements CallbackInterface {

    private JSONObject result;
    private HashMap<String,Integer> categories;
    private String[] difficulties = { "any" , "easy" , "medium" , "hard"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getCategories();

        Spinner spinner = findViewById(R.id.difficulty_spinner);
        spinner.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, difficulties));
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

            Spinner spinner = (Spinner) findViewById(R.id.category_spinner);
            spinner.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("Tagtag", "Weer JsonError");
        }
    }
}
