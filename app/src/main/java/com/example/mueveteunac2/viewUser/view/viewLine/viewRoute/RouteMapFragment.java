package com.example.mueveteunac2.viewUser.view.viewLine.viewRoute;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mueveteunac2.R;
import com.example.mueveteunac2.viewUser.model.Route;
import com.example.mueveteunac2.viewUser.model.Stop;
import com.example.mueveteunac2.viewUser.viewModel.RouteViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;

public class RouteMapFragment extends Fragment implements OnMapReadyCallback{

    private GoogleMap map;
    private Double longitudOrigen,latitudOrigen,longitudFinal,latitudFinal;
    private Double latitudPosition,longitudPosition;
    private RouteViewModel routeViewModel;
    private FloatingActionButton btnLocation;
    private Marker markerStop;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_route_map, container, false);

        btnLocation=view.findViewById(R.id.btnLocation);

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMinZoomPreference(11.0f);
        map.setMaxZoomPreference(19.0f);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(-12.06215, -77.11726)).zoom(16).build();
        map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitudPosition, longitudPosition), 11.2f));
            }
        });

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
        );*/
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
                    map.addMarker(new MarkerOptions().position(busStop).
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

                latitudPosition = (latitudOrigen + latitudFinal) / 2;
                longitudPosition = (longitudOrigen + longitudFinal) / 2;

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

    public void moveToStop(Stop stop) {

        if(markerStop!=null){
            markerStop.remove();
        }

        Double stopLatitud = stop.getStopPosition().getLatitude();
        Double stopLongitud = stop.getStopPosition().getLongitude();

        LatLng stopPosition = new LatLng(stopLatitud, stopLongitud);

        markerStop=map.addMarker(new MarkerOptions().position(stopPosition).title(stop.getStopName()).
                icon(bitmapDescriptorFromVector(getActivity(),
                        R.drawable.baseline_location_on_48)));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(stopPosition).
                zoom(18).build();

        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}