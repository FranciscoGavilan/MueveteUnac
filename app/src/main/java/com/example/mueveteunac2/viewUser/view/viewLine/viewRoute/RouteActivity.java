package com.example.mueveteunac2.viewUser.view.viewLine.viewRoute;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.net.Uri;

import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.mueveteunac2.LoginActivity;
import com.example.mueveteunac2.R;
import com.example.mueveteunac2.viewUser.model.Route;
import com.example.mueveteunac2.viewUser.model.Stop;
import com.example.mueveteunac2.viewUser.view.UserActivity;
import com.example.mueveteunac2.viewUser.view.interfaces.MoveMapAndFragment;
import com.example.mueveteunac2.viewUser.viewModel.RouteViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import de.hdodenhof.circleimageview.CircleImageView;

public class RouteActivity extends AppCompatActivity implements MoveMapAndFragment {

    private CircleImageView image;
    private ImageButton back;
    private String lineId,firstTurnId,secondTurnId;
    private float tam1,tam2;
    private RouteViewModel routeViewModel;
    private TextView twRoute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        FirebaseUser currentUser=FirebaseAuth.getInstance().getCurrentUser();

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

        back= findViewById(R.id.back);

        back.setOnClickListener(v -> {
            Intent intent = new Intent(RouteActivity.this, UserActivity.class);
            startActivity(intent);
        });

        twRoute=findViewById(R.id.twRoute);

        lineId= getIntent().getExtras().getString("lineId");
        firstTurnId= getIntent().getExtras().getString("firstTurnId");
        secondTurnId= getIntent().getExtras().getString("secondTurnId");


        routeViewModel = new ViewModelProvider(this).get(RouteViewModel.class);
        routeViewModel.getRouteFromFirestore(lineId,firstTurnId);
        routeViewModel.getLiveDatafromFireStore().observe(this, new Observer<Route>() {
            @Override
            public void onChanged(Route route) {
                String routeSelected= route.getTurn();
                twRoute.setText("Turno "+routeSelected);
            }
        });

        Fragment map=new RouteMapFragment();
        Fragment info=new RouteInfoFragment();

        Bundle turns=new Bundle();
        turns.putString("firstTurnId",firstTurnId);
        turns.putString("secondTurnId",secondTurnId);
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,map).commit();

        getSupportFragmentManager().beginTransaction().replace(R.id.mostrarparaderos,info).commit();
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.inicio, popup.getMenu());
        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                // Respond to the action bar's Up/Home button
                case R.id.CerrarSesion:
                    FirebaseAuth.getInstance().signOut();
                    Intent intent1 = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent1);
                    break;
            }
            return false;
        });
        popup.show();
    }

    /*public void pulsarvista(Boolean stopSelected,Boolean isMapSeen) {
        tam1= ((LinearLayout.LayoutParams) RouteActivity.this.findViewById(R.id.linea1).getLayoutParams()).weight;
        tam2=((LinearLayout.LayoutParams) RouteActivity.this.findViewById(R.id.linea2).getLayoutParams()).weight;

        LinearLayout.LayoutParams p1= (LinearLayout.LayoutParams) RouteActivity.this.findViewById(R.id.linea1).getLayoutParams();
        LinearLayout.LayoutParams p2= (LinearLayout.LayoutParams) RouteActivity.this.findViewById(R.id.linea2).getLayoutParams();

        if(stopSelected==false){
            if(!isMapSeen){
                p1.weight = (float) 0.0;
                p2.weight = (float) 1.0;

            }else{
                p1.weight = (float) 0.8;
                p2.weight = (float) 0.2;
            }
        }else{
            if(isMapSeen){
                p1.weight = (float) 0.6;
                p2.weight = (float) 0.4;
            }else {
                p1.weight = (float) 0.0;
                p2.weight = (float) 1.0;
            }
        }

        RouteActivity.this.findViewById(R.id.linea1).setLayoutParams(p1);
        RouteActivity.this.findViewById(R.id.linea2).setLayoutParams(p2);
        Toast.makeText(this,stopSelected+" "+isMapSeen,Toast.LENGTH_SHORT).show();
    }*/

    @Override
    public void mapAndFragment(Stop stop) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        RouteMapFragment routeMapFragment=(RouteMapFragment) fragmentManager.findFragmentById(R.id.contenedor);
        RouteInfoFragment routeInfoFragment=(RouteInfoFragment) fragmentManager.findFragmentById(R.id.mostrarparaderos);

        routeMapFragment.moveToStop(stop);
        routeInfoFragment.moveFragment();
    }
}