package totabraz.com.monitoriasufrn.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import totabraz.com.monitoriasufrn.R;
import totabraz.com.monitoriasufrn.activities.MainTeacherActivity;
import totabraz.com.monitoriasufrn.dao.UserDao;
import totabraz.com.monitoriasufrn.domain.Vinculo;

public class ListVinculosAdapter extends RecyclerView.Adapter<ListVinculosAdapter.Holder> {
    private ArrayList<Vinculo> vinculos;
    private Activity activity;

    public ListVinculosAdapter(Activity activity, ArrayList<Vinculo> vinculos) {
        this.vinculos = vinculos;
        this.activity = activity;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview_layout, null);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        final Vinculo vinculo = vinculos.get(position);

        holder.tvTitlte.setText(vinculo.getIdentificador());

        holder.tvTitlte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserDao.setVinculoDefault(activity, vinculo);
                activity.startActivity(new Intent(activity.getApplicationContext(), MainTeacherActivity.class));
                activity.finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.vinculos.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        private TextView tvTitlte;

        public Holder(View itemView) {
            super(itemView);
            tvTitlte = itemView.findViewById(R.id.tvTitle);
        }
    }
}
