package jajabor.in.app.Helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper3 extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "custom_product_database";
    private static final Integer DATABASE_VERSION = 1;

    public DatabaseHelper3(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MISSEDCALLS_TABLE = "CREATE TABLE " +
                Contract3.CartItem.TABLE_NAME + " (" +
                Contract3.CartItem._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Contract3.CartItem.COLUMN_NAME + " TEXT NOT NULL, " +
                Contract3.CartItem.COLUMN_PRICE + " INTEGER NOT NULL, " +
                Contract3.CartItem.COLUMN_PIC + " TEXT NOT NULL," +
                Contract3.CartItem.COLUMN_SIZE + " TEXT NOT NULL," +
                Contract3.CartItem.COLUMN_COLOR + " TEXT NOT NULL," +
                Contract3.CartItem.COLUMN_QUANTITY + " INTEGER NOT NULL," +
                Contract3.CartItem.COLUMN_PID + " INTEGER NOT NULL" +
                ");";
        db.execSQL(SQL_CREATE_MISSEDCALLS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ Contract3.CartItem.TABLE_NAME);
        onCreate(db);
    }
}
