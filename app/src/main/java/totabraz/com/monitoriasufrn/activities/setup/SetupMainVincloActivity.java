package totabraz.com.monitoriasufrn.activities.setup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import io.paperdb.Paper;
import totabraz.com.monitoriasufrn.R;
import totabraz.com.monitoriasufrn.adapters.ListVinculosAdapter;
import totabraz.com.monitoriasufrn.dao.UserDao;
import totabraz.com.monitoriasufrn.domain.Vinculo;

public class SetupMainVincloActivity extends AppCompatActivity {

    private RecyclerView rvMyList;


    private void setupView() {
        rvMyList = findViewById(R.id.rvMyList);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_main_vinclo);
        ArrayList<Vinculo> vinculos = new ArrayList<>();
        vinculos = (ArrayList<Vinculo>) UserDao.getLocalUser(getApplicationContext()).getVinculos();
        LinearLayoutManager lmManager = new LinearLayoutManager(getApplicationContext());
        rvMyList.setLayoutManager(lmManager);
        rvMyList.setAdapter(new ListVinculosAdapter(this,vinculos));
    }
}
