package totabraz.com.monitoriasufrn.fragments.monitors;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import totabraz.com.monitoriasufrn.R;
import totabraz.com.monitoriasufrn.dao.UserDao;
import totabraz.com.monitoriasufrn.domain.Monitor;
import totabraz.com.monitoriasufrn.domain.User;
import totabraz.com.monitoriasufrn.utils.FirebaseUtils;
import totabraz.com.monitoriasufrn.utils.SysUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListMonitorsFragment extends Fragment {

    private TextView tvNothingToShow;
    private ProgressBar progress;
    private TextInputEditText tiMatricula;
    private Button btnAdd;
    private RecyclerView rvMyList;
    private View rootView;
    private Context mContext;
    private ArrayList<User> monitors;

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
        rootView = inflater.inflate(R.layout.fragment_list_monitors, container, false);
        monitors = null;
        mContext = getActivity().getApplicationContext();
        setupViews();
        init();
        return rootView;
    }

    private void setupViews() {
        rvMyList = rootView.findViewById(R.id.rvMyList);
        tvNothingToShow = rootView.findViewById(R.id.tvNothingToShow);
        progress = rootView.findViewById(R.id.progress);
        tiMatricula = rootView.findViewById(R.id.tiMatricula);
        btnAdd = rootView.findViewById(R.id.btnAdd);
    }


    private void getMonitoresUser() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(FirebaseUtils.getMonitors(UserDao.getVinculoDefault(mContext).getIdentificador()));
        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                monitors = new ArrayList<User>();
                for (DataSnapshot subdataSnapshot : dataSnapshot.getChildren()){
                    monitors.add(subdataSnapshot.getValue(User.class));
                }
                doneToGetMonitors();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(FirebaseUtils.TAG_Firebase, "loadPost:onCancelled", databaseError.toException());
            }
            @Override
            protected void finalize() throws Throwable {
                super.finalize();
                shotContent();

            }
        };
        mDatabase.addValueEventListener(userListener);
    }

    private void doneToGetMonitors() {
        if (monitors.size()>0){
            rvMyList.setVisibility(View.VISIBLE);
            tvNothingToShow.setVisibility(View.GONE);
        }else {
            rvMyList.setVisibility(View.GONE);
            tvNothingToShow.setVisibility(View.VISIBLE);
        }
        progress.setVisibility(View.GONE);
        tiMatricula.setVisibility(View.VISIBLE);
        btnAdd.setVisibility(View.VISIBLE);

        // TODO: METHODO TO SHOW RECYCLE VIEW WITH ADAPTERS
    }

    private void shotContent() {
    }

    private void init(){
        progress.setVisibility(View.VISIBLE);
        tvNothingToShow.setVisibility(View.GONE);
        rvMyList.setVisibility(View.GONE);
        tiMatricula.setVisibility(View.GONE);
        btnAdd.setVisibility(View.GONE);
    }

}
