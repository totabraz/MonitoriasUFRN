package totabraz.com.monitoriasufrn.adapters.professor;

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
import totabraz.com.monitoriasufrn.domain.Monitoring;
import totabraz.com.monitoriasufrn.utils.SysUtils;

public class ListProfMonitoringAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Object> monitorings;
    private Context mContext;
    private String cpfMonitor;
    private View rootView;

    public ListProfMonitoringAdapter(Context context, ArrayList<Object> monitorings) {
        this.monitorings = monitorings;
        this.mContext = context;
        this.cpfMonitor = null;
    }

    public ListProfMonitoringAdapter(Context context, ArrayList<Object> monitorings, String cpfMonitor) {
        this.monitorings = monitorings;
        this.mContext = context;
        this.cpfMonitor = cpfMonitor;

    }

    @Override
    public int getItemViewType(int position) {
        int viewType = 1;
        String typeClass = "";
        typeClass = typeClass.getClass().getName();
        String nameArray = monitorings.get(position).getClass().getName();

        if (nameArray.equals(typeClass)) viewType = 0;
        return viewType;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview_single_title_layout, null);
            return new HolderTitle(rootView);
        } else {
            rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview_monitoring_prof_layout, null);
            return new HolderMonitoring(rootView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        switch (holder.getItemViewType()) {
            case 0:
                String title = (String) monitorings.get(position);
                title = SysUtils.getNameOfDay(Integer.parseInt(title));
                HolderTitle holderTitle = (HolderTitle) holder;
                holderTitle.tvTitle.setText(title);
                break;
            case 1:
                HolderMonitoring holderMonitoring = (HolderMonitoring) holder;
                Monitoring monitoring = (Monitoring) monitorings.get(position);
                holderMonitoring.tvTurma.setText(monitoring.getNomeComponente());
                holderMonitoring.tvSector.setText(monitoring.getSetor());
                holderMonitoring.tvClass.setText(monitoring.getSala());
                holderMonitoring.tvMonitor.setText(monitoring.getMonitor().getNomePessoa());
                holderMonitoring.tvTime.setText(monitoring.getClassTime());
                if (cpfMonitor != null) {
                    holderMonitoring.ivBtn.setVisibility(View.VISIBLE);
                    holderMonitoring.ivBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            monitorings.remove(position);

                        }
                    });
                }
                break;
        }

    }

    @Override
    public int getItemCount() {
        return this.monitorings.size();
    }

    class HolderMonitoring extends RecyclerView.ViewHolder {
        private TextView tvTurma;
        private TextView tvSector;
        private TextView tvClass;
        private TextView tvMonitor;
        private TextView tvTime;
        private ImageView ivBtn;

        public HolderMonitoring(View itemView) {
            super(itemView);
            tvTurma = itemView.findViewById(R.id.tvTurma);
            tvSector = itemView.findViewById(R.id.tvSector);
            tvClass = itemView.findViewById(R.id.tvClass);
            tvMonitor = itemView.findViewById(R.id.tvMonitor);
            tvTime = itemView.findViewById(R.id.tvTime);
            ivBtn = itemView.findViewById(R.id.ivBtn);
        }
    }

    class HolderTitle extends RecyclerView.ViewHolder {
        private TextView tvTitle;

        public HolderTitle(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
        }
    }
}
