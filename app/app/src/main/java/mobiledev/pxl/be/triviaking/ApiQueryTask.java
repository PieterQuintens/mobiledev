package mobiledev.pxl.be.triviaking;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class ApiQueryTask extends AsyncTask<String, Void, String> {
    public CallbackInterface delegate = null;

    private JSONObject jsonObject;

    @Override
    protected String doInBackground(String... strings) {
        String results = "";
        URL url;
        HttpsURLConnection urlConnection = null;

        try {
            url = new URL(strings[0]);

            urlConnection = (HttpsURLConnection) url.openConnection();

            InputStream in = urlConnection.getInputStream();

            InputStreamReader reader = new InputStreamReader(in);

            int data = reader.read();

            while (data != -1) {
                char current = (char) data;

                results += current;

                data = reader.read();
            }

            return results;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try {
            jsonObject = new JSONObject(s);
            delegate.processFinish(jsonObject);
            Log.i("token", s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
