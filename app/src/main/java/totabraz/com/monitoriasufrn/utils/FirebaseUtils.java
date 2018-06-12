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
    public static final String SPACER_KEY = "___";
    private static final String INFO = "info/";
    /**
     * Monitoria
     */
    private static final String MONITORING_ROOT = PUBLIC_ROOT + "monitoring/";
    private static final String MONITORING = "monitoring/";
    /**
     * Monitores
     */
    private static final String MONITORS_MONITORING = PRIVATE_ROOT + "monitors/monitoring/";
    private static final String MONITORS_LIST = PRIVATE_ROOT + "monitors/list/";
    private static final String MONITORS = "monitors/";
    private static final String MONITORS_ROOT = PRIVATE_ROOT + "monitors/";
    /**
     * Professor
     */
    private static final String PROFESSOR_ROOT = PRIVATE_ROOT + "professors/";
    private static final String PROFESSOR = "professors/";

    public static final String USERS = PRIVATE_ROOT + "user/";

    public static final String SUBJECTS = PUBLIC_ROOT + "subjects/";

    //------------------------------------------------------------------
    //------------------------------------------------------------------
    //------------------------------------------------------------------
//
//    public static String getMonitors(String siape) {
//        return MONITOR_PER_PROFESSOERS + siape + "/monitors/";
//    }
//
//    public static String setMonitor(String siape, String cpf) {
//        return MONITOR_PER_PROFESSOERS + siape + cpf;
//    }
//
//    public static String getChildProfSubjects(String fbUserID) {
//        return PROFESSOR + fbUserID + "/subjects/";
//    }


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
        return MONITORS_LIST + siape + "/";
    }

    /* Add Monitors
     * -> addMonitorAtList(...)
     * -> addMonitorAtProfessor(...)
     */

    private static void addMonitorsAtList(String siape, HashMap<String, Monitor> monitor) {
        String rootDir = PROFESSOR_ROOT + "/" + siape + "/" + MONITORS;
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(rootDir);
        mDatabase.setValue(monitor);
    }


    public static void updateMonitors(Context mContext, String cpf, HashMap<String, Monitor> monitors) {
        String siape = UserDao.getVinculoDefault(mContext).getIdentificador();
        addMonitorsAtList(siape, monitors);
        addMonitorAtProfList(siape, cpf);
    }

    private static void addMonitorAtProfList(String siape, String cpf) {
        String rootDir = PROFESSOR_ROOT + "/" + siape + "/" + MONITORS;
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(rootDir);
        mDatabase.setValue(cpf);
    }

    private static void addMonitorAtMonitorsfList(String siape, String cpf, String monitoringCode) {
        String rootDir = MONITORS_ROOT + "/" + cpf + "/" + siape;
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(rootDir);
        mDatabase.setValue(monitoringCode);
    }

    private static void updateMonitorAtMonitorsfList(String siape, String cpf, String monitoringCode) {
        String rootDir = PROFESSOR_ROOT + "/" + siape + MONITORS + cpf;
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(rootDir);
        mDatabase.setValue(monitoringCode);
    }

    /**
     * ======================================
     * -------------- MONITORIA ------------
     * ======================================
     */

    /* get URL MONITORIA
     * -> addMonitorAtList(...)
     * -> addMonitorAtProfessor(...)
     */
    private static void addMonitoringAtGeralList(String key, Monitoring monitoring) {
        String rootDir = MONITORING_ROOT + monitoring.getCodigoComponente() + "/" + monitoring.getDia() + "/" + key;
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(rootDir);
        mDatabase.setValue(monitoring);
    }

    private static void addMonitoringAtProfList(String siape, String monitoringCode) {
        String rootDir = PROFESSOR_ROOT + MONITORING + siape + "/" + monitoringCode;
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(rootDir);
        mDatabase.setValue(monitoringCode);
    }

    private static void addMonitoringAtMonitorList(String siape, String cpf, String monitoringCode) {
        String rootDir = MONITORS_ROOT;
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(rootDir);
        String monitorsRoot =  MONITORING + cpf+ "/" + siape + "/" + monitoringCode;
        mDatabase.child(monitorsRoot).setValue(monitoringCode);
        String infoRoot =  INFO + cpf;
        mDatabase.child(infoRoot).setValue(cpf);
    }

    public static void addMonitoring(Context mContext, Monitoring monitoring) {
        String dia = monitoring.getDia();
        String codComp = monitoring.getCodigoComponente();
        String siape = UserDao.getVinculoDefault(mContext).getIdentificador();
        String cpf = monitoring.getMonitor().getCpfCnpj();
        String turno = monitoring.getClassTime();

        String monitoringCode = dia + SPACER_KEY + codComp + SPACER_KEY + siape + SPACER_KEY + cpf + SPACER_KEY + turno;

        addMonitoringAtGeralList(monitoringCode, monitoring);
        addMonitoringAtProfList(siape, monitoringCode);
        addMonitoringAtMonitorList(siape, cpf, monitoringCode);
        addMonitorAtMonitorsfList(siape, monitoring.getMonitor().getCpfCnpj(), monitoringCode);
    }

    public static String getAllMonitoring() {
        return  MONITORING_ROOT;
    }

}
