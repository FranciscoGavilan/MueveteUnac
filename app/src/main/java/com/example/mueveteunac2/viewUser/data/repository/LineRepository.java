package com.example.mueveteunac2.viewUser.data.repository;

import androidx.annotation.Nullable;

import com.example.mueveteunac2.viewUser.data.model.Line;
import com.example.mueveteunac2.viewUser.domain.GetLinesUseCase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class LineRepository{
    private GetLinesUseCase getLinesUseCase;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private Query lineRef = firestore.collection("Line").orderBy("lineId", Query.Direction.ASCENDING);

    public LineRepository(GetLinesUseCase getLinesUseCase){
        this.getLinesUseCase=getLinesUseCase;
    }

    public void getLinesFromFireStore() {
        lineRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value,
                                @Nullable FirebaseFirestoreException e) {

                if (e != null) {
                    e.printStackTrace();
                } else {

                    List<Line> lineList=new ArrayList<>();
                    for (DocumentSnapshot doc : value.getDocuments()) {
                        Line line=doc.toObject(Line.class);
                        lineList.add(line);
                    }
                    getLinesUseCase.linesAdded(lineList);
                }
            }
        });
    }
}
