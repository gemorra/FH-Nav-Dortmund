package FHNav.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Helper class for SQLite queries.
 *
 */
public class SQLiteHelper extends SQLiteOpenHelper {

	public static final String TABLE = "aktuelles";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_TITLE = "title";
	public static final String COLUMN_DESCRIPTION = "description";
	public static final String COLUMN_LINK = "link";
	public static final String COLUMN_PUBDATE = "pubdate";
	public static final String COLUMN_HASH = "hash";

	private static final String DATABASE_NAME = "fhnav.db";
	private static final int DATABASE_VERSION = 4;

	// Database creation sql statement
	private static final String DATABASE_CREATE = String
			.format("create table %s (%s integer primary key autoincrement, %s text not null, %s text not null, %s text not null, %s text not null, %s text not null);",
					TABLE, COLUMN_ID, COLUMN_TITLE, COLUMN_DESCRIPTION,
					COLUMN_LINK, COLUMN_PUBDATE, COLUMN_HASH);

	public SQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(SQLiteHelper.class.getName(),
				String.format(
						"Upgrading database from version %s to %s, which will destroy all old data",
						oldVersion, newVersion));
		db.execSQL("DROP TABLE IF EXISTS " + TABLE);
		onCreate(db);
	}

}