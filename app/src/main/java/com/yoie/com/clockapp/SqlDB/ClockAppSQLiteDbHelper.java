package com.yoie.com.clockapp.SqlDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.yoie.com.clockapp.Model.Clock;

import java.util.ArrayList;
import java.util.List;

public class ClockAppSQLiteDbHelper extends SQLiteOpenHelper {
    private static final String mDatabaseName = "ClockApp.db";
    private static final int mVersion = 1;
    public static final String KEY_ID = "_id";
    private static SQLiteDatabase mDataBase;
    private Context mContext ;

    public ClockAppSQLiteDbHelper(Context context) {
        super(context, mDatabaseName, null, mVersion);
    }

//    public ClockAppSQLiteDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
//        super(context, name, factory, mVersion);
//    }

    public static SQLiteDatabase getDatabase(Context context) {
        if (mDataBase == null || !mDataBase.isOpen()) {
            mDataBase = new ClockAppSQLiteDbHelper(context).getWritableDatabase();
        }

        return mDataBase;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        mDataBase.execSQL(ClockDaoImp.sCreateTableSQL());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mDataBase.execSQL(ClockDaoImp.sDeleteTableSQL());
        mDataBase.execSQL(ClockDaoImp.sCreateTableSQL());
    }

    public void deleteTable(String tableName){
        mDataBase.execSQL("DROP TABLE IF EXISTS " + tableName);
    }












}
