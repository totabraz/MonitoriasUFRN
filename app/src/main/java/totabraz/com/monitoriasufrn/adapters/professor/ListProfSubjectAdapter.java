package totabraz.com.monitoriasufrn.adapters.professor;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import totabraz.com.monitoriasufrn.R;
import totabraz.com.monitoriasufrn.fragments.monitoring.prof.ListProfMonitoringFragment;
import totabraz.com.monitoriasufrn.utils.SysUtils;

public class ListProfSubjectAdapter extends RecyclerView.Adapter<ListProfSubjectAdapter.Holder> {
    private Activity mActivity;
    private ArrayList<String> keys;
    private HashMap<String, String> components;

    private View rootView;

    public ListProfSubjectAdapter(Activity activity, HashMap<String, String> components) {
        this.keys = new ArrayList<String >(components.keySet());
        this.components = components;
        this.mActivity = activity;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview_subject_layout, null);
        return new Holder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position) {
        final String key = keys.get(position);
        holder.tvCodTurma.setText(key);
        holder.tvTurma.setText(components.get(key));
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FragmentTransaction ft = mActivity.getFragmentManager().beginTransaction();
                ListProfMonitoringFragment listProfMonitoringFragment = new ListProfMonitoringFragment();
                Bundle bundle = new Bundle();
                bundle.putString(SysUtils.KEY_COMPONENT,key);
                listProfMonitoringFragment.setArguments(bundle);
                ft.replace(R.id.rlFragmentsArea, listProfMonitoringFragment, "listAll");
                ft.addToBackStack(null);
                ft.commit();
            }
        });


    }

    @Override
    public int getItemCount() {
        return this.keys.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        private TextView tvTurma;
        private TextView tvCodTurma;


        public Holder(View itemView) {
            super(itemView);

            tvTurma = itemView.findViewById(R.id.tvTurma);
            tvCodTurma = itemView.findViewById(R.id.tvCodTurma);

        }
    }
}
