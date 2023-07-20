package com.example.socialmedia;

import static com.example.socialmedia.YouTubeUtils.extractVideoIdFromLink;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.socialmedia.databinding.ActivityAddVideoBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AddVideoActivity extends AppCompatActivity {

    ActivityAddVideoBinding binding;
    private DatabaseReference databaseReference;
    String vLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityAddVideoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

       // FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        binding.buttonAddVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vLink = binding.editTextVideoLink.getText().toString();
                addVideoLinkToFirebase(vLink);
            }
        });
    }
    private void addVideoLinkToFirebase(String videoLink) {
        String videoId = YouTubeUtils.extractVideoIdFromLink(videoLink);

        if (videoId != null) {
            // Add the video ID to the Firebase Realtime Database
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("videoLinks");
            databaseReference.push().setValue(videoId);

            // Clear the EditText after adding the link
            binding.editTextVideoLink.setText("");

            Toast.makeText(this, "Video link added!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(AddVideoActivity.this,LoginDashboard.class));
        } else {
            Toast.makeText(this, "Please enter a valid YouTube video link.", Toast.LENGTH_SHORT).show();
        }
    }
}