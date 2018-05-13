package totabraz.com.monitoriasufrn.activities;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import totabraz.com.monitoriasufrn.R;
import totabraz.com.monitoriasufrn.fragments.AddMonitorFragment;
import totabraz.com.monitoriasufrn.fragments.AddObservationFragment;
import totabraz.com.monitoriasufrn.fragments.subject.ListSubjectsFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextInputEditText toolbarSearchInput;
    private Toolbar toolbar;
    private Fragment fragment;
    private FragmentTransaction ft;

    private void drawerPreset(){

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.drawerPreset();

        toolbarSearchInput = findViewById(R.id.toolbarSearchInput);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        final Animation animScaleOpen = AnimationUtils.loadAnimation(this, R.anim.fill_parent_open);
        final Animation animScaleClose = AnimationUtils.loadAnimation(this, R.anim.fill_parent_close);

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            if (toolbarSearchInput.getVisibility()==View.VISIBLE){

                toolbarSearchInput.setVisibility(View.GONE);
                toolbarSearchInput.startAnimation(animScaleOpen);
            } else {
                toolbarSearchInput.startAnimation(animScaleClose);
                toolbarSearchInput.setVisibility(View.VISIBLE);
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_monitorias) {

        } else  if (id == R.id.nav_favourites) {

        } else  if (id == R.id.nav_my_monitors) {
            fragment = AddMonitorFragment.newInstance();
        } else  if (id == R.id.nav_my_subjects) {
            fragment = ListSubjectsFragment.newInstance();
        } else  if (id == R.id.nav_my_observations) {
            fragment = AddObservationFragment.newInstance();
        }
        if (fragment!= null){
            ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.rlFragmentsArea, fragment);
            ft.addToBackStack(null);
            ft.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
