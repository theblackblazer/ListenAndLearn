package com.example.listenandlearn;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class StudentActivity extends AppCompatActivity {

    private Bundle obj;
    private TextView ques,answer;
    private int id;
    private  UsersDbHelper usersDbHelper;
    private SQLiteDatabase database;
    private Cursor cursor;
    private CardView ans_card;

    @Override
    protected void onStart() {
        super.onStart();
        fetchAnswer();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        usersDbHelper = new UsersDbHelper(getApplicationContext());
        database = usersDbHelper.getReadableDatabase();
        obj=getIntent().getBundleExtra("q");
        ques=findViewById(R.id.qname_view);
        answer=findViewById(R.id.aname_view);
        id=obj.getInt("qno");
        ans_card=findViewById(R.id.ans_card);

        ques.setText(obj.getString("qname"));
        fetchAnswer();

    }
    private void fetchAnswer(){
        cursor = database.rawQuery("SELECT * FROM "+
                UsersContract.UsersEntry.ANSWER_TABLE
                + " WHERE " + UsersContract.UsersEntry.Answer_Id +
                "=?",new String[]{String.valueOf(id)});

        if(!cursor.moveToFirst()){
            Toast.makeText(this, "No Answer", Toast.LENGTH_SHORT).show();
            ans_card.setVisibility(View.INVISIBLE);
        }
        else{
            ans_card.setVisibility(View.VISIBLE);
            answer.setText(cursor.getString(1));
        }
    }
}
