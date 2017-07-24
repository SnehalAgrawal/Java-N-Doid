package agrawal.snehal.allwidgetexample;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import agrawal.snehal.allwidgetexample.datahandler.DataHandlerPersonDetail;


public class SqliteDatabaseExample extends AppCompatActivity {

    EditText etname, etage, etid;
    TextView tvdata;
    DataHandlerPersonDetail handlerPersonDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlitedata);
        etname = (EditText) findViewById(R.id.etname);
        etage = (EditText) findViewById(R.id.etage);
        etid = (EditText) findViewById(R.id.etid);
        tvdata = (TextView) findViewById(R.id.tvdata);
    }

    private void resetviewdata() {
        etname.setText(null);
        etage.setText(null);
        etid.setText(null);
        tvdata.setText(null);
    }

    public void clickaction(View view) {
        switch (view.getId()) {
            case R.id.bdeleteall:
                handlerPersonDetail = new DataHandlerPersonDetail(SqliteDatabaseExample.this);
                handlerPersonDetail.open();
                boolean resultdeleteall = handlerPersonDetail.delete_all();
                if (resultdeleteall)
                    Toast.makeText(this, "Table cleared", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this, "Table empty", Toast.LENGTH_SHORT).show();
                handlerPersonDetail.close();
                resetviewdata();
                break;
            case R.id.biddelete:
                handlerPersonDetail = new DataHandlerPersonDetail(SqliteDatabaseExample.this);
                handlerPersonDetail.open();
                boolean resultdeleterow = handlerPersonDetail.deleteRow(etid.getText().toString().trim());
                if (resultdeleterow)
                    Toast.makeText(this, "Data cleared for the id", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this, "Oops no data for the id", Toast.LENGTH_SHORT).show();
                handlerPersonDetail.close();
                resetviewdata();
                break;
            case R.id.bviewall:
                handlerPersonDetail = new DataHandlerPersonDetail(SqliteDatabaseExample.this);
                handlerPersonDetail.open();
                Cursor calldetail = handlerPersonDetail.returnAllData();
                if (calldetail.moveToFirst()) {
                    resetviewdata();
                    String str = "";
                    do {
                        str = str + "\nid : " + calldetail.getString(0) + "\n" + "name : " + calldetail.getString(1) + "\n" + "age : " + calldetail.getString(2);
                    } while (calldetail.moveToNext());
                    tvdata.setText(str);
                } else
                    Toast.makeText(this, "Table is empty", Toast.LENGTH_SHORT).show();
                handlerPersonDetail.close();
                break;
            case R.id.bidview:
                handlerPersonDetail = new DataHandlerPersonDetail(SqliteDatabaseExample.this);
                handlerPersonDetail.open();
                Cursor ciddetail = handlerPersonDetail.returnDataAccId(etid.getText().toString().trim());
                if (ciddetail.moveToFirst()) {
                    resetviewdata();
                    String str = "";
                    do {
                        str = str + "\nid : " + ciddetail.getString(0) + "\n" + "name : " + ciddetail.getString(1) + "\n" + "age : " + ciddetail.getString(2);
                    } while (ciddetail.moveToNext());
                    tvdata.setText(str);
                } else
                    Toast.makeText(this, "No data found for the id", Toast.LENGTH_SHORT).show();
                handlerPersonDetail.close();
                break;
            case R.id.bsave:
                handlerPersonDetail = new DataHandlerPersonDetail(SqliteDatabaseExample.this);
                handlerPersonDetail.open();
                if (checkinput(etname) && checkinput(etage)) {
                    boolean resultnisertdata = handlerPersonDetail.insertData(etname.getText().toString().trim(), etage.getText().toString().trim());
                    if (resultnisertdata) {
                        Toast.makeText(this, "Data inserted", Toast.LENGTH_SHORT).show();
                        resetviewdata();
                    } else
                        Toast.makeText(this, "Oops sorry problem occur", Toast.LENGTH_SHORT).show();
                }
                handlerPersonDetail.close();
                break;
        }
    }

    private boolean checkinput(EditText et) {
        if (TextUtils.isEmpty(et.getText().toString().trim())) {
            et.setError("This field is required");
            return false;
        } else {
            et.setError(null);
            return true;
        }
    }
}
