package agrawal.snehal.allwidgetexample.datahandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataHandlerPersonDetail {
    private static final String KEY_ROWID = "_id";
    private static final String NAME = "name";
    private static final String AGE = "age";

    private static final String TABLE_NAME = "table_person_detail";
    private static final String DATABASE_NAME = "db_person_detail";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_CREATE = "create table "
            + TABLE_NAME + " ("
            + KEY_ROWID + " integer primary key autoincrement,"
            + NAME + " text,"
            + AGE + " text)";

    private DatabaseHelper dbhelper;
    private SQLiteDatabase db;

    public DataHandlerPersonDetail(Context ctx) {
        this.dbhelper = new DatabaseHelper(ctx);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context ctx) {
            super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(TABLE_CREATE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

    public DataHandlerPersonDetail open() {
        this.db = this.dbhelper.getWritableDatabase();
        return this;
    }

    public void close() {
        this.db.close();
    }

    public boolean insertData(String name, String age) {
        ContentValues cv = new ContentValues();
        cv.put(NAME, name);
        cv.put(AGE, age);
        return this.db.insertOrThrow(TABLE_NAME, null, cv) > 0;
    }

    public boolean updatedata(String rowid, String name, String age) {
        ContentValues cv = new ContentValues();
        cv.put(NAME, name);
        cv.put(AGE, age);
        return this.db.update(TABLE_NAME, cv, KEY_ROWID + "=?", new String[]{rowid}) > 0;
    }

    public Cursor returnAllData() {
        return this.db.query(TABLE_NAME, new String[]{KEY_ROWID, NAME, AGE}, null, null, null, null, null);
    }

    public Cursor returnDataAccId(String uid) {
        return this.db.query(true, TABLE_NAME, new String[]{KEY_ROWID, NAME, AGE}, KEY_ROWID + "=?", new String[]{uid}, null, null, null, null);
    }

    public boolean deleteRow(String rowId) {
        return this.db.delete(TABLE_NAME, KEY_ROWID + "=?", new String[]{rowId}) > 0;
    }

    public boolean delete_all() {
        return this.db.delete(TABLE_NAME, null, null) > 0;
    }
}
