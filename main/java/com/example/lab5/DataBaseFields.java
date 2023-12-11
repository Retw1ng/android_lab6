package com.example.lab5;
import android.provider.BaseColumns;

public class DataBaseFields {

    private DataBaseFields() {}

    public static class UserEntry implements BaseColumns {

        public static final String TABLE_NAME = "users";
        public static final String COLUMN_NAME_KEY_ID = "id";
        public static final String COLUMN_NAME_LOGIN = "login";
        public static final String COLUMN_NAME_PASS = "pass";

    }
    public static class PlantsEntry implements BaseColumns{
        public static final String TABLE_NAME = "plantstable";
        public static final String PLANTS_NAME = "plants";

    }

}
