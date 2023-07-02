package com.example.mueveteunac2.viewUser.ui.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mueveteunac2.viewUser.data.model.Stop;
import com.example.mueveteunac2.viewUser.data.repository.PointRepository;
import com.example.mueveteunac2.viewUser.data.model.PointResponse;
import com.example.mueveteunac2.viewUser.domain.GetPointsApiUseCase;

import java.util.List;

public class PointViewModel extends ViewModel implements GetPointsApiUseCase {

    MutableLiveData<PointResponse> mutableLiveData = new MutableLiveData<>();

    PointRepository pointRepository=new PointRepository(this);

    public PointViewModel() {

    }

    public void getPointFromApi(List<Stop> stopList) {
        pointRepository.getPoints(stopList);
    }

    public LiveData<PointResponse> getPointResponseLiveData() {
        return mutableLiveData;
    }

    @Override
    public void pointsAdded(PointResponse pointResponse) {
        mutableLiveData.setValue(pointResponse);
    }
}
