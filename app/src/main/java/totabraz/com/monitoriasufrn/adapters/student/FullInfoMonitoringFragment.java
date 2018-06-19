package totabraz.com.monitoriasufrn.adapters.student;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import totabraz.com.monitoriasufrn.R;
import totabraz.com.monitoriasufrn.domain.Monitor;
import totabraz.com.monitoriasufrn.domain.Monitoring;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class FullInfoMonitoringFragment extends Fragment {

    private View rootView;
    private TextView tvTurma;
    private TextView tvSector;
    private TextView tvClass;
    private TextView tvMonitor;
    private TextView tvTime;
    private TextView tvObs;

    private Monitoring monitoring;

    private void setupViews(){
        tvTurma = rootView.findViewById(R.id.tvTurma);
        tvSector = rootView.findViewById(R.id.tvSector);
        tvClass = rootView.findViewById(R.id.tvClass);
        tvMonitor = rootView.findViewById(R.id.tvMonitor);
        tvTime = rootView.findViewById(R.id.tvTime);
        tvObs = rootView.findViewById(R.id.tvObs);
    }

    @SuppressLint("ValidFragment")
    public FullInfoMonitoringFragment(Monitoring monitoring) {
        this.monitoring = monitoring;
    }

    //pathbuilder idex
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_full_info_monitoring, container, false);
        setupViews();

        tvTurma.setText(monitoring.getNomeComponente());
        tvSector.setText(monitoring.getSetor());
        tvClass.setText(monitoring.getSala());
        tvMonitor.setText(monitoring.getMonitor().getNomePessoa());
        tvTime.setText(monitoring.getClassTime());
        return rootView;
    }



}
