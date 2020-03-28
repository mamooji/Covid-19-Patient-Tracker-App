package com.example.dbmarch11;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dbmarch11.UserDatabaseContract.UserDatabase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

//CLASS         : RegistrationActivity
//PURPOSE       : Class to run code behind user registration.  Handles
//                filling of all widgets with appropriate user data from
//                DB.
public class RegistrationActivity extends AppCompatActivity
{
    //new shit for firebase
    public static final String NAME_KEY = "name";
    public static final String ADDRESS_KEY = "address";
    public static final String PHONE_KEY = "phone";
    public static final String PROF_KEY = "profession";
    public static final String GENDER_KEY = "gender";
    public static final String CORONA_KEY = "hasCorona";
    public static final String AGE_KEY = "ageRange";
    public static final String TAG = "user";

    //private DocumentReference mDocRef = FirebaseFirestore.getInstance().document("sampleData/user");

    //db instantiation/declarations
    UserDatabaseHelper dbHelper;
    SQLiteDatabase db;

    //string variable declarations
    String name, address, phone, profession, gender, corona, ageRange;

    //private xml fields
    private EditText etName, etAddress, etPhone, etProfession;
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

        //variable declarations to their specific IDs
        dbHelper = new UserDatabaseHelper(this);
        db = dbHelper.getWritableDatabase();
        etName = (EditText) findViewById(R.id.et_name);
        etAddress = (EditText) findViewById(R.id.et_address);
        etPhone = (EditText) findViewById(R.id.et_phone);
        etProfession = (EditText) findViewById(R.id.et_pro);
        spGender = (Spinner) findViewById(R.id.sp_gender);
        swCorona = (Switch) findViewById(R.id.sw_corona);
        rgAgeRange = (RadioGroup) findViewById(R.id.rg_ageRange);

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

                values.put(UserDatabase.COLUMN_NAME, name);
                values.put(UserDatabase.COLUMN_ADDRESS, address);
                values.put(UserDatabase.COLUMN_PHONE, phone);
                values.put(UserDatabase.COLUMN_PROF, profession);
                values.put(UserDatabase.COLUMN_GENDER, gender);
                values.put(UserDatabase.COLUMN_CORONA, corona);
                values.put(UserDatabase.COLUMN_AGE, ageRange);

                //new shit for firebase?
                Map<String, Object> dataToSave = new HashMap<String, Object>();
                dataToSave.put(NAME_KEY, name);
                dataToSave.put(ADDRESS_KEY, address);
                dataToSave.put(PHONE_KEY, phone);
                dataToSave.put(PROF_KEY, profession);
                dataToSave.put(GENDER_KEY, gender);
                dataToSave.put(CORONA_KEY, corona);
                dataToSave.put(AGE_KEY, ageRange);

                DocumentReference mDocRef = FirebaseFirestore.getInstance().document("users/"+name);
                mDocRef.set(dataToSave).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Document has been saved");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Document was not saved");
                    }
                });



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
