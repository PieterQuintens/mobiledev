package mobiledev.pxl.be.triviaking;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NewQuizActivity extends AppCompatActivity implements CallbackInterface {

    private JSONObject result;
    private ArrayList<String> categories;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getCategories();
        setContentView(R.layout.activity_settings);
    }

    public void getCategories() {
        final ApiQueryTask task = new ApiQueryTask();
        task.delegate = this;
        task.execute("https://opentdb.com/api_category.php");
        try {
            Log.i("testTag", (String) result.toString());
        } catch (Exception e) {
            Log.e("Exception", "Json");
        }
    }

    @Override
    public void processFinish(JSONObject result) {
        this.result = result;
        try {
            Log.i("Tagtag", result.toString());
            JSONArray array = result.getJSONArray("trivia_categories");
            categories = new ArrayList<>();

            for (int i = 0 ; i < array.length(); i++) {
                categories.add(array.getJSONObject(i).getString("name"));
                Log.i("Tagtag", array.getJSONObject(i).getString("name"));
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, categories);

            Spinner spinner = (Spinner) findViewById(R.id.category_spinner);
            spinner.setAdapter(adapter);

        } catch (JSONException e) {
            Log.e("Tagtag", "Weer JsonError");
        }
    }
}
