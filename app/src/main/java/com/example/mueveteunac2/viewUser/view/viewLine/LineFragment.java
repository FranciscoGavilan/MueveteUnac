package com.example.mueveteunac2.viewUser.view.viewLine;

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

import com.example.mueveteunac2.R;
import com.example.mueveteunac2.viewUser.adapter.LineAdapter;
import com.example.mueveteunac2.viewUser.model.Line;
import com.example.mueveteunac2.viewUser.viewModel.LineViewModel;

import java.util.List;

public class LineFragment extends Fragment {
    LineAdapter lineAdapter;
    RecyclerView recyclerViewLine;
    LineViewModel lineViewModel;

    public LineFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_line, container, false);

        recyclerViewLine=view.findViewById(R.id.recyclerView);

        lineAdapter=new LineAdapter();
        recyclerViewLine.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewLine.setAdapter(lineAdapter);

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
                lineAdapter.setLineList(getContext(),lines);
                lineAdapter.notifyDataSetChanged();
            }
        });
    }
}
