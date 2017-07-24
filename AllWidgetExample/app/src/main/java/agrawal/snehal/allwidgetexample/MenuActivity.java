package agrawal.snehal.allwidgetexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


public class MenuActivity extends AppCompatActivity {
    ListView listView1;
    String contacts[] = {"Ajay", "Sachin", "Sumit", "Tarun", "Yogesh"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        listView1 = (ListView) findViewById(R.id.listView1);
        final Button mpopup = (Button) findViewById(R.id.mpopup);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contacts);
        listView1.setAdapter(adapter);

        /////////////////////////////  Popupmenu menu   ////////////////////////////////////////////////////////////////////
        mpopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(MenuActivity.this, mpopup);
                popup.getMenuInflater().inflate(R.menu.menu_album, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(MenuActivity.this, "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
                popup.show();
            }
        });
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////


        // Register the ListView  for Context menu
        registerForContextMenu(listView1);
    }

    /////////////////////////////  Context menu   ////////////////////////////////////////////////////////////////////
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select The Action");
        menu.add(0, v.getId(), 0, "Call");
        menu.add(0, v.getId(), 0, "SMS");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle() == "Call")
            Toast.makeText(getApplicationContext(), "calling code", Toast.LENGTH_LONG).show();
        else if (item.getTitle() == "SMS")
            Toast.makeText(getApplicationContext(), "sending sms code", Toast.LENGTH_LONG).show();
        else
            return false;
        return true;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    /////////////////////////////  Option menu   ////////////////////////////////////////////////////////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_album, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_favourite:
                Toast.makeText(getApplicationContext(), "Item 1 Selected", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_play_next:
                Toast.makeText(getApplicationContext(), "Item 2 Selected", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
}