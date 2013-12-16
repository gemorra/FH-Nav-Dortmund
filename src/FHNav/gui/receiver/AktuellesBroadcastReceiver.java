package FHNav.gui.receiver;

import FHNav.gui.service.AktuellesNotificationService;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
 
/**
 * Receives a broadcast to check Aktuelles. 
 * 
 * @author Admin
 *
 */
public class AktuellesBroadcastReceiver extends BroadcastReceiver
{
	
    @Override
    public void onReceive(Context context, Intent intent)
    {
    	Intent notificationService = new Intent(context, AktuellesNotificationService.class);
        context.startService(notificationService);
    	
    }
}