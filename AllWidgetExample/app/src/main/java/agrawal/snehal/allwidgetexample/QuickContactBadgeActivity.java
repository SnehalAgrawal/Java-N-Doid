package agrawal.snehal.allwidgetexample;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.QuickContactBadge;

public class QuickContactBadgeActivity extends AppCompatActivity {
    private static final int CONTACT_PICKER_RESULT = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quickcontactbadge);
        QuickContactBadge badgeSmall = (QuickContactBadge) findViewById(R.id.badge_small);
        badgeSmall.assignContactFromEmail("any@gmail.com", true);
        badgeSmall.setMode(ContactsContract.QuickContact.MODE_SMALL);

        QuickContactBadge badgeMedium = (QuickContactBadge) findViewById(R.id.badge_medium);
        badgeMedium.assignContactFromPhone("831-555-1212", true);
        badgeMedium.setImageResource(R.mipmap.ic_launcher);
    }

    public void onPickContact(View view) {
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CONTACT_PICKER_RESULT:
                    Uri contactUri = data.getData();
                    FrameLayout badgeLargeHolder = (FrameLayout) findViewById(R.id.badge_holder_large);
                    QuickContactBadge badgeLarge = new QuickContactBadge(this);
                    badgeLarge.assignContactUri(contactUri);
                    badgeLarge.setMode(ContactsContract.QuickContact.MODE_LARGE);
                    badgeLarge.setImageResource(R.mipmap.ic_launcher);
                    badgeLargeHolder.addView(badgeLarge);
                    break;
            }
        }
    }
}
