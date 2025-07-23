package com.example.studentreward;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardFragment extends Fragment {
    private RecyclerView recyclerView;
    private LeaderboardAdapter adapter;
    private List<LeaderboardItem> leaderboardList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        leaderboardList = new ArrayList<>();
        fetchLeaderboard();

        return view;
    }

    private void fetchLeaderboard() {
        String url = DBContract.urlLeaderboard;

        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {

                            Log.d("JSON Response", response.toString());

                            leaderboardList.clear();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject item = response.getJSONObject(i);

                                int rank = item.getInt("rank");
                                String name = item.getString("username");
                                int points = item.getInt("points");

                                leaderboardList.add(new LeaderboardItem(name, points));
                            }

                            if (adapter == null) {
                                adapter = new LeaderboardAdapter(leaderboardList);
                                recyclerView.setAdapter(adapter);
                            } else {
                                adapter.notifyDataSetChanged(); // Update data jika adapter sudah ada
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Error parsing data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Menangani error pada request
                        Toast.makeText(getContext(), "Error fetching data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestQueue.add(jsonArrayRequest);
    }
}


