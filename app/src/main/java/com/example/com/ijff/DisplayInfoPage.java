package com.example.com.ijff;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DisplayInfoPage extends AppCompatActivity {

    private DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_info_page);

        mydb = new DBHelper(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int id = extras.getInt("id");

            if (id > 0) {
                Cursor res = mydb.getData(id);
                res.moveToFirst();

                String keyword = res.getString(res.getColumnIndex(DBHelper.KEYWORD_COLUMN_NAME));
                String defn = res.getColumnName(res.getColumnIndex(DBHelper.VALUE_COLUMN_NAME));

                TextView info_value = (TextView) findViewById(R.id.info_value);
                info_value.setText(defn);

                if(!res.isClosed()) {
                    res.close();
                }
            }
        }
    }

    public void deleteButtonClick(View view) {
    }

    public void editButtonClick(View view) {
    }
}
