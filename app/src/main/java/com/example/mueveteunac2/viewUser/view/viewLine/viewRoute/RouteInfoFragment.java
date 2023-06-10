package com.example.mueveteunac2.viewUser.view.viewLine.viewRoute;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mueveteunac2.R;
import com.example.mueveteunac2.viewUser.model.Line;
import com.example.mueveteunac2.viewUser.model.Route;
import com.example.mueveteunac2.viewUser.view.viewLine.viewRoute.viewStop.StopInfoFragment;
import com.example.mueveteunac2.viewUser.viewModel.LineViewModel;
import com.example.mueveteunac2.viewUser.viewModel.RouteViewModel;

import java.util.List;

public class RouteInfoFragment extends Fragment implements RouteStopFragment.OnFragmentInteractionListener{

    ImageButton btnSubir;
    Button btnCambiarSentido;
    private TextView edtverruta;
    private boolean boton_pulsado=true;
    private RouteViewModel routeViewModel;

    VisualizarparaderosFragment visualizarparaderos;

    private static final String ARG_PARAM1 = "firstTurnId";
    private static final String ARG_PARAM2 = "secondTurnId";


    // TODO: Rename and change types of parameters
    private String firstTurnId,secondTurnId;

    public RouteInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    // TODO: Rename and change types and number of parameters
    public static StopInfoFragment newInstance(String firstTurnId, String secondTurnId) {
        StopInfoFragment fragment = new StopInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, firstTurnId);
        args.putString(ARG_PARAM2, secondTurnId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            firstTurnId = getArguments().getString(ARG_PARAM1);
            secondTurnId = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_route_info, container, false);

        edtverruta= view.findViewById(R.id.edtverruta);

        btnCambiarSentido =view.findViewById(R.id.btnCambiarSentido);

        btnSubir = view.findViewById(R.id.btnSubir);
        btnSubir.setOnClickListener(v -> {
            Fragment paraderosFragment=new RouteStopFragment();
            if(boton_pulsado){
                boton_pulsado=false;
                /*btnSubir.setImageResource(R.drawable.baseline_keyboard_arrow_down_30);*/
                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.todosparaderos,paraderosFragment).commit();
            }else{
                boton_pulsado=true;
                /*btnSubir.setImageResource(R.drawable.baseline_keyboard_arrow_up_30);*/
                getActivity().getSupportFragmentManager().beginTransaction().
                        remove(getActivity().getSupportFragmentManager().
                                findFragmentById(R.id.todosparaderos)).commit();
            }
            visualizarparaderos.pulsarvista();
        });
        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        routeViewModel = new ViewModelProvider(requireActivity()).get(RouteViewModel.class);
        routeViewModel.getLiveDatafromFireStore().observe(getViewLifecycleOwner(), new Observer<Route>() {
            @Override
            public void onChanged(Route route) {
                String routeSelected=route.getLineName().toUpperCase();
                edtverruta.setText(routeSelected);

                btnCambiarSentido.setOnClickListener(v2 -> {
                    String lineId=route.getLineId();
                    String turnId=route.getTunId();
                    if(turnId.equals(firstTurnId)){
                        firstTurnId=secondTurnId;
                        secondTurnId=turnId;
                    }
                    Intent intent = new Intent(getActivity().getApplicationContext(), RouteActivity.class);
                    intent.putExtra("lineId",lineId);
                    intent.putExtra("firstTurnId",secondTurnId);
                    intent.putExtra("secondTurnId",firstTurnId);
                    getActivity().getApplicationContext().startActivity(intent);
                });
            }
        });
    }

    public void onAttach(Context context){
        super.onAttach(context);
        if (context instanceof VisualizarparaderosFragment){
            visualizarparaderos=(VisualizarparaderosFragment) context;
        }
    }

     public void onFragmentInteraction(Uri uri) {

    }

    public interface VisualizarparaderosFragment{
        void pulsarvista();
    }
}
