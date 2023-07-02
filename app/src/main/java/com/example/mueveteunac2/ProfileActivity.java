package com.example.mueveteunac2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mueveteunac2.viewUser.data.model.Profile;
import com.example.mueveteunac2.viewUser.data.model.Route;
import com.example.mueveteunac2.viewUser.ui.view.UserActivity;
import com.example.mueveteunac2.viewUser.ui.viewModel.ProfileViewModel;
import com.example.mueveteunac2.viewUser.ui.viewModel.RouteViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    CircleImageView imgProfile;
    TextView tvName, tvEmail;
    Button btnCerrarSesion;
    ImageButton backProfile;
    ProfileViewModel profileViewModel;
    FirebaseUser currentUser=FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        imgProfile=findViewById(R.id.imgProfile);
        tvName=findViewById(R.id.tvName);
        tvEmail=findViewById(R.id.tvEmail);
        btnCerrarSesion=findViewById(R.id.btnCerrarSesion);
        backProfile=findViewById(R.id.backProfile);

        backProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), UserActivity.class);
                startActivity(intent);
            }
        });

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        String userId=currentUser.getUid();
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        profileViewModel.getProfileFromFirestore(userId);
        profileViewModel.getLiveDatafromFireStore().observe(this, new Observer<Profile>() {
            @Override
            public void onChanged(Profile profile) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Glide
                            .with(getApplicationContext())
                            .load(profile.getUserImage())
                            .placeholder(R.drawable.image_photo)
                            .into(imgProfile);
                }
                String name= profile.getUserName();
                tvName.setText(name);
                String email= profile.getUserEmail();
                tvEmail.setText(email);
            }
        });
    }
}