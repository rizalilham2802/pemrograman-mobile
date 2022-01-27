package com.example.pam_uas.model;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pam_uas.R;
import com.example.pam_uas.activities.DetailMovie;
import com.example.pam_uas.activities.MainActivity;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    private Context mContext;
    private List<MainActivity.MovieModel> mData;

    public MovieAdapter(Context mContext, List<MainActivity.MovieModel> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        v = inflater.inflate(R.layout.movie_item, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final MainActivity.MovieModel data = mData.get(position);
        String JSON_URL = "https://image.tmdb.org/t/p/w500/";
        holder.title.setText(mData.get(position).getTitle());
        holder.overview.setText(mData.get(position).getOverview());
        holder.vote_average.setText(mData.get(position).getVote_Average());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailMovie.class);

                Bundle bundle = new Bundle();
                bundle.putString("title", data.getTitle());
                bundle.putString("overview", data.getOverview());
                bundle.putString("vote_average", data.getVote_Average());
                bundle.putString("original_language", data.getLanguage());
                bundle.putString("poster_path", data.getPoster());
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
        Glide.with(mContext)
                .load(JSON_URL+data.getPoster())
                .into(holder.poster);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title, overview, vote_average;
        ImageView poster;
        LinearLayout linearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.main_layout);
            title = itemView.findViewById(R.id.tv_title);
            overview = itemView.findViewById(R.id.tv_overview);
            vote_average = itemView.findViewById(R.id.tv_voteaverage);
            poster = itemView.findViewById(R.id.img_poster);
        }
    }

}
