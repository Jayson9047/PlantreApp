package com.example.plantreapp.connection;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plantreapp.R;
import com.example.plantreapp.entities.Moisture;

public class WaterInfoAdapter extends ListAdapter<Moisture, WaterInfoAdapter.WaterInfoViewHolder>  {

    WaterInfoInterface waterInfoInterface;

    public WaterInfoAdapter(@NonNull DiffUtil.ItemCallback<Moisture> diffCallback, WaterInfoAdapter.WaterInfoInterface waterInfoInterface) {
        super(diffCallback);
        this.waterInfoInterface = waterInfoInterface;
    }

    @NonNull
    @Override
    public WaterInfoAdapter.WaterInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WaterInfoAdapter.WaterInfoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_conn_btn, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WaterInfoAdapter.WaterInfoViewHolder holder, int position) {
        Moisture moisture = getItem(position);
        holder.bind(moisture);
    }

    class WaterInfoViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar bar;
        Button waterBtn, selectPlantBtn;
        TextView txt;

        public WaterInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            bar = itemView.findViewById(R.id.progessbar_circular);
            waterBtn = itemView.findViewById(R.id.btnSendPump);
            selectPlantBtn = itemView.findViewById(R.id.btnSelectPlant);
            txt = itemView.findViewById(R.id.text_status);

            waterBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    waterInfoInterface.onWaterBtnClick(getItem(getAdapterPosition()), getAdapterPosition());
                }
            });

            selectPlantBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    waterInfoInterface.onSelectPlantClick(getItem(getAdapterPosition()), getAdapterPosition());
                }
            });
        }

        public void bind(Moisture moisture) {
            bar.setProgress(moisture.getPercentage());
            waterBtn.setText(String.format("%s", moisture.getBtnName()));
            txt.setText(String.format("%s", moisture.getText()));
        }
    }

    public interface WaterInfoInterface {
        void onWaterBtnClick(Moisture moisture, int position);
        void onSelectPlantClick(Moisture moisture, int position);
    }
}