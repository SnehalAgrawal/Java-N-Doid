package agrawal.snehal.allwidgetexample;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

import java.util.Date;

public class NotificationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Button bnotify = (Button) findViewById(R.id.bnotify);
        bnotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendNotification("Hello Title", "Hello Body");
            }
        });
    }

    private void sendNotification(String temp_title, String temp_body) {
        int notificationIndex = (int) (new Date().getTime() / 1000L % 2147483647L);
        try {
            Intent intent = new Intent(this, NotificationActivity.class);
            intent.putExtra("title", temp_title.trim());
            intent.addFlags(notificationIndex);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, notificationIndex, intent, PendingIntent.FLAG_ONE_SHOT);

            NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this).setSmallIcon(R.mipmap.ic_launcher).setContentTitle(temp_title).setContentText(temp_body).setAutoCancel(true).setContentIntent(pendingIntent);
            notificationBuilder.setVibrate(new long[]{1000L, 1000L});
            notificationBuilder.setLights(Color.RED, 3000, 3000);
            notificationBuilder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(notificationIndex, notificationBuilder.build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
