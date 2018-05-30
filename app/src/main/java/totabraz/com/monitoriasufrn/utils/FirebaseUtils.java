package totabraz.com.monitoriasufrn.utils;

import android.content.Context;

import totabraz.com.monitoriasufrn.dao.UserDao;

public abstract class FirebaseUtils {
    /**
     *  Firebase constants
     */

    public static final String TAG_Firebase = "Firebase method";

    public static final String PUBLIC_ROOT = "public/";
    public static final String SEMI_PUBLIC_ROOT = "semi_public/";
    public static final String PRIVATE_ROOT = "private/";

    public static final String CHILD_MONITORING = PUBLIC_ROOT + "monitoring/";

    public static final String CHILD_MONITOR = PRIVATE_ROOT + "users/monitors/";
    public static final String CHILD_PROFESSOR = PRIVATE_ROOT + "users/professors/";
    public static final String CHILD_STUDENTS = PRIVATE_ROOT + "users/students/";

    public static final String CHILD_SUBJECTS = PUBLIC_ROOT + "subjects/";
    public static final String CHILD_USERS = PRIVATE_ROOT + "users/";

    public static String getMonitors(String siape){
        return CHILD_MONITOR + siape + "/monitors/";
    }

    public static String setMonitor(String siape ,String cpf){
        return CHILD_MONITOR + siape + cpf;
    }

    public static String getChildProfSubjects(String fbUserID){
        return CHILD_PROFESSOR + fbUserID + "/subjects/";
    }

    public static String getChildProfMonitors(Context context){
        return CHILD_PROFESSOR + UserDao.getVinculoDefault(context).getIdentificador() + "/monitors/" ;
    }

}
