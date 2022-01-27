package com.example.pam_uas;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pam_uas.activities.FavoriteMovie;
import com.example.pam_uas.activities.MainActivity;
import com.example.pam_uas.db.DbHelper;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.MovieViewHolder> {

    private ArrayList<MainActivity.MovieModel> favoriteList = new ArrayList<>();
    private Activity activity;
    private DbHelper dbHelper;

    public FavoriteAdapter(Activity activity) {
        this.activity = activity;
        dbHelper = new DbHelper(activity);
    }

    public ArrayList<MainActivity.MovieModel> getFavoriteList() {
        return favoriteList;
    }

    public void setListMovie(ArrayList<MainActivity.MovieModel> listNotes) {
        if (listNotes.size() > 0) {
            this.favoriteList.clear();
        }
        this.favoriteList.addAll(listNotes);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public FavoriteAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.MovieViewHolder holder, int position) {
        String JSON_URL = "https://image.tmdb.org/t/p/w500/";
        holder.title.setText(favoriteList.get(position).getTitle());
        holder.overview.setText(favoriteList.get(position).getOverview());

        holder.btnRemove.setOnClickListener((View v) -> {
            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(activity);

            builder.setTitle(activity.getResources().getString(R.string.del_confirm_title));
            builder.setMessage(activity.getResources().getString(R.string.del_confirm_text));

            builder.setPositiveButton(activity.getResources().getString(R.string.del_text_yes), (dialog, which) -> {
                dbHelper.deleteMovie(favoriteList.get(position).getId());
                Toast.makeText(activity, activity.getResources().getString(R.string.ok_delete), Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(activity, FavoriteMovie.class);
                activity.startActivity(myIntent);
                activity.finish();
            });

            builder.setNegativeButton(activity.getResources().getString(R.string.del_text_no), (dialog, which) -> dialog.dismiss());

            AlertDialog alert = builder.create();
            alert.show();

        });

        Glide.with(activity)
                .load(JSON_URL+favoriteList.get(position).getPoster())
                .into(holder.poster);
    }

    @Override
    public int getItemCount() {
        return favoriteList.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        TextView id, title, overview, vote_average;
        ImageView poster;
        Button btnDetail, btnRemove;
        LinearLayout linearLayout;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            btnRemove = itemView.findViewById(R.id.btn_remove);
            linearLayout = itemView.findViewById(R.id.main_layout);
            title = itemView.findViewById(R.id.tv_title);
            overview = itemView.findViewById(R.id.tv_overview);
            vote_average = itemView.findViewById(R.id.tv_voteaverage);
            poster = itemView.findViewById(R.id.img_poster);
        }
    }
}