package jajabor.in.app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "cart_database";
    private static final Integer DATABASE_VERSION = 1;

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MISSEDCALLS_TABLE = "CREATE TABLE " +
                Contract.CartItem.TABLE_NAME + " (" +
                Contract.CartItem._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Contract.CartItem.COLUMN_NAME + " TEXT NOT NULL, " +
                Contract.CartItem.COLUMN_PRICE + " INTEGER NOT NULL, " +
                Contract.CartItem.COLUMN_PIC + " TEXT NOT NULL," +
                Contract.CartItem.COLUMN_SIZE + " TEXT NOT NULL," +
                Contract.CartItem.COLUMN_COLOR + " TEXT NOT NULL," +
                Contract.CartItem.COLUMN_QUANTITY + " INTEGER NOT NULL," +
                Contract.CartItem.COLUMN_PID + " INTEGER NOT NULL" +
                ");";
        db.execSQL(SQL_CREATE_MISSEDCALLS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ Contract.CartItem.TABLE_NAME);
        onCreate(db);
    }
}
