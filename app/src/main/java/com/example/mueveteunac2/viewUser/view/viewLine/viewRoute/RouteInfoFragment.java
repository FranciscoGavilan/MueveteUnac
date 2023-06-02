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

import androidx.fragment.app.Fragment;
import com.example.mueveteunac2.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RouteInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RouteInfoFragment extends Fragment implements RouteStopFragment.OnFragmentInteractionListener{

    ImageButton btnSubir;
    Button btnCambiarSentido;
    private TextView edtverruta;
    private boolean boton_pulsado=true;

    VisualizarparaderosFragment visualizarparaderos;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "documentRoute";
    private static final String ARG_PARAM2 = "shift";
    private static final String ARG_PARAM3 = "idLine";
    private static final String ARG_PARAM4 = "nameLine";




    // TODO: Rename and change types of parameters
    private String documentRoute;
    private String shift;
    private String idLine;
    private String nameLine;



    public RouteInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    // TODO: Rename and change types and number of parameters
    public static RouteInfoFragment newInstance(String documentRoute,String shift,String idLine,String nameLine) {
        RouteInfoFragment fragment = new RouteInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, documentRoute);
        args.putString(ARG_PARAM2, shift);
        args.putString(ARG_PARAM3, idLine);
        args.putString(ARG_PARAM4, nameLine);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            documentRoute = getArguments().getString(ARG_PARAM1);
            shift = getArguments().getString(ARG_PARAM2);
            idLine = getArguments().getString(ARG_PARAM3);
            nameLine = getArguments().getString(ARG_PARAM4);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_route_info, container, false);

        edtverruta= view.findViewById(R.id.edtverruta);
        cargar_linea();

        btnCambiarSentido =view.findViewById(R.id.btnCambiarSentido);
        btnCambiarSentido.setOnClickListener(v2 -> {
            if(shift.equals("Mañana")){
               shift="Tarde";
            }else  if (shift.equals("Tarde")){
                shift="Mañana";
            }
            Intent intent = new Intent(getActivity().getApplicationContext(), RouteActivity.class);
            intent.putExtra("idLine",idLine);
            intent.putExtra("shift",shift);
            getActivity().getApplicationContext().startActivity(intent);
        });

        btnSubir = view.findViewById(R.id.btnSubir);
        btnSubir.setOnClickListener(v -> {
            Fragment paraderosFragment=new RouteStopFragment();
            if(boton_pulsado){
                boton_pulsado=false;
                btnSubir.setImageResource(R.drawable.baseline_keyboard_arrow_down_30);
                Bundle linea=new Bundle();
                linea.putString("documentRoute",documentRoute);
                linea.putString("idLine",idLine);
                linea.putString("nameLine",nameLine);
                linea.putString("shift",shift);
                paraderosFragment.setArguments(linea);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.todosparaderos,paraderosFragment).commit();
            }else{
                boton_pulsado=true;
                btnSubir.setImageResource(R.drawable.baseline_keyboard_arrow_up_30);
                getActivity().getSupportFragmentManager().beginTransaction().
                        remove(getActivity().getSupportFragmentManager().findFragmentById(R.id.todosparaderos)).commit();
            }
            visualizarparaderos.pulsarvista();
        });
        return view;
    }

    private void cargar_linea() {
        String idTurno="";
        if(shift.equals("Mañana")){
            idTurno="TM";
        }else if(shift.equals("Tarde")){
            idTurno="TT";
        }
        edtverruta.setText("RUTA - "+nameLine.toUpperCase()+" "+idTurno);
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
