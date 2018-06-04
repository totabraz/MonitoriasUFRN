package totabraz.com.monitoriasufrn.fragments.monitoring;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import totabraz.com.monitoriasufrn.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListMonitoringFragment extends Fragment {


    public ListMonitoringFragment() {
        // Required empty public constructor
    }

    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_monitoring, container, false);
    }

}
