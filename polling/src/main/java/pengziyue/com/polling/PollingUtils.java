package pengziyue.com.polling;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

/**
 * Created by PengYue on 2016/5/5.
 * 轮询的工具类 用来开启轮询，
 */
public class PollingUtils {
    /**
     * 开启轮询服务
     *
     * @param context
     * @param seconds
     * @param cls
     * @param action
     */
    public static void startPollingService(Context context, int seconds,
                                           Class<?> cls, String action) {
        //获取AlarmManager系统服务
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        //包装需要执行Service的Intent
        Intent intent = new Intent(context, cls);
        intent.setAction(action);

        //预处理意图
        PendingIntent pendingIntent = PendingIntent.getActivity
                (context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        //触发服务的起始时间
        long triggerAtTime = SystemClock.elapsedRealtime();
        //使用AlarmManger的setRepeating方法设置定期执行的时间间隔
        //和需要执行 的Service
        manager.setRepeating(AlarmManager.ELAPSED_REALTIME, triggerAtTime,
                seconds * 1000, pendingIntent);//seconds * 1000（秒）
    }

    /**
     * 停止轮询服务
     *
     * @param context
     * @param cls
     * @param action
     */
    public static void stopPollingService(Context context, Class<?> cls, String action) {
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, cls);
        intent.setAction(action);


    }
}
