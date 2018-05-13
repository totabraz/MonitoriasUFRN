package totabraz.com.monitoriasufrn.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import totabraz.com.monitoriasufrn.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListMonitorsFragment extends Fragment {

    private RecyclerView rvList;

    public static ListMonitorsFragment newInstance() {
        return new ListMonitorsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View  view =  inflater.inflate(R.layout.fragment_recycleview, container, false);
        rvList = view.findViewById(R.id.rvList);

        return view;
    }

}
