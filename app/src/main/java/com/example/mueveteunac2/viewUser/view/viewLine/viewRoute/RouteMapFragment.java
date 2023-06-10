package com.example.mueveteunac2.viewUser.view.viewLine.viewRoute;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mueveteunac2.R;
import com.example.mueveteunac2.viewUser.model.Route;
import com.example.mueveteunac2.viewUser.model.Stop;
import com.example.mueveteunac2.viewUser.viewModel.RouteViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RouteMapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RouteMapFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap map;
    JSONObject jso;
    Double longitudOrigen,latitudOrigen,longitudFinal,latitudFinal;

    private RouteViewModel routeViewModel;

    private OnFragmentInteractionListener mListener;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "documentRoute";


    // TODO: Rename and change types of parameters
    private String documentRoute;


    public RouteMapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     * @return A new instance of fragment MostrarmapaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RouteMapFragment newInstance(String documentLine) {
        RouteMapFragment fragment = new RouteMapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, documentLine);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            documentRoute = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_route_map, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;
    }

    public void OnButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    public void onAttach(Context context){
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener){
            mListener=(OnFragmentInteractionListener) context;
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
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(-12.06215, -77.11726)).zoom(18).build();
        map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        /*String url = "https://maps.googleapis.com/maps/api/directions/json?origin=" + Geopoint_Lat.get(i-1) + "," + Geopoint_Long.get(i-1) +
                "&destination=" + Geopoint_Lat.get(i) + "," + Geopoint_Long.get(i) + "&key=AIzaSyAR8NVCtaWJeA9PPPZFDLcWJczug-xZ5GQ";

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            try {
                jso = new JSONObject(response);
                trazarruta(jso);
                Log.i("jsonRuta", "" + response);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error1 -> {

        });/*

        //-12.0613052!4d-77.1172487
        /*String idlinea=String.valueOf(itemdetail);
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String URL1 = "http://192.168.0.22/mueveteunac/visualizar_latylong.php?idlinea=" +idlinea+"&idturno="+MOT;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL1,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int t = response.length();
                        try {
                            for (int i = 0; i < t; i++) {
                                JSONObject jsonObject = new JSONObject(response.get(i).toString());
                                if (i == 0) {
                                    latitudOrigen = Double.valueOf(jsonObject.getDouble("Latitud"));
                                    longitudOrigen = Double.valueOf(jsonObject.getDouble("Longitud"));
                                } else if (i == 1) {
                                    latitudFinal = Double.valueOf(jsonObject.getDouble("Latitud"));
                                    longitudFinal = Double.valueOf(jsonObject.getDouble("Longitud"));
                                }

                            }
                            LatLng Origen = new LatLng(latitudOrigen, longitudOrigen);
                            map.addMarker(new MarkerOptions().position(Origen).title("Paredero Inicial"));

                            LatLng Final = new LatLng(latitudFinal, longitudFinal);
                            map.addMarker(new MarkerOptions().position(Final).title("Paredero Final"));

                            Double latitud = (latitudOrigen + latitudFinal) / 2;
                            Double longitud = (longitudOrigen + longitudFinal) / 2;
                            CameraPosition cameraPosition = new CameraPosition.Builder()
                                    .target(new LatLng(latitud, longitud)).zoom(12).build();
                            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                            String url = "https://maps.googleapis.com/maps/api/directions/json?origin=" + latitudOrigen + "," + longitudOrigen +
                                    "&destination=" + latitudFinal + "," + longitudFinal + "&key=AIzaSyAR8NVCtaWJeA9PPPZFDLcWJczug-xZ5GQ";

                            RequestQueue queue = Volley.newRequestQueue(getActivity());
                            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        jso = new JSONObject(response);
                                        trazarruta(jso);
                                        Log.i("jsonRuta", "" + response);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            });
                            queue.add(stringRequest);
                        } catch (JSONException e) {
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

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        routeViewModel = new ViewModelProvider(requireActivity()).get(RouteViewModel.class);
        routeViewModel.getLiveDatafromFireStore().observe(getViewLifecycleOwner(), new Observer<Route>() {
            @Override
            public void onChanged(Route route) {
                List<Stop> stopList=route.getStopList();
                Integer tamaño=stopList.size();
                Double latitud,longitud;
                for (Stop stop: stopList) {
                    latitud=stop.getStopPosition().getLatitude();
                    longitud=stop.getStopPosition().getLongitude();

                    LatLng busStop = new LatLng(latitud, longitud);
                    map.addMarker(new MarkerOptions().position(busStop).title(stop.getStopName()));

                    if(stop.getStopOrder()==1){
                        latitudOrigen=stop.getStopPosition().getLatitude();
                        longitudOrigen=stop.getStopPosition().getLongitude();
                    } else if (stop.getStopOrder()==tamaño) {
                        latitudFinal=stop.getStopPosition().getLatitude();
                        longitudFinal=stop.getStopPosition().getLongitude();
                    }
                }

                Double latitudPosition = (latitudOrigen + latitudFinal) / 2;
                Double longitudPosition = (longitudOrigen + longitudFinal) / 2;

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(latitudPosition, longitudPosition)).zoom(11.2F).build();
                map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });

    }

    private void trazarruta(JSONObject jso) {
        JSONArray jRoutes;
        JSONArray jLegs;
        JSONArray jSteps;

        try {
            jRoutes=jso.getJSONArray("routes");
            for (int i=0; i<jRoutes.length();i++){
                jLegs=((JSONObject)(jRoutes.get(i))).getJSONArray("legs");

                for (int j=0; j<jLegs.length();j++){
                    jSteps=((JSONObject)(jLegs.get(i))).getJSONArray("steps");

                    for (int k=0; k<jSteps.length();k++){
                        String polyline=""+((JSONObject)((JSONObject)jSteps.get(k)).get("polyline")).get("points");
                        Log.i("end",""+polyline);
                        List<LatLng> list= PolyUtil.decode(polyline);
                        map.addPolyline(new PolylineOptions().addAll(list).color(Color.BLUE).width(10));
                    }
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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