package com.example.studentreward;

import static com.example.studentreward.R.id.textView1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.studentreward.databinding.FragmentRewardBinding;

public class RewardFragment extends Fragment {
    private EditText etRewardName;
    private TextView textView1, textView2;
    private Button addRewardButton, deleteRewardButton;
    private SharedPreferences sharedPreferences;
    private boolean isTextView1Filled = false; // Untuk melacak apakah textView1 sudah diisi

    public RewardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentRewardBinding binding = FragmentRewardBinding.inflate(inflater, container, false);

        View view = binding.getRoot();

        etRewardName = binding.etRewardName;
        textView1 = binding.textView1;
        textView2 = binding.textView2;
        addRewardButton = binding.addRewardButton;
        deleteRewardButton = binding.deleteRewardButton;

        sharedPreferences = getActivity().getSharedPreferences("RewardPrefs", Context.MODE_PRIVATE);

        addRewardButton.setOnClickListener(v -> addReward());

        deleteRewardButton.setOnClickListener(v -> deleteRewards());

        loadRewards();

        return view;
    }

    private void addReward() {
        String rewardName = etRewardName.getText().toString().trim();

        if (rewardName.isEmpty()) {

            return;
        }

        if (!isTextView1Filled) {
            textView1.setText(rewardName);
            isTextView1Filled = true;
        } else {
            textView2.setText(rewardName);
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Reward1", textView1.getText().toString());
        editor.putString("Reward2", textView2.getText().toString());
        editor.apply();

        etRewardName.setText("");
    }

    private void deleteRewards() {
        textView1.setText("");
        textView2.setText("");

        isTextView1Filled = false;

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("Reward1");
        editor.remove("Reward2");
        editor.apply();
    }
    private void loadRewards() {
        String reward1 = sharedPreferences.getString("Reward1", "");
        String reward2 = sharedPreferences.getString("Reward2", "");

        if (!reward1.isEmpty()) {
            textView1.setText(reward1);
            isTextView1Filled = true;
        }
        if (!reward2.isEmpty()) {
            textView2.setText(reward2);
        }
    }

}
