package com.example.plantreapp.myPlants;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.plantreapp.R;

public class PlantDialog extends AppCompatDialogFragment {
    private EditText editTextName;
    private EditText editTextDescription;
    private PlantDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_plant_dialog, null);

        builder.setView(view)
                .setTitle("Plant Details")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String name = editTextName.getText().toString();
                        String description = editTextDescription.getText().toString();

                        if (!name.equals("")) {
                            listener.applyTexts(name, description);
                        } else {
                            listener.applyTexts("My Plant", null);
                        }
                    }
                });

        editTextName = view.findViewById(R.id.edit_name);
        editTextDescription = view.findViewById(R.id.edit_description);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (PlantDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }

    public interface PlantDialogListener {
        void applyTexts(String name, String description);
    }
}