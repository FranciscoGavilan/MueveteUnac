package com.example.mueveteunac2.viewUser.repository;

import androidx.annotation.Nullable;

import com.example.mueveteunac2.viewUser.model.Route;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RouteRepository {
    RouteRepository.OnFireStoreDataAdded fireStoreDataAdded;
    List<Route> routeList=new ArrayList<>();
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    Query routeRef = firestore.collection("Route");


    public RouteRepository(RouteRepository.OnFireStoreDataAdded fireStoreDataAdded) {
        this.fireStoreDataAdded = fireStoreDataAdded;
    }

    public void getDataFromFireStore(String lineId,String turnId) {

        routeRef.whereEqualTo("lineId",lineId).whereEqualTo("turnId",turnId).
                orderBy("stopOrder", Query.Direction.ASCENDING).
                addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value,
                                @Nullable FirebaseFirestoreException e) {

                if (e != null) {
                    fireStoreDataAdded.OnError(e);
                } else {
                    routeList.clear();
                    for (DocumentSnapshot doc : value.getDocuments()) {
                        Route route=doc.toObject(Route.class);
                        routeList.add(route);
                        fireStoreDataAdded.routeDataAdded(routeList);
                    }
                }
            }
        });
    }

    public interface OnFireStoreDataAdded {
        void routeDataAdded(List<Route> routeList);
        void OnError(Exception e);
    }
}
