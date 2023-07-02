package com.example.mueveteunac2.viewUser.ui.viewModel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mueveteunac2.LoginActivity;
import com.example.mueveteunac2.R;
import com.example.mueveteunac2.viewUser.ui.view.UserActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginViewModel extends ViewModel {
    private FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    private GoogleSignInClient googleSignInClient;
    private MutableLiveData<Boolean> loginSuccessLiveData=new MutableLiveData<>();
    private MutableLiveData<FirebaseUser> userMutableLiveData=new MutableLiveData<>();
    private static final int RC_SIGN_IN = 1;

    public LoginViewModel() {
    }

    public void createGoogleClient(Context context) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(context, gso);
    }

    public void signInWithGoogle(Activity activity) {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        activity.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void handleGoogleSignInResult(int requestCode,Intent data) {
        if(requestCode==RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                loginSuccessLiveData.setValue(false);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        loginSuccessLiveData.setValue(true);
                        userMutableLiveData.setValue(firebaseAuth.getCurrentUser());
                    } else {
                        loginSuccessLiveData.setValue(false);
                    }
                });
    }

    public LiveData<Boolean> getLoginSuccessLiveData() {
        return loginSuccessLiveData;
    }

    public LiveData<FirebaseUser> getCurrentUser() {
        return userMutableLiveData;
    }

    public void signOut(Context context) {
        // Cerrar sesión de Firebase
        FirebaseAuth.getInstance().signOut();

        // Cerrar sesión de Google
        GoogleSignIn.getClient(context, GoogleSignInOptions.DEFAULT_SIGN_IN).signOut()
                .addOnCompleteListener(task -> {
                    Intent intent = new Intent(context, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                });
    }

}
