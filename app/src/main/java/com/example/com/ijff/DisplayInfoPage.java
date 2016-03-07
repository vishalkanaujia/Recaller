package com.example.com.ijff;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayInfoPage extends AppCompatActivity {

    private DBHelper mydb;
    private String search_string;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_info_page);

        mydb = new DBHelper(this);
        search_string = getIntent().getStringExtra("searchString");
        //Toast.makeText(this, "Display got string " + search_string, Toast.LENGTH_SHORT).show();
        TextView queried_tv = (TextView)findViewById(R.id.queried);
        queried_tv.setText("You queried: " + search_string);

        Cursor res = mydb.getData(search_string);
        if (res.getCount() < 1) {
            Toast.makeText(getApplicationContext(), "No record found!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        res.moveToFirst();
        String defn = res.getString(res.getColumnIndex("value"));
        id = res.getInt(res.getColumnIndex("id"));
        //Toast.makeText(this, "Result string " + defn, Toast.LENGTH_SHORT).show();
        Log.wtf("displaylog", "Result string " + defn);
        EditText defn_text = (EditText)findViewById(R.id.info_value);
        defn_text.setText(defn);
        mydb.close();
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
    }

    public void deleteButtonClick(View view) {
        EditText defn_text = (EditText)findViewById(R.id.info_value);
        mydb.deleteInfo(id);
        Toast.makeText(this, "Delete successful!", Toast.LENGTH_SHORT).show();//
        mydb.close();
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    public void editButtonClick(View view) {
        EditText defn_text = (EditText)findViewById(R.id.info_value);
        mydb.updateInfo(id, search_string, defn_text.getText().toString());
        Toast.makeText(this, "Update successful!", Toast.LENGTH_SHORT).show();
        mydb.close();
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
    }
}