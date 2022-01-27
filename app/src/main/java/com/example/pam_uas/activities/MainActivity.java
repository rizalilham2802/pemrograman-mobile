package com.example.pam_uas.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.pam_uas.model.MovieAdapter;
import com.example.pam_uas.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static String JSON_URL = "https://api.themoviedb.org/3/movie/top_rated?api_key=b94f7a4fbc7f8787158e9ecc4543c3ca&language=en-US&page=1";

    List<MovieModel> movieList, movieList2;
    RecyclerView recyclerView, recyclerView2;
    private LinearLayoutManager layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        layout = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);

        GetData getData = new GetData();
        getData.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflaterMenu = getMenuInflater();
        inflaterMenu.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.favorite)
        {
            startActivity(new Intent(MainActivity.this, FavoriteMovie.class));
        }
        else if (item.getItemId()==R.id.about)
        {
            startActivity(new Intent(MainActivity.this, About.class));
        }
        else if (item.getItemId()==R.id.language)
        {
            startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
        }
        return true;
    }

    public class GetData extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            String current = "";
            try {
                URL url;
                HttpURLConnection urlConnection = null;

                try {
                    url = new URL(JSON_URL);
                    urlConnection = (HttpURLConnection) url.openConnection();

                    InputStream is = urlConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);

                    int data = isr.read();
                    while(data != -1) {
                        current += (char) data;
                        data = isr.read();
                    }
                    return current;
                } catch(MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null){
                        urlConnection.disconnect();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return current;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("results");

                for(int i=0; i< jsonArray.length(); i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    MovieModel model = new MovieModel();
                    model.setTitle(jsonObject1.getString("title"));
                    model.setOverview(jsonObject1.getString("overview"));
                    model.setVote_Average(jsonObject1.getString("vote_average"));
                    model.setPoster(jsonObject1.getString("poster_path"));
                    model.setLanguage(jsonObject1.getString("original_language"));
                    movieList.add(model);
                }
            }catch (Exception e) {
                e.printStackTrace();
            }

            PutDataIntoMovieList(movieList);
        }
    }

    private void PutDataIntoMovieList (List<MovieModel> movieList) {
        MovieAdapter movieAdapter = new MovieAdapter(this, movieList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(movieAdapter);
    }

    public static class MovieModel {
        private int id;
        String title, poster_path, vote_average, overview, language;

        public String getLanguage() {
            return language;
        }
        public void setLanguage(String language) {
            this.language = language;
        }


        public MovieModel(int id, String title, String poster_path, String vote_average, String overview, String language) {
            this.id = id;
            this.title = title;
            this.poster_path = poster_path;
            this.vote_average = vote_average;
            this.overview = overview;
            this.language = language;
        }

        public MovieModel() {
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPoster() {
            return poster_path;
        }

        public void setPoster(String poster) {
            this.poster_path = poster;
        }

        public String getVote_Average() {
            return vote_average;
        }

        public void setVote_Average(String vote_average) {
            this.vote_average = vote_average;
        }

        public String getOverview() {
            return overview;
        }

        public void setOverview(String overview) {
            this.overview = overview;
        }
    }
}