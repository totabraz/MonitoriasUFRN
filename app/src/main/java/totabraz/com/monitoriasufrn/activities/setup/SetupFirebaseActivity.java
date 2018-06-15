package totabraz.com.monitoriasufrn.activities.setup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;
import totabraz.com.monitoriasufrn.R;
import totabraz.com.monitoriasufrn.activities.MainProfActivity;
import totabraz.com.monitoriasufrn.activities.error.ErrorMsgActivity;
import totabraz.com.monitoriasufrn.dao.UserDao;
import totabraz.com.monitoriasufrn.domain.User;
import totabraz.com.monitoriasufrn.utils.FirebaseUtils;

public class SetupFirebaseActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ProgressBar progress;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_firebase);
        this.mAuth = FirebaseAuth.getInstance();
        this.progress = findViewById(R.id.progress);
        Paper.init(getApplicationContext());
        user = UserDao.getLocalUser(getApplicationContext());
        setupFirebaseUser(user);
    }

    public void setupFirebaseUser(final User user) {
        final boolean[] userNotExist = {true, false};
        this.mAuth.signInWithEmailAndPassword(user.getEmail(), user.getCpfCnpj())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            userNotExist[0] = false;
                            getFirebaseUser(user);
                        } else
                            Log.w(FirebaseUtils.TAG_Firebase, "signInWithEmail:failure", task.getException());
                    }
                });
        if (userNotExist[0]) {
            createUSer(user);
        }
    }

    private void createUSer(final User user) {
        this.mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getCpfCnpj())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) addUser(user);
                        else
                            Log.w(FirebaseUtils.TAG_Firebase, "createUserWithEmail:failure", task.getException());
                    }
                });
    }

    private void addUser(User user) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(FirebaseUtils.USERS).child(user.getCpfCnpj());
        mDatabase.setValue(user);
        UserDao.setLocalUser(getApplicationContext(), user);
        goToNextActivity();

    }

    private void getFirebaseUser(User user) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(FirebaseUtils.USERS).child(user.getCpfCnpj());
        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserDao.setLocalUser(getApplicationContext(), dataSnapshot.getValue(User.class));
                goToNextActivity();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(FirebaseUtils.TAG_Firebase, "loadPost:onCancelled", databaseError.toException());
            }

            @Override
            protected void finalize() throws Throwable {
                super.finalize();

            }
        };
        mDatabase.addValueEventListener(userListener);
    }


    private void goToNextActivity() {
        Intent intent;
        if (user.getVinculos().size() == 1) {
            intent = new Intent(getApplicationContext(), MainProfActivity.class);
            UserDao.setVinculoDefault(getApplicationContext(), user.getVinculos().get(0));
        } else if (user.getVinculos().size() > 1) {
            intent = new Intent(getApplicationContext(), SetupMainVincloActivity.class);
        } else {
            intent = new Intent(getApplicationContext(), ErrorMsgActivity.class);
        }

        startActivity(intent);
        finish();
    }
}
