package com.example.mueveteunac2.viewUser.ui.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mueveteunac2.viewUser.data.model.Line;
import com.example.mueveteunac2.viewUser.data.repository.LineRepository;
import com.example.mueveteunac2.viewUser.domain.useCase.GetLinesUseCase;

import java.util.List;

public class LineViewModel extends ViewModel implements GetLinesUseCase {

    private MutableLiveData<List<Line>> mutableLiveData = new MutableLiveData<>();
    private LineRepository lineRepo=new LineRepository(this);

    public LineViewModel(){
    }

    public void getLines() {
        lineRepo.getLinesFromFireStore();
    }

    public LiveData<List<Line>> getLiveLinesFromFireStore() {
        return mutableLiveData;
    }

    @Override
    public void linesAdded(List<Line> lineList) {
        mutableLiveData.setValue(lineList);
    }
}
