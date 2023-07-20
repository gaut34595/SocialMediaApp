package com.example.socialmedia;

import static com.example.socialmedia.YouTubeUtils.extractVideoIdFromLink;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.socialmedia.databinding.ActivityAddVideoBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class AddVideoActivity extends AppCompatActivity {

    ActivityAddVideoBinding binding;
    private DatabaseReference databaseReference;

    FirebaseFirestore firestore;
    String vLink;
    FirebaseUser user;

    FirebaseAuth auth;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityAddVideoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firestore = FirebaseFirestore.getInstance();
        auth= FirebaseAuth.getInstance();
        user= auth.getCurrentUser();


        DocumentReference doc = FirebaseFirestore.getInstance().collection("Users").document(user.getEmail());
        doc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
              username  = documentSnapshot.getString("Username");
            }
        });
        binding.buttonAddVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vLink = binding.editTextVideoLink.getText().toString();
                saveVideoLinkToFirestore(username);
            }
        });
    }

    private void saveVideoLinkToFirestore(String username) {
        String videoId = YouTubeUtils.extractVideoIdFromLink(vLink);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        String time = formatter.format(date);

        if (videoId != null) {
            // Create a new VideoLink object with the videoId and username
            VideoLink videoLinkObject = new VideoLink(videoId, username, time);

            // Add the VideoLink object to the "videoLinks" collection in Firestore
            firestore.collection("videoLinks")
                    .add(videoLinkObject)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(AddVideoActivity.this, "Video link added!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AddVideoActivity.this,LoginDashboard.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddVideoActivity.this, "Failed to save video link: " + e.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

            // Clear the EditText after adding the link
            binding.editTextVideoLink.setText("");
        } else {
            Toast.makeText(AddVideoActivity.this, "Please enter a valid YouTube video link.", Toast.LENGTH_SHORT).show();
        }
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