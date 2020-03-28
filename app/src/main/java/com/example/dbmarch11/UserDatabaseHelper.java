package com.example.dbmarch11;

//import statements
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.dbmarch11.UserDatabaseContract.UserDatabase;

//CLASS         : UserDatabaseHelper
//PURPOSE       :
public class UserDatabaseHelper extends SQLiteOpenHelper
{

    public static final int DATABASE_VERSION = 5;
    public static final String DATABASE_NAME = "UserRegistration.db";

    public UserDatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

        db.execSQL(DELETE_USER_TABLE);
        onCreate(db);

    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        onUpgrade(db, oldVersion, newVersion);
    }

    private static final String CREATE_USER_TABLE = "CREATE TABLE " + UserDatabase.TABLE_NAME +
            "( " + UserDatabase._ID + " INTEGER PRIMARY KEY," +
            UserDatabase.COLUMN_NAME + " text," +
            UserDatabase.COLUMN_ADDRESS + " text," +
            UserDatabase.COLUMN_PHONE + " text," +
            UserDatabase.COLUMN_PROF + " text," +
            UserDatabase.COLUMN_GENDER + " text," +
            UserDatabase.COLUMN_CORONA + " text," +
            UserDatabase.COLUMN_AGE + " text)";

    private static final String DELETE_USER_TABLE = "DROP TABLE IF EXISTS " + UserDatabase.TABLE_NAME;

}