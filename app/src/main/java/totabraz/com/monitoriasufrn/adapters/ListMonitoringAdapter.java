package totabraz.com.monitoriasufrn.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import totabraz.com.monitoriasufrn.R;
import totabraz.com.monitoriasufrn.domain.Monitoring;

public class ListMonitoringAdapter extends RecyclerView.Adapter<ListMonitoringAdapter.Holder> {
    private ArrayList<Monitoring> monitorings;
    private Context mContext;
    private String cpfMonitor;

    public ListMonitoringAdapter(Context context, ArrayList<Monitoring> monitorings) {
        this.monitorings = monitorings;
        this.mContext = context;
        this.cpfMonitor = null;
    }
    public ListMonitoringAdapter(Context context, ArrayList<Monitoring> monitorings, String cpfMonitor) {
        this.monitorings = monitorings;
        this.mContext = context;
        this.cpfMonitor = cpfMonitor;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview_monitoring_layout, null);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position) {
        final Monitoring monitoring = monitorings.get(position);
        holder.tvSigla.setText(monitoring.getSiglaNivel());
        holder.tvTurma.setText(monitoring.getNomeComponente());
        holder.tvSector.setText(monitoring.getSetor());
        holder.tvClass.setText(monitoring.getSala());
        holder.tvMonitor.setText(monitoring.getMonitor().getNomePessoa());
        holder.tvTime.setText(monitoring.getClassTime());
//
//        holder.tvTitle.setText(monitor.getNomePessoa());
        if (cpfMonitor!=null){
            holder.ivBtn.setVisibility(View.VISIBLE);
            holder.ivBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    monitorings.remove(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return this.monitorings.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        private TextView tvSigla;
        private TextView tvTurma;
        private TextView tvSector;
        private TextView tvClass;
        private TextView tvMonitor;
        private TextView tvTime;
        private ImageView ivBtn;


        public Holder(View itemView) {
            super(itemView);
            tvSigla = itemView.findViewById(R.id.tvSigla);
            tvTurma = itemView.findViewById(R.id.tvTurma);
            tvSector = itemView.findViewById(R.id.tvSector);
            tvClass = itemView.findViewById(R.id.tvClass);
            tvMonitor = itemView.findViewById(R.id.tvMonitor);
            tvTime = itemView.findViewById(R.id.tvTime);
            ivBtn = itemView.findViewById(R.id.ivBtn);

            ivBtn.setVisibility(View.GONE);
        }
    }
}
