package com.example.studentreward;

public class Student {
    private String username;
    private int points;
    private int user_id;

    public Student(String username, int points, int user_id) {
        this.username = username;
        this.points = points;
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getUserId() {
        return user_id;
    }

    public void setUserId(int userId) {
        this.user_id = user_id;
    }
}

