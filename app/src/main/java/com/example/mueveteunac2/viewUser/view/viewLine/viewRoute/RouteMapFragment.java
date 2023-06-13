package com.example.mueveteunac2.viewUser.view.viewLine.viewRoute;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mueveteunac2.R;
import com.example.mueveteunac2.viewUser.model.Route;
import com.example.mueveteunac2.viewUser.model.Stop;
import com.example.mueveteunac2.viewUser.view.interfaces.MoveMapAndFragment;
import com.example.mueveteunac2.viewUser.viewModel.RouteViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;

public class RouteMapFragment extends Fragment implements OnMapReadyCallback{

    private GoogleMap map;
    private Double longitudOrigen,latitudOrigen,longitudFinal,latitudFinal;
    private RouteViewModel routeViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_route_map, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(-12.06215, -77.11726)).zoom(16).build();
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
                    map.addMarker(new MarkerOptions().position(busStop).title(stop.getStopName()).
                            icon(bitmapDescriptorFromVector(getActivity(),
                                    R.drawable.baseline_point_map)));

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
                        .target(new LatLng(latitudPosition, longitudPosition)).
                        zoom(11.2F).build();
                map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
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

    public void moveToStop(Stop stop) {
        Double stopLatitud = stop.getStopPosition().getLatitude();
        Double stopLongitud = stop.getStopPosition().getLongitude();

        LatLng stopPosition = new LatLng(stopLatitud, stopLongitud);

        /*map.addMarker(new MarkerOptions().position(stopPosition).title(stop.getStopName()).
                icon(bitmapDescriptorFromVector(getActivity(),
                        R.drawable.baseline_point_map)));*/

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(stopPosition).
                zoom(18).build();

        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}