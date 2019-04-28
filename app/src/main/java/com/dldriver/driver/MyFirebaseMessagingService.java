package com.dldriver.driver;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import com.dldriver.driver.models.Address;
import com.dldriver.driver.room.AppDatabase;
import com.dldriver.driver.ui.MyNewOrders;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.core.app.NotificationCompat;

public class MyFirebaseMessagingService extends FirebaseMessagingService {


    private static final String TAG = "MyFirebaseMsgService";
    private static int NOTIFICATION_ID = 1;
    public List<Address> mAddressList = new ArrayList<>();
    private Context context;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {


        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            scheduleJob();
        }

        if (remoteMessage.getData() != null) {
            String title = "New Order";
            Map<String, String> addressMap = remoteMessage.getData();
            Address address = new Address(Integer.parseInt(addressMap.get("addressId")), addressMap.get("email"), addressMap.get("orderId"), addressMap.get("orderNo"),
                    addressMap.get("Additional"), addressMap.get("Address_Name"),
                    addressMap.get("Apartment"), addressMap.get("Area"), addressMap.get("Block"),
                    addressMap.get("Floor"), addressMap.get("Full_Name"), addressMap.get("House"),
                    addressMap.get("Mobile"), addressMap.get("Street"), addressMap.get("delType"), addressMap.get("timeSlot"),
                    addressMap.get("OrderTime"), addressMap.get("OrderDate"), addressMap.get("DelTime"),
                    addressMap.get("DelDate"),
                    Double.parseDouble(addressMap.get("lat") == null || addressMap.get("lat").equals(" ") ? "0" : addressMap.get("lat")),
                    Double.parseDouble(addressMap.get("lng") == null || addressMap.get("lng").equals(" ") ? "0" : addressMap.get("lng")),
                    false, addressMap.get("category"), Integer.parseInt(addressMap.get("deliveryStatus")), Integer.parseInt(addressMap.get("paymentStatus") == null ? "0" : addressMap.get("paymentStatus")));
            AppDatabase database = AppDatabase.getInstance(getApplicationContext());
            if(Integer.parseInt(addressMap.get("deliveryStatus") ) == 8)
                title = "Cancelled Order "+address.getOrderId();
            new Thread(() -> {
                Address address1 = new Address();
                address1 = database.mAddressDao().getAddressByOrderId(address.getOrderId());
                if (address1 == null)
                    database.mAddressDao().insertNewOrderAddress(address);
                else {
                    address.setId(address1.getId());
                    if(address.getDeliveryStatus() == 8) {
                        Log.d(TAG, "Message data payload: Inside Delete" + address1.getId());
                        database.mAddressDao().deleteAddress(address);
                    }else {
                        database.mAddressDao().updateAddress(address);
                    }
                }
            }).start();
            mAddressList.add(address);
            Intent intent = new Intent(this, MyNewOrders.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("clickable", true);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0  /*Request code*/, intent, 0);
            String channelId = getString(R.string.default_notification_channel_id);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(this, channelId)
                            .setSmallIcon(R.drawable.icon)
                            .setContentTitle(title)
                            .setContentText(address.getFull_Name() + "\n" + address.getAddress_Name() + "\n" + address.getArea() + ", " + address.getFloor())
                            .setAutoCancel(true)
                            .setSound(defaultSoundUri)
                            .setContentIntent(pendingIntent);
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
          /*  int badgeCount = 1;
            ShortcutBadger.applyCount(context, badgeCount); //for 1.1.4+
            ShortcutBadger.with(getApplicationContext()).count(badgeCount); //for 1.1.3*/


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(channelId,
                        "Channel human readable title",
                        NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(channel);
            }
            notificationManager.notify(0, notificationBuilder.build());


        }
    }

    @Override
    public void onNewToken(String token) {
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
    }

    private void scheduleJob() {
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
        Job myJob = dispatcher.newJobBuilder()
                .setService(MyJobService.class)
                .setTag("my-job-tag")
                .build();
        dispatcher.schedule(myJob);
    }

    private void generateNotification(String title, String messageBody) {
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = getApplicationContext().getString(R.string.default_notification_channel_id);
            NotificationChannel channel = new NotificationChannel(channelId, "AlmoskyDriver", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(messageBody);
            channel.setName(getString(R.string.app_name));
            mNotificationManager.createNotificationChannel(channel);
            Intent intent = new Intent(this, MyNewOrders.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_ONE_SHOT);
            NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.icon)
                    .setContentTitle(title)
                    .setContentText(messageBody)
                    .setAutoCancel(true);

            mNotifyBuilder.setChannelId(channelId);
            mNotifyBuilder.setDefaults(Notification.DEFAULT_SOUND);
            mNotifyBuilder.setDefaults(Notification.DEFAULT_VIBRATE);
            mNotificationManager.notify(1, mNotifyBuilder.build());
        } else {
            Intent intent = new Intent(this, MyNewOrders.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.icon)
                    .setContentTitle(title)
                    .setContentText(messageBody)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);


            mNotifyBuilder.setDefaults(Notification.DEFAULT_SOUND);
            mNotifyBuilder.setDefaults(Notification.DEFAULT_VIBRATE);
            if (NOTIFICATION_ID > 1073741824) {
                NOTIFICATION_ID = 0;
            }
            mNotificationManager.notify(1, mNotifyBuilder.build());
        }


    }
}

