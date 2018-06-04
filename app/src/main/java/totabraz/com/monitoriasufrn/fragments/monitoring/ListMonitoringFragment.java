package totabraz.com.monitoriasufrn.fragments.monitoring;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import totabraz.com.monitoriasufrn.R;
import totabraz.com.monitoriasufrn.activities.monitoring.AddMonitoringActivity;
import totabraz.com.monitoriasufrn.dao.UserDao;
import totabraz.com.monitoriasufrn.enun.TipoVinculoEnum;
import totabraz.com.monitoriasufrn.fragments.monitors.ListAddMonitorsFragment;
import totabraz.com.monitoriasufrn.utils.SysUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListMonitoringFragment extends Fragment {
    private View rootView;
    private Button btnAdd;
    private Context mContext;

    public static ListMonitoringFragment newInstance() {
        return new ListMonitoringFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_list_monitoring, container, false);
        btnAdd = rootView.findViewById(R.id.btnAdd);
        mContext = getActivity().getApplicationContext();
        int idTipo = Integer.parseInt(UserDao.getVinculoDefault(mContext).getIdTipoVinculo());
        if ( idTipo == TipoVinculoEnum.DOCENTE.ordinal()) {
            btnAdd.setVisibility(View.VISIBLE);
            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, AddMonitoringActivity.class);
                    if (Integer.parseInt(UserDao.getVinculoDefault(mContext).getIdTipoVinculo()) == TipoVinculoEnum.DOCENTE.ordinal()) {
                        intent.putExtra(SysUtils.KEY_PROFESSOR, true);
                    }
                    startActivity(intent);
                }
            });
        }

        return rootView;
    }

}
