package com.yoie.com.clockapp.SqlDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yoie.com.clockapp.ObjDataStructure.Clock;

import java.util.ArrayList;
import java.util.List;

public class ClockDao extends BaseDao{
    private static ClockDao mInstance;
    private SQLiteDatabase mDataBase;
    public static final String KEY_ID = "_id";

    public static class Schema {
        public static final String TableName = "clocks";
        /**
         * Define table schema
         */
        public static class Columns {
            public static final String Id = "id";
            public static final String Name = "name";
            public static final String Date = "date";
            public static final String Content = "content";


        };
    }

    public ClockDao(Context context) {
        super(context);
        mDataBase = ClockAppSQLiteDbHelper.getDatabase(context);

    }

    public synchronized static ClockDao getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ClockDao(context);
        }
        return mInstance;
    }

    public void close() {
        mDataBase.close();
    }

    public Clock insert(Clock item) {
        ContentValues cv = new ContentValues();
        cv.put(Schema.Columns.Name, item.getName());
        cv.put(Schema.Columns.Date, item.getDate());
        cv.put(Schema.Columns.Content, item.getContent());
        long id = mDataBase.insert(Schema.TableName, null, cv);
        item.setId(id);
        return item;
    }

    public boolean update(Clock item) {
        ContentValues cv = new ContentValues();
        cv.put(Schema.Columns.Name, item.getName());
        cv.put(Schema.Columns.Date, item.getDate());
        cv.put(Schema.Columns.Content, item.getContent());
        String where = KEY_ID + "=" + item.getId();
        return mDataBase.update(Schema.TableName, cv, where, null) > 0;
    }

    public boolean delete(long id){
        String where = KEY_ID + "=" + id;
        return mDataBase.delete(Schema.TableName, where , null) > 0;
    }

    public List<Clock> getAll() {
        List<Clock> result = new ArrayList<>();
        Cursor cursor = mDataBase.query(
                Schema.TableName, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }

    public Clock get(long id) {
        Clock item = null;
        String where = KEY_ID + "=" + id;
        Cursor result = mDataBase.query(
                Schema.TableName, null, where, null, null, null, null, null);
        if (result.moveToFirst())
            item = getRecord(result);
        result.close();
        return item;
    }

    public Clock getRecord(Cursor cursor) {
        Clock result = new Clock();
        result.setId(cursor.getLong(0));
        result.setName(cursor.getString(1));
        result.setDate(cursor.getString(2));
        result.setContent(cursor.getString(3));
        return result;
    }

    public int getCount() {
        int result = 0;
        Cursor cursor = mDataBase.rawQuery("SELECT COUNT(*) FROM " + Schema.TableName, null);
        if (cursor.moveToNext()) {
            result = cursor.getInt(0);
        }
        return result;
    }

    public void sample() {
        Clock item = new Clock("test1", "2018-08-08/01", "act1");
        Clock item2 = new Clock("test2", "2018-08-08/02", "act2");
        Clock item3 = new Clock("test3", "2018-08-08/02", "act3");

        insert(item);
        insert(item2);
        insert(item3);
    }



    public static String getCraeteTableSQL() {
        return "CREATE TABLE IF NOT EXISTS `"
                + Schema.TableName + "`(" + "`"
                + Schema.Columns.Id + "` INTEGER PRIMARY KEY," + "`"
                + Schema.Columns.Name + "` TEXT NOT NULL," + "`"
                + Schema.Columns.Date + "` TEXT NOT NULL," + "`"
                + Schema.Columns.Content + "` TEXT NOT NULL)";
    }
}
