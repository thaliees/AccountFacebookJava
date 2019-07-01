package com.thaliees.accountfacebook;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class PrincipalActivity extends AppCompatActivity {
    private PrefManager prefManager;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        // Need to know if the user is already logged in
        prefManager = new PrefManager(getApplicationContext());
        if (prefManager.isLogin())
            initMainActivity();

        // Register a call
        callbackManager = CallbackManager.Factory.create();

        final LoginButton login = findViewById(R.id.loginButton);
        login.setLoginText("Facebook");
        // Indicate the permissions
        login.setPermissions("email", "public_profile");
        // Callback registration
        login.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // Save in PrefManager our session
                prefManager.setIsLogin(true);
                // Now, show MainActivity's Screen
                initMainActivity();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(PrincipalActivity.this, "Error: " + error.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initMainActivity(){
        // Launch intent
        Intent main = new Intent(PrincipalActivity.this, MainActivity.class);
        startActivity(main);
        finish();
    }
}
