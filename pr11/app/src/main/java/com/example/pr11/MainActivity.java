package com.example.pr11;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    TextView view_city;
    TextView view_temp;
    TextView view_desc;

    ImageView view_weather;
    EditText search;
    FloatingActionButton search_floating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view_city = (TextView) findViewById(R.id.tvTown);
        view_temp = (TextView) findViewById(R.id.tvTemp);
        view_desc = (TextView) findViewById(R.id.tvDesc);
        view_city.setText("");
        view_temp.setText("");
        view_desc.setText("");

        view_weather = findViewById(R.id.tvImage);
        search = findViewById(R.id.etName);
        search_floating = findViewById(R.id.btnSearch);

        search_floating.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                InputMethodManager imn = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imn.hideSoftInputFromWindow(getCurrentFocus().getRootView().getWindowToken(), 0);
                api_key(String.valueOf(search.getText()));
            }

            private void api_key(final String City) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("https://api.openweathermap.org/data/2.5/weather?q="+City+"&appid=23df53519973b7a0f5b39b79e5b9aec4&units=metric&lang=ru")
                        .get()
                        .build();
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                try {
                    Response response = client.newCall(request).execute();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            String responseData = response.body().string();
                            try {
                                JSONObject json = new JSONObject(responseData);
                                JSONArray array = json.getJSONArray("weather");
                                JSONObject object = array.getJSONObject(0);

                                String description = object.getString("description");
                                String icons = object.getString("icon");
                                JSONObject temp1 = json.getJSONObject("main");
                                Double Temperature = temp1.getDouble("temp");

                                setText(view_city, City);
                                String temps = (Math.round(Temperature) + " °С");
                                setText(view_temp, temps);
                                setText(view_desc, description);
                                setImage(view_weather, icons);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }

                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {

                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            private void setText(final TextView text, final String value) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        text.setText(value);
                    }
                });
            }

            private void setImage(final ImageView imageView, final String value) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        switch (value) {
                            case "01d":
                                imageView.setImageDrawable(getResources().getDrawable(R.drawable.icon1));
                                break;
                            case "01n":
                                imageView.setImageDrawable(getResources().getDrawable(R.drawable.icon1));
                                break;
                            case "02d":
                                imageView.setImageDrawable(getResources().getDrawable(R.drawable.icon2));
                                break;
                            case "02n":
                                imageView.setImageDrawable(getResources().getDrawable(R.drawable.icon2));
                                break;
                            case "03d":
                                imageView.setImageDrawable(getResources().getDrawable(R.drawable.icon3));
                                break;
                            case "03n":
                                imageView.setImageDrawable(getResources().getDrawable(R.drawable.icon3));
                                break;
                            case "04d":
                                imageView.setImageDrawable(getResources().getDrawable(R.drawable.icon4));
                                break;
                            case "04n":
                                imageView.setImageDrawable(getResources().getDrawable(R.drawable.icon4));
                                break;
                            case "09d":
                                imageView.setImageDrawable(getResources().getDrawable(R.drawable.icon5));
                                break;
                            case "09n":
                                imageView.setImageDrawable(getResources().getDrawable(R.drawable.icon5));
                                break;
                            case "10d":
                                imageView.setImageDrawable(getResources().getDrawable(R.drawable.icon6));
                                break;
                            case "10n":
                                imageView.setImageDrawable(getResources().getDrawable(R.drawable.icon6));
                                break;
                            case "11d":
                                imageView.setImageDrawable(getResources().getDrawable(R.drawable.icon7));
                                break;
                            case "11n":
                                imageView.setImageDrawable(getResources().getDrawable(R.drawable.icon7));
                                break;
                            default:
                                imageView.setImageDrawable(getResources().getDrawable(R.drawable.weather));
                        }
                    }
                });
            }
        });

    }
}