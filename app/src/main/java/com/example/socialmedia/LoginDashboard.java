package com.example.socialmedia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.socialmedia.databinding.ActivityLoginDashboardBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class LoginDashboard extends AppCompatActivity {
    ActivityLoginDashboardBinding binding;
    DatabaseReference databaseReference;

    private VideoAdapter adapter;
    private VideoLinkAdapter adap;
    private ArrayList<String> videoLinks;
    private ArrayList<VideoLink> videoLinkss;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityLoginDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firestore= FirebaseFirestore.getInstance();

        binding.fabAddVideoLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginDashboard.this,AddVideoActivity.class));
            }
        });
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        videoLinks = new ArrayList<>();
        videoLinkss = new ArrayList<>();
//        adapter = new VideoAdapter(videoLinks);
        adap = new VideoLinkAdapter(videoLinkss);

        binding.recyclerView.setAdapter(adap);

//        fetchDataFromFirebase();
        fetchVideoLinksFromFirestore();

    }

    private void fetchVideoLinksFromFirestore() {

        firestore.collection("videoLinks")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            videoLinkss.clear();
                            for (DocumentSnapshot document : task.getResult()) {
                                String videoId = document.getString("videoId");
                                String username = document.getString("userName");
                                String timestamp = document.getString("timeStamp");
                                VideoLink videoLink = new VideoLink(videoId, username, timestamp);
                                videoLinkss.add(videoLink);
                            }
                            adap.notifyDataSetChanged();
                        } else {
                            Toast.makeText(LoginDashboard.this, "Failed to fetch video links", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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

