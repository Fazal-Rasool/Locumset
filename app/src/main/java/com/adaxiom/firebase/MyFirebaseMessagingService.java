package com.adaxiom.firebase;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.adaxiom.locumset.Home;
import com.adaxiom.locumset.R;
import com.adaxiom.locumset.Splash;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
int NOTIFICATION_ID=1;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        showNotification(remoteMessage.getData().get("message"));
        Intent intent = new Intent();
        intent.putExtra("extra", remoteMessage.getData().get("message"));
        intent.setAction("com.adaxiom.firebase.onMessageReceived");
        sendBroadcast(intent);
    }

    private void showNotification(String message) {

        Intent i = new Intent(this,Splash.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle("FCM Notification")
                .setContentText(message)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(0,builder.build());

    }



//    private void sendNotification( String content) {
//        NotificationChannel mChannel = null;
//        int notifyID = 1;
//        String CHANNEL_ID = "my_channel_01";// The id of the channel.
//        CharSequence name = "Hello channel";// The user-visible name of the channel.
//        int importance = NotificationManager.IMPORTANCE_HIGH;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
//        }
//// Create a notification and set the notification channel.
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            Notification notification = new Notification.Builder(getApplicationContext())
//                    .setContentTitle("New Query")
//                    .setContentText(content)
//                    .setSmallIcon(R.drawable.logo)
//                    .setChannelId(CHANNEL_ID)
//                    .setAutoCancel(true)
//                    .build();
//            NotificationManager mNotificationManager =
//                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//            mNotificationManager.createNotificationChannel(mChannel);
//
//// Issue the notification.
//            mNotificationManager.notify(notifyID, notification);
//        }
//        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O) {
//
//            NotificationManager alarmNotificationManager = (NotificationManager) this
//                    .getSystemService(Context.NOTIFICATION_SERVICE);
//            PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
//                    new Intent(this, Home.class), PendingIntent.FLAG_UPDATE_CURRENT);
//
//            //Create notification
//            NotificationCompat.Builder alamNotificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
//                    .setContentTitle("New Query")
//                    .setSmallIcon(R.drawable.logo)
//                    .setContentText(content).setAutoCancel(true);
//            alamNotificationBuilder.setContentIntent(contentIntent);
//
//            //notiy notification manager about new notificationss
//            alarmNotificationManager.notify(NOTIFICATION_ID, alamNotificationBuilder.build());
//        }
//
//    }





    //This method is only generating push notification
    //It is same as we did in earlier posts
//    private void sendNotification(String messageBody) {
//        Intent intent = new Intent(this, Home.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
//                PendingIntent.FLAG_ONE_SHOT);
//
//        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentTitle("Firebase Push Notification")
//                .setContentText(messageBody)
//                .setAutoCancel(true)
//                .setSound(defaultSoundUri)
//                .setContentIntent(pendingIntent);
//
//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        notificationManager.notify(0, notificationBuilder.build());
//    }



}