package com.example.dbmarch11;

import android.provider.BaseColumns;

//CLASS         : UserDatabaseContract
//PURPOSE       : Defines structure of the database.
//                Contains all column names and types.
public final class UserDatabaseContract
{
    private UserDatabaseContract()
    {
    }
    public static class UserDatabase implements BaseColumns
    {
        public static final String TABLE_NAME = "user_details";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_ADDRESS = "address";
        public static final String COLUMN_PHONE = "phone_no";
        public static final String COLUMN_PROF = "profession";
        public static final String COLUMN_GENDER = "gender";
        public static final String COLUMN_CORONA = "corona";
        public static final String COLUMN_AGE = "ageRange";

    }
}
