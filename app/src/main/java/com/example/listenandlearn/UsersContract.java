package com.example.listenandlearn;

public final class UsersContract {

    private UsersContract() {}

    public static class UsersEntry {

        public static final String TABLE_NAME = "users_info";
        public static final String User_ID = "user_id";
        public static final String Email = "user_email";
        public static final String Password = "user_password";

        public static final String QUESTION_TABLE="questions";
        public static final String Question_Id="question_id";
        public static final String Question_Name="question_name";

        public static final String ANSWER_TABLE="answers";
        public static final String Answer_Id = "answer_id";
        public static final String Answer_Name = "answer_name";


    }
}
