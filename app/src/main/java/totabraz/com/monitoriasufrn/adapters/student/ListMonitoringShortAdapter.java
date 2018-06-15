package totabraz.com.monitoriasufrn.adapters.student;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import totabraz.com.monitoriasufrn.R;
import totabraz.com.monitoriasufrn.utils.FirebaseUtils;

public class ListMonitoringShortAdapter extends RecyclerView.Adapter<ListMonitoringShortAdapter.Holder> {
    private ArrayList<String> monitorings;
    private Context mContext;
    private String cpfMonitor;

    public ListMonitoringShortAdapter(Context context, ArrayList<String> monitorings) {
        this.monitorings = monitorings;
        this.mContext = context;
        this.cpfMonitor = null;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview_monitoring_layout, null);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position) {
        final String monitoring = monitorings.get(position);
        String[] parts = monitoring.split(FirebaseUtils.SPACER_KEY);
        String part1 = parts[0]; // d
        String part2 = parts[1]; // codigo
        String part3 = parts[2]; // turno

        holder.tvDia.setText(part1);
        holder.tvTurma.setText(part2);
        holder.tvTurno.setText(part3);

        if (cpfMonitor != null) {
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
        private TextView tvTurma;
        private TextView tvDia;
        private TextView tvTurno;
        private ImageView ivBtn;


        public Holder(View itemView) {
            super(itemView);

            tvTurma = itemView.findViewById(R.id.tvTurma);
            ivBtn = itemView.findViewById(R.id.ivBtn);
            tvDia = itemView.findViewById(R.id.tvDia);
            tvTurno = itemView.findViewById(R.id.tvTurno);


            ivBtn.setVisibility(View.GONE);
        }
    }
}
