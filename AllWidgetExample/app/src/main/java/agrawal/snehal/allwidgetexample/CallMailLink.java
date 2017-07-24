package agrawal.snehal.allwidgetexample;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


public class CallMailLink extends AppCompatActivity {

    private static final int CALL_PERMISSION_CONSTANT = 123;
    EditText etcall, etmail;
    ImageView ivcall, ivmail;
    TextView tvlink;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_mail_link);
        etcall = (EditText) findViewById(R.id.etcall);
        ivcall = (ImageView) findViewById(R.id.ivcall);
        etmail = (EditText) findViewById(R.id.etmail);
        ivmail = (ImageView) findViewById(R.id.ivmail);
        tvlink = (TextView) findViewById(R.id.tvlink);
        ivcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(etcall.getText().toString().trim())) {
                    etcall.setError("This filed required");
                } else {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + etcall.getText().toString().trim()));
                    if (ActivityCompat.checkSelfPermission(CallMailLink.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    startActivity(callIntent);
                }
            }
        });

        ivmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(etmail.getText().toString().trim())) {
                    etmail.setError("This filed required");
                } else {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + etmail.getText().toString().trim()));
                    intent.putExtra(Intent.EXTRA_SUBJECT, "via: Subject from my application");
                    intent.putExtra(Intent.EXTRA_TEXT, "Hello, Here is a demo text from the application");
                    startActivity(intent);
                }
            }
        });

        tvlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ifb = new Intent(Intent.ACTION_VIEW);
                ifb.setData(Uri.parse(tvlink.getText().toString()));
                startActivity(ifb);
            }
        });

    }
}
