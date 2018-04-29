package com.iamyasinvirani.virani.readcontactdemoapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class DisplayContact extends AppCompatActivity {

    ListView listView;
    ArrayList<String> storeContacts, storeNumber;
    ArrayAdapter<String> arrayAdapter;
    Cursor cursor;
    String name, phonenumber;
    public static final int RequestPermissionCode = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contact);

        listView = (ListView) findViewById(R.id.listView);
        storeContacts = new ArrayList<String>();
        storeNumber = new ArrayList<String>();
        GetContactsIntoArrayList();
        EnableRuntimePermission();
        arrayAdapter = new ArrayAdapter<String>(DisplayContact.this, android.R.layout.simple_list_item_1, storeContacts);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String abc = storeNumber.get(i);
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel: " + abc));
                startActivity(intent);
            }
        });

    }

    public void GetContactsIntoArrayList() {

        cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

        while (cursor.moveToNext()) {

            name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

            phonenumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            storeContacts.add(name + " " + " \n " + " \t " + phonenumber);
            storeNumber.add(phonenumber);
        }

        cursor.close();

    }

    public void EnableRuntimePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(
                DisplayContact.this,
                Manifest.permission.READ_CONTACTS)) {

            Toast.makeText(DisplayContact.this, "CONTACTS permission allows us to Access CONTACTS app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(DisplayContact.this, new String[]{
                    Manifest.permission.READ_CONTACTS}, RequestPermissionCode);

        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {

        switch (RC) {

            case RequestPermissionCode:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(DisplayContact.this, "Permission Granted, Now your application can access CONTACTS.", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(DisplayContact.this, "Permission Canceled, Now your application cannot access CONTACTS.", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }


}


