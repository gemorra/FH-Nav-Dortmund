package FHNav.sqlite;

import java.util.ArrayList;
import java.util.List;

import FHNav.model.AktuellesItem;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Database access object for Aktuelles.
 *
 */
public class AktuellesDAO {

	// Database fields
	private SQLiteDatabase database;
	private SQLiteHelper dbHelper;
	private String[] allColumns = { SQLiteHelper.COLUMN_ID,
			SQLiteHelper.COLUMN_TITLE, SQLiteHelper.COLUMN_DESCRIPTION,
			SQLiteHelper.COLUMN_LINK, SQLiteHelper.COLUMN_PUBDATE,
			SQLiteHelper.COLUMN_HASH };

	
	public AktuellesDAO(Context context) {
		dbHelper = new SQLiteHelper(context);
		open();
	}

	/**
	 * Closes the database connection.
	 */
	public void close() {
		dbHelper.close();
	}

	/**
	 * Determines if a given hash is already present in the database.
	 * 
	 * @param hash input hash
	 * @return true if found, false if not found in database.
	 */
	public boolean containsHash(String hash) {
		boolean found = false;
		Cursor cursor = database.query(SQLiteHelper.TABLE, allColumns,
				String.format("%s = '%s'", SQLiteHelper.COLUMN_HASH, hash),
				null, null, null, null);
		if (cursor.getCount() > 0) {
			found = true;
		}
		cursor.close();
		return found;

	}

	/**
	 * Writes an item to the database.
	 * 
	 * @param item
	 */
	public void createItem(AktuellesItem item) {
		ContentValues values = new ContentValues();
		values.put(SQLiteHelper.COLUMN_TITLE, item.getTitle());
		values.put(SQLiteHelper.COLUMN_DESCRIPTION, item.getDescription());
		values.put(SQLiteHelper.COLUMN_LINK, item.getLink());
		values.put(SQLiteHelper.COLUMN_PUBDATE, item.getPubDate());
		values.put(SQLiteHelper.COLUMN_HASH, item.getHash());
		long insertId = database.insert(SQLiteHelper.TABLE, null, values);
		Cursor cursor = database.query(SQLiteHelper.TABLE, allColumns,
				SQLiteHelper.COLUMN_ID + " = " + insertId, null, null, null,
				null);
		cursor.moveToFirst();
		// AktuellesItem newItem = cursorToComment(cursor);
		cursor.close();
	}

	/**
	 * Compares items in the database to a given list of items. Items which are not in the DB yet are inserted and returned.
	 * 
	 * @param list input items to be checked
	 * @return items which were not in the database before and were inserted by this function.
	 */
	public List<AktuellesItem> getNewItemsFromList(List<AktuellesItem> list) {
		List<AktuellesItem> newItems = new ArrayList<AktuellesItem>();

		for (AktuellesItem i : list) {
			if (!containsHash(i.getHash())) {
				createItem(i);
				newItems.add(i);
			}
		}
		return newItems;
	}

	// TODO delete old entries

	/**
	 * Opens the database connection.
	 * 
	 * @throws SQLException
	 */
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	
}