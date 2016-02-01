
package com.example.kx.jinxin3_fueltrack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
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

    private Logs new_Log;
    private ArrayList<Logs> logs = new ArrayList<Logs>();
    private static final String FILENAME = "file2.sav";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_new_log);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        list_index = intent.getIntExtra("pos",0);

        loadFromFile();

        stationText = (EditText) findViewById(R.id.editStation);
        odoText = (EditText) findViewById(R.id.editOdometer);
        gradeText = (EditText) findViewById(R.id.editGrade);
        amountText = (EditText) findViewById(R.id.editAmount2);
        unitcosText = (EditText) findViewById(R.id.editunitcost);
        dateText = (EditText) findViewById(R.id.editDate);

        stationText.setText(logs.get(list_index).getStation());
        odoText.setText(logs.get(list_index).getOdometer());
        gradeText.setText(logs.get(list_index).getGrade());
        amountText.setText(logs.get(list_index).getAmount());
        unitcosText.setText(logs.get(list_index).getUnitcost());
        dateText.setText(logs.get(list_index).getDate());

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
            @Override
            public void onClick(View v) {
                //setResult(RESULT_OK);
                Toast.makeText(EditNewLog.this, "Saving", Toast.LENGTH_SHORT).show();
                String text1 = dateText.getText().toString();
                String text2 = stationText.getText().toString();
                String text3 = odoText.getText().toString();
                String text4 = gradeText.getText().toString();
                String text5 = amountText.getText().toString();
                String text6 = unitcosText.getText().toString();

                try {
                    new_Log = new Logs(text1,text2,text3,text4,text5,text6);
                    loadFromFile();
                    logs.set(list_index,new_Log);
                    saveInFile();
                    finish();
                }catch (Exception e){
                    Toast.makeText(EditNewLog.this, "Wrong", Toast.LENGTH_SHORT).show();
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





