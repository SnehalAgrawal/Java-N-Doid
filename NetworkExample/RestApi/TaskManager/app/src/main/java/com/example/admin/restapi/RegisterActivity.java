package com.example.admin.restapi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.restapi.support.InternetAccess;

public class RegisterActivity extends AppCompatActivity {
    private EditText etname, etemail, etpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button bregister = (Button) findViewById(R.id.bregister);
        etname = (EditText) findViewById(R.id.etname);
        etemail = (EditText) findViewById(R.id.etemail);
        etpassword = (EditText) findViewById(R.id.etpassword);
        bregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEmpty(etname))
                    if (isEmpty(etemail))
                        if (isEmpty(etpassword))
                            InternetAccess.register(RegisterActivity.this, InternetAccess.URL_REGISTER, etname.getText().toString().trim(), etemail.getText().toString().trim(), etpassword.getText().toString().trim());
            }
        });
    }

    private boolean isEmpty(EditText editText) {
        if (TextUtils.isEmpty(editText.getText().toString().trim()) || editText.getText().toString().trim().length() == 0) {
            editText.setError("Please fill detail");
            return false;
        } else {
            return true;
        }
    }
}
