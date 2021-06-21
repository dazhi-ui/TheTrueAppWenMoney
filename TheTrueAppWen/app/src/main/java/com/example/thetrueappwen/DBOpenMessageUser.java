package com.example.thetrueappwen;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBOpenMessageUser extends SQLiteOpenHelper
{
    final String message="create table db_wen (_id integer primary key autoincrement,username varchar,password varchar,usercheck varchar,userpicture varchar)";
    public DBOpenMessageUser(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, null, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(message);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("数据","更新"+"oldVerSion"+"-->"+newVersion);
    }
    public Cursor getAllCostData(String username) {
        SQLiteDatabase database = getWritableDatabase();
        return database.query("db_wen", null, "username=?",new String[]{username},null, null,null );//"userdata desc"
    }
    public void updatauser(String username,String usercheck)
    {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("usercheck",usercheck);
        database.update("db_wen",values,"username=?",new String[]{username});
    }
    public void updatauserpicture(String username,String userpicture)
    {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("userpicture",userpicture);
        database.update("db_wen",values,"username=?",new String[]{username});
    }
    public void updatapassword(String username,String password)
    {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("password",password);
        database.update("db_wen",values,"username=?",new String[]{username});
    }
}
