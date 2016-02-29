package com.example.com.ijff;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private HashMap<String, String> dictionary;
    public static String DICT_FILE_NAME = "my_dictionary.txt";
    public static boolean dictUpdated;
    private BufferedReader reader;
    private static final int REQ_CODE_ADD_ITEM = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dictUpdated = true;
        dictionary = new HashMap<>();
        readAllDefinitions();
    }

    private void readAllDefinitions() {
        if (dictionary == null) {
            Toast.makeText(this, "Null dictionary passed!", Toast.LENGTH_SHORT).show();
            dictUpdated = false;
        }

        FileInputStream f;
        try {
            f = openFileInput(DICT_FILE_NAME);
            reader = new BufferedReader(new InputStreamReader(f));

            String line = reader.readLine();
            while(line != null) {
                String[] pieces;
                pieces = line.split("\t");
                if (pieces.length >= 2) {
                    dictionary.put(pieces[0], pieces[1]);
                }
                Log.d("info", line);
                line = reader.readLine();
            }
            f.close();
        } catch (Exception e) {
            Toast.makeText(this, "Error opening file for read", Toast.LENGTH_SHORT).show();
        }
    }

    public void searchItemButtonClick(View view) {
        if (dictionary == null || dictUpdated) {
            dictionary = new HashMap<>();
            readAllDefinitions();
        }

        EditText keyword = (EditText)findViewById(R.id.searchString);
        String key = keyword.getText().toString();

        String defn = dictionary.get(key);
        TextView definition = (TextView)findViewById(R.id.definition);
        if (defn == null) {
            definition.setText("word not found!");
        } else {
            Log.i("info", "Found key=" + key + "def=" + defn);
            definition.setText(defn);
        }
    }

    public void AddItemMainButtonClick(View view) {
        Intent intent = new Intent(this, AddItemActivity.class);
        startActivityForResult(intent, REQ_CODE_ADD_ITEM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_ADD_ITEM &&
                resultCode == RESULT_OK) {
            String word = data.getStringExtra("word");
            String val = data.getStringExtra("value");

            Toast.makeText(this, "word is " + word + "def is " + val, Toast.LENGTH_SHORT).show();
        }
    }
}