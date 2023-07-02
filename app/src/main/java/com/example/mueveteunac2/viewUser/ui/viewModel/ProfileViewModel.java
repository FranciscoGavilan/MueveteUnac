package com.example.mueveteunac2.viewUser.ui.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mueveteunac2.viewUser.data.model.Profile;
import com.example.mueveteunac2.viewUser.data.model.Route;
import com.example.mueveteunac2.viewUser.data.repository.ProfileRepository;
import com.example.mueveteunac2.viewUser.data.repository.RouteRepository;
import com.example.mueveteunac2.viewUser.domain.GetProfileUseCase;

public class ProfileViewModel extends ViewModel implements GetProfileUseCase {
    MutableLiveData<Profile> profileMutableLiveData = new MutableLiveData<>();

    ProfileRepository profileRepo=new ProfileRepository(this);

    public ProfileViewModel(){

    }

    public void getProfileFromFirestore(String userId) {
        profileRepo.getProfileFromFireStore(userId);
    }

    public LiveData<Profile> getLiveDatafromFireStore() {
        return profileMutableLiveData;
    }
    @Override
    public void userAdded(Profile profile) {
        profileMutableLiveData.setValue(profile);
    }

}
