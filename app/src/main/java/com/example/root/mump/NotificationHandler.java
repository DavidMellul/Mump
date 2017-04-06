package com.example.root.mump;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by root on 05/04/17.
 */

// This class is responsible for sending notifications on the device
public class NotificationHandler {
    private Context m_context;

    // Singleton
    private static NotificationHandler m_instance = new NotificationHandler();

    private NotificationHandler() {
    }

    public static NotificationHandler getInstance() {
        return m_instance;
    }

    public void setContext(Context c) {
        m_context = c;
    }

    // Used when new messages are received
    public void sendNotification(Song s) {
        // Create an intent so that when the user clicks on the notification, he is redirected to the app
        Intent notificationIntent;
        notificationIntent  = new Intent(m_context, MainActivity.class);

        Intent playIntent = new Intent(m_context, MusicPlayerService.class);
  //      playIntent.setAction(MusicPlayerService.AC)

        PendingIntent contentIntent = PendingIntent.getActivity(m_context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent previous = PendingIntent.getActivity(m_context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent play = PendingIntent.getActivity(m_context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pause = PendingIntent.getActivity(m_context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification n = new Notification.Builder(m_context)
                .setSmallIcon(R.drawable.ic_marker)
                .setContentTitle(s.title)
                .setContentText(s.author)
                .setOngoing(true)
                .setContentIntent(contentIntent)
                .setContent(getCustomNotificationView(s))
                .build();

        NotificationManager nm = (NotificationManager) m_context.getSystemService(NOTIFICATION_SERVICE);
        nm.notify(1, n);
    }

    // Returns a new view for displaying a notification for a new message
    private RemoteViews getCustomNotificationView(Song s) {
        String title = s.title;
        String author = s.author;

        RemoteViews r = new RemoteViews(m_context.getPackageName(), R.layout.notification_music_player);
        r.setTextViewText(R.id.notificationSongTitle, title);
        r.setTextViewText(R.id.notificationSongAuthor, author);

        return r;
    }
}