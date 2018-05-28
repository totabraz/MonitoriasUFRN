package totabraz.com.monitoriasufrn.fragments.subject;


import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import io.paperdb.Paper;
import totabraz.com.monitoriasufrn.R;
import totabraz.com.monitoriasufrn.activities.MainActivity;
import totabraz.com.monitoriasufrn.dao.UserDao;
import totabraz.com.monitoriasufrn.domain.Subject;
import totabraz.com.monitoriasufrn.domain.User;
import totabraz.com.monitoriasufrn.domain.Vinculo;
import totabraz.com.monitoriasufrn.services.SubjectService;
import totabraz.com.monitoriasufrn.services.TurmaService;
import totabraz.com.monitoriasufrn.utils.FirebaseUtils;
import totabraz.com.monitoriasufrn.utils.SysUtils;


public class ListSubjectsFragment extends Fragment {

    private View rootView;
    private TextView tvNothingToShow;
    private RecyclerView rvMyList;
    private Button btnAdd;
    private ProgressBar progress;
    private ArrayList<Subject> subjects;
    private DatabaseReference myDatabase;
    private TextInputEditText tiCodedigoComponente;



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
        init();
        return rootView;
    }

    private void init() {
        User user = Paper.book().read("user");
        if (user.getVinculos().size()>0){
            SubjectService subjectService = new SubjectService((MainActivity) getActivity(),user.getVinculos().get(0).getIdVinculo(), subjects);
        } else {
            Toast.makeText((MainActivity) getActivity().getApplicationContext(), "Erro ao carregar vinculos", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * VIEW's SETUPS
     */
    private void showProgress() {
        progress.setVisibility(View.VISIBLE);
        tvNothingToShow.setVisibility(View.GONE);
        rvMyList.setVisibility(View.GONE);
    }

    private void showList() {
        rvMyList.setVisibility(View.VISIBLE);
        progress.setVisibility(View.GONE);
        tvNothingToShow.setVisibility(View.GONE);
    }

    private void showText() {
        tvNothingToShow.setVisibility(View.VISIBLE);
        progress.setVisibility(View.GONE);
        rvMyList.setVisibility(View.GONE);
    }

    private void setupViews() {
        tvNothingToShow = rootView.findViewById(R.id.tvNothingToShow);
        rvMyList = rootView.findViewById(R.id.rvMyList);
        progress = rootView.findViewById(R.id.progress);
        tiCodedigoComponente = rootView.findViewById(R.id.tiCodedigoComponente);
        btnAdd = rootView.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String component = tiCodedigoComponente.getText().toString();
                Vinculo mainVinculo = UserDao.getVinculoDefault(getActivity().getApplicationContext());
                if (component.length()<3) {
                    TurmaService turmaService = new TurmaService(getActivity(), mainVinculo.getIdentificador() ,component);
                    Subject subject =  turmaService.getTurma();
                    Toast.makeText(getActivity().getApplicationContext(), subject.getNomeComponente(),Toast.LENGTH_SHORT).show();
                }
            }
        });
        showProgress();
    }

    /**
     * Firebase methods
     */
    private void getComponents() {
        myDatabase = FirebaseDatabase.getInstance().getReference().child(FirebaseUtils.CHILD_SUBJECTS);
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
