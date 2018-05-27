package totabraz.com.monitoriasufrn.services;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
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
import java.util.ArrayList;
import java.util.List;

import totabraz.com.monitoriasufrn.R;
import totabraz.com.monitoriasufrn.domain.Subject;
import totabraz.com.monitoriasufrn.utils.ApiUtils;
import totabraz.com.monitoriasufrn.utils.SysUtils;

public class SubjectService {

    private ObjectMapper objectMapper;
    private Activity activity;
    private ArrayList<Subject> subjects;
    private String idvinculo;

    public SubjectService(Activity activity, String idvinculo, ArrayList<Subject> subjects) {
        this.activity = activity;
        this.idvinculo = idvinculo;
        this.subjects = subjects;
    }

    private ObjectMapper getObjectMapperInstance() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }
        return objectMapper;
    }

    public List<Subject> getSubjects(){
        String url = ApiUtils.CONSULTA_TURMAS + "&id-discente" + idvinculo;

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = SysUtils.getHeaders(activity.getApplicationContext());
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity responseEntity =  restTemplate.exchange(url, HttpMethod.GET,entity,String.class);
        if (responseEntity.getStatusCode().value() == HttpStatus.OK.value()){
            JavaType javaType = getObjectMapperInstance().getTypeFactory().constructCollectionType(List.class, Subject.class);
            try {
                return getObjectMapperInstance().readValue(responseEntity.getBody().toString(), javaType);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    private class GetSubject extends AsyncTask<ArrayList<Subject>, Subject, ArrayList<Subject>> {
        private ProgressBar progressBar;

        @Override
        protected ArrayList<Subject> doInBackground(ArrayList<Subject>... arrayLists) {
            subjects = (ArrayList<Subject>) getSubjects();
            return subjects;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar = activity.findViewById(R.id.progress);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(ArrayList<Subject> subject) {
            super.onPostExecute(subject);
            progressBar.setVisibility(View.GONE);
        }
    }


}
