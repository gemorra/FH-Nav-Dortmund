package FHNav.gui.service;

import FHNav.controller.AktuellesParser;
import FHNav.gui.R;
import FHNav.model.AktuellesItem;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

/**
 * Service to notify new items from Aktuelles.
 *
 */
public class AktuellesNotificationService extends Service

{
	private NotificationManager notificationManager;

	private void notify(Context context, String title, String content,
			String ticket) {
		notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);

		Notification notification = new Notification(
				R.drawable.notification_icon, ticket, 0);
		// generate notification as web link
		Intent notificationIntent = new Intent(Intent.ACTION_VIEW);
		notificationIntent.setData(Uri.parse(AktuellesParser.WEB_URL));
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
				notificationIntent, 0);

		notification.setLatestEventInfo(context, title, content, contentIntent);
		notification.defaults |= Notification.DEFAULT_SOUND;
		notification.defaults |= Notification.DEFAULT_VIBRATE;
		notification.defaults |= Notification.DEFAULT_LIGHTS;
		notification.flags |= Notification.FLAG_AUTO_CANCEL;

		// generate hash as unique internal ID to never overwrite old
		// notifications
		int internalId = (title + content + ticket).hashCode();
		notificationManager.notify(internalId, notification);

	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);

		Log.d(this.getClass().getName(), "starting notification service");

		AktuellesParser aktuelles = new AktuellesParser(
				this.getApplicationContext());

		for (AktuellesItem i : aktuelles.getNewAktuellesEntries()) {
			if (i != null) {
				notify(this.getApplicationContext(), i.getTitle(),
						i.getDescription(), i.getTitle());
				Log.w(this.getClass().toString(), "notifying: " + i.getTitle());
			} else {
				Log.e(this.getClass().toString(),
						"Item is null but was added as a new item");
			}
		}
		this.stopSelf();
	}
}