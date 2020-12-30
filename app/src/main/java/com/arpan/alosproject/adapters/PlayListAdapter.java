package com.arpan.alosproject.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.arpan.alosproject.R;
import com.arpan.alosproject.model.firebase.CourseVideo;

import java.net.ContentHandler;
import java.util.List;

public class PlayListAdapter extends RecyclerView.Adapter<PlayListAdapter.PlayItemViewHolder> {

    private Context context;
    private List<CourseVideo> videoList;
    private int curPosition;
    private OnItemClickListener listener;

    private LinearLayout lastSelected = null;
    private CardView lastCard = null;
    private boolean firstTime = true;


    public PlayListAdapter(Context context, List<CourseVideo> videoList, OnItemClickListener listener) {
        this.context = context;
        this.videoList = videoList;
        this.listener = listener;
    }

    public PlayListAdapter() {

    }

    @NonNull
    @Override
    public PlayItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_item, parent, false);
        return new PlayItemViewHolder(view);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull PlayItemViewHolder holder, int position) {
        CourseVideo videoItem = videoList.get(position);

        holder.video_Name.setText(videoItem.getVideoTitle());

        if(firstTime && position == 0) {
            holder.layout.setBackground(context.getDrawable(R.drawable.side_panel));
            holder.cardView.setCardElevation(15);
            lastSelected = holder.layout;
            lastCard = holder.cardView;
            firstTime = false;
        }

        holder.video_Name.setOnClickListener(v -> {
            if(lastSelected != null) {
                lastSelected.setBackground(context.getDrawable(R.drawable.layout_background));
                lastCard.setCardElevation(5);
            }
            holder.layout.setBackground(context.getDrawable(R.drawable.side_panel));
            holder.cardView.setCardElevation(15);
            lastSelected = holder.layout;
            lastCard = holder.cardView;
            listener.onItemClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public static class PlayItemViewHolder extends RecyclerView.ViewHolder {

        TextView video_Name;
        View side_Panel;
        CardView cardView;
        LinearLayout layout;

        public PlayItemViewHolder(@NonNull View itemView) {
            super(itemView);

            video_Name = itemView.findViewById(R.id.videoName);
            side_Panel = itemView.findViewById(R.id.side_panel);
            cardView = itemView.findViewById(R.id.cardView);
            layout = itemView.findViewById(R.id.selectedLayout);
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(int position);
    }
}
