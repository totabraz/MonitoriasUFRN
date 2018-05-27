package totabraz.com.monitoriasufrn.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import io.paperdb.Paper;
import totabraz.com.monitoriasufrn.activities.setup.LoginActivity;
import totabraz.com.monitoriasufrn.apisinfo.JApi;
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
     * FIREBASE CONSTANTS
     */

    /**
     *  Firebase constants
     */

    public static final String PUBLIC_ROOT = "public";
    public static final String SEMI_PUBLIC_ROOT = "semi_public";
    public static final String PRIVATE_ROOT = "private";

    public static final String CHILD_MONITORING = PUBLIC_ROOT + "/monitoring/";
    public static final String CHILD_MONITOR = PRIVATE_ROOT +"/users/monitors/";
    public static final String CHILD_PROFESSOR = PRIVATE_ROOT +"/users/professors/";

    public static final String CHILD_STUDENTS = PRIVATE_ROOT +"/users/students/";

    public static final String CHILD_SUBJECTS = PUBLIC_ROOT + "/subjects/";
    public static final String CHILD_USERS = PRIVATE_ROOT + "/users/";

    public String getChildProfSubjects(String fbUserID){
        return PRIVATE_ROOT +"/users/professors/" + fbUserID + "/subjects/";
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



//    public static User getLocalUser(Activity activity) {
//        Singleton singleton = Singleton.getInstance();
//        if (singleton.getUser() != null) return singleton.getUser();
//        else {
//            SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
//            Gson gson = new Gson();
//            String json = appSharedPrefs.getString(SysUtils.FB_USER, "");
//            Usuario user = gson.fromJson(json, Usuario.class);
//            if (user == null) {
//                UserService userService = new UserService(activity);
//                user = userService.getUser();
//            }
//            return user;
//        }
//    }

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
