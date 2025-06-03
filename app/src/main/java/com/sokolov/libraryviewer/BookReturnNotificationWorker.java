package com.sokolov.libraryviewer;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class BookReturnNotificationWorker extends Worker {

    public BookReturnNotificationWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        UserDataManager.loadUserData(getApplicationContext());
        if (UserDataManager.getUser() == null) {
            return Result.success();
        }
        List<Book> books = UserDataManager.getUser().bookList;
        long now = System.currentTimeMillis();
        long minDaysLeft = Integer.MAX_VALUE;
        for (Book book : books) {
            long planReturn = book.getPlanDate().getTime();
            long daysLeft = (planReturn - now) / (1000 * 60 * 60 * 24);

            if (minDaysLeft > daysLeft) {
                minDaysLeft = daysLeft;
            }
        }
        if (minDaysLeft <= 7) {
            sendNotification(minDaysLeft);
            setupNextWorker(getApplicationContext());
        }
        return Result.success();
    }

    public static void setupNextWorker(Context context) {
        Constraints constraints = new Constraints.Builder()
                .setRequiresBatteryNotLow(false)
                .build();
        PeriodicWorkRequest notificationWork =
                new PeriodicWorkRequest.Builder(BookReturnNotificationWorker.class, 15, TimeUnit.MINUTES)
                        .addTag("book_notification_work")
                        .setConstraints(constraints)
                        .setInitialDelay(15, TimeUnit.MINUTES)
                        .build();

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                "BookReturnNotifications",
                ExistingPeriodicWorkPolicy.KEEP,
                notificationWork
        );
    }

    private void sendNotification(long daysLeft) {
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = "book_channel";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Book notifications", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder;
        if (daysLeft < 0) {
            builder = new NotificationCompat.Builder(getApplicationContext(), channelId)
                    .setContentTitle("НТБ СГУГиТ")
                    .setContentText("У Вас закончился срок возврата взятой литературы.")
                    .setSmallIcon(R.drawable.ic_notification);
        } else {
            builder = new NotificationCompat.Builder(getApplicationContext(), channelId)
                    .setContentTitle("НТБ СГУГиТ")
                    .setContentText("У Вас заканчивается  срок возврата взятой литературы.")
                    .setSmallIcon(R.drawable.ic_notification);
        }
        notificationManager.notify(UUID.randomUUID().hashCode(), builder.build());
    }
}