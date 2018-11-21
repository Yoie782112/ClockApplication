package com.yoie.com.clockapp.SqlDB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ClockAppSQLiteDbHelper extends SQLiteOpenHelper {
    private static final String mDatabaseName = "ClockApp.db";
    private static final int mVersion = 1;
    private static SQLiteDatabase mDatabase;
    private Context context ;

    public ClockAppSQLiteDbHelper(Context context) {
        super(context, mDatabaseName, null, mVersion);
        this.context = context;
    }

//    public ClockAppSQLiteDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
//        super(context, name, factory, mVersion);
//    }

    public static SQLiteDatabase getDatabase(Context context) {
        if (mDatabase == null || !mDatabase.isOpen()) {
            mDatabase = new ClockAppSQLiteDbHelper(context).getWritableDatabase();
        }

        return mDatabase;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ClockDao.getCraeteTableSQL());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ClockDao.Schema.TableName) ;
        db.execSQL(ClockDao.getCraeteTableSQL());
    }
}
