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

////
//import android.support.v7.widget.LinearLayoutManager;
/////

public class MainActivity extends AppCompatActivity
{

    UserDatabaseHelper dbHelper;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter userAdapter;
    private RecyclerView.LayoutManager layoutManager;
    Button btnRegister;
    List<UserDetails> userDetailsList;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new UserDatabaseHelper(this);
        db = dbHelper.getReadableDatabase();
        recyclerView = (RecyclerView) findViewById(R.id.rv_users);
        btnRegister = (Button) findViewById(R.id.bt_register);

        btnRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
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
                userDetailsItem.setName(c1.getString(c1.getColumnIndex(UserDatabase.COLUMN_NAME_COL1)));
                userDetailsItem.setAddress(c1.getString(c1.getColumnIndex(UserDatabase.COLUMN_NAME_COL2)));
                userDetailsItem.setMobileNo(c1.getString(c1.getColumnIndex(UserDatabase.COLUMN_NAME_COL3)));
                userDetailsItem.setProfession(c1.getString(c1.getColumnIndex(UserDatabase.COLUMN_NAME_COL4)));
                userDetailsList.add(userDetailsItem);
            }
        }
        c1.close();
        layoutManager = new LinearLayoutManager(this);
        userAdapter = new UserDetailsAdapter(userDetailsList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(userAdapter);
    }
    @Override
    protected void onDestroy()
    {
        db.close();
        super.onDestroy();
    }
}
