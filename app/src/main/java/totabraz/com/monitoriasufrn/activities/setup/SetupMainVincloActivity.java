package totabraz.com.monitoriasufrn.activities.setup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Objects;

import totabraz.com.monitoriasufrn.R;
import totabraz.com.monitoriasufrn.adapters.ListVinculosAdapter;
import totabraz.com.monitoriasufrn.dao.UserDao;
import totabraz.com.monitoriasufrn.domain.Vinculo;

public class SetupMainVincloActivity extends AppCompatActivity {

    private RecyclerView rvMyList;
    private ProgressBar progress;


    private void setupView() {
        progress = findViewById(R.id.progress);
        rvMyList = findViewById(R.id.rvMyList);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_main_vinclo);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.vinculos);
//        getSupportActionBar().setTitle(R.string.vinculos);
        getSupportActionBar().hide();

        setupView();
        ArrayList<Vinculo> vinculos = new ArrayList<>();
        vinculos = (ArrayList<Vinculo>) UserDao.getLocalUser(getApplicationContext()).getVinculos();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvMyList.setLayoutManager(mLayoutManager);
        rvMyList.setAdapter(new ListVinculosAdapter(this, vinculos));

        progress.setVisibility(View.GONE);
        rvMyList.setVisibility(View.VISIBLE);

    }
}
