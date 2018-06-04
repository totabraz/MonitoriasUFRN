package totabraz.com.monitoriasufrn.utils;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import totabraz.com.monitoriasufrn.dao.UserDao;
import totabraz.com.monitoriasufrn.domain.Monitor;
import totabraz.com.monitoriasufrn.domain.Monitoring;

public abstract class FirebaseUtils {
    /**
     * Firebase constants
     */


    public static final String TAG_Firebase = "Firebase method";

    private static final String PUBLIC_ROOT = "public/";
    private static final String SEMI_PUBLIC_ROOT = "semi_public/";
    private static final String PRIVATE_ROOT = "private/";

    public static final String MONITORING = PUBLIC_ROOT + "monitoring/";
//    public static final String MONITORING_AT_MONITORS = PRIVATE_ROOT + "monitors/monitoring/";

    public static final String MONITOR_PER_PROFESSOERS = PRIVATE_ROOT + "monitors/monitoring/";
    public static final String LIST_MONITOR = PRIVATE_ROOT + "monitors/list_monitors/";

    public static final String PROFESSOR = PRIVATE_ROOT + "professors/";
    public static final String STUDENTS = PRIVATE_ROOT + "students/";
    public static final String USERS = PRIVATE_ROOT + "users/";

    public static final String SUBJECTS = PUBLIC_ROOT + "subjects/";

    //------------------------------------------------------------------
    //------------------------------------------------------------------
    //------------------------------------------------------------------

    public static String getMonitors(String siape) {
        return MONITOR_PER_PROFESSOERS + siape + "/monitors/";
    }

    public static String setMonitor(String siape, String cpf) {
        return MONITOR_PER_PROFESSOERS + siape + cpf;
    }

    public static String getChildProfSubjects(String fbUserID) {
        return PROFESSOR + fbUserID + "/subjects/";
    }


    /*

    monitoring/cod_day/cod_component/siape_cpf/(monitoring)

    monitors/professors/siape/cpf/(monitor)
    monitors/list_monitors/cpf/siape/cpf
    monitors/monitoring/siape/cpf/cod_component/cod_component

    professors/turmas/cod_component/(subject)

    user/cpf/info/(user)
    user/cpf/favorites/cod_component/(subject)
     */

    /**
     * ======================================
     * -------------- MONITORS --------------
     * ======================================
     */

    /* get URL Monitors
     * -> addMonitorAtList(...)
     * -> addMonitorAtProfessor(...)
     */
    public static String getChildProfMonitors(Context context, String siape) {
        return MONITOR_PER_PROFESSOERS + siape + "/";
    }

    /* Add Monitors
     * -> addMonitorAtList(...)
     * -> addMonitorAtProfessor(...)
     */

    private static void addMonitorAtList(String siape, String cpf) {
        String key = siape + "/" + cpf;
        String rootDir = LIST_MONITOR + "/" + key;
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(rootDir);
        mDatabase.setValue(cpf);
    }

    private static void addMonitorAtProfessor(String siape, String cpf, HashMap<String, Monitor> monitors) {
        String rootDir = MONITOR_PER_PROFESSOERS + siape + "/";
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(rootDir);
        mDatabase.setValue(monitors);
    }

    public static void addMonitor(Context mContext, String siape, String cpf, HashMap<String, Monitor> monitors) {
        UserDao.getLocalUser(mContext);
        addMonitorAtProfessor(siape, cpf, monitors);
        addMonitorAtList(siape, cpf);
    }

    /**
     * ======================================
     * -------------- MONITORING ------------
     * ======================================
     */

    /* get URL MONITORING
     * -> addMonitorAtList(...)
     * -> addMonitorAtProfessor(...)
     */

    public static void addMonitorings(Context context, Monitoring monitoring) {
        int diaCount = 0;
        for (Boolean dia : monitoring.getDias()) {
            diaCount++;
            if (dia) {
                String siape = monitoring.getMonitor().getSiapeProfessor();
                String cpf = monitoring.getMonitor().getCpfCnpj();
                String rootDir = MONITORING + "/" + dia + "/" + monitoring.getCodigoComponente() + "/" + siape + "_" +  cpf;
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(rootDir);
                mDatabase.setValue(monitoring);
            }
        }
    }
    /* Add Monitors
     * -> addMonitorAtList(...)
     * -> addMonitorAtProfessor(...)
     */




    public static String addMonitoringsDIR(Context context, String siape) {
        return MONITORING;

    }

}
