package pengziyue.com.polling;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class PollingService extends Service {
    private static final String TAG = "MainActivity";
    public static final String ACTIOON = "android.intent.action.PollingService";

    public PollingService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new PollingThread().start();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    int count = 0;

    private class PollingThread extends Thread {
        @Override
        public void run() {
            Log.v(TAG, "polling....");
            count++;
            //当计数被5整除的时弹出消息
            if (count % 5 == 0) {
                Log.v(TAG, "you have new message...");
            }
            super.run();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initNotificationManager();
    }

    //初始化通知
    private void initNotificationManager() {

    }
}
