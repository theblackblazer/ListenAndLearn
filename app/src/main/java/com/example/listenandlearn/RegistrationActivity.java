package com.example.listenandlearn;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

// Registration Activity to let users signup to app.
public class RegistrationActivity extends AppCompatActivity {

    private EditText Email;
    private EditText Password;
    private TextView login;
    private Button signup;

    // Initialize App Signup Screen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        login = findViewById(R.id.login_txt);
        Email = findViewById(R.id.email_signup);
        Password = findViewById(R.id.password_signup);
        signup = findViewById(R.id.signup_btn);

        // Login Textview used to navigate to Login Screen
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });

        // Signup button used to register a user on to the Application.
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = Email.getText().toString();
                String password = Password.getText().toString();

                UsersDbHelper usersDbHelper = new UsersDbHelper(getApplicationContext());
                    SQLiteDatabase database = usersDbHelper.getWritableDatabase();
                usersDbHelper.addUser(email,password,database);
                usersDbHelper.close();
                Email.setText("");
                Password.setText("");
                {
                    Toast.makeText(getApplicationContext(), "Registration Successful..", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), ParentActivity.class));
                }
            }
        });


    }
}
