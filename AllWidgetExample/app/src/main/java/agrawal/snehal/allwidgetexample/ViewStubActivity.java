package agrawal.snehal.allwidgetexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;

public class ViewStubActivity extends AppCompatActivity {
    ViewStub simpleViewStub;
    Button showButton, hideButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewstub);
        simpleViewStub = ((ViewStub) findViewById(R.id.simpleViewStub));
        simpleViewStub.inflate();
        showButton = (Button) findViewById(R.id.showButton);
        hideButton = (Button) findViewById(R.id.hideButton);
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleViewStub.setVisibility(View.VISIBLE);
            }
        });
        hideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleViewStub.setVisibility(View.GONE);
            }
        });
    }
}
