package agrawal.snehal.allwidgetexample;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import agrawal.snehal.allwidgetexample.xmlparsersupport.SingleMenuItemActivity;
import agrawal.snehal.allwidgetexample.xmlparsersupport.XMLParser;

import static android.content.ContentValues.TAG;

public class XMLParsing extends AppCompatActivity {
    static final String xml_url = "http://api.androidhive.info/pizza/?format=xml";
    static final String KEY_ITEM = "item";
    static final String KEY_ID = "id";
    static final String KEY_NAME = "name";
    static final String KEY_COST = "cost";
    static final String KEY_DESC = "description";
    private static ProgressDialog loading = null;
    ListView lv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        lv = (ListView) findViewById(R.id.listview);
        getxmlfromserver(XMLParsing.this, xml_url);

    }

    public void getxmlfromserver(final Context context, String url) {
        class getdata extends AsyncTask<String, Void, String> {
            private String result = "";

            protected void onPreExecute() {
                loading = (ProgressDialog.show(context, "Loading", "Please wait...", false, false));
                super.onPreExecute();
            }

            protected String doInBackground(String... params) {
                try {
                    URL url = new URL(params[0]);
                    Log.d(TAG, "doInBackground: " + url);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setConnectTimeout(8000);
                    int statusCode = urlConnection.getResponseCode();
                    Log.d(TAG, "doInBackground: " + statusCode);
                    if (statusCode == 200) {
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
                    loading.dismiss();
                    maketoast(context, "Sorry!! sever not responding..");
                }
                return String.valueOf(this.result);
            }

            protected void onPostExecute(String result) {
                result = result.trim();
                if (!result.contentEquals("")) {
                    ArrayList<HashMap<String, String>> menuItems = new ArrayList<>();
                    XMLParser parser = new XMLParser();
                    Document doc = parser.getDomElement(result);
                    NodeList nl = doc.getElementsByTagName(KEY_ITEM);
                    for (int i = 0; i < nl.getLength(); i++) {
                        HashMap<String, String> map = new HashMap<>();
                        Element e = (Element) nl.item(i);
                        map.put(KEY_ID, parser.getValue(e, KEY_ID));
                        map.put(KEY_NAME, parser.getValue(e, KEY_NAME));
                        map.put(KEY_COST, "Rs." + parser.getValue(e, KEY_COST));
                        map.put(KEY_DESC, parser.getValue(e, KEY_DESC));
                        menuItems.add(map);
                    }
                    ListAdapter adapter = new SimpleAdapter(context, menuItems, R.layout.activity_xmlparser_item, new String[]{KEY_NAME, KEY_DESC, KEY_COST}, new int[]{R.id.name, R.id.desciption, R.id.cost});
                    //setListAdapter(adapter);

                    lv.setAdapter(adapter);
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                            String name = ((TextView) view.findViewById(R.id.name)).getText().toString();
                            String cost = ((TextView) view.findViewById(R.id.cost)).getText().toString();
                            String description = ((TextView) view.findViewById(R.id.desciption)).getText().toString();
                            Intent in = new Intent(getApplicationContext(), SingleMenuItemActivity.class);
                            in.putExtra(KEY_NAME, name);
                            in.putExtra(KEY_COST, cost);
                            in.putExtra(KEY_DESC, description);
                            startActivity(in);
                        }
                    });
                } else {
                    maketoast(context, "Failed");
                }
                loading.dismiss();
            }
        }
        getdata get = new getdata();
        get.execute(url);
    }

    public void maketoast(final Context context, final String s) {
        Handler handler = new Handler(context.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
                Toast.makeText(context, s, Toast.LENGTH_LONG).show();
            }
        });
    }
}