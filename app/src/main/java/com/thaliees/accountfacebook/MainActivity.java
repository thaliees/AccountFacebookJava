package com.thaliees.accountfacebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView displayName, emailID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.image_view);
        displayName = findViewById(R.id.display_name);
        emailID = findViewById(R.id.email);

        if (AccessToken.getCurrentAccessToken() != null)
            useLoginInformation(AccessToken.getCurrentAccessToken());
        else {
            AccessToken.refreshCurrentAccessTokenAsync();
            useLoginInformation(AccessToken.getCurrentAccessToken());
        }

        final Button login = findViewById(R.id.logoutButton);
        login.setOnClickListener(logout);
    }

    private View.OnClickListener logout = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LoginManager.getInstance().logOut();
            initPrincipalActivity();
        }
    };

    private void initPrincipalActivity(){
        PrefManager prefManager = new PrefManager(getApplicationContext());
        prefManager.setIsLogin(false);

        Intent main = new Intent(MainActivity.this, PrincipalActivity.class);
        startActivity(main);
        finish();
    }

    private void useLoginInformation(AccessToken accessToken) {
        // Creating the GraphRequest to fetch user details
        // 1st Param - AccessToken
        // 2nd Param - Callback (which will be invoked once the request is successful)
        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            //OnCompleted is invoked once the GraphRequest is successful
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String name = object.getString("name");
                    String email = object.getString("email");
                    String image = object.getJSONObject("picture").getJSONObject("data").getString("url");
                    // Set the profile photo
                    // Option 1: Using AsyncTask
                    //new DownloadPhoto(imageView).execute(image);
                    // Option 2: Using Picasso
                    // Form: Squared
                    //Picasso.get().load(image).into(imageView);
                    // Form: Circle
                    Picasso.get().load(image).transform(new CircleTransform()).into(imageView);
                    // Set the name
                    displayName.setText(name);
                    // Set the email
                    emailID.setText(email);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        // We set parameters to the GraphRequest using a Bundle.
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,picture.width(200)");
        request.setParameters(parameters);
        // Initiate the GraphRequest
        request.executeAsync();
    }
}
