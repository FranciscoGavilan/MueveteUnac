package com.example.mueveteunac2.viewUser.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.mueveteunac2.viewUser.model.Route;
import com.example.mueveteunac2.viewUser.repository.RouteRepository;

public class RouteViewModel extends ViewModel implements RouteRepository.OnFireStoreDataAdded{

    MutableLiveData<Route> mutableLiveData = new MutableLiveData<>();

    RouteRepository routeRepo=new RouteRepository(this);

    public RouteViewModel(){

    }

    public void getRouteFromFirestore(String lineId,String turnId) {
        routeRepo.getDataFromFireStore(lineId,turnId);
    }

    public LiveData<Route> getLiveDatafromFireStore() {
        return mutableLiveData;
    }
    @Override
    public void routeDataAdded(Route route) {
        mutableLiveData.setValue(route);
    }

    @Override
    public void OnError(Exception e) {

    }
}
