package com.example.mueveteunac2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mueveteunac2.viewUser.ui.view.UserActivity;
import com.example.mueveteunac2.viewUser.ui.viewModel.LoginViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private SignInButton btnGoogle;
    private LoginViewModel loginViewModel;

    // [START declare_auth]
    // [END declare_auth]

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        loginViewModel.createGoogleClient(getApplicationContext());

        btnGoogle = findViewById(R.id.btnGoogle);
        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginViewModel.signInWithGoogle(LoginActivity.this);
            }
        });

        loginViewModel.getLoginSuccessLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loginSuccess) {
                if(loginSuccess){
                    loginViewModel.getCurrentUser().observe(LoginActivity.this, new Observer<FirebaseUser>() {
                        @Override
                        public void onChanged(FirebaseUser currentUser) {
                            updateUI(currentUser);
                        }
                    });
                }else {
                    Toast.makeText(LoginActivity.this,"Ocurrio un error al iniciar sesión",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void updateUI(FirebaseUser account){

        if(account != null){
            String userId=account.getUid();
            String userName=account.getDisplayName();
            String userEmail=account.getEmail();
            String userImage=account.getPhotoUrl().toString();
            Map<String, Object> userData = new HashMap<>();
            userData.put("userName", userName);
            userData.put("userEmail", userEmail);
            userData.put("userImage", userImage);
            // Otros datos de Google SignIn...

            // Guarda los datos del usuario en Firestore
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("User").document(userId).set(userData)
                    .addOnSuccessListener(aVoid -> {
                        // Los datos del usuario se guardaron correctamente
                    })
                    .addOnFailureListener(e -> {
                        // Hubo un error al guardar los datos del usuario
                    });
            goToMainApp();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            loginViewModel.handleGoogleSignInResult(requestCode,data);
    }

    private void goToMainApp(){
        Intent intent=new Intent(this, UserActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account!=null) {
            goToMainApp();
        }
    }
}
