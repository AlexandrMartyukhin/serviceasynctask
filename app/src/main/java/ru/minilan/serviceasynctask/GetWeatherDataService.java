package ru.minilan.serviceasynctask;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Locale;

public class GetWeatherDataService extends Service {
    String town;
    int temp;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {


        new Thread(new Runnable() {
            @Override
            public void run() {

                if ((town = intent.getExtras().getString("TOWN")) == null) {
                    town = "bad town";
                }
                PendingIntent pendingIntent = intent.getParcelableExtra("PI");

                Log.i("MyTAG", "Town in thread = " + town);

                temp = getData(town);

                Intent backIntent = new Intent().putExtra("TEMP", temp);
                try {
                    pendingIntent.send(GetWeatherDataService.this, 0, backIntent);
                } catch (PendingIntent.CanceledException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return super.onStartCommand(intent, flags, startId);
    }

    private int getData(String town) {
        //STUB
        int temp;
        temp = Calendar.getInstance().get(Calendar.SECOND);
        if (temp > 31) temp -= 30;
        return temp;
    }


}
