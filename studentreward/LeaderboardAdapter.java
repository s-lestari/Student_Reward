package com.example.studentreward;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.ViewHolder> {

    private List<LeaderboardItem> leaderboardList;
    public LeaderboardAdapter(List<LeaderboardItem> leaderboardList) {
        this.leaderboardList = leaderboardList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_leaderboard, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        LeaderboardItem item = leaderboardList.get(position);

        holder.rank.setText(String.valueOf(position + 1));
        holder.username.setText(item.getUsername());
        holder.points.setText(String.valueOf(item.getPoints()));
    }

    @Override
    public int getItemCount() {

        return leaderboardList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView rank, username, points;

        public ViewHolder(View itemView) {
            super(itemView);
            // Inisialisasi komponen UI
            rank = itemView.findViewById(R.id.tvRank);
            username = itemView.findViewById(R.id.tvUsername);
            points = itemView.findViewById(R.id.tvPoints);
        }
    }
}
