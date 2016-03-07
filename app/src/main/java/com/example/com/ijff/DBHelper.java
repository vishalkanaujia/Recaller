package com.example.com.ijff;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Vishal Kanaujia on 2/29/2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "infowallet.db";
    public static final String INFO_TABLE_NAME = "info";
    public static final String KEYWORD_COLUMN_NAME = "keyword";
    public static final String VALUE_COLUMN_NAME = "value";

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(
                "create table info " +
                        "(id integer primary key, keyword text, value text, time integer);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS info");
        onCreate(db);
    }

    public boolean insertInfo(String keyword, String value)
    {
        Log.wtf("dblog", "Insert: keyword= " + keyword + " Value= " + value);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("keyword", keyword);
        contentValues.put("value", value);
        Calendar c = Calendar.getInstance();
        int seconds = c.get(Calendar.SECOND);
        contentValues.put("time", seconds);
        db.insert(INFO_TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getData(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from info where id=" + id + "", null);
        return res;
    }

    public Cursor getData(String search_string)
    {
        Log.wtf("dblog", "Query is=" + search_string);

        SQLiteDatabase db = this.getReadableDatabase();
        //Cursor res = db.rawQuery("select * from info where keyword like " + search_string + "", null);
        String query = "select * from info where keyword like '" + search_string + "'";
        Cursor res = db.rawQuery(query, null);
        return res;
    }

    public int numOfRows()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, INFO_TABLE_NAME);
        return numRows;
    }

    public boolean updateInfo(int id, String keyword, String new_value)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("keyword", keyword);
        contentValues.put("value", new_value);

        Calendar c = Calendar.getInstance();
        int seconds = c.get(Calendar.SECOND);
        contentValues.put("time", seconds);

        db.update(INFO_TABLE_NAME, contentValues, "id= ? ", new String[] {Integer.toString(id)} );
        return true;
    }

    public Integer deleteInfo(Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(INFO_TABLE_NAME, "id = ? ", new String[] {Integer.toString(id)});
    }

    public ArrayList<String> getAllInfo(String search_string)
    {
        ArrayList<String> arrayList = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res;

        Log.wtf("dbmsg", "getAllInfo: Search string is " + search_string);
        if (search_string == null) {
            res = db.rawQuery("select * from info", null);
        } else {
            String query = "select * from info where keyword like '%" + search_string + "%'";
            Log.wtf("dbmsg", "getAllInfo: query is " + query);
            res = db.rawQuery(query, null);
        }
        Log.wtf("dbmsg", "Cursor row count is " + res.getCount());
        res.moveToFirst();

        while (!res.isAfterLast()) {
            arrayList.add(res.getString(res.getColumnIndex(KEYWORD_COLUMN_NAME)));
            res.moveToNext();
        }
        return arrayList;
    }

    public void closeDB(Cursor res)
    {
        if (!res.isClosed()) {
            Log.wtf("dblog", "Closing DB\n");
            res.close();
        }
    }
}