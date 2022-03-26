package com.example.plantreapp.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.plantreapp.MainActivity;
import com.example.plantreapp.R;
import com.example.plantreapp.api.APIClient;
import com.example.plantreapp.onBoarding.OnBoardingActivity;
import com.google.android.material.snackbar.Snackbar;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;

public class LoginActivity extends AppCompatActivity {
    private APIClient apiClient;
    private TextView textViewEmail;
    private TextView textViewPassword;
    private Button buttonLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        APIClient.Companion.invoke(getApplicationContext());

        apiClient = new APIClient(this);
        setContentView(R.layout.activity_login);

        textViewEmail = findViewById(R.id.editTextEmailAddress);
        textViewPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apiClient.loginUser(textViewEmail.getText().toString(), textViewPassword.getText().toString(), new Continuation<Boolean>() {
                    @NonNull
                    @Override
                    public CoroutineContext getContext() {
                        return EmptyCoroutineContext.INSTANCE;
                    }

                    @Override
                    public void resumeWith(@NonNull Object o) {
                        boolean loggedIn = (boolean) o;

                        if (!loggedIn) {

                        } else {
                            Intent intent = new Intent(LoginActivity.this, OnBoardingActivity.class);
                            startActivity(intent);
                        }
                    }
                });
            }
        });
    }

}
