package com.example.pam_uas.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.pam_uas.activities.MainActivity;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "db_movie";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_MOVIE = "movie";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_POSTER_PATH = "poster_path";
    private static final String KEY_VOTE_AVERAGE = "vote_average";
    private static final String KEY_ORIGINAL_LANGUAGE = "original_language";
    private static final String KEY_OVERVIEW = "overview";

    private static final String CREATE_TABLE_MOVIE = "CREATE TABLE "
            + TABLE_MOVIE + "(" + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_TITLE + " TEXT, "
            + KEY_POSTER_PATH + " TEXT, "
            + KEY_VOTE_AVERAGE + " TEXT, "
            + KEY_ORIGINAL_LANGUAGE + " TEXT, "
            + KEY_OVERVIEW + " TEXT );";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_MOVIE+ "'");
        onCreate(db);
    }

    public long addMovieDetail(String title, String poster_path, String vote_average, String original_language,
                               String overview) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, title);
        values.put(KEY_POSTER_PATH, poster_path);
        values.put(KEY_VOTE_AVERAGE, vote_average);
        values.put(KEY_ORIGINAL_LANGUAGE, original_language);
        values.put(KEY_OVERVIEW, overview);

        long insert = db.insert(TABLE_MOVIE, null, values);
        return insert;
    }

    @SuppressLint("Range")
    public ArrayList<MainActivity.MovieModel> getAllMovie() {
        ArrayList<MainActivity.MovieModel> movieModelArrayList = new ArrayList<MainActivity.MovieModel>();

        String selectQuery = "SELECT * FROM " + TABLE_MOVIE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                MainActivity.MovieModel std = new MainActivity.MovieModel();
                std.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                std.setTitle(c.getString(c.getColumnIndex(KEY_TITLE)));
                std.setPoster(c.getString(c.getColumnIndex(KEY_POSTER_PATH)));
                std.setVote_Average(c.getString(c.getColumnIndex(KEY_VOTE_AVERAGE)));
                std.setLanguage(c.getString(c.getColumnIndex(KEY_ORIGINAL_LANGUAGE)));
                std.setOverview(c.getString(c.getColumnIndex(KEY_OVERVIEW)));

                // adding to Students list
                movieModelArrayList.add(std);
            } while (c.moveToNext());
        }
        return movieModelArrayList;
    }

    public void deleteMovie(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MOVIE, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

}
