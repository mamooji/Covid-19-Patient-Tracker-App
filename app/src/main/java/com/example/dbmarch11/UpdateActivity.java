package com.example.dbmarch11;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dbmarch11.UserDatabaseContract.UserDatabase;

import java.util.ArrayList;
import java.util.List;

public class UpdateActivity extends AppCompatActivity
{
    UserDatabaseHelper dbHelper;
    EditText upName, upAddress, upPhone, upProfession;
    Button btUpdate;
    List<UserDetails> userDetailsList;
    String name, address, phone, profession;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_activity);
        dbHelper = new UserDatabaseHelper(this);
        db = dbHelper.getWritableDatabase();
        upName = (EditText) findViewById(R.id.et_up_name);
        upAddress = (EditText) findViewById(R.id.et_up_address);
        upPhone = (EditText) findViewById(R.id.et_up_phone);
        upProfession = (EditText) findViewById(R.id.et_up_pro);
        btUpdate = (Button) findViewById(R.id.bt_update);
        final int rowId = getIntent().getIntExtra("USERID", -1);
        Cursor c1 = db.query(UserDatabase.TABLE_NAME, null, UserDatabase._ID + " = " + rowId, null, null, null, null);
        userDetailsList = new ArrayList<UserDetails>();
        userDetailsList.clear();
        if (c1 != null && c1.getCount() != 0)
        {
            while (c1.moveToNext())
            {
                upName.setText(c1.getString(c1.getColumnIndex(UserDatabase.COLUMN_NAME_COL1)));
                upAddress.setText(c1.getString(c1.getColumnIndex(UserDatabase.COLUMN_NAME_COL2)));
                upPhone.setText(c1.getString(c1.getColumnIndex(UserDatabase.COLUMN_NAME_COL3)));
                upProfession.setText(c1.getString(c1.getColumnIndex(UserDatabase.COLUMN_NAME_COL4)));
            }
        }
        btUpdate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                name = upName.getText().toString();
                address = upAddress.getText().toString();
                phone = upPhone.getText().toString();
                profession = upProfession.getText().toString();
                ContentValues values = new ContentValues();
                values.put(UserDatabase.COLUMN_NAME_COL1, name);
                values.put(UserDatabase.COLUMN_NAME_COL2, address);
                values.put(UserDatabase.COLUMN_NAME_COL3, phone);
                values.put(UserDatabase.COLUMN_NAME_COL4, profession);
                int updateId = db.update(UserDatabase.TABLE_NAME, values, UserDatabase._ID + " = " + rowId, null);
                if (updateId != -1)
                {
                    Toast.makeText(UpdateActivity.this, "User Details Updated", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(UpdateActivity.this, "User Update Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    protected void onDestroy()
    {
        db.close();
        super.onDestroy();
    }
}