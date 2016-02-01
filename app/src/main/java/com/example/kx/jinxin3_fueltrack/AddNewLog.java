package com.example.kx.jinxin3_fueltrack;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.Toolbar;
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

public class AddNewLog extends AppCompatActivity {

    private static final String FILENAME = "file2.sav";
    private ArrayList<Logs> logs = new ArrayList<>();
    private ArrayAdapter<Logs> adapter;

    private EditText stationText;
    private EditText odoText;
    private EditText gradeText;
    private EditText amountText;
    private EditText unitcosText;
    private EditText dateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_log);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button addButton = (Button) findViewById(R.id.addbutton);

        stationText = (EditText) findViewById(R.id.editStation2);
        odoText = (EditText) findViewById(R.id.editOdometer2);
        gradeText = (EditText) findViewById(R.id.editGrade2);
        amountText = (EditText) findViewById(R.id.editAmount2);
        unitcosText = (EditText) findViewById(R.id.editUnitCost2);
        dateText = (EditText) findViewById(R.id.editDate2);

        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Logs newlog;
                String text1 = dateText.getText().toString();
                String text2 = stationText.getText().toString();
                String text3 = odoText.getText().toString();
                String text4 = gradeText.getText().toString();
                String text5 = amountText.getText().toString();
                String text6 = unitcosText.getText().toString();
                try {
                    newlog = new Logs(text1,text2,text3,text4,text5,text6);
                    loadFromFile();
                    logs.add(newlog);
                    saveInFile();
                    finish();
                }catch (Exception e){
                    Toast.makeText(AddNewLog.this, "Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
            logs = gson.fromJson(in, listType);
            //unedited_Log = logs;


        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            logs = new ArrayList<Logs>();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }


}
