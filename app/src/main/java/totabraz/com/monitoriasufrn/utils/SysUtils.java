package totabraz.com.monitoriasufrn.utils;

import android.content.Context;
import android.util.Log;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import totabraz.com.monitoriasufrn.apisinfo.JApi;

public abstract class SysUtils {

    public static final HashMap<Integer, String> dias = (HashMap<Integer, String>) createDias();

    private static Map<Integer, String> createDias() {
        Map<Integer, String> mDias = new HashMap<Integer, String>();
        mDias.put(1, "Domingo");
        mDias.put(2, "Segunda-Feira");
        mDias.put(3, "Terça-Feira");
        mDias.put(4, "Quarta-Feira");
        mDias.put(5, "Quinta-Feira");
        mDias.put(6, "Sexta-Feira");
        mDias.put(7, "Sábado");
        return mDias;
    }

    public static final String KEY_PROFESSOR = "PROFESSOR";
    public static final String KEY_COMPONENT = "KEY_COMPONENT";

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
     * Methods to verify FirebaseAuth
     */

    public static String getNameOfDay(int dia) {
        String diaFinal = "";
        switch (dia) {
            case 0:
                diaFinal = "Domingo";
                break;
            case 1:
                diaFinal = "Segunda-feira";
                break;
            case 2:
                diaFinal = "Terça-feira";
                break;
            case 3:
                diaFinal = "Quarta-feira";
                break;
            case 4:
                diaFinal = "Quinta-feira";
                break;
            case 5:
                diaFinal = "Sexta-feira";
                break;
            case 6:
                diaFinal = "Sábado";
        }
        return diaFinal;
    }
}
