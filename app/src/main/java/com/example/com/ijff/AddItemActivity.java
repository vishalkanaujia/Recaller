package com.example.com.ijff;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.PrintStream;

public class AddItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
    }

    public void AddItemButtonClick(View view) {
        EditText keyword = (EditText)findViewById(R.id.addItemKey);
        EditText val = (EditText)findViewById(R.id.addItemValue);

        String key = keyword.getText().toString();
        String value = val.getText().toString();

        if (key.isEmpty() || key == null) {
            Toast.makeText(this, "Empty keyword is not allowed!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            setResult(RESULT_CANCELED, intent);
            finish();
        }

        try {
            PrintStream defn = new PrintStream(openFileOutput(MainActivity.DICT_FILE_NAME, MODE_APPEND));
            defn.println(key + "\t" + value);
            Log.wtf("info", "Added key =" + key + " value=" + value);
            defn.close();

            // Intent to finish the activity
            Intent intent = new Intent();
            intent.putExtra("word", key);
            intent.putExtra("value", value);
            setResult(RESULT_OK, intent);
            finish();
        } catch (Exception e) {
            Log.wtf("Add: Error opening definitions.txt", e);
        }
        MainActivity.dictUpdated = true;
    }
}