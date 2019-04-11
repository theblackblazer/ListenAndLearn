package com.example.listenandlearn;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;

public class UsersDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "users_db";
    public static final int DATABASE_VERSION = 1;

    public static final String CREATE_TABLE = "create table "+ UsersContract.UsersEntry.TABLE_NAME+"( "+ UsersContract.UsersEntry.User_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT,"+UsersContract.UsersEntry.Email+" text,"+UsersContract.UsersEntry.Password+" text);";
    public static final String DROP_TABLE = "drop table if exists "+ UsersContract.UsersEntry.TABLE_NAME;


    public UsersDbHelper(Context context) {

        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        Log.d("Database Operations" , "Database Created ...");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE);
        Log.d("Database Operation" , "Table Created ...");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public void addUser(String email,String password,SQLiteDatabase database) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(UsersContract.UsersEntry.Email,email);
        contentValues.put(UsersContract.UsersEntry.Password,password);

        database.insert(UsersContract.UsersEntry.TABLE_NAME,null,contentValues);
        Log.d("Database Operations" , "One Row Inserted ...");

    }
}
