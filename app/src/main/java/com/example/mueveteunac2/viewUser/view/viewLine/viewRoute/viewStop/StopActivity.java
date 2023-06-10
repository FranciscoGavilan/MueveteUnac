package com.example.mueveteunac2.viewUser.view.viewLine.viewRoute.viewStop;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.net.Uri;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.mueveteunac2.viewUser.model.Stop;
import com.example.mueveteunac2.LoginActivity;
import com.example.mueveteunac2.viewUser.view.viewLine.viewRoute.RouteActivity;
import com.example.mueveteunac2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import de.hdodenhof.circleimageview.CircleImageView;

public class StopActivity extends AppCompatActivity implements StopMapFragment.OnFragmentInteractionListener{

    private Stop item;
    private CircleImageView image;
    private ImageButton back;
    private Button btnSeguirviendoparaderos;
    private String idLine,nameLine,shift,idStop,nameStop;
    private TextView edtseewhereabouts;
    Fragment paraderomapa=new StopMapFragment();
    Fragment detalleparadero=new StopInfoFragment();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop);

        /*item= (Stop) getIntent().getExtras().getSerializable("Stop");
        idLine=String.valueOf(item.getIdLine());
        nameLine= String.valueOf(item.getNameLine());
        shift= String.valueOf(item.getShift());
        idStop= String.valueOf(item.getIdStop());
        nameStop= String.valueOf(item.getNameStop());

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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), RouteActivity.class);
                    intent.putExtra("idLine",idLine);
                    intent.putExtra("shift",shift);
                    startActivity(intent);
            }
        });

        String idTurno="";
        if(shift.equals("Mañana")){
            idTurno="TM";
        } else if (shift.equals("Tarde")){
            idTurno="TT";
        }

        edtseewhereabouts= findViewById(R.id.edtseewhereabouts);
        edtseewhereabouts.setText("RUTA - "+nameLine.toUpperCase()+" "+idTurno);


        btnSeguirviendoparaderos = findViewById(R.id.btnSeguirviendoparaderos);
        btnSeguirviendoparaderos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RouteActivity.class);
                intent.putExtra("idLine",idLine);
                intent.putExtra("shift",shift);
                startActivity(intent);
            }
        });

        cargardatosparadero();
    }

    private void cargardatosparadero() {
        FirebaseFirestore db=FirebaseFirestore.getInstance();

        Query query=db.collection("Stop").whereEqualTo("idStop",idStop);

        query.addSnapshotListener((value, error) -> {
            if (error != null) {
                error.printStackTrace();
                return;
            }
            Double latitud=0.0;
            Double longitud=0.0;
            String location="";
            for (QueryDocumentSnapshot doc : value) {
                if (doc.get("idStop") != null) {
                    latitud= doc.getGeoPoint("position").getLatitude();
                    longitud=doc.getGeoPoint("position").getLongitude();
                    location= doc.getString("location");
                }
            }
            Bundle datosmapa=new Bundle();
            datosmapa.putString("nameStop",nameStop);
            datosmapa.putDouble("latitud",latitud);
            datosmapa.putDouble("longitud",longitud);

            paraderomapa.setArguments(datosmapa);
            getSupportFragmentManager().beginTransaction().replace(R.id.contenermapa,paraderomapa).commit();

            Bundle datosprds=new Bundle();
            datosprds.putString("idStop",idStop);
            datosprds.putString("nameStop",nameStop);
            datosprds.putString("location",location);
            datosprds.putString("shift",shift);
            datosprds.putString("idLine",idLine);

            detalleparadero.setArguments(datosprds);
            getSupportFragmentManager().beginTransaction().replace(R.id.verparadero,detalleparadero).commit();
        });
        /*requestQueue= Volley.newRequestQueue(getApplicationContext());
        String URL="http://192.168.0.22/mueveteunac/detalle_paradero.php?idlinea="+idlinea+"&idturno="+MOT+"&idparadero="+idparadero;
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int t=response.length();
                        try {
                            for(int i=0;i<t;i++){
                                JSONObject jsonObject = new JSONObject(response.get(i).toString());
                                Double Latitud= Double.valueOf(jsonObject.getDouble("Latitud"));
                                Double Longitud=Double.valueOf(jsonObject.getDouble("Longitud"));
                                String Descripcion=String.valueOf(jsonObject.getString("Descripción"));

                                Bundle datosmapa=new Bundle();
                                datosmapa.putString("nombre_paradero",nombre_paradero);
                                datosmapa.putDouble("Latitud",Latitud);
                                datosmapa.putDouble("Longitud",Longitud);

                                paraderomapa.setArguments(datosmapa);
                                getSupportFragmentManager().beginTransaction().replace(R.id.contenermapa,paraderomapa).commit();

                                Bundle datosprds=new Bundle();
                                datosprds.putString("idparadero",idparadero);
                                datosprds.putString("nombre_paradero",nombre_paradero);
                                datosprds.putString("Descripcion",Descripcion);
                                datosprds.putString("MOT",MOT);
                                datosprds.putSerializable("item",item);

                                detalleparadero.setArguments(datosprds);
                                getSupportFragmentManager().beginTransaction().replace(R.id.verparadero,detalleparadero).commit();
                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        requestQueue.add(jsonArrayRequest);*/
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

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


