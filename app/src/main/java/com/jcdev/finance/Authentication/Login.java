package com.jcdev.finance.Authentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.jcdev.finance.Home;
import com.jcdev.finance.R;

public class Login extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener{

    /* Variaveis Google */
    private GoogleApiClient mGoogleApiClient;
    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;
    private ProgressDialog mProgressDialog;
    /* Variaveis Google */

    /* Variaveis Facebook */
    //private LoginButton loginButton_facebook;
    //CallbackManager callbackManager;
    /* Variaveis Facebook */

    protected EditText mNome;
    protected EditText mSenha;
    protected Button btnLogin;
    protected Button btnRegistro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /* ********************************** Google ********************************** */
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // ATTENTION: This "addApi(AppIndex.API)"was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addApi(AppIndex.API).build();


        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button_google);
        assert signInButton != null;
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setScopes(gso.getScopeArray());

        /* ********************************** Google ********************************** */

        /* ********************************** Facebook ********************************** */

        //FacebookSdk.sdkInitialize(getApplicationContext());
        //AppEventsLogger.activateApp(this);

        /* ********************************** Facebook ********************************** */

        //iniciando variaveis
        mNome = (EditText) findViewById(R.id.loginNome);
        mSenha = (EditText) findViewById(R.id.loginSenha);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegistro = (Button) findViewById(R.id.btnCriar);
        findViewById(R.id.sign_in_button_google).setOnClickListener(this);

        //Click botão "Login"
        btnRegistro.setOnClickListener(this);

        //Click botão "Criar conta"
        btnLogin.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mGoogleApiClient.connect();

        /* *************************************** Google *************************************** */
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            //se o usuario se conectar no Google, vai trocar de tela
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            //Se o usuario nao logar ou a sessão expirar por não logar vai exibir o progresso na tela
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
        /* *************************************** Google *************************************** */

    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    // Opção escolhida pelo usuario para logar
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in_button_google:
                signIn();
                break;
            case R.id.btnCriar:
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
                finish();
                break;

        }
    }


    /* ********************************** Login Google ********************************** */

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            Toast.makeText(getApplicationContext(),getString(R.string.signed_in_fmt, acct.getDisplayName()), Toast.LENGTH_SHORT).show();
            updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            updateUI(false);
        }
    }

    private void updateUI(boolean signedIn) {
        if (signedIn) {
            Intent sucess_google = new Intent(this, Home.class);
            startActivity(sucess_google);
        }
    }

    /* ********************************** Login Google ********************************** */

    /* ********************************** Login Facebook ********************************** */

    //@Override
    /*public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.splash_facebook, container, false);

        loginButton_facebook = (LoginButton) view.findViewById(R.id.login_button_facebook);
        loginButton_facebook.setReadPermissions("user_friends");
        // If using in a fragment
        //loginButton_facebook.setFragment(this);
        // Other app specific specialization

        // Callback registration
        loginButton_facebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });

        //Ajustar o return
        return loginButton_facebook;
    }

    /* ********************************** Login Facebook ********************************** */

    /* ********************************** Opções de Menu ********************************** */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    //implementar o logout
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        switch (id){
            case R.id.menuLogout:
                Intent disconecta = new Intent(this, Login.class);
                startActivity(disconecta);
                break;

            case R.id.menuDesconecta:
                /* *********************** Logout Google ********************** */
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(@NonNull Status status) {
                                // [START_EXCLUDE]
                                updateUI(false);
                                // [END_EXCLUDE]
                            }
                        });
                /* *********************** Logout Google ********************** */

                Intent logout = new Intent(this, Login.class);
                startActivity(logout);
                break;


        }
        return super.onOptionsItemSelected(item);
    }

    /* ********************************** Opções de Menu ********************************** */
}
