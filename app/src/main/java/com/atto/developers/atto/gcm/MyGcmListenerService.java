package com.atto.developers.atto.gcm;

import com.google.android.gms.gcm.GcmListenerService;

/**
 * Created by Tacademy on 2016-09-12.
 */
public class MyGcmListenerService extends GcmListenerService {
//
//    private static final String TAG = "MyGcmListenerService";
//    public static final String ACTION_CHAT = "com.begentgroup.miniproject.action.chatmessage";
//    public static final String EXTRA_CHAT_USER = "chatuser";
//    public static final String EXTRA_RESULT = "result";
//
//    LocalBroadcastManager mLBM;
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        mLBM = LocalBroadcastManager.getInstance(this);
//    }
//
//    @Override
//    public void onMessageReceived(String from, Bundle data) {
//        String message = data.getString("message");
//        Log.d(TAG, "From: " + from);
//        Log.d(TAG, "Message: " + message);
//
//        if (from.startsWith("/topics/")) {
//        } else {
//            long lastTime = DBManager.getInstance().getLastReceiveDate();
//            Date date = new Date(lastTime);
//            MessageListRequest request = new MessageListRequest(this, date);
//            try {
//                NetworkResult<List<ChatMessage>> result = NetworkManager.getInstance().getNetworkDataSync(request);
//                List<ChatMessage> list = result.getResult();
//                for (ChatMessage m : list) {
//
//                    try {
//                        DBManager.getInstance().addMessage(m.getSender(), ChatContract.ChatMessage.TYPE_RECEIVE, m.getMessage(),
//                                Utils.convertStringToTime(m.getDate()));
//                        Intent i = new Intent(ACTION_CHAT);
//                        i.putExtra(EXTRA_CHAT_USER,m.getSender());
//                        mLBM.sendBroadcastSync(i);
//                        boolean processed = i.getBooleanExtra(EXTRA_RESULT, false);
//                        if (!processed) {
//                            sendNotification(m);
//                        }
//
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
////        sendNotification(message);
//    }
//
//    private void sendNotification(ChatMessage m) {
//        Intent intent = new Intent(this, SplashActivity.class);
//        intent.putExtra(ChatActivity.EXTRA_USER, m.getSender());
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setTicker("Chat Message")
//                .setContentTitle(m.getSender().getUserName())
//                .setContentText(m.getMessage())
//                .setAutoCancel(true)
//                .setDefaults(NotificationCompat.DEFAULT_ALL)
//                .setContentIntent(pendingIntent);
//
//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
//
//    }
//    private void sendNotification(String message) {
//        Intent intent = new Intent(this, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
//                PendingIntent.FLAG_ONE_SHOT);
//
//        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setTicker("GCM Message")
//                .setContentTitle("GCM Message")
//                .setContentText(message)
//                .setAutoCancel(true)
//                .setSound(defaultSoundUri)
//                .setContentIntent(pendingIntent);
//
//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
//    }
}