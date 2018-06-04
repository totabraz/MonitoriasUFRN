package totabraz.com.monitoriasufrn.fragments.monitors;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import totabraz.com.monitoriasufrn.R;
import totabraz.com.monitoriasufrn.adapters.ListMonitorsAdapter;
import totabraz.com.monitoriasufrn.dao.UserDao;
import totabraz.com.monitoriasufrn.domain.Monitor;
import totabraz.com.monitoriasufrn.domain.Vinculo;
import totabraz.com.monitoriasufrn.utils.ApiUtils;
import totabraz.com.monitoriasufrn.utils.FirebaseUtils;
import totabraz.com.monitoriasufrn.utils.SysUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListAddMonitorsFragment extends Fragment {

    private HashMap<String, Monitor> monitors;
    private String lastCpf;
    private ObjectMapper objectMapper;
    private ListMonitorsAdapter mAdapter;
    private String siape;

    // Views
    private Button btnAdd;
    private Context mContext;
    private LinearLayout llAreaAdd;
    private ProgressBar progress;
    private RecyclerView rvMyList;
    private TextInputEditText tiMatricula;
    private TextView tvNothingToShow;
    private View rootView;

    public static ListAddMonitorsFragment newInstance() {
        return new ListAddMonitorsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_list_add_monitors, container, false);
        monitors = null;
        mContext = getActivity().getApplicationContext();
        siape = UserDao.getVinculoDefault(mContext).getIdentificador();
        setupViews();
        setupOnClicks();
        init();
        return rootView;
    }

    private void setupViews() {
        monitors = new HashMap<String, Monitor>();
        llAreaAdd = rootView.findViewById(R.id.llAreaAdd);
        rvMyList = rootView.findViewById(R.id.rvMyList);
        tvNothingToShow = rootView.findViewById(R.id.tvNothingToShow);
        progress = rootView.findViewById(R.id.progress);
        tiMatricula = rootView.findViewById(R.id.tiMatricula);
        btnAdd = rootView.findViewById(R.id.btnAdd);
    }

    private void setupOnClicks() {
        tiMatricula.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            addMonitor();
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMonitor();
            }
        });
    }

    private void init() {
        progress.setVisibility(View.VISIBLE);
        tvNothingToShow.setVisibility(View.GONE);
        rvMyList.setVisibility(View.GONE);
        llAreaAdd.setVisibility(View.GONE);
        getMonitoresUser();
    }

    private void getMonitoresUser() {

        String siape = UserDao.getVinculoDefault(mContext).getIdentificador();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(FirebaseUtils.getChildProfMonitors(mContext, siape));
        ValueEventListener monitorListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot subdataSnapshot : dataSnapshot.getChildren()) {
                    monitors.put(subdataSnapshot.getKey(), subdataSnapshot.getValue(Monitor.class));
                }
                if (monitors.size() > 0) {
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
                    mAdapter = new ListMonitorsAdapter(mContext, SysUtils.getGetArrayMonitors(monitors));
                    rvMyList.setLayoutManager(mLayoutManager);
                    rvMyList.setItemAnimator(new DefaultItemAnimator());
                    rvMyList.setAdapter(mAdapter);
                }

                updateListMonitors();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(FirebaseUtils.TAG_Firebase, "loadPost:onCancelled", databaseError.toException());
            }

            @Override
            protected void finalize() throws Throwable {
                super.finalize();
                updateListMonitors();
            }
        };
        mDatabase.addValueEventListener(monitorListener);
    }

    /**
     * First steps after to get Firebase Users.
     */
    private void updateListMonitors() {
        if (monitors.size() > 0) {
            rvMyList.setVisibility(View.VISIBLE);
            tvNothingToShow.setVisibility(View.GONE);
            if (mAdapter != null) mAdapter.notifyDataSetChanged();
        } else {
            rvMyList.setVisibility(View.GONE);
            tvNothingToShow.setVisibility(View.VISIBLE);
        }
        progress.setVisibility(View.GONE);
        llAreaAdd.setVisibility(View.VISIBLE);
    }

    private void addMonitor() {
        lastCpf = tiMatricula.getText().toString();
        if (!monitors.containsKey(lastCpf)){
            if (lastCpf.length() > 0) new GetUserByCPF().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            hideKeyboard();
        } else {
            Toast.makeText(mContext, "Usuário já cadastrado", Toast.LENGTH_SHORT).show();
        }
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(mContext.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(tiMatricula.getWindowToken(), 0);
    }


    /**
     * ::: Task GetUser :::
     * Get infos from monitor from refreshToken
     */

    private Monitor getMonitorCPF() {
        String url = ApiUtils.CONSULTA_USER + ApiUtils.QUERY_AND_CPF + lastCpf;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = SysUtils.getHeaders(mContext);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        Log.d("HTTP STATUS : ", "" + responseEntity.getStatusCode().value());
        if (responseEntity.getStatusCode().value() == HttpStatus.OK.value()) {
            Log.d("HTTP BODY: ", "" + responseEntity.getBody().toString());
            JavaType javaType = getObjectMapperInstance().getTypeFactory().constructCollectionType(List.class, Monitor.class);
            try {
                ArrayList<Monitor> monitors = getObjectMapperInstance().readValue(responseEntity.getBody().toString(), javaType);
                if (monitors.size()>0) return monitors.get(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private List<Vinculo> getVinculoInfo(Monitor monitor) {
        String url = ApiUtils.CONSULTA_VINCULOS + "&id-usuario=" + monitor.getIdUsuario();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = SysUtils.getHeaders(mContext);
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
     * ::: Task GetUserByCPF :::
     * Get infos from monitor from refreshToken
     */


    private class GetUserByCPF extends AsyncTask<String, Monitor, Monitor> {
        private ProgressBar mProgressBar;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar = rootView.findViewById(R.id.progress);
        }

        @Override
        protected Monitor doInBackground(String... parameters) {
            Monitor monitor = getMonitorCPF();
            if (monitor != null) {
                monitor.setCpfCnpj(SysUtils.fixeCpf(monitor.getCpfCnpj()));
                monitor.setVinculos(getVinculoInfo(monitor));
                monitor.setSiapeProfessor(siape);
            }
            return monitor;
        }

        @Override
        protected void onPostExecute(Monitor result) {
            if (result != null) {
                monitors.put(result.getCpfCnpj(), result);
//                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(FirebaseUtils.getChildProfMonitors(mContext));
//                mDatabase.setValue(monitors);
                /**
                 * TESTARRRRRRRRRRRRR
                 * TESTARRRRRRRRRRRRR
                 * TESTARRRRRRRRRRRRR
                 * TESTARRRRRRRRRRRRR
                 */
                FirebaseUtils.addMonitor(mContext, siape, lastCpf, monitors);
                updateListMonitors();
                tiMatricula.setText("");
                mProgressBar.setVisibility(android.view.View.GONE);
            } else {
                SysUtils.longToast(mContext, "Ops! Algo de errado não está certo!");
            }
        }
    }

}
