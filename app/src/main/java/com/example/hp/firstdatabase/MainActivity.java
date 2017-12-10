package com.example.hp.firstdatabase;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText name, marks, id;
    TextView txtResult;
    Button insert, read, update, delete;
    DataBaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DataBaseHelper(this);
        id = (EditText) findViewById(R.id.idText);
        name = (EditText) findViewById(R.id.nameText);
        marks = (EditText) findViewById(R.id.marksText);
        insert = (Button) findViewById(R.id.insertBtn);
        read = (Button) findViewById(R.id.readBtn);
        update = (Button) findViewById(R.id.updateBtn);
        delete = (Button) findViewById(R.id.deleteBtn);
        txtResult = (TextView) findViewById(R.id.result);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                insert();
                Toast.makeText(this, "Insert selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item2:
                read();
                Toast.makeText(this, "Read selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item3:
                updateD();
                Toast.makeText(this, "Update selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item4:
                deleteD();
                Toast.makeText(this, "Delete selected", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showPopup(View v){
        PopupMenu popupMenu = new PopupMenu(MainActivity.this,v);
        //MenuInflater infla = popupMenu.getMenuInflater();
        getMenuInflater().inflate(R.menu.pop_up,popupMenu.getMenu());
        popupMenu.show();
    }

    public void insertClick(View view) {
        insert();
    }

    public void readClick(View view) {
        read();
    }

    public void updateClick(View view) {
        String idTxt = id.getText().toString();
        int len = idTxt.length();
        if(len == 0){
            Toast.makeText(this, "Enter the ID", Toast.LENGTH_SHORT).show();
        }
        else updateD();
        showPopup(view);
    }

    public void delClick(View view) {
        deleteD();
    }

    public void insert() {
        String name1 = name.getText().toString();
        String marks1 = marks.getText().toString();
        Boolean result = myDb.insertData(name1, marks1);
        if (result == true) {
            Toast.makeText(this, "data inserted successfully", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "data insertion failed", Toast.LENGTH_LONG).show();
        }
    }

    public void read() {
        Cursor res = myDb.getAllData();
        StringBuffer stringBuffer = new StringBuffer();
        if (res != null && res.getCount() > 0) {
            while (res.moveToNext()) {
                stringBuffer.append("Id: " + res.getString(0) + "\n");
                stringBuffer.append("Name: " + res.getString(1) + "\n");
                stringBuffer.append("Marks: " + res.getString(2) + "\n\n");
            }
            txtResult.setText(stringBuffer.toString());
            Toast.makeText(this, "data retrieved successfully", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "no data to retrieve", Toast.LENGTH_LONG).show();
        }
    }

    public void updateD() {
        String id1 = id.getText().toString();
        String name1 = name.getText().toString();
        String marks1 = marks.getText().toString();
        Boolean result = myDb.updateData(id1, name1, marks1);
        if (result == true) {
            Toast.makeText(this, "Data updated successfully", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "No rows affected", Toast.LENGTH_LONG).show();
        }
    }

    public void deleteD() {
        String id1 = id.getText().toString();
        int result = myDb.deleteData(id1);
        Toast.makeText(this, result + " :Rows Affected", Toast.LENGTH_LONG).show();
    }
}
