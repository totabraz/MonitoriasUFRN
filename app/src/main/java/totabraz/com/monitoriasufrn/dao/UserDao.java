package totabraz.com.monitoriasufrn.dao;

import android.content.Context;

import java.util.HashMap;

import io.paperdb.Paper;
import totabraz.com.monitoriasufrn.domain.Subject;
import totabraz.com.monitoriasufrn.domain.User;
import totabraz.com.monitoriasufrn.domain.Vinculo;

public abstract class UserDao {

    private static final String USER_LOCAL_ROOT = "user_local";
    private static final String USER_VINCULO_ROOT = "user_vinuclo_local";
    private static final String USER_TURMAS_ROOT = "turmas_local";

    public static void setLocalUser(Context context, User user) {
        Paper.init(context);
        Paper.book().write(USER_LOCAL_ROOT, user);
    }

    public static User getLocalUser(Context context) {
        Paper.init(context);
        return Paper.book().read(USER_LOCAL_ROOT);
    }


    public static void setVinculoDefault(Context context, Vinculo vinculo) {
        Paper.init(context);
        Paper.book().write(USER_VINCULO_ROOT, vinculo);
    }

    public static Vinculo getVinculoDefault(Context context) {
        Paper.init(context);
        return Paper.book().read(USER_VINCULO_ROOT);
    }

    public static void updateTurmas(Context context, Subject subject) {
        Paper.init(context);
        HashMap<String, Subject> subjects = Paper.book().read(USER_TURMAS_ROOT);
        if (subjects != null) {
            if (subjects.isEmpty()) {
                subjects.put(subject.getCodigoComponente(), subject);
                Paper.book().write(USER_TURMAS_ROOT, subjects);
            }
        }
    }
}
