package com.professionalandroid.apps.databasetest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    DBHelper dbHelper;
    ArrayAdapter mAdapter;
    ArrayList arrayList;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listview);

        dbHelper = new DBHelper(this);
        arrayList = dbHelper.getAllMovies();

        mAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(mAdapter);

//        dbHelper = new DBHelper(this);
//        arrayList = dbHelper.getAllMovies();
//        mAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
//        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = (String)((ListView)adapterView).getItemAtPosition(i);
                String[] strArray = item.split(" ");
                int id = Integer.parseInt(strArray[0]);
                Bundle bundle = new Bundle();
                bundle.putInt("id", id);
                bundle.putString("action", "update");
                Intent intent = new Intent(getApplicationContext(), DisplayMovie.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        dbHelper = new DBHelper(this);
        mAdapter.clear();
        mAdapter.addAll(dbHelper.getAllMovies());
        mAdapter.notifyDataSetChanged();
    }

    public void buttonAdd(View view) { //DisplayMovie 화면으로 이동
        Bundle bundle = new Bundle();
        bundle.putInt("id", 0);
        bundle.putString("action", "insert");
        Intent intent = new Intent(getApplicationContext(), DisplayMovie.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}