package com.example.dbmarch11;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dbmarch11.UserDatabaseContract.UserDatabase;
import java.util.ArrayList;
import java.util.List;


//CLASS         : UpdateActivity
//PURPOSE       : Handles the updating of an existing user within the DB.
public class UpdateActivity extends AppCompatActivity
{

    //variable declarations
    UserDatabaseHelper dbHelper;
    SQLiteDatabase db;
    List<UserDetails> userDetailsList;

    EditText upName, upAddress, upPhone, upProfession;
    Spinner upGender;
    Switch upCorona;
    RadioGroup upAgeRange;

    String name, address, phone, profession, gender, corona, ageRange;

    Button btUpdate;

    //FUNCTION      : onCreate
    //PARAMETERS    : Bundle savedInstanceState
    //RETURNS       : void
    //DESCRIPTION   : Fills all fields with relevant info tied
    //                the the associated user in the DB.
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_activity);
        dbHelper = new UserDatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        //setting or variables to the layout
        upName = (EditText) findViewById(R.id.et_up_name);
        upAddress = (EditText) findViewById(R.id.et_up_address);
        upPhone = (EditText) findViewById(R.id.et_up_phone);
        upProfession = (EditText) findViewById(R.id.et_up_pro);
        btUpdate = (Button) findViewById(R.id.bt_update);
        upGender = (Spinner) findViewById(R.id.sp_up_gender);
        upCorona = (Switch) findViewById(R.id.sw_up_corona);
        upAgeRange = (RadioGroup) findViewById(R.id.rg_up_ageRange);


        final int rowId = getIntent().getIntExtra("USERID", -1);
        Cursor c1 = db.query(UserDatabase.TABLE_NAME, null, UserDatabase._ID + " = " + rowId, null, null, null, null);
        userDetailsList = new ArrayList<UserDetails>();
        userDetailsList.clear();
        if (c1 != null && c1.getCount() != 0)
        {
            while (c1.moveToNext())
            {
                upName.setText(c1.getString(c1.getColumnIndex(UserDatabase.COLUMN_NAME)));
                upAddress.setText(c1.getString(c1.getColumnIndex(UserDatabase.COLUMN_ADDRESS)));
                upPhone.setText(c1.getString(c1.getColumnIndex(UserDatabase.COLUMN_PHONE)));
                upProfession.setText(c1.getString(c1.getColumnIndex(UserDatabase.COLUMN_PROF)));
                upGender.setSelection(((ArrayAdapter<String>)upGender.getAdapter()).getPosition(c1.getString(c1.getColumnIndex(UserDatabase.COLUMN_GENDER))));
                String dbString = c1.getString(c1.getColumnIndex(UserDatabase.COLUMN_CORONA));
                String ourString = getString(R.string.lbl_corona_pos);
                if (dbString.equals(ourString))
                {
                    upCorona.setChecked(true);
                }
                else
                {
                    upCorona.setChecked(false);
                }
                int numRadioBTN = upAgeRange.getChildCount();
                for (int x = 0; x<numRadioBTN; x++)
                {
                    RadioButton tmp = (RadioButton) upAgeRange.getChildAt(x);
                    if ( (tmp.getText().toString().equals((c1.getString(c1.getColumnIndex(UserDatabase.COLUMN_AGE))))))
                    {
                        tmp.setChecked(true);
                    }
                }
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
                gender = upGender.getSelectedItem().toString();
                if (upCorona.isChecked())
                {
                    corona = upCorona.getTextOn().toString();
                }
                else
                {
                    corona = upCorona.getTextOff().toString();
                }
                int numRadioBTN = upAgeRange.getChildCount();
                for (int x = 0; x<numRadioBTN; x++)
                {
                    RadioButton tmp = (RadioButton) upAgeRange.getChildAt(x);
                    if (tmp.isChecked())
                    {
                        ageRange = tmp.getText().toString();
                    }
                }

                ContentValues values = new ContentValues();
                values.put(UserDatabase.COLUMN_NAME, name);
                values.put(UserDatabase.COLUMN_ADDRESS, address);
                values.put(UserDatabase.COLUMN_PHONE, phone);
                values.put(UserDatabase.COLUMN_PROF, profession);
                values.put(UserDatabase.COLUMN_GENDER, gender);
                values.put(UserDatabase.COLUMN_CORONA, corona);
                values.put(UserDatabase.COLUMN_AGE, ageRange);

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