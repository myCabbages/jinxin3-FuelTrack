package com.example.kx.jinxin3_fueltrack;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class ViewLogEntries extends AppCompatActivity {

    private ListView oldLogView;
    private ArrayList<Logs> logs = new ArrayList<Logs> ();
    private static final String FILENAME = "file2.sav";
    private ArrayAdapter<Logs> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_log_entries);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        oldLogView = (ListView) findViewById(R.id.oldloglist);
        oldLogView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ViewLogEntries.this, "View Old Log Entries", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ViewLogEntries.this, EditNewLog.class);
                intent.putExtra("pos", position);
                startActivity(intent);
            }
        });
    }

    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        loadFromFile();
        adapter = new ArrayAdapter<Logs>(this,
                R.layout.list_view, logs);
        oldLogView.setAdapter(adapter);
    }

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();

            // Took from https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html 01-19 2016
            Type listType = new TypeToken<ArrayList<Logs>>() {
            }.getType();
            logs = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            logs = new ArrayList<Logs>();
        }
    }

}

