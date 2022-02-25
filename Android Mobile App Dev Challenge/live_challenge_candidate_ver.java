package com.example.challenge;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.*;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rv_contacts;
    private ContactListAdapter adapter;
    private List<Contact> contacts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new ContactListAdapter(contacts);
        rv_contacts = findViewById(R.id.rvContacts);
        rv_contacts.setLayoutManager(new LinearLayoutManager(this));
        rv_contacts.setAdapter(adapter);

        readContacts();
        save_contact_count();
    }

    @SuppressLint("Range")
    private void readContacts() {
        String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY;
        String FILTER = DISPLAY_NAME + " NOT LIKE '%@%'";
        String ORDER = String.format("%1$s COLLATE NOCASE", DISPLAY_NAME);
        String[] PROJECTION = {
                ContactsContract.Contacts._ID,
                DISPLAY_NAME,
                ContactsContract.Contacts.HAS_PHONE_NUMBER,
        };

        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI, PROJECTION, FILTER, null, ORDER);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME));
                int hasPhoneNumber = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                contacts.add(new Contact(name, hasPhoneNumber));
            } while (cursor.moveToNext());
        }

        adapter.notifyDataSetChanged();
    }

    private void save_contact_count() {
        OkHttpClient client = new OkHttpClient();

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, "{\"count\":" + contacts.size() + "}");
        Request request = new Request.Builder()
                .url("https://api.example.com/")
                .addHeader("X-API-KEY", "abcd1234")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    if (response.code() == 200) {
                        Toast.makeText(MainActivity.this, "Saved successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        String res = response.body().string();
                        JSONObject rootObj = new JSONObject(res);
                        if (rootObj.has("message")) {
                            String msg = rootObj.getString("message");
                            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    // handle exceptions
                }
            }
        });
    }
}