package jajabor.in.app.Helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper2 extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "wishlist_database";
    private static final Integer DATABASE_VERSION = 1;


    public DatabaseHelper2(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MISSEDCALLS_TABLE = "CREATE TABLE " +
                Contract2.CartItem2.TABLE_NAME + " (" +
                Contract2.CartItem2._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Contract2.CartItem2.COLUMN_NAME + " TEXT NOT NULL, " +
                Contract2.CartItem2.COLUMN_PRICE + " INTEGER NOT NULL, " +
                Contract2.CartItem2.COLUMN_PIC + " TEXT NOT NULL," +
                Contract2.CartItem2.COLUMN_DESC + " TEXT NOT NULL," +
                Contract2.CartItem2.COLUMN_PID + " INTEGER NOT NULL" +

                ");";
        db.execSQL(SQL_CREATE_MISSEDCALLS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ Contract2.CartItem2.TABLE_NAME);
        onCreate(db);
    }
}
