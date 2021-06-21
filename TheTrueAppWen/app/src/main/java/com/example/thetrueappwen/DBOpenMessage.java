package com.example.thetrueappwen;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBOpenMessage extends SQLiteOpenHelper {
    final String db_wen2 = "create table db_wen2 (_id integer primary key autoincrement,username varchar,usermoney integer,userevent varchar,userkind varchar,userdata varchar,usertime varchar,userchoice varchar)";

    public DBOpenMessage(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(db_wen2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("数据", "更新" + "oldVerSion" + "-->" + newVersion);
    }

    //具体数据库的相关操作
    public void insertMessage(SQLiteDatabase sqLiteDatabase, Message message) {
        ContentValues cv = new ContentValues();
        cv.put("username", message.username);
        cv.put("userkind", message.userkind);
        cv.put("userchoice", message.userchoice);
        cv.put("userdata", message.userdata);
        cv.put("usertime", message.usertime);
        cv.put("userevent", message.userevent);
        cv.put("usermoney", message.usermoney);
        sqLiteDatabase.insert(db_wen2, null, cv);
    }

    public Cursor getAllCostData(String username) {
        SQLiteDatabase database = getWritableDatabase();
        return database.query("db_wen2", null, "username=?",new String[]{username},null, null,"usermoney desc" );//"userdata desc"
    }

    public void deleteAllData() {
        SQLiteDatabase database = getWritableDatabase();
        database.delete("db_wen2", null, null);
    }
    public void deletebyid(String userevent,String usermoney,String username)
    {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("delete from db_wen2 where userevent=? and usermoney=? and username=?", new String[]{userevent,usermoney,username});

    }

}
