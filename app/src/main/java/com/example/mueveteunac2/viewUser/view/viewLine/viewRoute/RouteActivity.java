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

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.example.mueveteunac2.LoginActivity;
import com.example.mueveteunac2.R;
import com.example.mueveteunac2.viewUser.view.UserActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import de.hdodenhof.circleimageview.CircleImageView;

public class RouteActivity extends AppCompatActivity implements RouteMapFragment.OnFragmentInteractionListener,RouteInfoFragment.VisualizarparaderosFragment{

    private CircleImageView image;
    private ImageButton back;
    private String lineId,turnId;
    private float tam1,tam2;

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

        lineId= getIntent().getExtras().getString("lineId");
        turnId= getIntent().getExtras().getString("turnId");

        Bundle documentRoute=new Bundle();
        documentRoute.putString("documentRoute",document.getId());

        Fragment fragmento=new RouteMapFragment();
        fragmento.setArguments(documentRoute);
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,fragmento).commit();

        Bundle route=new Bundle();
        route.putString("documentRoute",document.getId());
        route.putString("shift",turnId);
        route.putString("idLine",lineId);
        route.putString("nameLine",document2.getString("nameLine"));

        Fragment fragmentparadero=new RouteInfoFragment();
        fragmentparadero.setArguments(route);
        getSupportFragmentManager().beginTransaction().replace(R.id.mostrarparaderos,fragmentparadero).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

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

    public void pulsarvista() {
        tam1= ((LinearLayout.LayoutParams) RouteActivity.this.findViewById(R.id.linea1).getLayoutParams()).weight;
        tam2=((LinearLayout.LayoutParams) RouteActivity.this.findViewById(R.id.linea2).getLayoutParams()).weight;

        float t1= (float) 0.7;
        float t2=(float) 0.3;
        LinearLayout.LayoutParams p1= (LinearLayout.LayoutParams) RouteActivity.this.findViewById(R.id.linea1).getLayoutParams();
        LinearLayout.LayoutParams p2= (LinearLayout.LayoutParams) RouteActivity.this.findViewById(R.id.linea2).getLayoutParams();

        if(tam1==t1 && tam2==t2){
            p1.weight = (float) 0.0;
            p2.weight = (float) 1.0;

        }else{
            p1.weight = (float) 0.7;
            p2.weight = (float) 0.3;

        }
        RouteActivity.this.findViewById(R.id.linea1).setLayoutParams(p1);
        RouteActivity.this.findViewById(R.id.linea2).setLayoutParams(p2);

    }

}