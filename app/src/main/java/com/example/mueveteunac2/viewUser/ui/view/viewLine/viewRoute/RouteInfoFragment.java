package com.example.mueveteunac2.viewUser.ui.view.viewLine.viewRoute;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mueveteunac2.R;
import com.example.mueveteunac2.viewUser.data.model.Route;
import com.example.mueveteunac2.viewUser.ui.viewModel.RouteViewModel;

public class RouteInfoFragment extends Fragment {

    private Button btnCambiarSentido;
    private TextView edtverruta;
    private RouteViewModel routeViewModel;
    private ViewGroup resizeHandle,routeFragment;
    private RelativeLayout rlCabecera;
    private ProgressBar circularProgressBar;
    private Integer viewHeightInfo;

    private static final String ARG_PARAM1 = "firstTurnId";
    private static final String ARG_PARAM2 = "secondTurnId";

    // TODO: Rename and change types of parameters
    private String firstTurnId, secondTurnId;

    public RouteInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    // TODO: Rename and change types and number of parameters
    public static RouteInfoFragment newInstance(String firstTurnId, String secondTurnId) {
        RouteInfoFragment fragment = new RouteInfoFragment();
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
        View view = inflater.inflate(R.layout.fragment_route_info, container, false);

        Fragment stopFragment = new RouteStopFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.todosparaderos, stopFragment).commit();

        edtverruta = view.findViewById(R.id.edtverruta);
        btnCambiarSentido = view.findViewById(R.id.btnCambiarSentido);
        circularProgressBar=view.findViewById(R.id.circularProgressBar);
        rlCabecera=view.findViewById(R.id.rlCabecera);
        resizeHandle = view.findViewById(R.id.resize_handle);
        resizeHandle.setVisibility(View.GONE);
        routeFragment = view.findViewById(R.id.routeFragment);

        ViewTreeObserver vto = rlCabecera.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // Se llama cuando la vista se ha medido y diseñado correctamente
                viewHeightInfo = rlCabecera.getHeight();
                ViewGroup.LayoutParams layoutParams = routeFragment.getLayoutParams();
                layoutParams.height = viewHeightInfo;
                routeFragment.setLayoutParams(layoutParams);
                // Si no necesitas seguir recibiendo notificaciones de cambios en la vista, puedes eliminar este listener
                rlCabecera.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });


        resizeHandle.setOnTouchListener(new View.OnTouchListener() {
            private float initialY;
            private int initialHeight;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int minHeight=v.getHeight();
                DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                int screenHeight = displayMetrics.heightPixels;
                int maxHeight = screenHeight - 120;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialY = event.getRawY();
                        initialHeight = routeFragment.getHeight();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float newY = event.getRawY();
                        int newHeight = (int) (initialHeight + (initialY-newY));
                        // Aplica restricciones si es necesario
                        newHeight = Math.max(newHeight, minHeight);
                        newHeight = Math.min(newHeight, maxHeight);
                        // Actualiza el tamaño del fragmento
                        ViewGroup.LayoutParams layoutParams = routeFragment.getLayoutParams();
                        layoutParams.height = newHeight;
                        routeFragment.setLayoutParams(layoutParams);
                }
                return true;
            }
        });
        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        routeViewModel = new ViewModelProvider(requireActivity()).get(RouteViewModel.class);
        routeViewModel.getLiveDatafromFireStore().observe(getViewLifecycleOwner(), new Observer<Route>() {
            @Override
            public void onChanged(Route route) {
                circularProgressBar.setVisibility(View.GONE);
                resizeHandle.setVisibility(View.VISIBLE);
                String routeSelected = route.getLineName().toUpperCase();
                edtverruta.setText(routeSelected);

                btnCambiarSentido.setOnClickListener(v2 -> {
                    String lineId = route.getLineId();
                    String turnId = route.getTurnId();
                    if (turnId.equals(firstTurnId)) {
                        firstTurnId = secondTurnId;
                        secondTurnId = turnId;
                    }
                    Intent intent = new Intent(getActivity(), RouteActivity.class);
                    intent.putExtra("lineId", lineId);
                    intent.putExtra("firstTurnId", firstTurnId);
                    intent.putExtra("secondTurnId", secondTurnId);
                    startActivity(intent);
                });
            }
        });
    }

    public void moveFragment() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int screenHeight = displayMetrics.heightPixels;
        int newHeight = (screenHeight - 120)/2;
        ViewGroup.LayoutParams layoutParamsInfo = routeFragment.getLayoutParams();
        layoutParamsInfo.height = newHeight;
        routeFragment.setLayoutParams(layoutParamsInfo);
    }

    public void onParentConfigurationChanged() {
        ViewGroup.LayoutParams layoutParamsInfo = routeFragment.getLayoutParams();
        layoutParamsInfo.height = viewHeightInfo;
        routeFragment.setLayoutParams(layoutParamsInfo);
    }
}
