package totabraz.com.monitoriasufrn.fragments.monitoring.student;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import totabraz.com.monitoriasufrn.R;
import totabraz.com.monitoriasufrn.adapters.ListMonitoringAdapter;
import totabraz.com.monitoriasufrn.domain.Monitoring;
import totabraz.com.monitoriasufrn.utils.FirebaseUtils;
import totabraz.com.monitoriasufrn.utils.SysUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListMonitoringFragment extends Fragment {
    private View rootView;

    private ArrayList<Object> monitorings;
    private ListMonitoringAdapter mAdapter;
    private Context mContext;

    private String componentToShow;


    private ProgressBar progress;
    private RecyclerView rvMyList;
    private TextView tvNothingToShow;


    public static ListMonitoringFragment newInstance() {
        return new ListMonitoringFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_list_monitoring, container, false);
        monitorings = null;
        componentToShow = null;
        mContext = getActivity().getApplicationContext();
        Bundle bundle = getArguments();
        if (bundle!=null){
            componentToShow = bundle.getString(SysUtils.KEY_COMPONENT);;
        }
        setupViews();
        init();
        return rootView;
    }


    private void setupViews() {
        monitorings = new ArrayList<>();
        rvMyList = rootView.findViewById(R.id.rvMyList);
        tvNothingToShow = rootView.findViewById(R.id.tvNothingToShow);
        progress = rootView.findViewById(R.id.progress);
    }

    private void init() {
        progress.setVisibility(View.VISIBLE);
        tvNothingToShow.setVisibility(View.GONE);
        rvMyList.setVisibility(View.GONE);
        getMonitoring();
    }


    private void getMonitoring() {
        DatabaseReference mDatabase;
        if (componentToShow!=null) mDatabase = FirebaseDatabase.getInstance().getReference().child(FirebaseUtils.getAllMonitoring()).child(componentToShow);
        else mDatabase = FirebaseDatabase.getInstance().getReference().child(FirebaseUtils.getAllMonitoring());
        ValueEventListener monitorListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                monitorings = new ArrayList<>();

                    for (DataSnapshot indexSnapshot : dataSnapshot.getChildren()) {

                        for (DataSnapshot monitoringSnapshot : indexSnapshot.getChildren()) {
                            String key = indexSnapshot.getKey();
                            if (!monitorings.contains(key))monitorings.add(key);
                            monitorings.add(monitoringSnapshot.getValue(Monitoring.class));


                        }

                }

                if (monitorings != null && monitorings.size() > 0) {
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
                    mAdapter = new ListMonitoringAdapter(mContext, monitorings);
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
        if (monitorings != null && monitorings.size() > 0) {
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
