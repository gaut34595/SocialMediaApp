package com.example.socialmedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.socialmedia.databinding.ActivityLoginDashboardBinding;

public class LoginDashboard extends AppCompatActivity {
    ActivityLoginDashboardBinding binding;
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
    }
}