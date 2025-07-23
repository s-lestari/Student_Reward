package com.example.studentreward;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RewardStudentFragment extends Fragment {

    private TextView titleTextView, messageTextView, rewardTextView;
    private static final String URL = DBContract.urlGetPoint; // Ganti dengan URL PHP API Anda
    private SharedPreferences sharedPreferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_reward_student, container, false);

        titleTextView = view.findViewById(R.id.title);
        messageTextView = view.findViewById(R.id.message);
        rewardTextView = view.findViewById(R.id.textView);

        sharedPreferences = getActivity().getSharedPreferences("RewardPrefs", Context.MODE_PRIVATE);

        fetchStudentPoints();

        return view;
    }

    private void fetchStudentPoints() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("StudentRewardPrefs", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("user_id", -1);

        if (userId == -1) {
            showError("User ID not found. Please login again.");
            return;
        }

        String url = URL + "?user_id=" + userId;

        RequestQueue queue = Volley.newRequestQueue(requireContext());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.has("points")) {
                            int points = jsonObject.getInt("points");
                            updateUIBasedOnPoints(points);
                        } else {
                            showError("Failed to get points");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        showError("Invalid response");
                    }
                },
                error -> showError("Failed to connect to server"));

        queue.add(stringRequest);
    }


    private void updateUIBasedOnPoints(int points) {
        if (points < 80) {
            titleTextView.setText("Sorry");
            messageTextView.setText("Your point is not enough");
            rewardTextView.setText(":(");
        } else if (points >= 80 && points <= 89) {
            String reward1 = sharedPreferences.getString("Reward1", "No reward available");
            titleTextView.setText("Congratulations!");
            messageTextView.setText("You have earned:");
            rewardTextView.setText(reward1);
        } else if (points >= 90 && points <= 100) {
            String reward2 = sharedPreferences.getString("Reward2", "No reward available");
            titleTextView.setText("Congratulations!");
            messageTextView.setText("You have earned:");
            rewardTextView.setText(reward2);
        }
    }

    private void showError(String errorMessage) {
        titleTextView.setText("Error");
        messageTextView.setText(errorMessage);
        rewardTextView.setText(":(");
    }
}
