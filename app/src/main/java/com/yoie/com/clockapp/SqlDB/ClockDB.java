package com.yoie.com.clockapp.SqlDB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ClockDB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Clock";
    private static final int DATABASE_VERSION = 1;

    public ClockDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public ClockDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "  + ClockScheme.ClockTable.name  +"(" +
                "_id integer primary key autoincrement," +
                ClockScheme.ClockTable.Cols.NAME + ","   +
                ClockScheme.ClockTable.Cols.NAME + ","   +
                ClockScheme.ClockTable.Cols.NAME + ","   +
                ClockScheme.ClockTable.Cols.NAME + ","   +
                ")"
        );

        db.execSQL( "CREATE TABLE IF NOT EXISTS `"
                +  ClockScheme.ClockTable.Cols.NAME + "`(" + "`"
                +  ClockScheme.ClockTable.Cols.NAME + "` TEXT KEY UNIQUE," + "`"
                +  ClockScheme.ClockTable.Cols.NAME + "` TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
