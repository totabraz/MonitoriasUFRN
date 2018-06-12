package totabraz.com.monitoriasufrn.fragments.monitoring;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import totabraz.com.monitoriasufrn.R;
import totabraz.com.monitoriasufrn.activities.MainActivity;
import totabraz.com.monitoriasufrn.adapters.ListSubjectShortAdapter;
import totabraz.com.monitoriasufrn.dao.UserDao;
import totabraz.com.monitoriasufrn.domain.Monitoring;
import totabraz.com.monitoriasufrn.utils.FirebaseUtils;
import totabraz.com.monitoriasufrn.utils.SysUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListTurmasFragment extends Fragment {
    private View rootView;

    private ListSubjectShortAdapter mAdapter;
    private String siape;
    private Context mContext;

    private HashMap<String, String> components;


    private ProgressBar progress;
    private RecyclerView rvMyList;
    private TextView tvNothingToShow;


    public static ListTurmasFragment newInstance() {
        return new ListTurmasFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_list_prof_monitoring, container, false);
        mContext = getActivity().getApplicationContext();
        siape = UserDao.getVinculoDefault(mContext).getIdentificador();
        setupViews();
        init();
        return rootView;
    }


    private void setupViews() {
        rvMyList = rootView.findViewById(R.id.rvMyList);
        tvNothingToShow = rootView.findViewById(R.id.tvNothingToShow);
        progress = rootView.findViewById(R.id.progress);
    }

    private void init() {
        progress.setVisibility(View.VISIBLE);
        tvNothingToShow.setVisibility(View.GONE);
        rvMyList.setVisibility(View.GONE);
        getSubjects();
    }


    private void getSubjects() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(FirebaseUtils.getAllMonitoring());
        ValueEventListener monitorListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                components = new HashMap<>();

                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    getSubjectName(objSnapshot);
                }

                if (components != null && components.size() > 0) {
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
                    mAdapter = new ListSubjectShortAdapter(getActivity(),components);
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

    private void getSubjectName(DataSnapshot objSnapshot) {

        for (DataSnapshot indexSnapshot : objSnapshot.getChildren()) {
            String key = objSnapshot.getKey();
            for (DataSnapshot monitoringSnapshot : indexSnapshot.getChildren()) {
                components.put(key, monitoringSnapshot.getValue(Monitoring.class).getNomeComponente());
                break;
            }
        }
    }

    /**
     * First steps after to get Firebase Users.
     */

    private void updateListMonitors() {
        if (components != null && components.size() > 0) {
            rvMyList.setVisibility(View.VISIBLE);
            tvNothingToShow.setVisibility(View.GONE);
            if (mAdapter != null) mAdapter.notifyDataSetChanged();
        } else {
            rvMyList.setVisibility(View.GONE);
            tvNothingToShow.setVisibility(View.VISIBLE);
        }
        progress.setVisibility(View.GONE);
    }
}
