package com.example.admin.restapi.support;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.restapi.AddTask;
import com.example.admin.restapi.ListAdapter.CustomTaskCardAdapter;
import com.example.admin.restapi.ListAdapter.TaskCard;
import com.example.admin.restapi.LoginActivity;
import com.example.admin.restapi.R;
import com.example.admin.restapi.ShowDetail;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;

public class InternetAccess {
    private static final String TAG = "InternetAccess";
    public static final String URL_LOGIN = "http://192.168.10.101/task_manager/v1/login";
    public static final String URL_REGISTER = "http://192.168.10.101/task_manager/v1/register";
    public static final String URL_TASK = "http://192.168.10.101/task_manager/v1/tasks";
    public static final String URL_FILE_UPLOAD = "http://192.168.10.101/task_manager/v1/imageupload";
    private static ProgressDialog loading;
    /*
    URL	                                                Method	    Parameters	            Description
    http://192.168.10.101/task_manager/v1/register	    POST	    name, email, password	User registration
    http://192.168.10.101/task_manager/v1/login	        POST	    email, password	        User login
    http://192.168.10.101/task_manager/v1/tasks	        POST	    task	                To create new task
    http://192.168.10.101/task_manager/v1/tasks	        GET		                            Fetching all tasks
    http://192.168.10.101/task_manager/v1/tasks/:id	    GET		                            Fetching single task
    http://192.168.10.101/task_manager/v1/tasks/:id	    PUT		    task, status            Updating single task(unsuccessful while making same string update)
    http://192.168.10.101/task_manager/v1/tasks/:id	    DELETE	    	                    Deleting single task
     */

    public static void login(final Context context, String url, final String email, final String password) {
        class getdata extends AsyncTask<String, Void, String> {
            private String result = "";

            protected void onPreExecute() {
                loading = ProgressDialog.show(context, "Authenticating", "Please wait..", false, false);
                super.onPreExecute();
            }

            protected String doInBackground(String... params) {
                try {
                    URL url = new URL(params[0]);
                    JSONObject postDataParams = new JSONObject();
                    postDataParams.put("email", email);
                    postDataParams.put("password", password);
                    Log.d(TAG, postDataParams.toString());
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setReadTimeout(15000);
                    urlConnection.setConnectTimeout(15000);
                    urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    urlConnection.setRequestMethod("POST");
                    /*urlConnection.setRequestProperty ("Authorization", basicAuth);
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setRequestProperty("Content-Length", "" + postData.getBytes().length);
                    urlConnection.setRequestProperty("Content-Language", "en-US");*/
                    urlConnection.setDoInput(true);
                    urlConnection.setDoOutput(true);
                    OutputStream os = urlConnection.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write(getPostDataString(postDataParams));
                    writer.flush();
                    writer.close();
                    os.close();
                    int statusCode = urlConnection.getResponseCode();
                    Log.d(TAG, String.valueOf(statusCode));
                    if (statusCode != 400) {
                        BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = r.readLine()) != null) {
                            response.append(line);
                        }
                        this.result = response.toString();
                    } else {
                        loading.dismiss();
                        maketoast(context, "Sorry!! connection problem..");
                    }
                } catch (Exception e) {
                    loading.dismiss();
                    maketoast(context, "Sorry!! sever not responding..");
                }
                return String.valueOf(this.result);
            }

            private String getPostDataString(JSONObject params) throws Exception {
                StringBuilder result = new StringBuilder();
                boolean first = true;
                Iterator<String> itr = params.keys();
                while (itr.hasNext()) {
                    String key = itr.next();
                    Object value = params.get(key);
                    if (first) {
                        first = false;
                    } else {
                        result.append("&");
                    }
                    result.append(URLEncoder.encode(key, "UTF-8"));
                    result.append("=");
                    result.append(URLEncoder.encode(value.toString(), "UTF-8"));
                }
                return result.toString();
            }

            protected void onPostExecute(String result) {
                result = result.trim();
                loading.dismiss();
                Log.d(TAG, "onPostExecute: " + result);
                try {
                    JSONObject response = new JSONObject(result);
                    boolean error = Boolean.parseBoolean(response.optString("error"));
                    if (!error) {
                        String name = response.optString("name");
                        String email = response.optString("email");
                        String apiKey = response.optString("apiKey");
                        String createdAt = response.optString("createdAt");
                        Util.setname(context, name);
                        Util.setauthkey(context, apiKey);
                        Util.setemail(context, email);
                        Util.setcreatedat(context, createdAt);
                        LoginActivity.fa.finish();
                        context.startActivity(new Intent(context, ShowDetail.class));
                    } else {
                        String message = response.optString("message");
                        Toast.makeText(context, "" + message, Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        getdata get = new getdata();
        get.execute(url);
    }

    public static void register(final Context context, String url, final String name, final String email, final String password) {
        class getdata extends AsyncTask<String, Void, String> {
            private String result = "";

            protected void onPreExecute() {
                loading = ProgressDialog.show(context, "Registering", "Please wait..", false, false);
                super.onPreExecute();
            }

            protected String doInBackground(String... params) {
                try {
                    URL url = new URL(params[0]);
                    JSONObject postDataParams = new JSONObject();
                    postDataParams.put("name", name);
                    postDataParams.put("email", email);
                    postDataParams.put("password", password);
                    Log.d(TAG, postDataParams.toString());
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setReadTimeout(15000);
                    urlConnection.setConnectTimeout(15000);
                    urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setDoInput(true);
                    urlConnection.setDoOutput(true);
                    OutputStream os = urlConnection.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write(getPostDataString(postDataParams));
                    writer.flush();
                    writer.close();
                    os.close();
                    int statusCode = urlConnection.getResponseCode();
                    Log.d(TAG, String.valueOf(statusCode));
                    if (statusCode != 400) {
                        BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = r.readLine()) != null) {
                            response.append(line);
                        }
                        this.result = response.toString();
                    } else {
                        loading.dismiss();
                        maketoast(context, "Sorry!! connection problem..");
                    }
                } catch (Exception e) {
                    loading.dismiss();
                    maketoast(context, "Sorry!! sever not responding..");
                }
                return String.valueOf(this.result);
            }

            private String getPostDataString(JSONObject params) throws Exception {
                StringBuilder result = new StringBuilder();
                boolean first = true;
                Iterator<String> itr = params.keys();
                while (itr.hasNext()) {
                    String key = itr.next();
                    Object value = params.get(key);
                    if (first) {
                        first = false;
                    } else {
                        result.append("&");
                    }
                    result.append(URLEncoder.encode(key, "UTF-8"));
                    result.append("=");
                    result.append(URLEncoder.encode(value.toString(), "UTF-8"));
                }
                return result.toString();
            }

            protected void onPostExecute(String result) {
                result = result.trim();
                loading.dismiss();
                Log.d(TAG, "onPostExecute: " + result);
                try {
                    JSONObject response = new JSONObject(result);
                    boolean error = Boolean.parseBoolean(response.optString("error"));
                    if (!error) {
                        String message = response.optString("message");
                        Toast.makeText(context, "" + message, Toast.LENGTH_LONG).show();
                        InternetAccess.login(context, InternetAccess.URL_LOGIN, email, password);
                    } else {
                        String message = response.optString("message");
                        Toast.makeText(context, "" + message, Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        getdata get = new getdata();
        get.execute(url);
    }

    public static void getalltask(final Context context, String url, final ListView lvtask) {
        class getdata extends AsyncTask<String, Void, String> {
            private String result = "";

            protected void onPreExecute() {
                loading = (ProgressDialog.show(context, "Getting TaskList", "Please wait...", false, false));
                super.onPreExecute();
            }

            protected String doInBackground(String... params) {
                try {
                    URL url = new URL(params[0]);
                    Log.d(TAG, "doInBackground: " + url);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    urlConnection.setRequestProperty("authorization", Util.getauthkey(context));
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setConnectTimeout(10000);
                    int statusCode = urlConnection.getResponseCode();
                    Log.d(TAG, "doInBackground: " + statusCode);
                    if (statusCode != 400) {
                        BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = r.readLine()) != null) {
                            response.append(line);
                        }
                        this.result = response.toString();
                    } else {
                        loading.dismiss();
                        maketoast(context, "Sorry!! connection problem");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    loading.dismiss();
                    maketoast(context, "Sorry!! sever not responding..");
                }
                return String.valueOf(this.result);
            }

            protected void onPostExecute(String result) {
                result = result.trim();
                Log.d(TAG, "onPostExecute: " + result);
                loading.dismiss();
                try {
                    JSONObject response = new JSONObject(result);
                    boolean error = Boolean.parseBoolean(response.optString("error"));
                    if (!error) {
                        JSONArray posts = response.optJSONArray("tasks");
                        if (posts.length() > 0) {
                            CustomTaskCardAdapter custom_task_card_adapter;
                            TaskCard task_card;
                            ArrayList<TaskCard> taskcarditem = new ArrayList<>();
                            for (int i = 0; i < posts.length(); i++) {
                                JSONObject post = posts.optJSONObject(i);
                                String id = post.optString("id");
                                String task = post.optString("task");
                                String status = post.optString("status");
                                String createdAt = post.optString("createdAt");
                                Log.d(TAG, " " + id + " " + task + " " + status + " " + createdAt);
                                task_card = new TaskCard(id, task, createdAt, status);
                                taskcarditem.add(task_card);
                            }
                            custom_task_card_adapter = new CustomTaskCardAdapter(context);
                            for (TaskCard sigletaskitem : taskcarditem) {
                                custom_task_card_adapter.add(sigletaskitem);
                            }
                            lvtask.setAdapter(custom_task_card_adapter);
                        } else {
                            Toast.makeText(context, "No data", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        String message = response.optString("message");
                        Toast.makeText(context, "" + message, Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        getdata get = new getdata();
        get.execute(url);
    }

    public static void gettask(final Context context, String url) {
        class getdata extends AsyncTask<String, Void, String> {
            private String result = "";

            protected void onPreExecute() {
                loading = (ProgressDialog.show(context, "Getting Task", "Please wait...", false, false));
                super.onPreExecute();
            }

            protected String doInBackground(String... params) {
                try {
                    URL url = new URL(params[0]);
                    Log.d(TAG, "doInBackground: " + url);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    urlConnection.setRequestProperty("authorization", Util.getauthkey(context));
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setConnectTimeout(10000);
                    int statusCode = urlConnection.getResponseCode();
                    Log.d(TAG, "doInBackground: " + statusCode);
                    if (statusCode != 400) {
                        BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = r.readLine()) != null) {
                            response.append(line);
                        }
                        this.result = response.toString();
                    } else {
                        loading.dismiss();
                        maketoast(context, "Sorry!! connection problem");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    loading.dismiss();
                    maketoast(context, "Sorry!! sever not responding..");
                }
                return String.valueOf(this.result);
            }

            protected void onPostExecute(String result) {
                result = result.trim();
                Log.d(TAG, "onPostExecute: " + result);
                loading.dismiss();
                try {
                    JSONObject response = new JSONObject(result);
                    boolean error = Boolean.parseBoolean(response.optString("error"));
                    if (!error) {
                        String id = response.optString("id");
                        String task = response.optString("task");
                        String status = response.optString("status");
                        String createdAt = response.optString("createdAt");
                        Toast.makeText(context, id + " " + task + " " + status + " " + createdAt, Toast.LENGTH_LONG).show();
                    } else {
                        String message = response.optString("message");
                        Toast.makeText(context, "" + message, Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        getdata get = new getdata();
        get.execute(url);
    }


    public static void addtask(final Context context, String url, final String task) {
        class getdata extends AsyncTask<String, Void, String> {
            private String result = "";

            protected void onPreExecute() {
                loading = ProgressDialog.show(context, "Loading", "Please wait..", false, false);
                super.onPreExecute();
            }

            protected String doInBackground(String... params) {
                try {
                    URL url = new URL(params[0]);
                    JSONObject postDataParams = new JSONObject();
                    postDataParams.put("task", task);
                    Log.d(TAG, postDataParams.toString());
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setReadTimeout(15000);
                    urlConnection.setConnectTimeout(15000);
                    urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    urlConnection.setRequestProperty("authorization", Util.getauthkey(context));
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setDoInput(true);
                    urlConnection.setDoOutput(true);
                    OutputStream os = urlConnection.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write(getPostDataString(postDataParams));
                    writer.flush();
                    writer.close();
                    os.close();
                    int statusCode = urlConnection.getResponseCode();
                    Log.d(TAG, String.valueOf(statusCode));
                    if (statusCode != 400) {
                        BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = r.readLine()) != null) {
                            response.append(line);
                        }
                        this.result = response.toString();
                    } else {
                        loading.dismiss();
                        maketoast(context, "Sorry!! connection problem..");
                    }
                } catch (Exception e) {
                    loading.dismiss();
                    maketoast(context, "Sorry!! sever not responding..");
                }
                return String.valueOf(this.result);
            }

            private String getPostDataString(JSONObject params) throws Exception {
                StringBuilder result = new StringBuilder();
                boolean first = true;
                Iterator<String> itr = params.keys();
                while (itr.hasNext()) {
                    String key = itr.next();
                    Object value = params.get(key);
                    if (first) {
                        first = false;
                    } else {
                        result.append("&");
                    }
                    result.append(URLEncoder.encode(key, "UTF-8"));
                    result.append("=");
                    result.append(URLEncoder.encode(value.toString(), "UTF-8"));
                }
                return result.toString();
            }

            protected void onPostExecute(String result) {
                result = result.trim();
                loading.dismiss();
                Log.d(TAG, "onPostExecute: " + result);
                try {
                    JSONObject response = new JSONObject(result);
                    boolean error = Boolean.parseBoolean(response.optString("error"));
                    if (!error) {
                        String message = response.optString("message");
                        Toast.makeText(context, "" + message, Toast.LENGTH_LONG).show();
                        context.startActivity(new Intent(context, ShowDetail.class));
                    } else {
                        String message = response.optString("message");
                        Toast.makeText(context, "" + message, Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        getdata get = new getdata();
        get.execute(url);
    }

    public static void upatetask(final Context context, String url, final String task, final String status) {
        class getdata extends AsyncTask<String, Void, String> {
            private String result = "";

            protected void onPreExecute() {
                loading = ProgressDialog.show(context, "Loading", "Please wait..", false, false);
                super.onPreExecute();
            }

            protected String doInBackground(String... params) {
                try {
                    URL url = new URL(params[0]);
                    JSONObject postDataParams = new JSONObject();
                    postDataParams.put("task", task);
                    postDataParams.put("status", status);
                    Log.d(TAG, postDataParams.toString());
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setReadTimeout(15000);
                    urlConnection.setConnectTimeout(15000);
                    urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    urlConnection.setRequestProperty("authorization", Util.getauthkey(context));
                    urlConnection.setRequestMethod("PUT");
                    urlConnection.setDoInput(true);
                    urlConnection.setDoOutput(true);
                    OutputStream os = urlConnection.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write(getPostDataString(postDataParams));
                    writer.flush();
                    writer.close();
                    os.close();
                    int statusCode = urlConnection.getResponseCode();
                    Log.d(TAG, String.valueOf(statusCode));
                    if (statusCode != 400) {
                        BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = r.readLine()) != null) {
                            response.append(line);
                        }
                        this.result = response.toString();
                    } else {
                        loading.dismiss();
                        maketoast(context, "Sorry!! connection problem..");
                    }
                } catch (Exception e) {
                    loading.dismiss();
                    maketoast(context, "Sorry!! sever not responding..");
                }
                return String.valueOf(this.result);
            }

            private String getPostDataString(JSONObject params) throws Exception {
                StringBuilder result = new StringBuilder();
                boolean first = true;
                Iterator<String> itr = params.keys();
                while (itr.hasNext()) {
                    String key = itr.next();
                    Object value = params.get(key);
                    if (first) {
                        first = false;
                    } else {
                        result.append("&");
                    }
                    result.append(URLEncoder.encode(key, "UTF-8"));
                    result.append("=");
                    result.append(URLEncoder.encode(value.toString(), "UTF-8"));
                }
                return result.toString();
            }

            protected void onPostExecute(String result) {
                result = result.trim();
                loading.dismiss();
                Log.d(TAG, "onPostExecute: " + result);
                try {
                    JSONObject response = new JSONObject(result);
                    boolean error = Boolean.parseBoolean(response.optString("error"));
                    if (!error) {
                        String message = response.optString("message");
                        Toast.makeText(context, "" + message, Toast.LENGTH_LONG).show();
                        context.startActivity(new Intent(context, ShowDetail.class));
                    } else {
                        String message = response.optString("message");
                        Toast.makeText(context, "" + message, Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        getdata get = new getdata();
        get.execute(url);
    }

    public static void deletetask(final Context context, String url) {
        class getdata extends AsyncTask<String, Void, String> {
            private String result = "";

            protected void onPreExecute() {
                loading = (ProgressDialog.show(context, "Getting TaskList", "Please wait...", false, false));
                super.onPreExecute();
            }

            protected String doInBackground(String... params) {
                try {
                    URL url = new URL(params[0]);
                    Log.d(TAG, "doInBackground: " + url);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    urlConnection.setRequestProperty("authorization", Util.getauthkey(context));
                    urlConnection.setRequestMethod("DELETE");
                    urlConnection.setConnectTimeout(10000);
                    int statusCode = urlConnection.getResponseCode();
                    Log.d(TAG, "doInBackground: " + statusCode);
                    if (statusCode != 400) {
                        BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = r.readLine()) != null) {
                            response.append(line);
                        }
                        this.result = response.toString();
                    } else {
                        loading.dismiss();
                        maketoast(context, "Sorry!! connection problem");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    loading.dismiss();
                    maketoast(context, "Sorry!! sever not responding..");
                }
                return String.valueOf(this.result);
            }

            protected void onPostExecute(String result) {
                result = result.trim();
                loading.dismiss();
                Log.d(TAG, "onPostExecute: " + result);
                try {
                    JSONObject response = new JSONObject(result);
                    boolean error = Boolean.parseBoolean(response.optString("error"));
                    if (!error) {
                        String message = response.optString("message");
                        Toast.makeText(context, "" + message, Toast.LENGTH_LONG).show();
                        context.startActivity(new Intent(context, ShowDetail.class));
                    } else {
                        String message = response.optString("message");
                        Toast.makeText(context, "" + message, Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        getdata get = new getdata();
        get.execute(url);
    }

    public static void uploadFile(final Context context, String sourceFileUri, String server_uri, final TextView tvattach) {
        new Handler(context.getMainLooper()).post(new Runnable() {
            public void run() {
                loading = ProgressDialog.show(context, "", "uploading", true);
            }
        });
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";

        int maxBufferSize = 10485760;
        File sourceFile = new File(sourceFileUri);
        if (!sourceFile.isFile()) {
            loading.dismiss();
            maketoast(context, "No source file ..");
        } else {
            try {
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL(server_uri);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("authorization", Util.getauthkey(context));
                conn.setRequestProperty("uploaded_file", sourceFileUri);
                DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\"" + sourceFileUri + "\"" + lineEnd);
                dos.writeBytes(lineEnd);
                int bytesAvailable = fileInputStream.available();
                int bufferSize = Math.min(bytesAvailable, maxBufferSize);
                byte[] buffer = new byte[bufferSize];
                int bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                while (bytesRead > 0) {
                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
                int serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();
                Log.i(TAG, "HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);
                if (serverResponseCode == 200) {
                    BufferedReader r = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        result.append(line);
                    }
                    Log.d(TAG, "uploadFile: " + result);
                    loading.dismiss();
                    try {
                        if (result.length() > 0) {
                            JSONObject response = new JSONObject(result.toString());
                            boolean error = Boolean.parseBoolean(response.optString("error"));
                            if (!error) {
                                final String message = response.optString("message");
                                new Handler(context.getMainLooper()).post(new Runnable() {
                                    public void run() {
                                        tvattach.setText(message);
                                    }
                                });
                            } else {
                                String message = response.optString("message");
                                Toast.makeText(context, "" + message, Toast.LENGTH_LONG).show();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    loading.dismiss();
                    maketoast(context, "File uploading failed..");
                }
                fileInputStream.close();
                dos.flush();
                dos.close();
            } catch (Exception e) {
                e.printStackTrace();
                loading.dismiss();
                maketoast(context, "File uploading failed");
            }
        }
    }
    private static void maketoast(final Context context, final String s) {
        new Handler(context.getMainLooper()).post(new Runnable() {
            public void run() {
                Toast.makeText(context, s, Toast.LENGTH_LONG).show();
            }
        });
    }
}