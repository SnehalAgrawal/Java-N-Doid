package agrawal.snehal.allwidgetexample.datahandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataHandlerDownloadManager {
    private static final String LINK = "link";
    private static final String DATE = "date";
    private static final String LOCAL_LINK = "local_link";
    public static final String KEY_ROWID = "_id";

    private static final String TABLE_NAME = "table_download_manager";
    private static final String DATABASE_NAME = "db_download_manager";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_CREATE = "create table " + TABLE_NAME + " (" + KEY_ROWID + " integer primary key autoincrement," + LINK + " text," + DATE + " text," + LOCAL_LINK + " text)";
    private DatabaseHelper dbhelper;
    private SQLiteDatabase db;

    public DataHandlerDownloadManager(Context ctx) {
        this.dbhelper = new DatabaseHelper(ctx);
    }

    public DataHandlerDownloadManager open() {
        this.db = this.dbhelper.getWritableDatabase();
        return this;
    }

    private class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context ctx) {
            super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(TABLE_CREATE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

    public void close() {
        this.db.close();
    }

    public long insertData(String link, String date, String local_link) {
        ContentValues cv = new ContentValues();
        cv.put(LINK, link);
        cv.put(DATE, date);
        cv.put(LOCAL_LINK, local_link);
        return this.db.insertOrThrow(TABLE_NAME, null, cv);
    }

    public Cursor returnData() {
        return this.db.query(TABLE_NAME, new String[]{LINK, DATE, LOCAL_LINK}, null, null, null, null, null);
    }

    public Cursor returnDataAccDateLink(String date, String link) {
        return this.db.query(true, TABLE_NAME, new String[]{LOCAL_LINK}, DATE + "=? and " + LINK + "=?", new String[]{date, link}, null, null, null, null);
    }

    public boolean deleteRow(String local_link) {
        return this.db.delete(TABLE_NAME, LOCAL_LINK + "=?", new String[]{local_link}) > 0;
    }

    public int delete_all() {
        return this.db.delete(TABLE_NAME, null, null);
    }
}
