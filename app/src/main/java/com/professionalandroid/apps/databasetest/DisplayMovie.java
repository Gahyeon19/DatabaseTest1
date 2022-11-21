package com.professionalandroid.apps.databasetest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;

public class DisplayMovie extends AppCompatActivity {
    DBHelper dbHelper;
    EditText editname;
    EditText editdirector;
    EditText edityear;
    EditText editnation;
    EditText editrating;
    DatabaseReference firebaseDB;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_movie);

        editname = (EditText) findViewById(R.id.editname);
        editdirector = (EditText) findViewById(R.id.editdirector);
        edityear = (EditText) findViewById(R.id.edityear);
        editnation = (EditText) findViewById(R.id.editnation);
        editrating = (EditText) findViewById(R.id.editrating);

        dbHelper = new DBHelper(this);
        //firebaseDB = FirebaseDatabase.getInstance().getReference().child("movies");

        Intent data = getIntent();
        Bundle bundle = data.getExtras();
        int id = bundle.getInt("id");
        String action = bundle.getString("action");
        if (action.equals("update")) {
            Cursor cursor = dbHelper.getData(id);
            cursor.moveToFirst();
            String name = cursor.getString(cursor.getColumnIndex(DBHelper.MOVIES_COLUMN_NAME));
            String director = cursor.getString(cursor.getColumnIndex(DBHelper.MOVIES_COLUMN_DIRECTOR));
            String year = cursor.getString(cursor.getColumnIndex(DBHelper.MOVIES_COLUMN_YEAR));
            String nation = cursor.getString(cursor.getColumnIndex(DBHelper.MOVIES_COLUMN_NATION));
            String rating = cursor.getString(cursor.getColumnIndex(DBHelper.MOVIES_COLUMN_RATING));

            editname.setText(name);
            editdirector.setText(director);
            edityear.setText(year);
            editnation.setText(nation);
            editrating.setText(rating);
        }
    }

    public void buttonSave(View view) {
        String name = editname.getText().toString();
        String director = editdirector.getText().toString();
        String year = edityear.getText().toString();
        String nation = editnation.getText().toString();
        String rating = editrating.getText().toString();

        dbHelper.insertMovie(name, director, year, nation, rating);
        finish();
    }

    public void buttonUpdate(View view) {
        Intent data = getIntent();
        Bundle bundle = data.getExtras();
        int id = bundle.getInt("id");

        String name = editname.getText().toString();
        String director = editdirector.getText().toString();
        String year = edityear.getText().toString();
        String nation = editnation.getText().toString();
        String rating = editrating.getText().toString();

        dbHelper.updateMovie(id, name, director, year, nation, rating);
        finish();
    }

    public void buttonDelete(View view) {
        Intent data = getIntent();
        Bundle bundle = data.getExtras();
        int id = bundle.getInt("id");

        dbHelper.deleteMovie(id);
        finish();
    }
}