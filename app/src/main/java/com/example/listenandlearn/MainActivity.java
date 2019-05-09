package com.example.listenandlearn;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

// The main screen or home screen of App. Shows Student Mode
public class MainActivity extends AppCompatActivity {

    private FloatingActionButton login;
    private RecyclerView recyclerView;
    private List<QList> qlist=new ArrayList<>();
    private QAdapterMain adapter;
    private  UsersDbHelper usersDbHelper;
    private SQLiteDatabase database;
    private Cursor cursor;
    private int resId;
    private LayoutAnimationController animation;

    // Refreshes Activity after every user action.
    @Override
    protected void onStart() {
        super.onStart();
        fetchQuestions();
    }

    // Instantiate App HomePage.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usersDbHelper = new UsersDbHelper(getApplicationContext());
        database = usersDbHelper.getReadableDatabase();
        resId = R.anim.falldownlayout;
        animation= AnimationUtils.loadLayoutAnimation(this, resId);
        cursor=database.rawQuery("SELECT * FROM "+
                UsersContract.UsersEntry.QUESTION_TABLE,null);

        recyclerView=findViewById(R.id.recyclerview_main);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(false);

        login =findViewById(R.id.login_btn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });


fetchQuestions();

    }

    // Function to fetch questions from database and view them in a recycler view.
    private void fetchQuestions(){

        qlist.clear();
        cursor=database.rawQuery("SELECT * FROM "+
                UsersContract.UsersEntry.QUESTION_TABLE,null);
        if(!cursor.moveToFirst()){
            Toast.makeText(this, "No questions", Toast.LENGTH_SHORT).show();
        }
        else{
            qlist.add(new QList(cursor.getInt(0),cursor.getString(1)));
            while (cursor.moveToNext())
                qlist.add(new QList(cursor.getInt(0),cursor.getString(1)));
        }
        adapter=new QAdapterMain(qlist,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutAnimation(animation);
    }
}
