package totabraz.com.monitoriasufrn.activities.monitoring;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;

import totabraz.com.monitoriasufrn.R;
import totabraz.com.monitoriasufrn.dao.UserDao;
import totabraz.com.monitoriasufrn.utils.SysUtils;

public class AddMonitoringActivity extends AppCompatActivity {

    private boolean isProfessor;

        private TextInputEditText tiCpfMonitor;
        private TextInputLayout tilCpfMonitor;
        private TextInputEditText tiCodComponent;
        private TextView tvCpfMonitor;
        private CheckBox horarioM1;
        private CheckBox horarioM2;
        private CheckBox horarioM3;
        private CheckBox horarioM4;
        private CheckBox horarioM5;
        private CheckBox horarioM6;
        private CheckBox horarioT1;
        private CheckBox horarioT2;
        private CheckBox horarioT3;
        private CheckBox horarioT4;
        private CheckBox horarioT5;
        private CheckBox horarioT6;
        private CheckBox horarioN1;
        private CheckBox horarioN2;
        private CheckBox horarioN3;
        private CheckBox horarioN4;
        private Button btnAdd;
        private RadioGroup rgDayOfWeek;

    private void setupViews(){
        tiCpfMonitor = findViewById(R.id.tiCpfMonitor);
        tilCpfMonitor = findViewById(R.id.tilCpfMonitor);
        tiCodComponent = findViewById(R.id.tiCodComponent);
        tvCpfMonitor = findViewById(R.id.tvSiape);
        horarioM1 = findViewById(R.id.horarioM1);
        horarioM2 = findViewById(R.id.horarioM2);
        horarioM3 = findViewById(R.id.horarioM3);
        horarioM4 = findViewById(R.id.horarioM4);
        horarioM5 = findViewById(R.id.horarioM5);
        horarioM6 = findViewById(R.id.horarioM6);
        horarioT1 = findViewById(R.id.horarioT1);
        horarioT2 = findViewById(R.id.horarioT2);
        horarioT3 = findViewById(R.id.horarioT3);
        horarioT4 = findViewById(R.id.horarioT4);
        horarioT5 = findViewById(R.id.horarioT5);
        horarioT6 = findViewById(R.id.horarioT6);
        horarioN1 = findViewById(R.id.horarioN1);
        horarioN2 = findViewById(R.id.horarioN2);
        horarioN3 = findViewById(R.id.horarioN3);
        horarioN4 = findViewById(R.id.horarioN4);
        btnAdd = findViewById(R.id.btnAdd);
        rgDayOfWeek = findViewById(R.id.rgDayOfWeek);

        if (!isProfessor) {
            tvCpfMonitor.setText(UserDao.getVinculoDefault(getApplicationContext()).getIdentificador());
            tilCpfMonitor.setVisibility(View.GONE);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {     
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_monitoring);
        Bundle bundle = getIntent().getExtras();

        getSupportActionBar().hide();//Ocultar ActivityBar anterior

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); //NO PROBLEM !!!!
        getSupportActionBar().setTitle("Adicionar Monitoria");

        if (bundle!= null) isProfessor = bundle.getBoolean(SysUtils.KEY_PROFESSOR);

        setupViews();
    }
}
