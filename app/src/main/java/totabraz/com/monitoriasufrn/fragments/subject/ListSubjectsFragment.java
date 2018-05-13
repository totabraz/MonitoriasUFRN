package totabraz.com.monitoriasufrn.fragments.subject;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
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

import totabraz.com.monitoriasufrn.R;
import totabraz.com.monitoriasufrn.domains.Subject;
import totabraz.com.monitoriasufrn.utils.SysUtils;


public class ListSubjectsFragment extends Fragment {

    private View rootView;
    private TextView tvNothingToShow;
    private RecyclerView rvMyList;
    private Button btnAdd;
    private ProgressBar progressbar;
    private ArrayList<Subject> subjects;
    private DatabaseReference myDatabase;


    public ListSubjectsFragment() {
        this.subjects = new ArrayList<Subject>();
    }

    public static ListSubjectsFragment newInstance() {
        ListSubjectsFragment fragment = new ListSubjectsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //must to be at first line
        rootView = inflater.inflate(R.layout.fragment_list_subjects, container, false);
        this.setupViews();
        getComponents();
        return rootView;
    }


    /**
     * VIEW's SETUPS
     */
    private void showProgress() {
        progressbar.setVisibility(View.VISIBLE);
        tvNothingToShow.setVisibility(View.GONE);
        rvMyList.setVisibility(View.GONE);
    }

    private void showList() {
        rvMyList.setVisibility(View.VISIBLE);
        progressbar.setVisibility(View.GONE);
        tvNothingToShow.setVisibility(View.GONE);
    }

    private void showText() {
        tvNothingToShow.setVisibility(View.VISIBLE);
        progressbar.setVisibility(View.GONE);
        rvMyList.setVisibility(View.GONE);
    }

    private void setupViews() {
        tvNothingToShow = rootView.findViewById(R.id.tvNothingToShow);
        rvMyList = rootView.findViewById(R.id.rvMyList);
        progressbar = rootView.findViewById(R.id.progress);
        btnAdd = rootView.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        showProgress();
    }

    /**
     * Firebase methods
     */
    private void getComponents() {
        myDatabase = FirebaseDatabase.getInstance().getReference().child(SysUtils.CHILD_SUBJECTS);
        myDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                subjects = (ArrayList<Subject>) dataSnapshot.getValue();
                if (subjects != null) showList();
                else showText();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
