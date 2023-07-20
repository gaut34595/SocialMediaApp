package com.example.socialmedia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.socialmedia.databinding.ActivityLoginDashboardBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class LoginDashboard extends AppCompatActivity {
    ActivityLoginDashboardBinding binding;
    DatabaseReference databaseReference;

    private VideoAdapter adapter;
    private ArrayList<String> videoLinks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityLoginDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.fabAddVideoLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginDashboard.this,AddVideoActivity.class));
            }
        });
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        videoLinks = new ArrayList<>();
        adapter = new VideoAdapter(videoLinks);
        binding.recyclerView.setAdapter(adapter);

        fetchDataFromFirebase();

    }

    private void fetchDataFromFirebase() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("videoLinks");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                videoLinks.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String videoId = snapshot.getValue(String.class);
                    videoLinks.add(videoId);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}

