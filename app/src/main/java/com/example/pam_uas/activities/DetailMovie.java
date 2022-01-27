package com.example.pam_uas.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pam_uas.db.DbHelper;
import com.example.pam_uas.R;

public class DetailMovie extends AppCompatActivity {

    private Button btnFavorite;
    private ProgressDialog progressDialog;

    ImageView poster;
    TextView title, overview, vote_average, language;
    String extTitle, extOverview, extReleaseDate, extPopularity, extLanguage, extVoteAverage, extPoster;

    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        String JSON_URL = "https://image.tmdb.org/t/p/w500/";
        dbHelper = new DbHelper(this);

        btnFavorite = findViewById(R.id.btnFavorite);
        poster = findViewById(R.id.img_poster);
        title = findViewById(R.id.tv_title);
        overview = findViewById(R.id.tv_overview);
        vote_average = findViewById(R.id.tv_vote_average);
        language = findViewById(R.id.tv_language);


        Bundle bundle = getIntent().getExtras();

        extTitle = bundle.getString("title");
        extOverview = bundle.getString("overview");
        extPopularity = bundle.getString("popularity");
        extLanguage= bundle.getString("original_language");
        extVoteAverage = bundle.getString("vote_average");
        extPoster = bundle.getString("poster_path");
        Glide.with(this).load(JSON_URL+extPoster).into(poster);

        title.setText(extTitle);
        overview.setText(extOverview);
        language.setText(extLanguage);
        vote_average.setText(extVoteAverage);

        btnFavorite.setOnClickListener(v->{
            dbHelper.addMovieDetail(extTitle, extPoster, extVoteAverage, extLanguage, extOverview);
            Toast.makeText(DetailMovie.this, getResources().getString(R.string.ok_save), Toast.LENGTH_SHORT).show();
        });
    }

}