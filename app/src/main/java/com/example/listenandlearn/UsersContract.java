package com.example.listenandlearn;

public final class UsersContract {

    private UsersContract() {}

    public static class UsersEntry {

        public static final String TABLE_NAME = "users_info";
        public static final String User_ID = "user_id";
        public static final String Email = "user_email";
        public static final String Password = "user_password";
    }
}
