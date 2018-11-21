package com.yoie.com.clockapp.SqlDB;

import android.content.Context;

public class BaseDao {
    protected ClockAppSQLiteDbHelper mSQLite;

    public BaseDao(Context context) {
        mSQLite = new ClockAppSQLiteDbHelper(context);
    }
}
