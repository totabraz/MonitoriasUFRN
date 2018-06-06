package totabraz.com.monitoriasufrn.activities.monitoring;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import totabraz.com.monitoriasufrn.R;
import totabraz.com.monitoriasufrn.dao.UserDao;
import totabraz.com.monitoriasufrn.domain.Monitor;
import totabraz.com.monitoriasufrn.domain.Monitoring;
import totabraz.com.monitoriasufrn.domain.Turma;
import totabraz.com.monitoriasufrn.domain.User;
import totabraz.com.monitoriasufrn.domain.Vinculo;
import totabraz.com.monitoriasufrn.utils.ApiUtils;
import totabraz.com.monitoriasufrn.utils.FirebaseUtils;
import totabraz.com.monitoriasufrn.utils.SysUtils;

public class AddMonitoringActivity extends AppCompatActivity {

    private Turma turma;
    private User user;
    private ObjectMapper objectMapper;
    private String idVinculo;
    private String lastCpf;
    private String lastCodigoComponent;
    private String dia;
    private String turnoM;
    private String turnoT;
    private String turnoN;
    private String sala;
    private String setor;
    private String year;

    private TextInputEditText tiSetor;
    private TextInputEditText tiSala;
    private TextInputEditText tiCpfMonitor;
    private TextInputEditText tiCodComponent;
    private TextInputEditText tiObservation;
    private TextView tvNomeMonitor;
    private TextView tvNomeTurma;
    private ImageView ivAddMonitor;
    private ImageView ivAddTurma;
    private CheckBox horarioM1, horarioM2, horarioM3, horarioM4, horarioM5, horarioM6, horarioT1, horarioT2, horarioT3, horarioT4, horarioT5, horarioT6, horarioN1, horarioN2, horarioN3, horarioN4;
    private Button btnAdd;
    private RadioGroup rgDayOfWeek;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_monitoring);
        lastCodigoComponent = "";
        lastCpf = "";
        dia = "";
        turnoM = "";
        turnoT = "";
        turnoN = "";

        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        year = String.valueOf(calendar.get(Calendar.YEAR));

        idVinculo = UserDao.getVinculoDefault(getApplicationContext()).getIdVinculo();
        getSupportActionBar().hide();//Ocultar ActivityBar anterior
        setupViews();
    }

    private void setupViews() {
        ivAddMonitor = findViewById(R.id.ivAddMonitor);
        tvNomeMonitor = findViewById(R.id.tvNomeMonitor);
        ivAddTurma = findViewById(R.id.ivAddTurma);
        tvNomeTurma = findViewById(R.id.tvNomeTurma);
        progress = findViewById(R.id.progress);
        tiSetor = findViewById(R.id.tiSetor);
        tiSala = findViewById(R.id.tiSala);
        tiCpfMonitor = findViewById(R.id.tiCpfMonitor);
        tiCodComponent = findViewById(R.id.tiCodComponent);
        tiObservation = findViewById(R.id.tiObservation);
        horarioM1 = findViewById(R.id.horarioM1);
        horarioM2 = findViewById(R.id.horarioM2);
        horarioM3 = findViewById(R.id.horarioM3);
        horarioM4 = findViewById(R.id.horarioM4);
        horarioM5 = findViewById(R.id.horarioM5);
        horarioM6 = findViewById(R.id.horarioM6);
        horarioT1 = findViewById(R.id.horarioT1);
        horarioT2 = findViewById(R.id.horarioT2);
        horarioT3 = findViewById(R.id.horarioT3);
        horarioT4 = findViewById(R.id.horarioT4);
        horarioT5 = findViewById(R.id.horarioT5);
        horarioT6 = findViewById(R.id.horarioT6);
        horarioN1 = findViewById(R.id.horarioN1);
        horarioN2 = findViewById(R.id.horarioN2);
        horarioN3 = findViewById(R.id.horarioN3);
        horarioN4 = findViewById(R.id.horarioN4);
        btnAdd = findViewById(R.id.btnAdd);
        rgDayOfWeek = findViewById(R.id.rgDayOfWeek);

        setupOnClicks();
    }

    public void setupOnClicks() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkFormInput()) {
                    addMonitoring();
                }
            }
        });

        ivAddMonitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codAux = tiCpfMonitor.getText().toString();
                tvNomeMonitor.setVisibility(View.GONE);
                tvNomeMonitor.setText("");
                if (codAux.length() > 0) {
                    lastCpf = codAux;
                    new GetUserByCPF().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
            }
        });

        ivAddTurma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvNomeTurma.setVisibility(View.GONE);
                tvNomeTurma.setText("");
                String codAux = tiCodComponent.getText().toString();
                if (codAux.length() > 0) {
                    lastCodigoComponent = codAux;
                    new GetTurma().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
            }
        });
    }

    public void showMsgError() {
        Toast.makeText(getApplicationContext(), "Ops! Deu ruim! :(", Toast.LENGTH_LONG).show();
    }

    private void setupMonitorName(User user) {
        tiCpfMonitor.setText("");
        tvNomeMonitor.setText(user.getNomePessoa());
        tvNomeMonitor.setVisibility(View.VISIBLE);
        lastCpf = "";
    }


    public void setupTurmaName(Turma turma) {
        tiCodComponent.setText("");
        tvNomeTurma.setText(turma.getNomeComponente());
        tvNomeTurma.setVisibility(View.VISIBLE);
        lastCodigoComponent = "";
    }


    private String getTurnos() {
        turnoM = "";
        turnoT = "";
        turnoN = "";
        if (horarioM1.isChecked()) turnoM += "1";
        if (horarioM2.isChecked()) turnoM += "2";
        if (horarioM3.isChecked()) turnoM += "3";
        if (horarioM4.isChecked()) turnoM += "4";
        if (horarioM5.isChecked()) turnoM += "5";
        if (horarioM6.isChecked()) turnoM += "6";
        if (turnoM.length() > 0) turnoM += "M" + turnoM;
        if (horarioT1.isChecked()) turnoT += "1";
        if (horarioT2.isChecked()) turnoT += "2";
        if (horarioT3.isChecked()) turnoT += "3";
        if (horarioT4.isChecked()) turnoT += "4";
        if (horarioT5.isChecked()) turnoT += "5";
        if (horarioT6.isChecked()) turnoT += "6";
        if (turnoT.length() > 0) turnoT += "T" + turnoT;
        if (horarioN1.isChecked()) turnoN += "1";
        if (horarioN2.isChecked()) turnoN += "2";
        if (horarioN3.isChecked()) turnoN += "3";
        if (horarioN4.isChecked()) turnoN += "4";
        if (turnoN.length() > 0) turnoN += "N" + turnoN;

        return turnoM + turnoT + turnoN;
    }

    private boolean checkFormInput() {
        boolean checked = false;
        RadioButton checkedRadioButton = rgDayOfWeek.findViewById(rgDayOfWeek.getCheckedRadioButtonId());
        setor = tiSetor.getText().toString();
        sala = tiSala.getText().toString();
        dia = checkedRadioButton.getText().toString();
        if (tvNomeTurma.length() > 0 &&
                tvNomeMonitor.length() > 0 &&
                getTurnos().length() > 0 &&
                dia.length() > 0 &&
                sala.length() > 0 &&
                setor.length() > 0) checked = true;
        return checked;
    }

    private void addMonitoring(){
        FirebaseUtils.addMonitoring(getApplicationContext(),setupMonitoria());

    }

    private Monitoring setupMonitoria() {
        Monitoring monitoring = new Monitoring();
        monitoring.setSetor(setor);
        monitoring.setSala(sala);
        monitoring.setHorarioM(turnoM);
        monitoring.setHorarioT(turnoT);
        monitoring.setHorarioN(turnoN);
        monitoring.setMonitor(user);
        monitoring.setDia(dia);
        monitoring.setCodigoComponente(turma.getCodigoComponente());
        monitoring.setNomeComponente(turma.getNomeComponente());
        monitoring.setSiglaComponente(turma.getSiglaNivel());
        monitoring.setObservacao(tiObservation.getText().toString());
        return  monitoring;
    }

    /**
     * ::: Task GetUser :::
     * Get infos from monitor from refreshToken
     */

    private Monitor getUserByCPF() {
        String url = ApiUtils.CONSULTA_USER + ApiUtils.QUERY_AND_CPF + lastCpf;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = SysUtils.getHeaders(getApplicationContext());
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        try {
            ResponseEntity responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            Log.d("HTTP STATUS : ", "" + responseEntity.getStatusCode().value());
            if (responseEntity.getStatusCode().value() == HttpStatus.OK.value()) {
                Log.d("HTTP BODY: ", "" + responseEntity.getBody().toString());
                JavaType javaType = getObjectMapperInstance().getTypeFactory().constructCollectionType(List.class, Monitor.class);
                try {
                    ArrayList<Monitor> monitors = getObjectMapperInstance().readValue(responseEntity.getBody().toString(), javaType);
                    if (monitors.size() > 0) return monitors.get(0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (RuntimeException ignored) {
            Log.e("Erro", "Error na API - " + " usuario/V../USUARIOS ");
        }
        return null;
    }

    private List<Vinculo> getVinculoInfo(User user) {
        String url = ApiUtils.CONSULTA_VINCULOS + "&id-usuario=" + user.getIdUsuario();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = SysUtils.getHeaders(getApplicationContext());
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        try {
            ResponseEntity responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            if (responseEntity.getStatusCode().value() == HttpStatus.OK.value()) {
                JavaType javaType = getObjectMapperInstance().getTypeFactory().constructCollectionType(List.class, Vinculo.class);
                try {
                    return getObjectMapperInstance().readValue(responseEntity.getBody().toString(), javaType);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (RuntimeException ignored) {
            Log.e("Erro", "Error na API - " + " vinculo/V../VINCULOS ");
        }
        return null;
    }

    private List<Turma> getTurma() {
        String url = ApiUtils.CONSULTA_TURMAS_USER + idVinculo + ApiUtils.QUERY_AND_LIMIT + ApiUtils.QUERY_AND_CODIGO_COMPONENTE + lastCodigoComponent + "&ano=" + year;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = SysUtils.getHeaders(getApplicationContext());
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        try {
            ResponseEntity responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            if (responseEntity.getStatusCode().value() == HttpStatus.OK.value()) {
                JavaType javaType = getObjectMapperInstance().getTypeFactory().constructCollectionType(List.class, Turma.class);
                try {
                    return getObjectMapperInstance().readValue(responseEntity.getBody().toString(), javaType);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (RuntimeException ignored) {
            Log.e("Erro", "Error na API - " + " turma/V../TURMAS ");
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
     * ::: Task GetUserByCPF :::
     * Get infos from monitor from refreshToken
     */

    private class GetUserByCPF extends AsyncTask<String, User, User> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected User doInBackground(String... parameters) {
            User user = getUserByCPF();
            if (user != null) {
                user.setCpfCnpj(SysUtils.fixeCpf(user.getCpfCnpj()));
                user.setVinculos(getVinculoInfo(user));
            }
            return user;
        }

        @Override
        protected void onPostExecute(User result) {
            if (result != null) {
                user = result;
                setupMonitorName(result);
            } else showMsgError();
            progress.setVisibility(View.GONE);
        }
    }


    private class GetTurma extends AsyncTask<String, Turma, Turma> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected Turma doInBackground(String... parameters) {
            List<Turma> turmas = getTurma();
            Turma turma = null;
            if (turmas != null && turmas.size() > 0) turma = getTurma().get(0);
            return turma;
        }

        @Override
        protected void onPostExecute(Turma result) {
            if (result != null) {
                turma = result;
                setupTurmaName(result);
            } else showMsgError();
            progress.setVisibility(View.GONE);
        }
    }

}


//                monitors.put(result.getCpfCnpj(), result);
//                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(FirebaseUtils.getChildProfMonitors(mContext));
//                mDatabase.setValue(monitors);
/**
 * TESTARRRRRRRRRRRRR
 * TESTARRRRRRRRRRRRR
 * TESTARRRRRRRRRRRRR
 * TESTARRRRRRRRRRRRR
 */


//                FirebaseUtils.addMonitoring(getApplicationContext(), result);
//                Toast.makeText(getActivity().getApplicationContext(), subject.getNomeComponente(),Toast.LENGTH_SHORT).show();
//                monitoring.setDia();
//                FirebaseUtils.addMonitoring();
//                updateMonitors(mContext, lastCpf, monitors);
//                updateListMonitors();
//                tiMatricula.setText("");