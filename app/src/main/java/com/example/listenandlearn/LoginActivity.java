package com.example.listenandlearn;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

// Creating Login Activity for Login to Parent Mode in App.
public class LoginActivity extends AppCompatActivity {

    private TextView signup;
    private EditText email;
    private EditText pass;
    private Button login;
    private Cursor cursor;

    // Instantiate LoginActivity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signup = (TextView) findViewById(R.id.signup_txt);
        email = findViewById(R.id.email_login);
        pass = findViewById(R.id.password_login);
        login = findViewById(R.id.login_btn);

        // Signup TextView used to move to Registration Screen
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), RegistrationActivity.class));
            }
        });

        // Login Button used to login user using correct credentials.
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = email.getText().toString();
                String password = pass.getText().toString();

                UsersDbHelper usersDbHelper = new UsersDbHelper(getApplicationContext());
                    SQLiteDatabase database = usersDbHelper.getReadableDatabase();

                    cursor = database.rawQuery("SELECT * FROM "+
                            UsersContract.UsersEntry.TABLE_NAME
                            + " WHERE " + UsersContract.UsersEntry.Email +
                            "=? AND "+ UsersContract.UsersEntry.Password +
                            "=?",new String[]{Email,password});
                    if(cursor!=null)
                    {
                        if(cursor.getCount()>0){
                            Toast.makeText(getApplicationContext(), "Login Successful..", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), ParentActivity.class));
                        }else {
                            Toast.makeText(getApplicationContext(),"Error Logging In" , Toast.LENGTH_SHORT).show();
                        }
                    }


            }
        });



    }
}
