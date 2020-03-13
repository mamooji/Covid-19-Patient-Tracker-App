package com.example.dbmarch11;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dbmarch11.UserDatabaseContract.UserDatabase;


//CLASS         : RegistrationActivity
//PURPOSE       : Class to run code behind user registration.  Handles
//                filling of all widgets with appropriate user data from
//                DB.
public class RegistrationActivity extends AppCompatActivity
{
    UserDatabaseHelper dbHelper;
    SQLiteDatabase db;

    String name, address, phone, profession;

    //updated fields
    String gender, corona, ageRange;

    private EditText etName, etAddress, etPhone, etProfession;

    //updated fields
    private Spinner spGender;
    private Switch swCorona;
    private RadioGroup rgAgeRange;


    private Button btRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //setting content view/instance state
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);

        //variable declarations
        dbHelper = new UserDatabaseHelper(this);
        db = dbHelper.getWritableDatabase();
        etName = (EditText) findViewById(R.id.et_name);
        etAddress = (EditText) findViewById(R.id.et_address);
        etPhone = (EditText) findViewById(R.id.et_phone);
        etProfession = (EditText) findViewById(R.id.et_pro);


        //updates fields
        spGender = (Spinner) findViewById(R.id.sp_gender);
        swCorona = (Switch) findViewById(R.id.sw_corona);
        rgAgeRange = (RadioGroup) findViewById(R.id.rg_ageRange);
        //

        btRegister = (Button) findViewById(R.id.bt_registration);


        //register button onClick listener
        btRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                name = etName.getText().toString();
                address = etAddress.getText().toString();
                phone = etPhone.getText().toString();
                profession = etProfession.getText().toString();

                //updated fields
                gender = spGender.getSelectedItem().toString();

                if (swCorona.isChecked())
                {
                    corona = swCorona.getTextOn().toString();
                }
                else
                {
                    corona = swCorona.getTextOff().toString();
                }

                int selectedID = rgAgeRange.getCheckedRadioButtonId();
                RadioButton tmpRadioButton = (RadioButton) findViewById(selectedID);
                ageRange = tmpRadioButton.getText().toString();


                ContentValues values = new ContentValues();

                values.put(UserDatabase.COLUMN_NAME_COL1, name);
                values.put(UserDatabase.COLUMN_NAME_COL2, address);
                values.put(UserDatabase.COLUMN_NAME_COL3, phone);
                values.put(UserDatabase.COLUMN_NAME_COL4, profession);

                //updated values
                values.put(UserDatabase.COLUMN_NAME_COL5, gender);
                values.put(UserDatabase.COLUMN_NAME_COL6, corona);
                values.put(UserDatabase.COLUMN_NAME_COL7, ageRange);

                long rowId = db.insert(UserDatabase.TABLE_NAME, null, values);

                //check if insertion into db was successful or not
                if (rowId != -1)
                {
                    Toast.makeText(RegistrationActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
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

    //FUNCTION      : onDestroy
    //PARAMETERS    : void
    //RETURNS       : void
    //DESCRIPTION   : Closes the db upon the destruction of the page
    @Override
    protected void onDestroy()
    {
        db.close();
        super.onDestroy();
    }
}
