package com.example.plantreapp.connection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plantreapp.R;

import java.util.ArrayList;

public class WaterInfoAdapter extends RecyclerView.Adapter<WaterInfoAdapter.WaterInfoHolder> {
    private Context context;
    private ArrayList<WaterInfo> waterInfoArr;

    public WaterInfoAdapter(Context context, ArrayList<WaterInfo> waterInfoArr) {
        this.context = context;
        this.waterInfoArr = waterInfoArr;
    }

    @NonNull
    @Override
    public WaterInfoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_row_sensor,parent,false);
        return new WaterInfoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WaterInfoHolder holder, int position) {
        WaterInfo waterInfo = waterInfoArr.get(position);
        holder.setDetails(waterInfo);
    }

    @Override
    public int getItemCount() {
        return waterInfoArr.size();
    }

    public class WaterInfoHolder extends RecyclerView.ViewHolder {
        private ProgressBar bar;
        Button btn;
        TextView txt;

        public WaterInfoHolder(@NonNull View itemView) {
            super(itemView);
            bar = itemView.findViewById(R.id.progessbar_circular);
            btn = itemView.findViewById(R.id.btnSendPump);
            txt = itemView.findViewById(R.id.text_status);
        }

        public void setDetails(WaterInfo waterInfo) {
            bar.setProgress(waterInfo.getProgress());
            btn.setText(String.format("%s", waterInfo.getBtnName()));
            txt.setText(String.format("%s", waterInfo.getText()));
        }
    }
}