package com.example.myapplication;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import android.util.Log;
import android.widget.Toast;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class FirebaseMessageService extends FirebaseMessagingService {
    public FirebaseMessageService() {
    }
    String type = "";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getData() != null){
            type = "json";
            sendNotification(remoteMessage);
        }
        if (remoteMessage.getNotification() != null){
            type = "message";
            sendNotification(remoteMessage);
        }
//
//        String result = "From : " + remoteMessage.getFrom() + "\nMessageId = " + remoteMessage.getMessageId() + "\nMessageType =  " + remoteMessage.getMessageType()
//                + "\nCollapeseKey = " + remoteMessage.getCollapseKey() + "\nTo: " + remoteMessage.getTo() + "\nTtl = " + remoteMessage.getTtl()
//                + "\nSent Time = " + remoteMessage.getSentTime();
//        /*+"\nTitle = " + remoteMessage.getNotification().getTitle()
//                + "\nBody = " + remoteMessage.getNotification().getBody()*/
//        Map<String, String> map = remoteMessage.getData();
//        for (String key : map.keySet())
//            result += "\n(" + key + "," + map.get(key) + ")";
    }
    private void sendNotification(RemoteMessage remoteMessage){
        Map<String, String> data = remoteMessage.getData();
        String title = data.get("title");
        String body = data.get("body");
        CharSequence subTitle = data.get("subtitle");
        NotificationCompat.Builder constructorNoti = new NotificationCompat.Builder(this, null);
        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){ //Validamos que la versión es mayor o igual a Oreo(8)
            CharSequence nombre = "Notificaciones";
            String descripcion = "Notificación acerca de algo";
            int importancia = NotificationManager.IMPORTANCE_HIGH;
            String canalID = "canal_de_prueba";
            //CONFIGURAMOS EL CANAL DE LA NOTIFICACIÓN
            NotificationChannel canal = new NotificationChannel(canalID, nombre, importancia);
            canal.setDescription(descripcion);
            canal.enableLights(true);

            //ESTABLECEMOS EL COLOR DE LA NOTIFICACIÓN ADJUNTA AL CANAL SI EL DISPOSITIVO LA SOPORTA
            canal.setLightColor(Color.GREEN);
            canal.enableVibration(true);
            canal.setVibrationPattern(new long[]{100,3000,100,3000,100,3000});
            nm.createNotificationChannel(canal);
            constructorNoti = new NotificationCompat.Builder(this,canalID);
        }
        Intent intento = new Intent(this, StartedSession.class);
        intento.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intento.putExtra("title",title);
        intento.putExtra("body",body);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intento,0);
        constructorNoti.setAutoCancel(true);
        constructorNoti.setContentTitle(title);  //getString(R.string.app_name)
        constructorNoti.setContentText(body);
        constructorNoti.setSubText(subTitle);
        constructorNoti.setPriority(Notification.PRIORITY_HIGH);
        constructorNoti.setSmallIcon(android.R.drawable.stat_notify_chat);
        constructorNoti.setColor(Color.BLUE);
        constructorNoti.setVibrate(new long[]{100,3000,100,3000,100,3000});
        constructorNoti.setContentIntent(pi);
        nm.notify(0,constructorNoti.build());
//        if (type.equals("json")){
//            try{
//                JSONObject objetoJason = new JSONObject(messageBody);
//                id = objetoJason.getString("id");
//                message = objetoJason.getString("message");
//                title = objetoJason.getString("title");
//            }
//            catch (JSONException e){
//                Log.d("Error de Jason",e.toString());
//            }
//        }
//        else if(type.equals("message")){
//            message = messageBody;
//        }
    }
}