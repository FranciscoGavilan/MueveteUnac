package com.example.mueveteunac2.viewUser.view.viewLine.viewRoute;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mueveteunac2.viewUser.adapter.StopAdapter;
import com.example.mueveteunac2.viewUser.model.Stop;
import com.example.mueveteunac2.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class RouteStopFragment extends Fragment {

    StopAdapter stopAdapter;
    RecyclerView recyclerViewStop;
    ArrayList<Stop> listStop;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "documentRoute";
    private static final String ARG_PARAM2 = "idLine";
    private static final String ARG_PARAM3 = "shift";
    private static final String ARG_PARAM4 = "nameLine";



    // TODO: Rename and change types of parameters
    private String documentRoute;
    private String idLine;
    private String shift;
    private String nameLine;

    public RouteStopFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *@return A new instance of fragment RutasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RouteStopFragment newInstance(String documentRoute, String idLine, String shift, String nameLine) {
        RouteStopFragment fragment = new RouteStopFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, documentRoute);
        args.putString(ARG_PARAM2, idLine);
        args.putString(ARG_PARAM3, shift);
        args.putString(ARG_PARAM4, nameLine);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            documentRoute = getArguments().getString(ARG_PARAM1);
            idLine = getArguments().getString(ARG_PARAM2);
            shift = getArguments().getString(ARG_PARAM3);
            nameLine = getArguments().getString(ARG_PARAM4);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_route_stop, container, false);
        recyclerViewStop=view.findViewById(R.id.paradarorecyclerView);

        recyclerViewStop.setLayoutManager(new LinearLayoutManager(getContext()));

        listStop=new ArrayList<>();

        cargarparaderos();

        return view;
    }



    private void cargarparaderos() {
        FirebaseFirestore db= FirebaseFirestore.getInstance();
        Query query=db.collection("Route").
                document(documentRoute).
                collection("Stop").
                orderBy("orderStop", Query.Direction.ASCENDING);

        query.addSnapshotListener((value, error) -> {
            if (error != null) {
                error.printStackTrace();
                return;
            }
            for (QueryDocumentSnapshot doc : value) {
                if (doc.get("idStop") != null) {
                    String idStop=doc.getString("idStop");
                    String nameStop=doc.getString("nameStop");
                    listStop.add(new Stop(idStop,nameStop,shift,idLine,nameLine));
                }
            }
            stopAdapter=new StopAdapter(getContext(),listStop);
            recyclerViewStop.setAdapter(stopAdapter);
        });
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
