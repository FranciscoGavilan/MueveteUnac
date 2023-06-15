package com.example.mueveteunac2.viewUser.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mueveteunac2.viewUser.model.Stop;
import com.example.mueveteunac2.viewUser.repository.PointRepository;
import com.example.mueveteunac2.viewUser.view.response.PointResponse;

import java.util.List;

public class PointViewModel extends ViewModel implements PointRepository.OnPointAPIAdded{

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
    public void pointAdded(PointResponse pointResponse) {
        mutableLiveData.setValue(pointResponse);
    }
}
