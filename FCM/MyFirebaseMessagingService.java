import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.hashtroop.lilgeniusinternationalschool.DataHandler.DataHandler_student_profile;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        if (remoteMessage.getData().size() > 0) {
            try {
                JSONObject response = new JSONObject(remoteMessage.getData());
                notification = response.optString("notification");
                JSONObject titleobj;
                titleobj = new JSONObject(notification);
                title = titleobj.optString("title");
                body = titleobj.optString("body");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d(TAG, "onMessageReceived: " + title);
        }
        sendNotification(remoteMessage);
    }

    private void sendNotification(RemoteMessage messageBody) {
        Intent intent = null;
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.school_logo)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        notificationBuilder.setVibrate(new long[]{1000, 1000});
        notificationBuilder.setLights(Color.RED, 3000, 3000);
        notificationBuilder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }
}
