package totabraz.com.monitoriasufrn.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import totabraz.com.monitoriasufrn.R;
import totabraz.com.monitoriasufrn.domain.Monitor;

public class ListMonitorsAdapter extends RecyclerView.Adapter<ListMonitorsAdapter.Holder> {
    private ArrayList<Monitor> monitors;
    private Context mContext;

    public ListMonitorsAdapter(Context context, ArrayList<Monitor> monitors) {
        this.monitors = monitors;
        this.mContext = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview_layout_btn_x, null);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Monitor monitor = monitors.get(position);
        holder.tvTitlte.setText(monitor.getNomePessoa());
        holder.tvTitlte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //REMOVE
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.monitors.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        private TextView tvTitlte;
        private Button ivBtn;

        public Holder(View itemView) {
            super(itemView);
            tvTitlte = itemView.findViewById(R.id.tvTitle);
            ivBtn = itemView.findViewById(R.id.ivBtn);
        }
    }
}
