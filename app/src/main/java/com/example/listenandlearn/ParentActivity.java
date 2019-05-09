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

// ParentActivity where Parent can view all added questions.
public class ParentActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton addQuesBtn,logout;
    private List<QList> qlist=new ArrayList<>();
    private QAdapter adapter;
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

    // Initialize Parent Home Screen.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent);
        usersDbHelper = new UsersDbHelper(getApplicationContext());
        database = usersDbHelper.getReadableDatabase();
        resId = R.anim.falldownlayout;
        animation= AnimationUtils.loadLayoutAnimation(this, resId);
        cursor=database.rawQuery("SELECT * FROM "+
                UsersContract.UsersEntry.QUESTION_TABLE,null);

        addQuesBtn=findViewById(R.id.fab);
        logout=findViewById(R.id.logout_btn);
        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(false);

        // Button used to move to AddQuestionActivity
        addQuesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),AddQuestionActivity.class);
                i.putExtra("selected","Question");
                startActivity(i);

            }
        });

        // Button used to logout from Accounr and read Student Mode.
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
       fetchQuestions();
    }

    // Fetch Questions from Database and add to Recycler View
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
        adapter=new QAdapter(qlist,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutAnimation(animation);
    }

    // Session Management Function
    @Override
        public void onBackPressed()
    {

    }

}

