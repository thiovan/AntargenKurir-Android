package id.web.proditipolines.kurir2.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import id.web.proditipolines.kurir2.R;
import id.web.proditipolines.kurir2.activities.HalamanUtama;

/**
 * Created by Ravi Tamada on 08/08/16.
 * www.androidhive.info
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    private static int count = 0;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        sendNotification();

    }


    //This method is only generating push notification
    private void sendNotification() {
        int requestID = (int) System.currentTimeMillis();
        Intent intent = new Intent(getApplicationContext(), HalamanUtama.class);
        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), requestID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //PendingIntent contentIntent = null;
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_tracker))
                .setSmallIcon(R.drawable.ic_tracker)
                .setContentTitle("Permintaan Pengiriman Baru")
                .setContentText("Pengiriman menunggu untuk dikonfirmasi")
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(contentIntent);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(count, notificationBuilder.build());
        count++;
    }
}
