package com.example.mueveteunac2.viewUser.data.repository;

import androidx.annotation.Nullable;

import com.example.mueveteunac2.viewUser.data.model.Profile;
import com.example.mueveteunac2.viewUser.domain.GetProfileUseCase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class ProfileRepository {
    private GetProfileUseCase getProfileUseCase;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    public ProfileRepository(GetProfileUseCase getProfileUseCase){
        this.getProfileUseCase = getProfileUseCase;
    }

    public void getProfileFromFireStore(String userId) {
        firestore.collection("User").document(userId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot doc,
                                @Nullable FirebaseFirestoreException e) {

                if (e != null) {
                    e.printStackTrace();
                } else {
                        Profile profile =doc.toObject(Profile.class);
                        getProfileUseCase.userAdded(profile);
                }
            }
        });
    }
}
