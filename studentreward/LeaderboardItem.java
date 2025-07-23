package com.example.studentreward;

public class LeaderboardItem {
    private String username;
    private int points;

    public LeaderboardItem(String username, int points) {
        this.username = username;
        this.points = points;
    }

    public String getUsername() {
        return username;
    }

    public int getPoints() {
        return points;
    }
}
