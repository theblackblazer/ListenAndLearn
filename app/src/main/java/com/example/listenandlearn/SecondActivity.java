package com.example.listenandlearn;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends AppCompatActivity {

    private Button back;
    private Button login;
    private Button question;
    private Button answer;
    MediaPlayer mPlayer;
    MediaPlayer m1player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        question = findViewById(R.id.question_btn);
        answer = findViewById(R.id.answer_btn);
        mPlayer = MediaPlayer.create(this,R.raw.name);
        m1player = MediaPlayer.create(this,R.raw.name_answer);


        login =(Button)findViewById(R.id.login_btn);
        back = (Button) findViewById(R.id.back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),LoginActivity.class));

            }
        });

        question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer.start();
            }
        });

        answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m1player.start();
            }
        });
    }
}