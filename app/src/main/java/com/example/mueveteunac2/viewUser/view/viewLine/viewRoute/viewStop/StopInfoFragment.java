package com.example.mueveteunac2.viewUser.view.viewLine.viewRoute.viewStop;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.mueveteunac2.R;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class StopInfoFragment extends Fragment {

    ImageButton btnColocarparadero;
    private TextView nombreparadero,descripcion;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "idStop";
    private static final String ARG_PARAM2 = "nameStop";
    private static final String ARG_PARAM3 = "location";
    private static final String ARG_PARAM4 = "shift";
    private static final String ARG_PARAM5 = "idLine";


    // TODO: Rename and change types of parameters
    private String idStop,nameStop,location,shift,idLine;

    public StopInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    // TODO: Rename and change types and number of parameters
    public static StopInfoFragment newInstance(String idStop, String nameStop, String location, String shift, String idLine) {
        StopInfoFragment fragment = new StopInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, idStop);
        args.putString(ARG_PARAM2, nameStop);
        args.putString(ARG_PARAM3, location);
        args.putString(ARG_PARAM4, shift);
        args.putString(ARG_PARAM5, idLine);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idStop = getArguments().getString(ARG_PARAM1);
            nameStop = getArguments().getString(ARG_PARAM2);
            location = getArguments().getString(ARG_PARAM3);
            shift = getArguments().getString(ARG_PARAM4);
            idLine = getArguments().getString(ARG_PARAM5);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_stop_info, container, false);

        nombreparadero=view.findViewById(R.id.nombreparadero);
        descripcion=view.findViewById(R.id.descripcion);

        nombreparadero.setText(nameStop);
        descripcion.setText(location);

        return view;
    }


    public void onFragmentInteraction(Uri uri) {

    }
}
