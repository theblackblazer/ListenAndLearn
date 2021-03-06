package com.example.listenandlearn;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Px;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

// Activity to Add Questions and Answers
public class AddQuestionActivity extends AppCompatActivity {


    String selected = "";
    EditText q_name;
    FloatingActionButton record, play, save;
//    SeekBar seekbar;
//    Spinner spinner;

    private int id;
    private static final String LOG_TAG = "AudioRecordTest";
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private static String fileName = null;
    private File audio;
    private MediaRecorder recorder = null;
    private MediaPlayer player = null;

    // Requesting permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted = false;
    private String[] permissions = {Manifest.permission.RECORD_AUDIO};

    // Function to start or stop recording
    private void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    // Function to start or stop playing audio
    private void onPlay(boolean start) {
        if (start) {
            startPlaying();
        } else {
            stopPlaying();
        }
    }

    // Start Playing Audio Function
    private void startPlaying() {
        player = new MediaPlayer();
        try {
            player.setDataSource(fileName);
            player.prepare();
            player.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    // Stop Playing Audio function
    private void stopPlaying() {
        player.release();
//        seekbar.setProgress(0);
    }

    // Start Recording using Media Recorder
    private void startRecording() {
        recorder = null;
        recorder = new MediaRecorder();
        fileName += "/" + q_name.getText().toString() + ".3gp";
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(fileName);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        recorder.start();
    }

    private void stopRecording() {
        recorder.stop();
        recorder.release();
    }


    // Requesting Permissions to Record Audio
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted) finish();

    }


    // OnCreate Method to instantiate Activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        selected = getIntent().getStringExtra("selected");
        id = getIntent().getIntExtra("id", 0);

        Toast.makeText(this, "Selected: " + selected, Toast.LENGTH_SHORT).show();
//        spinner = (Spinner) findViewById(R.id.questions_spinner);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.planets_array, android.R.layout.simple_spinner_item);

//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);

        q_name = findViewById(R.id.question_name);
        record = findViewById(R.id.record_button);
        play = findViewById(R.id.play_button);
        save = findViewById(R.id.save_button);
//        seekbar = findViewById(R.id.seekbar);

        audio = new File(getBaseContext().getFilesDir().getPath());

        fileName = getExternalCacheDir().getAbsolutePath();

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);

        // Record button used to start and stop recording audio.
        record.setOnClickListener(new View.OnClickListener() {
            boolean mStartRecording = true;

            @Override
            public void onClick(View v) {
                onRecord(mStartRecording);
                if (mStartRecording) {
                    Toast.makeText(AddQuestionActivity.this, "Recording Started. Tap icon to stop recording", Toast.LENGTH_SHORT).show();
                    record.setImageResource(R.drawable.ic_mic_off_black_24dp);
                } else {
                    Toast.makeText(AddQuestionActivity.this, "Recording Stopped", Toast.LENGTH_SHORT).show();
                    record.setImageResource(R.drawable.ic_mic_black_24dp);
                }
                mStartRecording = !mStartRecording;
            }
        });

        // Play button used to play or stop playing audio
        play.setOnClickListener(new View.OnClickListener() {
            boolean mStartPlaying = true;

            @Override
            public void onClick(View v) {
                onPlay(mStartPlaying);
                if (mStartPlaying) {
                    play.setImageResource(R.drawable.ic_stop_black_24dp);
//                    seekbar.setMax(player.getDuration());
//                    seekUpdation();
                } else {
                    play.setImageResource(R.drawable.ic_play_arrow_black_24dp);

                }
                mStartPlaying = !mStartPlaying;
            }
        });

//        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            int progressvalue = 0;
//
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                progressvalue = progress;
//
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });

        // Save Button required to save Audio to File Storage.
        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String name = q_name.getText().toString();
                UsersDbHelper usersDbHelper = new UsersDbHelper(getApplicationContext());
                SQLiteDatabase database = usersDbHelper.getWritableDatabase();
//                selected=spinner.getSelectedItem().toString();
                if (selected.equals("Question") && !name.equals("")) {
                    usersDbHelper.addQuestion(name, database);
                    usersDbHelper.close();
                    q_name.setText("");
                    Toast.makeText(getApplicationContext(), "File Saved", Toast.LENGTH_SHORT).show();
                    finish();
                } else if (selected.equals("Answer") && !name.equals("")) {
                    usersDbHelper.addAnswer(id, name, database);
                    usersDbHelper.close();
                    q_name.setText("");
                    Toast.makeText(getApplicationContext(), "File Saved", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    finish();
                }

            }
        });
    }

    // Function to stop Media Recorder and Media Player
    @Override
    public void onStop() {
        super.onStop();
        if (recorder != null) {
            recorder.release();
            recorder = null;
        }

        if (player != null) {
            player.release();
            player = null;
        }
    }

//    Runnable run = new Runnable() {
//
//        @Override
//        public void run() {
//            seekUpdation();
//        }
//    };

//    public void seekUpdation() {
//
//        seekbar.setProgress(player.getCurrentPosition());
//        seekHandler.postDelayed(run, 1000);
//    }
}

