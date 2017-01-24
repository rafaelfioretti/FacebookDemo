package br.com.rafaelfioretti.facebookdemo;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    CallbackManager callbackManager;
    TextView txtemail;
    TextView txtnome;
    ProfilePictureView foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        txtemail = (TextView) findViewById(R.id.txtEmail);
        txtnome  = (TextView) findViewById(R.id.txtNome);
        foto = (ProfilePictureView) findViewById(R.id.foto);
        loginButton.setReadPermissions("email");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                System.out.println("Sucesso");
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback(){

                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                configurarProfile(object);
                            }
                        }

                );
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, name, email, gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                txtnome.setText(null);
                txtemail.setText(null);
                foto.setPresetSize(ProfilePictureView.NORMAL);
                foto.setProfileId(null);


            }

            @Override
            public void onError(FacebookException error) {

            }
        });



    }

    private void configurarProfile(JSONObject object) {
        try{
            txtnome.setText(object.getString("name"));
            txtemail.setText(object.getString("email"));
            foto.setPresetSize(ProfilePictureView.NORMAL);
            foto.setProfileId(object.getString("id"));
            //infoLayout.setVisibility(View.VISIBLE);

        }
        catch   (JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
