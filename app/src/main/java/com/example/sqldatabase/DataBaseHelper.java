package com.example.sqldatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Movie;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {


    public static final String MOVIE_TABLE = "MOVIE_TABLE";
    public static final String COLUMN_MOVIE_NAME = "MOVIE_NAME";
    public static final String COLUMN_MOVIE_RELEASE = "MOVIE_RELEASE";
    public static final String COLUMN_IN_CINEMAS = "IN_CINEMAS";
    public static final String COLUMN_ID = "ID";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "movie.db", null, 1);
    }

    // this is called the first time the database is accessed. There should ne code in here to create a new database.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + MOVIE_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_MOVIE_NAME + " TEXT, " + COLUMN_MOVIE_RELEASE + " INT, " + COLUMN_IN_CINEMAS + " BOOL)";

        db.execSQL(createTableStatement);
    }


    // this is called if the database version number changes. It prevents previous users apps from breaking when changing the database design.
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }


    public boolean addOne(MovieModel movieModel) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_MOVIE_NAME, movieModel.getName());
        cv.put(COLUMN_MOVIE_RELEASE, movieModel.getRelease());
        cv.put(COLUMN_IN_CINEMAS, movieModel.isActive());

        long insert = db.insert(MOVIE_TABLE, null, cv);
        if (insert == -1){
            return false;
        }
        else{
            return true;
        }

    }

    public boolean deleteOne(MovieModel movieModel){
        // find customerModel in the database. if its found, delete it and return true.
        // if its not then return false.

        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + MOVIE_TABLE + " WHERE " + COLUMN_ID + " = " + movieModel.getId();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()){
            return true;
        }
        else{
            return false;
        }
    }

    public List<MovieModel> getAll(){

        List<MovieModel> returnList = new ArrayList<>();

        // get data from database

        String queryString = "SELECT * FROM " + MOVIE_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            //loop through the cursor (result set) and creates new movie objects. puts them into the return list
            do{
                int movieID = cursor.getInt(0);
                String movieName = cursor.getString(1);
                int movieRelease = cursor.getInt(2);
                boolean movieActive = cursor.getInt(3) == 1 ? true: false;

                MovieModel newMovie = new MovieModel(movieID, movieName, movieRelease, movieActive);
                returnList.add(newMovie);


            } while (cursor.moveToNext());

        }
        else{
             // failure. do not add anything to the list
        }

        //closes both the cursor and the db when done.
        cursor.close();
        db.close();
        return returnList;


    }
}
