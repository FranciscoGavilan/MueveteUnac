package com.example.mueveteunac2.viewUser.view.viewLine.viewRoute;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mueveteunac2.viewUser.adapter.LineAdapter;
import com.example.mueveteunac2.viewUser.adapter.StopAdapter;
import com.example.mueveteunac2.viewUser.model.Line;
import com.example.mueveteunac2.viewUser.model.Route;
import com.example.mueveteunac2.viewUser.model.Stop;
import com.example.mueveteunac2.R;
import com.example.mueveteunac2.viewUser.viewModel.RouteViewModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class RouteStopFragment extends Fragment {

    private StopAdapter stopAdapter;
    private RecyclerView recyclerViewStop;
    private RouteViewModel routeViewModel;

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
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
