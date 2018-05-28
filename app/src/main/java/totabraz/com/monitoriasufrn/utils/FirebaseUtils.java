package totabraz.com.monitoriasufrn.utils;

public abstract class FirebaseUtils {
    /**
     *  Firebase constants
     */

    public static final String TAG_Firebase = "Firebase method";

    public static final String PUBLIC_ROOT = "public";
    public static final String SEMI_PUBLIC_ROOT = "semi_public";
    public static final String PRIVATE_ROOT = "private";

    public static final String CHILD_MONITORING = PUBLIC_ROOT + "/monitoring/";
    public static final String CHILD_MONITOR = PRIVATE_ROOT +"/users/monitors/";
    public static final String CHILD_PROFESSOR = PRIVATE_ROOT +"/users/professors/";

    public static final String CHILD_STUDENTS = PRIVATE_ROOT +"/users/students/";

    public static final String CHILD_SUBJECTS = PUBLIC_ROOT + "/subjects/";
    public static final String CHILD_USERS = PRIVATE_ROOT + "/users/";

    public static String getChildProfSubjects(String fbUserID){
        return PRIVATE_ROOT +"/users/professors/" + fbUserID + "/subjects/";
    }

    public static String getMonitors(String siape){
        return PRIVATE_ROOT +"/users/professors/" + siape + "/monitors/";
    }

    public static String setMonitor(String siape,String matricula){
        return PRIVATE_ROOT +"/users/professors/" + siape + "/monitors/" + matricula;
    }



}
