package com.example.studentreward;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

public class PointStudentFragment extends Fragment {

    private TextView pointsTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_point_student, container, false);

        pointsTextView = view.findViewById(R.id.pointsTextView);

        fetchPointsFromServer();

        return view;
    }

    private void fetchPointsFromServer() {

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("StudentRewardPrefs", requireContext().MODE_PRIVATE);
        int userId = sharedPreferences.getInt("user_id", -1);

        if (userId == -1) {
            pointsTextView.setText("User ID not found");
            return;
        }

        String url = DBContract.urlGetPoint + "?user_id=" + userId;

        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int points = response.getInt("points");
                            pointsTextView.setText(String.valueOf(points));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            pointsTextView.setText("Error parsing data");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        pointsTextView.setText("Error fetching data");
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }
}

