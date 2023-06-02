package com.example.mueveteunac2.viewUser.view.viewLine.viewRoute.viewStop;

import android.content.Context;
import android.content.pm.PackageManager;

import android.net.Uri;
import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.mueveteunac2.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class StopMapFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap map;

    private OnFragmentInteractionListener mListener;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "nameStop";
    private static final String ARG_PARAM2 = "latitud";
    private static final String ARG_PARAM3 = "longitud";


    // TODO: Rename and change types of parameters
    private String nameStop;
    private Double latitud;
    private Double longitud;


    public StopMapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     * @return A new instance of fragment MostrarmapaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StopMapFragment newInstance(String nameStop, Double latitud, Double longitud) {
        StopMapFragment fragment = new StopMapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, nameStop);
        args.putDouble(ARG_PARAM2, latitud);
        args.putDouble(ARG_PARAM3, longitud);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            nameStop = getArguments().getString(ARG_PARAM1);
            latitud = getArguments().getDouble(ARG_PARAM2);
            longitud = getArguments().getDouble(ARG_PARAM3);

        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stop_map, container, false);

        SupportMapFragment map1Fragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.mapaparadero);
        map1Fragment.getMapAsync(this);

        return view;
    }



    public void OnButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    public void onAttach(Context context){
        super.onAttach(context);
        if (context instanceof StopMapFragment.OnFragmentInteractionListener){
            mListener=(StopMapFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()+"debe implementar OnFragment InteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener=null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        /*if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)) {

            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            }

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
            return;
        }*/

        //-12.0613052!4d-77.1172487

        LatLng paradero = new LatLng(latitud, longitud);
        map.addMarker(new MarkerOptions().position(paradero).title(nameStop));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(latitud, longitud)).zoom(17.5F).build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case 1:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission is granted. Continue the action or workflow
                    // in your app.
                } else {
                    // Explain to the user that the feature is unavailable because
                    // the features requires a permission that the user has denied.
                    // At the same time, respect the user's decision. Don't link to
                    // system settings in an effort to convince the user to change
                    // their decision.
                }
                return;
        }
        // Other 'case' lines to check for other
        // permissions this app might request.
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
