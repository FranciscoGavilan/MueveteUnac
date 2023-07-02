package com.example.mueveteunac2.viewUser.ui.view.viewLine.viewRoute;

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

import com.example.mueveteunac2.viewUser.domain.MoveRouteMapInfoUseCase;
import com.example.mueveteunac2.viewUser.ui.adapter.StopAdapter;
import com.example.mueveteunac2.viewUser.data.model.Route;
import com.example.mueveteunac2.viewUser.data.model.Stop;
import com.example.mueveteunac2.R;
import com.example.mueveteunac2.viewUser.ui.viewModel.RouteViewModel;
import java.util.List;

public class RouteStopFragment extends Fragment {

    private StopAdapter stopAdapter;
    private RecyclerView recyclerViewStop;
    private RouteViewModel routeViewModel;
    private MoveRouteMapInfoUseCase moveRouteMapInfoUseCase;
    private LinearLayoutManager linearLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_route_stop, container, false);
        recyclerViewStop=view.findViewById(R.id.paradarorecyclerView);

        stopAdapter=new StopAdapter();
        linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerViewStop.setLayoutManager(linearLayoutManager);

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
            public void onStopClick(Stop stop,Integer position) {
                moveRouteMapInfoUseCase.moveMapInfo(stop);
                linearLayoutManager.scrollToPosition(position);
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
        if (context instanceof MoveRouteMapInfoUseCase) {
            moveRouteMapInfoUseCase = (MoveRouteMapInfoUseCase) context;
        }else {
            throw new RuntimeException(context.toString()
                    + " debe implementar OnDataPassListener");
        }
    }
}
