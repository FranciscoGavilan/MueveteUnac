package com.example.mueveteunac2.viewUser.repository;

import androidx.annotation.Nullable;

import com.example.mueveteunac2.viewUser.model.Line;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class LineRepository {

    OnFireStoreDataAdded fireStoreDataAdded;
    List<Line> lineList=new ArrayList<>();
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    Query lineRef = firestore.collection("Line").orderBy("lineId", Query.Direction.ASCENDING);

    public LineRepository(OnFireStoreDataAdded fireStoreDataAdded) {
        this.fireStoreDataAdded = fireStoreDataAdded;
    }

    public void getDataFromFireStore() {
        lineRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value,
                                @Nullable FirebaseFirestoreException e) {

                if (e != null) {
                    fireStoreDataAdded.OnError(e);
                } else {
                    lineList.clear();
                    for (DocumentSnapshot doc : value.getDocuments()) {
                        Line line=doc.toObject(Line.class);
                        lineList.add(line);
                        fireStoreDataAdded.lineDataAdded(lineList);
                    }
                }
            }
        });
    }

    public interface OnFireStoreDataAdded {
        void lineDataAdded(List<Line> lineList);
        void OnError(Exception e);
    }
}
