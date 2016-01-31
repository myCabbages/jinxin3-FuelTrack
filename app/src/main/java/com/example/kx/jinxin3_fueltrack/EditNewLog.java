
package com.example.kx.jinxin3_fueltrack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class EditNewLog extends AppCompatActivity {

    private EditText stationText;
    private EditText odoText;
    private EditText gradeText;
    private EditText amountText;
    private EditText unitcosText;
    private EditText dateText;
    private int list_index;


    private ArrayList<String> logs = new ArrayList<>();
    private ArrayList<Logs> oldlogs = new ArrayList<>();
    private ArrayAdapter<Logs> adapter;
    private static final String FILENAME = "file.sav";
    Logs latestLog = new Logs();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_new_log);

        Intent intent = getIntent();
        list_index = intent.getIntExtra("pos",0);

        loadFromFile();

        stationText = (EditText) findViewById(R.id.editStation);
        odoText = (EditText) findViewById(R.id.editOdometer);
        gradeText = (EditText) findViewById(R.id.editGrade);
        amountText = (EditText) findViewById(R.id.editAmount);
        unitcosText = (EditText) findViewById(R.id.editunitcost);
        dateText = (EditText) findViewById(R.id.editDate);


        stationText.setText(oldlogs.get(list_index).getStation());
        odoText.setText(oldlogs.get(list_index).getOdometer());
        gradeText.setText(oldlogs.get(list_index).getGrade());
        amountText.setText(oldlogs.get(list_index).getAmount());
        unitcosText.setText(oldlogs.get(list_index).getUnitcost());
        dateText.setText(oldlogs.get(list_index).getUnitcost());


        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button cancelButton = (Button) findViewById(R.id.cancelbutton);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                Toast.makeText(EditNewLog.this, "Cancel", Toast.LENGTH_SHORT).show();
                finish();

            }
        });

        Button saveButton = (Button) findViewById(R.id.savebutton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(EditNewLog.this, "Saved", Toast.LENGTH_SHORT).show();
                String text1 = stationText.getText().toString();
                String text2 = odoText.getText().toString();
                String text3 = gradeText.getText().toString();
                String text4 = amountText.getText().toString();
                String text5 = unitcosText.getText().toString();
                String text6 = dateText.getText().toString();
                latestLog.setStation(text1);
                latestLog.setOdormeter(text2);
                latestLog.setGrade(text3);
                latestLog.setAmount(text4);
                latestLog.setUnitcost(text5);
                latestLog.setDate(text6);
                latestLog.calculateTotal();
                logs.add(latestLog.toLog()); 

                saveInFile();
                finish();
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    0);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(logs, out);
            out.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();

            // Took from https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html 01-19 2016
            Type listType = new TypeToken<ArrayList<Logs>>() {
            }.getType();
            oldlogs = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            logs = new ArrayList<String>();
        }
    }



}





