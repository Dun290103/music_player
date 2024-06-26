package com.example.music_app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MyViewHolder> {

    private List<MusicList> list;
    private final Context context;
    private int playingPosition = 0;
    private final SongChangeListener songChangeListener;

    public MusicAdapter(List<MusicList> list, Context context) {
        this.list = list;
        this.context = context;
        this.songChangeListener = (SongChangeListener) context;
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public MusicAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // You need to inflate your layout and return a new instance of MyViewHolder here
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.music_adapter_layout, null));
    }

    @Override
    public void onBindViewHolder(@NonNull MusicAdapter.MyViewHolder holder, int position) {
        MusicList list2 = list.get(holder.getAdapterPosition());

        if (list2.isPlaying()) {
            playingPosition = holder.getAdapterPosition();
            holder.rootLayout.setBackgroundResource(R.drawable.round_back_blue_10);
        } else {
            holder.rootLayout.setBackgroundResource(R.drawable.round_back_10);
        }

        String generateDuration = String.format(Locale.getDefault(), "%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(Long.parseLong(list2.getDuration())),
                TimeUnit.MILLISECONDS.toSeconds(Long.parseLong(list2.getDuration())) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(Long.parseLong(list2.getDuration()))));

        holder.title.setText(list2.getTitle());
        holder.artist.setText(list2.getArtist());
        holder.musicDuration.setText(list2.getDuration());

        holder.rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.get(playingPosition).setPlaying(false);
                list2.setPlaying(true);
                songChangeListener.onSongChanged(holder.getAdapterPosition());

                notifyDataSetChanged();
            }
        });
    }

    public void updateList(List<MusicList> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        // Return the size of your data set here
        return list.size();
    }

    static public class MyViewHolder extends RecyclerView.ViewHolder {
        private final RelativeLayout rootLayout;
        private final TextView title;
        private final TextView artist;
        private final TextView musicDuration;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize your view references here
            rootLayout = itemView.findViewById(R.id.rootLayout);
            title = itemView.findViewById(R.id.musicTitle);
            artist = itemView.findViewById(R.id.musicArtist);
            musicDuration = itemView.findViewById(R.id.musicDuration);
        }
    }
}