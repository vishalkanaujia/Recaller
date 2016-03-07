package com.example.com.ijff;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddItemActivity extends AppCompatActivity {
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item2);
        dbHelper = new DBHelper(this);
    }

    public void SubmitAnItemButtonClick(View view) {
        EditText keyword = (EditText) findViewById(R.id.keyword1);
        String keyword1 = keyword.getText().toString();

        EditText defn = (EditText) findViewById(R.id.defn);
        defn.setSelection(0);

        String defn1 = defn.getText().toString();
        dbHelper.insertInfo(keyword1, defn1);

        Toast.makeText(getApplicationContext(), "Added entry " + keyword1, Toast.LENGTH_SHORT).show();
        dbHelper.close();
        Intent intent = new Intent();
        intent.putExtra("addedString", keyword1);
        setResult(RESULT_OK,intent);
    }
}