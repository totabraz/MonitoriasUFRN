package totabraz.com.monitoriasufrn.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import io.paperdb.Paper;
import totabraz.com.monitoriasufrn.activities.setup.LoginActivity;
import totabraz.com.monitoriasufrn.apisinfo.JApi;
import totabraz.com.monitoriasufrn.domain.Monitor;
import totabraz.com.monitoriasufrn.domain.User;

public abstract class SysUtils {

    public static final HashMap<Integer, String> dias = (HashMap<Integer, String>) createDias();

    private static Map<Integer, String> createDias(){
        Map<Integer, String> mDias = new HashMap<Integer, String>();
        mDias.put(1,"Domingo");
        mDias.put(2,"Segunda-Feira");
        mDias.put(3,"Terça-Feira");
        mDias.put(4,"Quarta-Feira");
        mDias.put(5,"Quinta-Feira");
        mDias.put(6,"Sexta-Feira");
        mDias.put(7,"Sábado");
        return mDias;
    }



    /**
     * Header to use on Async request
     *
     * @param context
     * @return
     */
    public static HttpHeaders getHeaders(Context context) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-API-KEY", ApiUtils.XAPIKEY);
        headers.add("Authorization", "bearer " + JApi.getAccessToken(context));
        Log.d("Token user:", JApi.getAccessToken(context));
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return headers;
    }

    /**
     * Method to fix the CPF with 0s on left side.
     * 082.202.000-22
     *
     * @param cpf
     * @return
     */

    public static String fixeCpf(String cpf) {
        int size = 11 - cpf.length();
        while (size-- > 0) cpf = "0" + cpf;
        return cpf;
    }

    /**
     * HashMap to ArrayList
     */
    public static ArrayList<Monitor> getGetArrayMonitors(HashMap<String, Monitor> monitors) {
        Collection<Monitor> values = monitors.values();
        return  new ArrayList<Monitor>(values);
    }

    /**
     *
     * Make toasts on a easier way
     *
     * @param context
     * @param msgm
     */

    public static void shortToast(Context context, String msgm) {
        notification(context, msgm, true);
    }

    public static void longToast(Context context, String msgm) {
        notification(context, msgm, false);
    }

    private static void notification(Context context, String msgm, boolean shortTime) {
        if (shortTime) {
            Toast.makeText(context, msgm, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, msgm, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Methods to verify FirebaseAuth
     */

    public static FirebaseAuth verifyFirebaseUser(Activity activity) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            activity.finish();
            activity.getApplicationContext().startActivity(new Intent(activity.getApplicationContext(), LoginActivity.class));
        } else {
            return firebaseAuth;
        }
        return null;
    }

}
