package com.yoie.com.clockapp.SqlDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.yoie.com.clockapp.Model.Clock;
import java.util.ArrayList;
import java.util.List;

public class ClockDaoImp implements ClockDao {

    ClockAppSQLiteDbHelper mDBHelper = null;
    public static final String KEY_ID = "_id";


    public ClockDaoImp(Context context) {
        mDBHelper = new ClockAppSQLiteDbHelper(context);
    }
    @Override
    public Clock add(Clock item) {
        ContentValues cv = new ContentValues();
        cv.put(ClockDaoImp.Schema.Columns.Name, item.getName());
        cv.put(ClockDaoImp.Schema.Columns.Date, item.getDate());
        cv.put(ClockDaoImp.Schema.Columns.Content, item.getContent());
        long id = mDBHelper.getWritableDatabase().insert(ClockDaoImp.Schema.TableName, null, cv);
        item.setId(id);
        return item;
    }

    @Override
    public boolean delete(long id) {
        String where = KEY_ID + "=" + id;
        return mDBHelper.getWritableDatabase().delete(ClockDaoImp.Schema.TableName, where , null) > 0;
    }

    @Override
    public void deleteAll() {
        mDBHelper.getWritableDatabase().execSQL("DELETE FROM " + Schema.TableName);
    }

    @Override
    public boolean update(Clock item) {
        ContentValues cv = new ContentValues();
        cv.put(ClockDaoImp.Schema.Columns.Name, item.getName());
        cv.put(ClockDaoImp.Schema.Columns.Date, item.getDate());
        cv.put(ClockDaoImp.Schema.Columns.Content, item.getContent());
        String where = KEY_ID + "=" + item.getId();
        return mDBHelper.getWritableDatabase().update(ClockDaoImp.Schema.TableName, cv, where, null) > 0;
    }

    @Override
    public Clock findById(long id) {
        Clock item = null;
        String where = KEY_ID + "=" + id;
        Cursor result = mDBHelper.getReadableDatabase().query(
                ClockDaoImp.Schema.TableName, null, where, null, null, null, null, null);
        if (result.moveToFirst()){
            item.setId(result.getLong(0));
            item.setName(result.getString(1));
            item.setDate(result.getString(2));
            item.setContent(result.getString(3));
        }
        result.close();
        return item;    }

    @Override
    public List<Clock> getAll() {
        List<Clock> result = new ArrayList<>();
        Cursor cursor = mDBHelper.getReadableDatabase().query(
                ClockDaoImp.Schema.TableName, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            Clock temp = new Clock();
            temp.setId(cursor.getLong(0));
            temp.setName(cursor.getString(1));
            temp.setDate(cursor.getString(2));
            temp.setContent(cursor.getString(3));
            result.add(temp);
        }
        cursor.close();
        return result;    }

    @Override
    public void test() {
        Clock item = new Clock("test1", "2018-08-08/01", "act1");
        Clock item2 = new Clock("test2", "2018-08-08/02", "act2");
        Clock item3 = new Clock("test3", "2018-08-08/02", "act3");

        add(item);
        add(item2);
        add(item3);
    }

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


    public static String sDeleteTableSQL(){

        return "DROP TABLE IF EXISTS " + Schema.TableName;
    }

    public static String sCreateTableSQL() {
        return "CREATE TABLE IF NOT EXISTS `"
                + Schema.TableName + "`(" + "`"
                + Schema.Columns.Id + "` INTEGER PRIMARY KEY," + "`"
                + Schema.Columns.Name + "` TEXT NOT NULL," + "`"
                + Schema.Columns.Date + "` TEXT NOT NULL," + "`"
                + Schema.Columns.Content + "` TEXT NOT NULL)";

    }
}
