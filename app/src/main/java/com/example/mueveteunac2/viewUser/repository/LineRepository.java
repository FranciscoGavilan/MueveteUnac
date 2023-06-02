package com.example.mueveteunac2.viewUser.repository;

import androidx.annotation.Nullable;

import com.example.mueveteunac2.viewUser.model.Line;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class LineRepository {

    boolean oj;
    OnFireStoreDataAdded fireStoreDataAdded;
    List<Line> lineList=new ArrayList<>();
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    Query lineRef = firestore.collection("Line").orderBy("idLine", Query.Direction.ASCENDING);

    public LineRepository(OnFireStoreDataAdded fireStoreDataAdded) {
        this.fireStoreDataAdded = fireStoreDataAdded;
    }

    public void getDataFromFireStore() {
        logicaLineExist(() -> {
            fireStoreDataAdded.lineDataAdded(lineList);
        });
    }

    public interface LineInterface{
        void lineCount();
    }

    public void logicaLineExist(final LineInterface lineInterface){
        lineRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value,
                                @Nullable FirebaseFirestoreException e) {

                if (e != null) {
                    fireStoreDataAdded.OnError(e);
                }else{
                    Integer countLine=0;
                    lineList.clear();
                    for (QueryDocumentSnapshot doc : value) {
                        if (doc.get("idLine") != null) {
                            countLine++;
                            List<String> line =new ArrayList<>();
                            line.add(doc.getString("idLine"));
                            line.add(doc.getString("symbol"));
                            line.add(doc.getString("nameLine"));

                            oj= countLine == value.size();

                            DocumentReference documentReference = firestore.collection("Line").document(doc.getId());
                            documentReference.collection("Stop").orderBy("idStop", Query.Direction.ASCENDING).addSnapshotListener((valueStop, error1) -> {
                                if (error1 != null) {
                                    error1.printStackTrace();
                                    return;
                                }
                                for (QueryDocumentSnapshot docStop : valueStop) {
                                    if (docStop.get("idStop") != null) {
                                        if(docStop.get("firstStop") != null){
                                            line.add(docStop.getString("firstStop"));
                                        }
                                        if(docStop.get("lastStop") != null){
                                            line.add(docStop.getString("lastStop"));
                                        }

                                    }
                                }

                                documentReference.collection("Route").orderBy("idRoute", Query.Direction.ASCENDING).addSnapshotListener((queryRoute, error2) -> {
                                    if (error2 != null) {
                                        error2.printStackTrace();
                                        return;
                                    }
                                    for (QueryDocumentSnapshot docRoute : queryRoute) {
                                        if (docRoute.get("idRoute") != null) {
                                            if(docRoute.get("fDepSchedule") != null){
                                                line.add(docRoute.getString("fDepSchedule"));
                                            }
                                            if(docRoute.get("sDepSchedule") != null){
                                                line.add(docRoute.getString("sDepSchedule"));
                                            }
                                        }

                                    }

                                    documentReference.collection("Driver").addSnapshotListener((queryDriv, e3) -> {
                                        if (e3 != null) {
                                            e3.printStackTrace();
                                            return;
                                        }
                                        for (QueryDocumentSnapshot docDriv : queryDriv) {
                                            if (docDriv.get("idDriver") != null) {
                                                line.add(docDriv.getString("firstName"));
                                                line.add(docDriv.getString("firstSurname"));
                                                line.add(docDriv.getString("secondSurname"));
                                            }
                                        }

                                        documentReference.collection("Bus").addSnapshotListener((queryBus, e4) -> {
                                            if (e4 != null) {
                                                e4.printStackTrace();
                                                return;
                                            }
                                            for (QueryDocumentSnapshot docBus :queryBus) {
                                                if (docBus.get("idBus") != null) {
                                                    line.add(docBus.getString("plate"));
                                                }
                                            }

                                            lineList.add(new Line(line.get(0), line.get(1), line.get(2),
                                                    line.get(3), line.get(4), line.get(5), line.get(6),
                                                    line.get(7), line.get(8), line.get(9), line.get(10)));
                                            if(oj){
                                                lineInterface.lineCount(); //aquí ya almacenas el valor para luego poder utilizarlo
                                            }
                                        });
                                    });

                                });
                            });


                        }
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
