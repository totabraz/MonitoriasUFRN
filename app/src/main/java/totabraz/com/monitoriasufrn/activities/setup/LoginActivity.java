package totabraz.com.monitoriasufrn.activities.setup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import totabraz.com.monitoriasufrn.R;
import totabraz.com.monitoriasufrn.apisinfo.JApi;
import totabraz.com.monitoriasufrn.apisinfo.JApiWebView;
import totabraz.com.monitoriasufrn.utils.ApiUtils;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        JApi.deslogar(getApplicationContext());
        JApiWebView japiWebView = findViewById(R.id.japiwebview);
        japiWebView.loadJapiWebView(ApiUtils.URLBASE_AUTH_TEST, ApiUtils.ID, ApiUtils.SECRET, this, SetupUserActivity.class);
    }
}
