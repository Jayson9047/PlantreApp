package com.example.plantreapp.myPlants;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plantreapp.R;
import com.example.plantreapp.entities.Plant;

public class PlantListAdapter extends ListAdapter<Plant, PlantListAdapter.PlantViewHolder> {

    PlantClickInterface plantClickInterface;

    protected PlantListAdapter(@NonNull DiffUtil.ItemCallback<Plant> diffCallback, PlantClickInterface plantClickInterface) {
        super(diffCallback);
        this.plantClickInterface = plantClickInterface;
    }

    @NonNull
    @Override
    public PlantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PlantViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_plant, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PlantViewHolder holder, int position) {
        Plant plant = getItem(position);
        holder.bind(plant);
    }

    class PlantViewHolder extends RecyclerView.ViewHolder {

        private static final String TAG = "PlantViewHolder";
        TextView nameTextView, descriptionTextView;
        ImageButton deleteButton;
        RelativeLayout plantItem;

        public PlantViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextview);
            descriptionTextView = itemView.findViewById(R.id.DescriptionTextView);
            deleteButton = itemView.findViewById(R.id.deletePlantButton);
            plantItem = itemView.findViewById(R.id.plantItem);

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Changed to get item at the position - conforms to db call to delete an item
                    plantClickInterface.onDelete(getItem(getAdapterPosition()));
                }
            });

            plantItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    plantClickInterface.onSelect(getAdapterPosition(), nameTextView.getText().toString());
                }
            });
        }

        public void bind(Plant plant) {
            nameTextView.setText(plant.getName());
            descriptionTextView.setText(plant.getDescription());
        }
    }

    interface PlantClickInterface {
        public void onDelete(Plant plant);
        public void onSelect(int position, String name); // This function may need to be changed to use plant entity
    }
}
