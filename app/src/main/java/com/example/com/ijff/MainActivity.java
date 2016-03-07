package com.example.com.ijff;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView obj;
    DBHelper mydb;
    public ArrayAdapter arrayAdapter;
    public static final int REQ_CODE_DISPLAY_ITEM = 1234;
    public static final int REQ_CODE_ADD_ITEM = 2345;
    String itemText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mydb = new DBHelper(this);
    }

    public void searchItemButtonClick(View view) {
        EditText searchString = (EditText) findViewById(R.id.searchString);
        String search_string = searchString.getText().toString();
        Log.wtf("mainActivity", "sear Item: Search string is " + search_string);

        ListView listView1 = (ListView) findViewById(R.id.listview1);
        //Cursor res = mydb.getData(search_string);

        ArrayList arrayList = mydb.getAllInfo(search_string);
        //ArrayList arrayList = mydb.getAllInfo(null);

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);

        listView1.setAdapter(arrayAdapter);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemText = parent.getItemAtPosition(position).toString();
                Log.wtf("main_activity: ", "You clicked " + itemText);
                //Toast.makeText(this, "You clicked " + itemText, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), DisplayInfoPage.class);
                intent.putExtra("searchString", itemText);
                startActivityForResult(intent, REQ_CODE_DISPLAY_ITEM);
            }
        });
    }

    public void AddItemMainButtonClick(View view) {
        Intent intent = new Intent(this, AddItemActivity.class);
        startActivityForResult(intent, REQ_CODE_ADD_ITEM);
    }

    public void updateListItems(boolean has_added, String itemText) {
        if (has_added == true) {
            arrayAdapter.remove(itemText);
        } else {
            arrayAdapter.add(itemText);
        }
        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.wtf("mainlog:", "Activity finished: result code = " + resultCode);
        switch (requestCode) {
            case REQ_CODE_DISPLAY_ITEM:
                if (resultCode == RESULT_CANCELED) {
                    Log.wtf("mainlog:", "Activity remove finished: Updating list... result code = " + resultCode);
                    updateListItems(false, itemText);
                }
                break;
            case REQ_CODE_ADD_ITEM:
                if (resultCode == RESULT_OK) {
                    String text = data.getStringExtra("addedString");
                    Log.wtf("mainlog:", "Activity add finished: added = " + text + "... result code = " + resultCode);
                    // ToDo: fix it
                    //updateListItems(true, text);
                }
                break;
        }
    }
}