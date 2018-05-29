package totabraz.com.monitoriasufrn.dao;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import totabraz.com.monitoriasufrn.activities.MainActivity;
import totabraz.com.monitoriasufrn.domain.User;
import totabraz.com.monitoriasufrn.utils.FirebaseUtils;
import totabraz.com.monitoriasufrn.utils.SysUtils;

/**
 * Created by totabraz on 21/03/18.
 */

public class FirebaseDao {
    private final String TAG;
    private Activity activity;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    public FirebaseDao(Activity activity) {
        this.activity = activity;
        this.mAuth = FirebaseAuth.getInstance();
        this.TAG = "Firebase_Dao";
        this.mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    /**
     * Login & sing up methods set:
     * - setupFirebaseUser(final User user)
     * - createUSer(final User user)
     * - addUser(Usuario user)
     * <p>
     * First try to sign in on Firebase, else
     * if the user does't exist, it'll be created automatically
     * And setup the user's info from UFRN's API on Firebase.
     */

    public void setupFirebaseUser(final User user) {
        final boolean[] userNotExist = {true, false};
        this.mAuth.signInWithEmailAndPassword(user.getEmail(), user.getCpfCnpj())
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            userNotExist[0] = false;
                            getFirebaseUser(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                        }
                    }
                });
        if (userNotExist[0]) {
            createUSer(user);
        }
    }

    private void createUSer(final User user) {
        this.mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getCpfCnpj())
                .addOnCompleteListener(this.activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            addUser(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        }
                    }

                });
    }

    private void addUser(User user) {
        // mDatabase.child(SysUtils.FB_PRIVATE).child(SysUtils.FB_USERS).child(SysUtils.fixeCpf(user.getCpfCnpj())).setValue(user);
        mDatabase.child(FirebaseUtils.CHILD_USERS).child(SysUtils.fixeCpf(user.getCpfCnpj())).setValue(user);
        UserDao.setLocalUser(activity, user);
        Intent intent = new Intent(this.activity, MainActivity.class);
        activity.startActivity(intent);
    }

    /**
     * This method get the User's info from Firebase
     * - getFirebaseUser(Usuario user)
     */

    private void getFirebaseUser(User user) {

        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserDao.setLocalUser(activity, dataSnapshot.getValue(User.class));
                Intent intent = new Intent(activity.getApplicationContext(), MainActivity.class);
                activity.startActivity(intent);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
            @Override
            protected void finalize() throws Throwable {
                super.finalize();

            }
        };
        mDatabase.child(FirebaseUtils.CHILD_USERS).child(user.getCpfCnpj()).addValueEventListener(userListener);
    }

    /**
     * Method to add a evaluation of a teacher
     * - addTeacherRated(String userCPF, String siape, ArrayList<Question> questions)
     * <p>
     * The evaluation is saved as a child of  on this method set the evaluation on the
     * private/teachers/< siape >/< userCPF >/questions
     * linked on User's List:
     * private/users/< userCPF>/evaluated_teachers/< siape >
     *
     * @param userCPF
     * @param siape
     * @param questions
     */

//    public void addTeacherRated(String userCPF, String siape, ArrayList<Question> questions) {
//        mDatabase.child(SysUtils.FB_TEACHERS_DIR).child(siape).child(userCPF).setValue(questions);
//        mDatabase.child(SysUtils.FB_USERS_DIR).child(userCPF).child(SysUtils.FB_EVALUATED_TEACHERS).child(siape).setValue(siape);
//    }

    /**
     * Method to get all teachers rated
     * - getMyTeachersRated()
     * <p>
     * It return a ArrayList<stings> as a list of siapes
     */

//    public ArrayList<String> getMyTeachers(String userCpf) {
//        mDatabase.child(SysUtils.FB_USERS_DIR).child(userCpf).child(SysUtils.FB_EVALUATED_TEACHERS);
//        final ArrayList<String> teachersRated = new ArrayList();
//        ValueEventListener teachersRatedListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
//                    teachersRated.add(objSnapshot.getKey());
//                }
//                SysUtils.setLocalUser(activity, dataSnapshot.getValue(Usuario.class));
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Getting Post failed, log a message
//                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
//            }
//            @Override
//            protected void finalize() throws Throwable {
//                super.finalize();
//            }
//        };
//        mDatabase.addValueEventListener(teachersRatedListener);
//        if (teachersRated.size() < 1) return teachersRated;
//        else return null;
//    }


//    public List<Teacher> getMyTeachersRated(ArrayList<String> siapes){
//        for (String siape : siapes){
//            mDatabase.child(SysUtils.FB_TEACHERS_DIR).child(siape).child()
//        }
//    }


    /**
     * Method to get a specific teacher
     */

//    public Teacher getTeacher(String siape, String userCpf) {
//        final Teacher[] teacher = new Teacher[1];
//        ValueEventListener getTeacher = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                teacher[0] = dataSnapshot.getValue(Teacher.class);
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        };
//        mDatabase.child(SysUtils.FB_TEACHERS_DIR).child(siape).child(userCpf).addValueEventListener(getTeacher);
//        return teacher[0];
//    }

}
