package com.example.dbmarch11;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dbmarch11.UserDatabaseContract.UserDatabase;

public class RegistrationActivity extends AppCompatActivity
{
    UserDatabaseHelper dbHelper;
    String name, address, phone, profession;
    SQLiteDatabase db;
    private EditText etName, etAddress, etPhone, etProfession;
    private Button btRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);
        dbHelper = new UserDatabaseHelper(this);
        db = dbHelper.getWritableDatabase();
        etName = (EditText) findViewById(R.id.et_name);
        etAddress = (EditText) findViewById(R.id.et_address);
        etPhone = (EditText) findViewById(R.id.et_phone);
        etProfession = (EditText) findViewById(R.id.et_pro);
        btRegister = (Button) findViewById(R.id.bt_registration);
        btRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                name = etName.getText().toString();
                address = etAddress.getText().toString();
                phone = etPhone.getText().toString();
                profession = etProfession.getText().toString();
                ContentValues values = new ContentValues();
                values.put(UserDatabase.COLUMN_NAME_COL1, name);
                values.put(UserDatabase.COLUMN_NAME_COL2, address);
                values.put(UserDatabase.COLUMN_NAME_COL3, phone);
                values.put(UserDatabase.COLUMN_NAME_COL4, profession);
                long rowId = db.insert(UserDatabase.TABLE_NAME, null, values);
                if (rowId != -1)
                {
                    Toast.makeText(RegistrationActivity.this, "User regstered succesfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(RegistrationActivity.this, "Something Went Wrong! ", Toast.LENGTH_SHORT).show();
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
