package com.example.mueveteunac2.viewDriver.view.viewPreviousTrip;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.mueveteunac2.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PreviousTripFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PreviousTripFragment extends Fragment {

    Button btnSubirRuta;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PreviousTripFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PreviousTripFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PreviousTripFragment newInstance(String param1, String param2) {
        PreviousTripFragment fragment = new PreviousTripFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_previous_trip, container, false);

        FirebaseFirestore db=FirebaseFirestore.getInstance();

        btnSubirRuta=view.findViewById(R.id.btnSubirRuta);

        btnSubirRuta.setOnClickListener(v -> {

            /*List<Map<String, Object>> datos = new ArrayList<>();

            Map<String, Object> documento1 = new HashMap<>();

            documento1.put("stopId", "S9999");
            documento1.put("stopName", "UNAC");
            documento1.put("stopPosition", new GeoPoint(-12.06215,-77.11726));
            documento1.put("stopOrder", 1);

            Map<String, Object> documento2 = new HashMap<>();

            documento2.put("stopId", "S0540");
            documento2.put("stopName", "Av. República de Venezuela");
            documento2.put("stopPosition", new GeoPoint(-12.06527,-77.11823));
            documento2.put("stopOrder", 2);

            Map<String, Object> documento3 = new HashMap<>();

            documento3.put("stopId", "S0539");
            documento3.put("stopName", "Paradero Corredor Verde Faucett");
            documento3.put("stopPosition", new GeoPoint(-12.06270,-77.09758));
            documento3.put("stopOrder", 3);

            Map<String, Object> documento4 = new HashMap<>();

            documento4.put("stopId", "S0538");
            documento4.put("stopName", "Av. Óscar R. Benavides");
            documento4.put("stopPosition", new GeoPoint(-12.05370,-77.09787));
            documento4.put("stopOrder", 4);

            Map<String, Object> documento5 = new HashMap<>();

            documento5.put("stopId", "S0537");
            documento5.put("stopName", "Av. Argentina");
            documento5.put("stopPosition", new GeoPoint(-12.04859,-77.09826));
            documento5.put("stopOrder", 5);

            Map<String, Object> documento6 = new HashMap<>();

            documento6.put("stopId", "S0536");
            documento6.put("stopName", "Paradero Hospital San Jose");
            documento6.put("stopPosition", new GeoPoint(-12.04364,-77.09872));
            documento6.put("stopOrder", 6);

            Map<String, Object> documento7 = new HashMap<>();

            documento7.put("stopId", "S0535");
            documento7.put("stopName", "Av. Morales Duárez");
            documento7.put("stopPosition", new GeoPoint(-12.04053,-77.09899));
            documento7.put("stopOrder", 7);

            Map<String, Object> documento8 = new HashMap<>();

            documento8.put("stopId", "S0534");
            documento8.put("stopName", "Paradero Setaca");
            documento8.put("stopPosition", new GeoPoint(-12.03762,-77.09917));
            documento8.put("stopOrder", 8);

            Map<String, Object> documento9 = new HashMap<>();

            documento9.put("stopId", "S0533");
            documento9.put("stopName", "Av. Quilca");
            documento9.put("stopPosition", new GeoPoint(-12.03673,-77.09914));
            documento9.put("stopOrder", 9);

            Map<String, Object> documento10 = new HashMap<>();

            documento10.put("stopId", "S0532");
            documento10.put("stopName", "Paradero Monark");
            documento10.put("stopPosition", new GeoPoint(-12.03319,-77.10026));
            documento10.put("stopOrder", 10);

            Map<String, Object> documento11 = new HashMap<>();

            documento11.put("stopId", "S0531");
            documento11.put("stopName", "Paradero Lima Cargo");
            documento11.put("stopPosition", new GeoPoint(-12.02863,-77.10254));
            documento11.put("stopOrder", 11);

            Map<String, Object> documento12 = new HashMap<>();

            documento12.put("stopId", "S0530");
            documento12.put("stopName", "Aeropuerto Jorge Chávez");
            documento12.put("stopPosition", new GeoPoint(-12.02358,-77.10522));
            documento12.put("stopOrder", 12);

            datos.add(documento1);
            datos.add(documento2);
            datos.add(documento3);
            datos.add(documento4);
            datos.add(documento5);
            datos.add(documento6);
            datos.add(documento7);
            datos.add(documento8);
            datos.add(documento9);
            datos.add(documento10);
            datos.add(documento11);
            datos.add(documento12);

            for (Map<String, Object> data : datos) {
                db.collection("Route").document("MpMOexKMHTDd9v3lDZ4o").collection("Stop")
                        .add(data)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(getContext(), "Se agrego correctamente", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "No se agrego el documento", Toast.LENGTH_SHORT).show();
                            }
                        });
            }*/
            });
        return view;
    }
}