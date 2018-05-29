package totabraz.com.monitoriasufrn.services;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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
import java.util.List;

import totabraz.com.monitoriasufrn.R;
import totabraz.com.monitoriasufrn.activities.error.ErrorMsgActivity;
import totabraz.com.monitoriasufrn.activities.setup.SetupFirebaseActivity;
import totabraz.com.monitoriasufrn.dao.UserDao;
import totabraz.com.monitoriasufrn.domain.User;
import totabraz.com.monitoriasufrn.domain.Vinculo;
import totabraz.com.monitoriasufrn.utils.ApiUtils;
import totabraz.com.monitoriasufrn.utils.SysUtils;

/**
 * Created by totabraz on 26/02/18.
 */

public class UserLoginService {
    private ObjectMapper objectMapper;
    private Activity activity;
    private User user;

    public UserLoginService(Activity activity) {
        this.activity = activity;
        this.user = null;

    }

    public User getUser() {
        new GetUser().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        return user;
    }

    private User getUserInfo() {
        String url = ApiUtils.CONSULTA_USER_INFO;
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

    private List<Vinculo> getVinculoInfo(User user) {
        String url = ApiUtils.CONSULTA_VINCULOS +"&id-usuario=" + user.getIdUsuario();
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = SysUtils.getHeaders(activity.getApplicationContext());
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        if (responseEntity.getStatusCode().value() == HttpStatus.OK.value()) {
            JavaType javaType = getObjectMapperInstance().getTypeFactory().constructCollectionType(List.class, Vinculo.class);
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

    /**
     * ::: Task GetUser :::
     * Get infos from user from refreshToken
     */

    private class GetUser extends AsyncTask<String, User, User> {
        private ProgressBar mProgressBar;
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar = activity.findViewById(R.id.progress);
//            dialog = new ProgressDialog(activity);
//            dialog.setMessage("Processing...");
//            dialog.show();
//            mProgressBar = (ProgressBar) activity.findViewById(R.id.progressBar);
//            mProgressBar.setVisibility(android.view.View.VISIBLE);
        }

        @Override
        protected User doInBackground(String... parameters) {
            user = getUserInfo();
            if (user != null) {
                user.setCpfCnpj(SysUtils.fixeCpf(user.getCpfCnpj()));
                user.setVinculos(getVinculoInfo(user));
            }
            return user;
        }

        @Override
        protected void onPostExecute(User result) {
            mProgressBar.setVisibility(android.view.View.GONE);
            if (result != null) {
                UserDao.setLocalUser(activity.getApplicationContext(), result);
                activity.startActivity(new Intent(activity.getApplicationContext(), SetupFirebaseActivity.class));
                activity.finish();
            } else  activity.startActivity(new Intent(activity.getApplicationContext(), ErrorMsgActivity.class));
        }
    }
}
