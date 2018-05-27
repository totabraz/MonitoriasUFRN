package totabraz.com.monitoriasufrn.services;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import totabraz.com.monitoriasufrn.R;
import totabraz.com.monitoriasufrn.dao.UserDao;
import totabraz.com.monitoriasufrn.domain.Subject;
import totabraz.com.monitoriasufrn.domain.User;
import totabraz.com.monitoriasufrn.utils.ApiUtils;
import totabraz.com.monitoriasufrn.utils.SysUtils;

/**
 * Created by totabraz on 26/02/18.
 */

public class TurmaService {
    private ObjectMapper objectMapper;
    private Activity activity;
    private String vinculo;
    private String codigoComponente;

    public TurmaService(Activity activity, String vinculo, String codigoComponente) {
        this.activity = activity;
        this.vinculo = vinculo;
        this.codigoComponente = codigoComponente;
    }

    public Subject getTurma() {
        String url = ApiUtils.CONSULTA_TURMAS_USER + this.vinculo + ApiUtils.QUERY_AND_LIMIT + ApiUtils.QUERY_AND_CODIGO_COMPONENTE + this.codigoComponente;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = SysUtils.getHeaders(activity.getApplicationContext());
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        Log.d("HTTP STATUS : ", "" + responseEntity.getStatusCode().value());
        if (responseEntity.getStatusCode().value() == HttpStatus.OK.value()) {
            Log.d("HTTP BODY: ", "" + responseEntity.getBody().toString());
            JavaType javaType = getObjectMapperInstance().getTypeFactory().constructType(User.class);
            try {
                return getObjectMapperInstance().readValue(responseEntity.getBody().toString(), javaType);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    private ObjectMapper getObjectMapperInstance() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }
        return objectMapper;
    }

    private class GetTurma extends AsyncTask<String, Subject, Subject> {
        private ProgressBar mProgressBar;
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar = activity.findViewById(R.id.progress);
            dialog = new ProgressDialog(activity);
            dialog.setMessage("Processing...");
            dialog.show();
        }

        @Override
        protected Subject doInBackground(String... parameters) {
            return getTurma();
        }

        @Override
        protected void onPostExecute(Subject result) {
            mProgressBar.setVisibility(android.view.View.GONE);
            if (result!=null){
                UserDao.updateTurmas(activity.getApplicationContext(), result);
            }
        }
    }
}
