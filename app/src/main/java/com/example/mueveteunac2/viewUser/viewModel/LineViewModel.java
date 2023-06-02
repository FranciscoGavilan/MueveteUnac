package com.example.mueveteunac2.viewUser.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mueveteunac2.viewUser.model.Line;
import com.example.mueveteunac2.viewUser.repository.LineRepository;

import java.util.List;

public class LineViewModel extends ViewModel implements LineRepository.OnFireStoreDataAdded {

    MutableLiveData<List<Line>> mutableLiveData = new MutableLiveData<>();


    LineRepository lineRepo = new LineRepository(this);

    public LineViewModel(){
    }

    public void getLineFromFirestore() {
        lineRepo.getDataFromFireStore();
    }

    public LiveData<List<Line>> getLiveDatafromFireStore() {
        return mutableLiveData;
    }
    @Override
    public void lineDataAdded(List<Line> lineList) {
        mutableLiveData.setValue(lineList);
    }

    @Override
    public void OnError(Exception e) {

    }


}
