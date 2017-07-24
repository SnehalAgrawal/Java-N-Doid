package agrawal.snehal.allwidgetexample.tabhost;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import agrawal.snehal.allwidgetexample.R;

public class Tab1Fragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout theLayout = (LinearLayout) inflater.inflate(R.layout.tab_frag1_layout, container, false);
        Button b = (Button) theLayout.findViewById(R.id.bclick);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Tab1Fragment.this.getActivity(), "OnClickMe button clicked", Toast.LENGTH_LONG).show();
            }
        });
        return theLayout;
    }
}