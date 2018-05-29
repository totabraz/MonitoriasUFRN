package totabraz.com.monitoriasufrn.activities.setup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import totabraz.com.monitoriasufrn.R;
import totabraz.com.monitoriasufrn.services.UserLoginService;

public class SetupUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_user);
        /**
         * User Service
         */
        UserLoginService userLoginService = new UserLoginService(this);
        userLoginService.getUser();
    }
}
