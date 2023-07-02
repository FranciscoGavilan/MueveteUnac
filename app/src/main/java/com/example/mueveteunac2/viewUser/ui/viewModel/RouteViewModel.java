package com.example.mueveteunac2.viewUser.ui.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.mueveteunac2.viewUser.data.model.Route;
import com.example.mueveteunac2.viewUser.data.repository.RouteRepository;
import com.example.mueveteunac2.viewUser.domain.GetRouteUseCase;

public class RouteViewModel extends ViewModel implements GetRouteUseCase {

    MutableLiveData<Route> mutableLiveData = new MutableLiveData<>();

    RouteRepository routeRepo=new RouteRepository(this);

    public RouteViewModel(){

    }

    public void getRouteFromFirestore(String lineId,String turnId) {
        routeRepo.getRouteFromFireStore(lineId,turnId);
    }

    public LiveData<Route> getLiveDatafromFireStore() {
        return mutableLiveData;
    }
    @Override
    public void routeAdded(Route route) {
        mutableLiveData.setValue(route);
    }
}
