package FHNav.sqlite;

import java.util.ArrayList;
import java.util.List;

import FHNav.model.AktuellesItem;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class AktuellesDAO {

  // Database fields
  private SQLiteDatabase database;
  private SQLiteHelper dbHelper;
  private String[] allColumns = { SQLiteHelper.COLUMN_ID,
      SQLiteHelper.COLUMN_TITLE, SQLiteHelper.COLUMN_DESCRIPTION, SQLiteHelper.COLUMN_LINK, SQLiteHelper.COLUMN_PUBDATE, SQLiteHelper.COLUMN_HASH };

  public AktuellesDAO(Context context) {
    dbHelper = new SQLiteHelper(context);
    open();
  }

  public void open() throws SQLException {
    database = dbHelper.getWritableDatabase();
  }

  public void close() {
    dbHelper.close();
  }

  public void createItem(AktuellesItem item) {
    ContentValues values = new ContentValues();
    values.put(SQLiteHelper.COLUMN_TITLE, item.getTitle());
    values.put(SQLiteHelper.COLUMN_DESCRIPTION, item.getDescription());
    values.put(SQLiteHelper.COLUMN_LINK, item.getLink());
    values.put(SQLiteHelper.COLUMN_PUBDATE, item.getPubDate());
    values.put(SQLiteHelper.COLUMN_HASH, item.getHash());
    long insertId = database.insert(SQLiteHelper.TABLE, null,
        values);
    Cursor cursor = database.query(SQLiteHelper.TABLE,
        allColumns, SQLiteHelper.COLUMN_ID + " = " + insertId, null,
        null, null, null);
    cursor.moveToFirst();
    //AktuellesItem newItem = cursorToComment(cursor);
    cursor.close();
  }
  
  
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

  //TODO delete old entries
//  public void deleteItem(AktuellesItem item) {
//    long id = item.getId();
//    System.out.println("Comment deleted with id: " + id);
//    database.delete(MySQLiteHelper.TABLE_COMMENTS, MySQLiteHelper.COLUMN_ID
//        + " = " + id, null);
//  }

//  public List<Comment> getAllComments() {
//    List<Comment> comments = new ArrayList<Comment>();
//
//    Cursor cursor = database.query(SQLiteHelper.TABLE_COMMENTS,
//        allColumns, null, null, null, null, null);
//
//    cursor.moveToFirst();
//    while (!cursor.isAfterLast()) {
//      Comment comment = cursorToComment(cursor);
//      comments.add(comment);
//      cursor.moveToNext();
//    }
//    // make sure to close the cursor
//    cursor.close();
//    return comments;
//  }
  
  public boolean containsHash(String hash) {
	  boolean found = false;
	  Cursor cursor = database.query(SQLiteHelper.TABLE, allColumns, String.format("%s = '%s'", SQLiteHelper.COLUMN_HASH, hash), null, null, null, null);
	  if (cursor.getCount() > 0) {
		  found = true;
	  }
	  cursor.close();
	  return found;
	  
  }

//  private Comment cursorToComment(Cursor cursor) {
//    Comment comment = new Comment();
//    comment.setId(cursor.getLong(0));
//    comment.setComment(cursor.getString(1));
//    return comment;
//  }
} 