package com.example.dbmarch11;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dbmarch11.UserDatabaseContract.UserDatabase;
import java.util.ArrayList;
import java.util.List;


//CLASS         : MainActivity
//PURPOSE       : The landing page class for the application.  Handles
//                filling the page with relevant data queried directly
//                from the DB using the db helper class and recyclerview.
//REFERENCE     : https://androidtuts4u.blogspot.com/2018/06/android-cardview-and-sqlite-example.html
public class MainActivity extends AppCompatActivity
{


    //Data base helper instantiations
    UserDatabaseHelper dbHelper;
    List<UserDetails> userDetailsList;
    SQLiteDatabase db;

    //View instantiations
    private RecyclerView recyclerView;
    private RecyclerView.Adapter userAdapter;
    private RecyclerView.LayoutManager layoutManager;

    //button instantiations
    Button btnRegister;

    //For notifications and notifications only
    private Intent notificationIntent;

    //FUNCTION          : onCreate
    //PARAMETERS        : Bundle savedInstanceState
    //RETURNS           : void
    //DESCRIPTION       : Handles the proper filling of widgets upon being instantiated.
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new UserDatabaseHelper(this);
        db = dbHelper.getReadableDatabase();

        //view/button declarations
        recyclerView = (RecyclerView) findViewById(R.id.rv_users);
        btnRegister = (Button) findViewById(R.id.bt_register);

        //register button listener
        btnRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //change activity
                Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity(intent);
                finish();
            }
        });

        userDetailsList = new ArrayList<UserDetails>();
        userDetailsList.clear();
        Cursor c1 = db.query(UserDatabase.TABLE_NAME, null, null, null, null, null, null);
        if (c1 != null && c1.getCount() != 0)
        {
            userDetailsList.clear();
            while (c1.moveToNext())
            {
                UserDetails userDetailsItem = new UserDetails();
                userDetailsItem.setUserId(c1.getInt(c1.getColumnIndex(UserDatabase._ID)));
                userDetailsItem.setName(c1.getString(c1.getColumnIndex(UserDatabase.COLUMN_NAME)));
                userDetailsItem.setAddress(c1.getString(c1.getColumnIndex(UserDatabase.COLUMN_ADDRESS)));
                userDetailsItem.setMobileNo(c1.getString(c1.getColumnIndex(UserDatabase.COLUMN_PHONE)));
                userDetailsItem.setProfession(c1.getString(c1.getColumnIndex(UserDatabase.COLUMN_PROF)));
                userDetailsItem.setGender(c1.getString(c1.getColumnIndex(UserDatabase.COLUMN_GENDER)));
                userDetailsItem.setCorona(c1.getString(c1.getColumnIndex(UserDatabase.COLUMN_CORONA)));
                userDetailsItem.setAgeGroup(c1.getString(c1.getColumnIndex(UserDatabase.COLUMN_AGE)));
                userDetailsList.add(userDetailsItem);
            }
        }
        c1.close();
        layoutManager = new LinearLayoutManager(this);
        userAdapter = new UserDetailsAdapter(userDetailsList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(userAdapter);

        //Start the notification service
        notificationIntent = new Intent(this, Notifications.class);
        startService(notificationIntent);
    }
    @Override
    protected void onDestroy()
    {
        db.close();
        //Stop notification service
        stopService(notificationIntent);
        super.onDestroy();
    }

    public void goToCoronaNews(View view) {
        Intent coronaNewsIntent = new Intent(this, CoronaNews.class);
        startActivity(coronaNewsIntent);
        finish();
    }
}
