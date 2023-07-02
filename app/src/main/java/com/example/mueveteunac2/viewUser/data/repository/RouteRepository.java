package com.example.mueveteunac2.viewUser.data.repository;

import androidx.annotation.Nullable;

import com.example.mueveteunac2.viewUser.data.model.Route;
import com.example.mueveteunac2.viewUser.data.model.Stop;
import com.example.mueveteunac2.viewUser.domain.GetRouteUseCase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RouteRepository {
    private GetRouteUseCase getRouteUseCase;
    private List<Stop> stopList=new ArrayList<>();
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private Query routeRef = firestore.collection("Route");


    public RouteRepository(GetRouteUseCase getRouteUseCase) {
        this.getRouteUseCase = getRouteUseCase;
    }

    public void getRouteFromFireStore(String lineId,String turnId) {

        routeRef.whereEqualTo("lineId",lineId).whereEqualTo("turnId",turnId).
                addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value,
                                @Nullable FirebaseFirestoreException e) {

                if (e != null) {
                    e.printStackTrace();
                } else {
                    for (DocumentSnapshot doc : value.getDocuments()) {
                        Route route=doc.toObject(Route.class);

                        firestore.collection("Route").
                                document(doc.getId()).
                                collection("Stop").
                                orderBy("stopOrder", Query.Direction.ASCENDING).
                                addSnapshotListener(new EventListener<QuerySnapshot>() {
                                    @Override
                                    public void onEvent(@Nullable QuerySnapshot value,
                                                        @Nullable FirebaseFirestoreException e) {

                                        if (e != null) {
                                            e.printStackTrace();
                                        } else {
                                            stopList.clear();
                                            for (DocumentSnapshot doc : value.getDocuments()) {
                                                Stop stop=doc.toObject(Stop.class);
                                                stopList.add(stop);
                                            }
                                            route.setStopList(stopList);
                                            getRouteUseCase.routeAdded(route);
                                        }
                                    }
                                });
                    }
                }
            }
        });
    }
}
