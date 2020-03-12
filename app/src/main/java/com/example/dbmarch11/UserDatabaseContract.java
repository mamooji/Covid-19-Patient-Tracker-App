package com.example.dbmarch11;

import android.provider.BaseColumns;

public final class UserDatabaseContract
{
    private UserDatabaseContract()
    {
    }
    public static class UserDatabase implements BaseColumns
    {
        public static final String TABLE_NAME = "user_details";
        public static final String COLUMN_NAME_COL1 = "name";
        public static final String COLUMN_NAME_COL2 = "address";
        public static final String COLUMN_NAME_COL3 = "phone_no";
        public static final String COLUMN_NAME_COL4 = "profession";
    }
}
