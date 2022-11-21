package com.professionalandroid.apps.databasetest;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyMovies.db";
    public static final int DATABASE_VERSION = 1;
    //public static final String MOVIES_TABLE_NAME = "movies";
    public static final String MOVIES_COLUMN_ID = "id";
    public static final String MOVIES_COLUMN_NAME = "name";
    public static final String MOVIES_COLUMN_DIRECTOR = "director";
    public static final String MOVIES_COLUMN_YEAR = "year";
    public static final String MOVIES_COLUMN_NATION = "nation";
    public static final String MOVIES_COLUMN_RATING = "rating";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE movies (id INTEGER PRIMARY KEY autoincrement, name TEXT, director TEXT, year TEXT, nation TEXT, rating TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS movies");
        onCreate(sqLiteDatabase);
    }

    //save 버튼 클릭 시 해당 항목을 database에 저장하기 위한 함수
    public boolean insertMovie(String name, String director, String year, String nation, String rating){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("director", director);
        contentValues.put("year", year);
        contentValues.put("nation", nation);
        contentValues.put("rating", rating);

        db.insert("movies", null, contentValues);
        return true;
    }

    //특정 항목의 data를 불러오는 함수
    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from movies where id=" + id, null);
        return cursor;
    }

    //update 버튼 클릭 시 해당 항목에 대해서 값을 수정하여 update 하는 함수
    public boolean updateMovie(int id, String name, String director, String year, String nation, String rating){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("director", director);
        contentValues.put("year", year);
        contentValues.put("nation", nation);
        contentValues.put("rating", rating);

        db.update("movies", contentValues, "id = ?", new String[]{Integer.toString(id)});
        return true;
    }

    //delete 버튼 클릭 시 해당 정보 삭제하는 함
    public Integer deleteMovie(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("movies", "id = ?", new String[]{Integer.toString(id)});
    }

    @SuppressLint("Range")
    public ArrayList getAllMovies(){
        ArrayList arrayList = new ArrayList();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from movies",null);
        cursor.moveToFirst();
        while(cursor.isAfterLast() == false){
            arrayList.add(cursor.getString(cursor.getColumnIndex(MOVIES_COLUMN_ID)) + " " + cursor.getString(cursor.getColumnIndex(MOVIES_COLUMN_NAME)));
            cursor.moveToNext();
        }
        return arrayList;
    }
}
