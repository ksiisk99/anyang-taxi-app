package com.ay.newchat;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;


import com.example.newchat.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


/**
 * NOTE: There can only be one service in each app that receives FCM messages. If multiple
 * are declared in the Manifest then the first one will be chosen.
 *
 * In order to make this Java sample functional, you must remove the following from the Kotlin messaging
 * service in the AndroidManifest.xml:
 *
 * <intent-filter>
 *   <action android:name="com.google.firebase.MESSAGING_EVENT" />
 * </intent-filter>
 */
public class f extends FirebaseMessagingService {

    private static final String TAG = "f";
    private int notifi_id=200;
    private CharSequence nam="nt";
    private String description="sh";
    private int importance=NotificationManager.IMPORTANCE_LOW;
    ac account;
    d helper;


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        account= ac.getInstance();
        helper= d.getInstance(getApplicationContext());


       if(remoteMessage.getData().get(getString(R.string.enter))!=null){
          ContentValues values=new ContentValues();
          values.put(getString(R.string.message),remoteMessage.getData().get(getString(R.string.name))+" 입장 했습니다.");
          values.put(getString(R.string.isSent),4);
          helper.insertData(values);
          helper.setName(remoteMessage.getData().get(getString(R.string.name)));
          return;
       }else if(remoteMessage.getData().get(getString(R.string.exit))!=null){
           ContentValues values=new ContentValues();
           values.put(getString(R.string.message),remoteMessage.getData().get(getString(R.string.name))+" 퇴장 했습니다.");
           values.put(getString(R.string.isSent),5);
           helper.insertData(values);
           helper.delName(remoteMessage.getData().get(getString(R.string.name)));
           return;
       }else if(remoteMessage.getData().get(getString(R.string.message))!=null){
           ContentValues values = new ContentValues(); //채팅연결 된 상태에서 백그라운드 됐어도 데이터는 주기
           values.put(getString(R.string.name),  remoteMessage.getData().get(getString(R.string.name)));
           values.put(getString(R.string.message), remoteMessage.getData().get(getString(R.string.message)));
           values.put(getString(R.string.time),getTime());
           if(helper.getLastMsg(remoteMessage.getData().get(getString(R.string.name)))){
               values.put(getString(R.string.isSent),2);
           }else {
               values.put(getString(R.string.isSent), 1);
           }
           helper.insertData(values);

           if(helper.getNotify()==1) {
               sendNotification(remoteMessage.getData().get(getString(R.string.title)), remoteMessage.getData().get(getString(R.string.ft2)), remoteMessage.getData().get(getString(R.string.name)), remoteMessage.getData().get(getString(R.string.message)),0);
           }

           return;
       }else if(remoteMessage.getData().get(getString(R.string.report))!=null) {
           helper.exitdelete();
           helper.logout();
           if(account.getConnect()){
               if(Integer.parseInt(remoteMessage.getData().get(getString(R.string.name)))>0){}
           }

           return;
       }else if(remoteMessage.getData().get(getString(R.string.ft3))!=null){
           helper.exitdelete();
           helper.logout();
           sendNotification(getString(R.string.ft4), getString(R.string.ft5), getString(R.string.ft4),getString(R.string.ft5),1);
           if(Integer.parseInt(remoteMessage.getData().get(getString(R.string.name)))>0){}
           return;
       }

    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    private void sendNotification(String title, String body, String name, String message,int choice){
        /*tion 11440995 , only wrote 11316240
        2021-05-05 23:34:11.678 2472-2486/com.google.android.gms I/PeopleChimeraService: onService. callbacks = aabq@754b65e, request = com.google.android.gms.common.internal.GetServiceRequest@9567a3f
        2021-05-05 23:34:11.770 1314-1314/? W/SurfaceFlinger: couldn't log to binary event log: overflow.
        2021-05-05 23:34:13.703 1688-1698/system_process I/art: Background partial concurrent mark sweep GC freed 20295(1195KB) AllocSpace objects, 7(440KB) LOS objects, 19% free, 16MB/20MB, paused 1.716ms total 115.538ms*/

        Intent intent=null;
        //알림 클릭시
        if(account.getConnect() && choice==0){ //사용자가 채팅방은 아니고 앱 실행중이라면 바로 main으로 간다.
            intent=new Intent(this, s1.class);
            intent.putExtra(getString(R.string.fcm),1);
        }else if(choice==0){//앱 실행중이 아니라면 Login을 거쳐서 main으로 보낸다.
            intent=new Intent(this, s1.class);
            intent.putExtra(getString(R.string.fcm),1);
        }else if(choice==1){
            intent=new Intent(this, s1.class);
        }


        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,
                intent,PendingIntent.FLAG_ONE_SHOT);
        /*Uri defaultSoundUri=RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone ringtone=RingtoneManager.getRingtone(getApplicationContext(),defaultSoundUri);*/

        String CHANNEL_ID=getString(R.string.ft6);
        String CHANNEL_NAME=getString(R.string.ft7);

        try {
            NotificationCompat.Builder notificationBuilder=
                    new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.noti_icon)
                    .setContentTitle(URLDecoder.decode(name,getString(R.string.ft8)))
                    .setContentText(URLDecoder.decode(message,getString(R.string.ft8)))
                    .setAutoCancel(true)
                            .setVibrate(new long[]{1000})
                    .setContentIntent(pendingIntent);
            NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            //notificationManager.cancelAll();//이전 알림 전체 삭제?

            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                String description="push";
                NotificationChannel channel=new NotificationChannel(CHANNEL_ID,CHANNEL_NAME,
                        NotificationManager.IMPORTANCE_DEFAULT);
                channel.setDescription(description);
                notificationManager.createNotificationChannel(channel);
            }
            //알림 표시
            notificationManager.notify(notifi_id,notificationBuilder.build());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //helper.close();

    }



    @Override
    public void onNewToken(String token) {
        sendRegistrationToServer(token);
    }


    /**
     * Schedule async work using WorkManager.
     */
//    private void scheduleJob() {
//        // [START dispatch_job]
//        OneTimeWorkRequest work = new OneTimeWorkRequest.Builder(MyWorker.class)
//                .build();
//        WorkManager.getInstance().beginWith(work).enqueue();
//        // [END dispatch_job]
//    }

    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private void handleNow() {

    }



    /**
     * Persist token to third-party servers.
     * <p>
     * Modify this method to associate the user's FCM registration token with any
     * server-side account maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        //TODO: Implement this method to send token to your app server.
    }

    private String getTime() {
        long now = System.currentTimeMillis();
        TimeZone tz=TimeZone.getTimeZone(getString(R.string.AsiaSeoul));
        Date date = new Date(now);
        SimpleDateFormat dateFormat = new SimpleDateFormat(getString(R.string.HHmm), Locale.KOREA);
        dateFormat.setTimeZone(tz);
        String getTime = dateFormat.format(date);
        String hh=String.valueOf(getTime.charAt(0))+String.valueOf(getTime.charAt(1));
        String mm=String.valueOf(getTime.charAt(2))+String.valueOf(getTime.charAt(3));
        if(Integer.parseInt(hh)>=12){
            return "오후 "+hh+":"+mm;
        }else{
            return "오전 "+hh+":"+mm;
        }
    }



}
