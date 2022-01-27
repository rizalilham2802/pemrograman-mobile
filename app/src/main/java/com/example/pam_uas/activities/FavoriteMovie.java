package com.example.pam_uas.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.pam_uas.db.DbHelper;
import com.example.pam_uas.FavoriteAdapter;
import com.example.pam_uas.R;

import java.util.ArrayList;

public class FavoriteMovie extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FavoriteAdapter adapter;
    private ArrayList<MainActivity.MovieModel> movieArrayList;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_movie);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new FavoriteAdapter(this);

        dbHelper = new DbHelper(this);
        movieArrayList = dbHelper.getAllMovie();
        adapter.setListMovie(movieArrayList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(FavoriteMovie.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}