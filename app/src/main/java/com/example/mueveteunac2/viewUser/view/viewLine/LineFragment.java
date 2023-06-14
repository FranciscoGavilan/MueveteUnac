package com.example.mueveteunac2.viewUser.view.viewLine;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mueveteunac2.R;
import com.example.mueveteunac2.viewUser.adapter.LineAdapter;
import com.example.mueveteunac2.viewUser.model.Line;
import com.example.mueveteunac2.viewUser.view.viewLine.viewRoute.RouteActivity;
import com.example.mueveteunac2.viewUser.viewModel.LineViewModel;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

public class LineFragment extends Fragment{
    private LineAdapter lineAdapter;
    private RecyclerView recyclerViewLine;
    private LineViewModel lineViewModel;
    private LinearLayout viewLineLoading;

    public LineFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_line, container, false);

        recyclerViewLine=view.findViewById(R.id.recyclerView);
        viewLineLoading=view.findViewById(R.id.viewLineLoading);

        lineAdapter=new LineAdapter();
        recyclerViewLine.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        lineViewModel = new ViewModelProvider(getActivity()).get(LineViewModel.class);
        lineViewModel.getLineFromFirestore();
        lineViewModel.getLiveDatafromFireStore().observe(getViewLifecycleOwner(), new Observer<List<Line>>() {
            @Override
            public void onChanged(List<Line> lines) {
                viewLineLoading.setVisibility(View.GONE);
                lineAdapter.setLineList(getContext(),lines);
                recyclerViewLine.setAdapter(lineAdapter);
            }
        });

        lineAdapter.setOnLineClickListener(new LineAdapter.OnLineClickListener(){
            @Override
            public void onLineClick(Line line) {
                String lineId=line.getLineId();
                String route1stTurnId=line.getRoute1stTurnId();
                String route2ndTurnId=line.getRoute2ndTurnId();

                Intent intent = new Intent(getActivity(), RouteActivity.class);
                intent.putExtra("lineId",lineId);
                intent.putExtra("firstTurnId",route1stTurnId);
                intent.putExtra("secondTurnId",route2ndTurnId);
                startActivity(intent);
            }
        });

    }
}
