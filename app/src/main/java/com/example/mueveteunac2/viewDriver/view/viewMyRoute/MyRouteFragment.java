package com.example.mueveteunac2.viewDriver.view.viewMyRoute;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyRouteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyRouteFragment extends Fragment {

    Button btnSubirDocumento;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyRouteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyRouteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyRouteFragment newInstance(String param1, String param2) {
        MyRouteFragment fragment = new MyRouteFragment();
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
        View view=inflater.inflate(R.layout.fragment_my_route, container, false);

        FirebaseFirestore db=FirebaseFirestore.getInstance();

        btnSubirDocumento=view.findViewById(R.id.btnSubirDocumento);

        btnSubirDocumento.setOnClickListener(v -> {
            /*Map<String, Object> data = new HashMap<>();
            data.put("lineId", "L006");
            data.put("lineName", "Cono Norte Túpac Amaru 2");
            data.put("lineSymbol", "NT2");
            data.put("stopFirstId", "S9999");
            data.put("stopFirst", "UNAC");
            data.put("stopLastId", "S0501");
            data.put("stopLast", "Av. Túpac Amaru");
            data.put("route1stId", "R011");
            data.put("route1stSchedule", "14:30");
            data.put("route2ndId", "R012");
            data.put("route2ndSchedule", "22:20");
            data.put("driverId", "D006");
            data.put("driverName", "Raul");
            data.put("driverLastname", "Juipa Ortega");
            data.put("busId", "B003");
            data.put("busPlate", "EGE-755");


            db.collection("Line")
                    .add(data)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(getContext(),"Se agrego correctamente",Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(),"No se agrego el documento",Toast.LENGTH_SHORT).show();
                        }
                    });*/
        });
        return view;
    }
}