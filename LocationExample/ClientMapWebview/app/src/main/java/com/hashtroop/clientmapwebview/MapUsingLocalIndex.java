package com.hashtroop.clientmapwebview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MapUsingLocalIndex extends Activity {
    private WebView mapView = null;
    String TAG = "MapUsingLocalIndex";

    HttpURLConnection urlConnection;
    double lat = 0.0;
    double lon = 0.0;
    String date_time = "";
    ProgressBar progress;
    TextView tvcount;
    Button bloc;
    boolean conti = true;
    boolean camemove = true;


    @SuppressLint({"SetTextI18n", "SetJavaScriptEnabled"})
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_using_local_index);
        progress = (ProgressBar) findViewById(R.id.progress);
        tvcount = (TextView) findViewById(R.id.tvcount);
        mapView = (WebView) findViewById(R.id.mapView);
        bloc = (Button) findViewById(R.id.bloc);
        mapView.getSettings().setJavaScriptEnabled(true);
        mapView.loadUrl("file:///android_asset/index.html");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Thread thread = new Thread(null, loadMoreListItems);
                thread.start();
                t.start();
            }
        }, 3000);
        bloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(updatecamera);
            }
        });
    }

    Runnable updatelocation = new Runnable() {
        @Override
        public void run() {
            if (mapView != null) {
                mapView.loadUrl("javascript:updateLocationsinglemarker(" + lat + "," + lon + ",\"" + date_time + "\")");
            }
            Toast.makeText(MapUsingLocalIndex.this, "Location Updated", Toast.LENGTH_SHORT).show();
        }
    };


    Runnable updatecamera = new Runnable() {
        @Override
        public void run() {
            if (mapView != null) {
                mapView.loadUrl("javascript:updateCamera(" + lat + "," + lon + ",\"" + gettime() + "\")");
            }
            CharSequence text = "GPS Location Updated";
            Toast.makeText(MapUsingLocalIndex.this, text, Toast.LENGTH_SHORT).show();
        }
    };

    Thread t = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                while (conti) {
                    for (int i = 0; i <= 5000; i += 1000) {
                        Thread.sleep(1000);
                        Handler handler = new Handler(getMainLooper());
                        final int finalI = i;
                        handler.post(new Runnable() {
                            public void run() {
                                int temp = (5000 - finalI) / 1000;
                                tvcount.setText(String.valueOf(temp));
                                Log.d("after ", String.valueOf(temp));
                            }
                        });
                    }
                    Thread thread = new Thread(null, loadMoreListItems);
                    thread.start();
                    Log.d("Map ", "location access");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });
    private Runnable loadMoreListItems = new Runnable() {
        String result = "";

        public void run() {
            try {
                runOnUiThread(showprogress);
                URL url = new URL(MainActivity.GET_LOCATION_URL);
                Log.d(TAG, String.valueOf(url));
                urlConnection = ((HttpURLConnection) url.openConnection());
                urlConnection.setRequestMethod("GET");
                urlConnection.setConnectTimeout(8000);
                int statusCode = urlConnection.getResponseCode();
                Log.d(TAG, String.valueOf(statusCode));
                if (statusCode == 200) {
                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    result = response.toString();
                } else {
                    Handler handler = new Handler(getMainLooper());
                    handler.post(new Runnable() {
                        public void run() {
                            runOnUiThread(hideprogress);
                            Toast.makeText(getApplicationContext(), "Sorry!! Connection problem..", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
                Handler handler = new Handler(getMainLooper());
                handler.post(new Runnable() {
                    public void run() {
                        runOnUiThread(hideprogress);
                        Toast.makeText(getApplicationContext(), "Sorry!! Server is not responding..", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            if (result.contains("loc")) {
                Log.d(TAG, "run: " + result);
                try {
                    JSONObject response = new JSONObject(result);
                    JSONArray posts = response.optJSONArray("loc");
                    for (int i = 0; i < posts.length(); i++) {
                        JSONObject post = posts.optJSONObject(i);
                        lat = Double.parseDouble(post.optString("lat"));
                        lon = Double.parseDouble(post.optString("lon"));
                        date_time = post.optString("dt");
                    }
                    runOnUiThread(hideprogress);
                    runOnUiThread(updatelocation);
                    if (camemove && mapView != null) {
                        runOnUiThread(updatecamera);
                        camemove = false;
                    }
                } catch (JSONException e) {
                    Handler handler = new Handler(getMainLooper());
                    handler.post(new Runnable() {
                        public void run() {
                            runOnUiThread(hideprogress);
                            Toast.makeText(getApplicationContext(), "Something went wrong..", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } else {
                Handler handler = new Handler(getMainLooper());
                handler.post(new Runnable() {
                    public void run() {
                        runOnUiThread(hideprogress);
                        Toast.makeText(getApplicationContext(), "Something went wrong..", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    };
    Runnable showprogress = new Runnable() {
        @Override
        public void run() {
            progress.setVisibility(View.VISIBLE);
            tvcount.setVisibility(View.GONE);
        }
    };
    Runnable hideprogress = new Runnable() {
        @Override
        public void run() {
            progress.setVisibility(View.GONE);
            tvcount.setVisibility(View.VISIBLE);
        }
    };

    private String gettime() {
        Time time = new Time();
        time.setToNow();
        return time.format("%d-%m-%Y %H:%M:%S");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        conti = false;
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        conti = false;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        conti = true;
    }
}

