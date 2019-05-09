package com.example.listenandlearn;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.lang.UScript;
import android.provider.ContactsContract;
import android.util.Log;
// Create Local Database
public class UsersDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "users_db";
    public static final int DATABASE_VERSION = 1;

    public static final String CREATE_TABLE = "create table " + UsersContract.UsersEntry.TABLE_NAME + "( " + UsersContract.UsersEntry.User_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + UsersContract.UsersEntry.Email + " text," + UsersContract.UsersEntry.Password + " text);";
    public static final String DROP_TABLE = "drop table if exists " + UsersContract.UsersEntry.TABLE_NAME;
    public static final String CREATE_TABLE_QUESTION = "create table " + UsersContract.UsersEntry.QUESTION_TABLE + "( " + UsersContract.UsersEntry.Question_Id + " INTEGER PRIMARY KEY AUTOINCREMENT," + UsersContract.UsersEntry.Question_Name + " text);";
    public static final String DROP_TABLE_QUESTION = "drop table if exists " + UsersContract.UsersEntry.QUESTION_TABLE;
    public static final String CREATE_TABLE_ANSWER = "create table " + UsersContract.UsersEntry.ANSWER_TABLE + "( " + UsersContract.UsersEntry.Answer_Id + " INTEGER PRIMARY KEY," + UsersContract.UsersEntry.Answer_Name + " text);";
    public static final String DROP_TABLE_ANSWER = "drop table if exists " + UsersContract.UsersEntry.ANSWER_TABLE;

    // Default Constructor to initialize database
    public UsersDbHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("Database Operations", "Database Created ...");
    }

    // Creating Tables in Database
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE);
        Log.d("Database Operation", "Table Created ...");
        db.execSQL(CREATE_TABLE_QUESTION);
        Log.d("Database Operations", "Table Created");
        db.execSQL(CREATE_TABLE_ANSWER);
        Log.d("Database Operations", "Table Created");

    }
    // Check whether Table Exists. Dropping Tables if existing.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(DROP_TABLE);
        db.execSQL(DROP_TABLE_QUESTION);
        db.execSQL(DROP_TABLE_ANSWER);
        onCreate(db);
    }
    // Adding Users to Users Table.
    public void addUser(String email, String password, SQLiteDatabase database) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(UsersContract.UsersEntry.Email, email);
        contentValues.put(UsersContract.UsersEntry.Password, password);

        database.insert(UsersContract.UsersEntry.TABLE_NAME, null, contentValues);
        Log.d("Database Operations", "One Row Inserted ...");

    }
    // Adding Questions to Questions Table
    public void addQuestion(String q_name, SQLiteDatabase database) {

        ContentValues contentValues = new ContentValues();
        //contentValues.put(UsersContract.UsersEntry.Question_Number,q_number);
        contentValues.put(UsersContract.UsersEntry.Question_Name, q_name);
        database.insert(UsersContract.UsersEntry.QUESTION_TABLE, null, contentValues);
        Log.d("Database Operations", "One Row Inserted ..");
    }

    // Adding Answers to Answers Table
    public void addAnswer(int a_id, String a_name, SQLiteDatabase database) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(UsersContract.UsersEntry.Answer_Id, a_id);
        contentValues.put(UsersContract.UsersEntry.Answer_Name, a_name);

        database.insert(UsersContract.UsersEntry.ANSWER_TABLE, null, contentValues);
        Log.d("Database Operations", "One Row Inserted ..");

    }

    // Delete Questions from Questions Database
    public void deleteQuestions(int id,SQLiteDatabase database) {
        String selection = UsersContract.UsersEntry.Question_Id+" = "+id;
        database.delete(UsersContract.UsersEntry.QUESTION_TABLE,selection,null);

    }

    // Delete Answers from Answers Table
    public void deleteAnswers(int id,SQLiteDatabase database) {
        String selection = UsersContract.UsersEntry.Answer_Id+" = "+id;
        database.delete(UsersContract.UsersEntry.ANSWER_TABLE,selection,null);
        database.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + UsersContract.UsersEntry.ANSWER_TABLE + "'");

    }

}
