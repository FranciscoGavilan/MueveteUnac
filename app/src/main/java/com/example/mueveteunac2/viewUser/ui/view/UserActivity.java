package com.example.mueveteunac2.viewUser.ui.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.viewpager.widget.ViewPager;


import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.mueveteunac2.LoginActivity;
import com.example.mueveteunac2.ProfileActivity;
import com.example.mueveteunac2.R;
import com.example.mueveteunac2.databinding.ActivityUserBinding;
import com.example.mueveteunac2.viewUser.ui.adapter.UserSectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserActivity extends AppCompatActivity {

    private ActivityUserBinding binding;
    CircleImageView image;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        UserSectionsPagerAdapter sectionsPagerAdapter = new UserSectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);

        FirebaseUser currentUser=FirebaseAuth.getInstance().getCurrentUser();

        image= findViewById(R.id.profile_image);

        if (currentUser!=null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Glide
                        .with(this)
                        .load(currentUser.getPhotoUrl().toString())
                        .placeholder(R.drawable.image_photo)
                        .into(image);
            }
        }
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });

    }

    /*public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.inicio, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    // Respond to the action bar's Up/Home button
                    case R.id.CerrarSesion:
                        FirebaseAuth.getInstance().signOut();
                        Intent intent1 = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent1);
                        break;
                }
                return false;
            }
        });
        popup.show();
    }*/

}