package totabraz.com.monitoriasufrn.fragments.observations;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import totabraz.com.monitoriasufrn.R;

public class AddObservationFragment extends Fragment {

    public static AddObservationFragment newInstance() {
        return new AddObservationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_observation, container, false);
    }
}
