package com.example.mueveteunac2.viewUser.view.viewLine.viewRoute;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mueveteunac2.viewUser.adapter.StopAdapter;
import com.example.mueveteunac2.viewUser.model.Route;
import com.example.mueveteunac2.viewUser.model.Stop;
import com.example.mueveteunac2.R;
import com.example.mueveteunac2.viewUser.view.interfaces.MoveMapAndFragment;
import com.example.mueveteunac2.viewUser.viewModel.RouteViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class RouteStopFragment extends Fragment {

    private StopAdapter stopAdapter;
    private RecyclerView recyclerViewStop;
    private RouteViewModel routeViewModel;
    private MoveMapAndFragment moveMapAndFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_route_stop, container, false);
        recyclerViewStop=view.findViewById(R.id.paradarorecyclerView);

        stopAdapter=new StopAdapter();
        recyclerViewStop.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        routeViewModel = new ViewModelProvider(requireActivity()).get(RouteViewModel.class);
        routeViewModel.getLiveDatafromFireStore().observe(getViewLifecycleOwner(), new Observer<Route>() {
            @Override
            public void onChanged(Route route) {
                List<Stop> stops=route.getStopList();
                stopAdapter.setStopList(getContext(),stops);
                recyclerViewStop.setAdapter(stopAdapter);
            }
        });

        stopAdapter.setOnStopClickListener(new StopAdapter.OnStopClickListener(){
            @Override
            public void onStopClick(Stop stop) {
                moveMapAndFragment.mapAndFragment(stop);
            }
        });

        stopAdapter.setOnButtonStopClickListener(new StopAdapter.OnButtonStopClickListener() {
            @Override
            public void onButtonStopClick(Stop stop) {

            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        // Verificamos que la actividad contenedora implemente la interfaz
        if (context instanceof MoveMapAndFragment) {
            moveMapAndFragment = (MoveMapAndFragment) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " debe implementar OnDataPassListener");
        }
    }
}
