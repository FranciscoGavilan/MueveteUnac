package com.example.mueveteunac2.viewDriver.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.mueveteunac2.LoginActivity;
import com.example.mueveteunac2.R;
import com.example.mueveteunac2.databinding.ActivityDriverBinding;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.example.mueveteunac2.viewDriver.view.ui.main.SectionsPagerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;

public class DriverActivity extends AppCompatActivity {

    private ActivityDriverBinding binding;
    CircleImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDriverBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);

        FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();

        image= findViewById(R.id.profile_image);

        if (currentUser!=null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Glide
                        .with(this)
                        .load(currentUser.getPhotoUrl().toString())
                        .placeholder(R.drawable.ic_launcher_background)
                        .into(image);
            }
        }

    }

    public void showPopup(View v) {
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
    }
}