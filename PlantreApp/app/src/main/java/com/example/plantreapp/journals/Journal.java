package com.example.plantreapp.journals;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.util.Objects;
import java.util.UUID;

public class Journal {
    private String id;
    private String name;
    private String description;

    public Journal(String name, String description) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    /*public void setId(String id) {
        this.id = id;
    }*/

    public String getName() {
        return name;
    }

    /*public void setName(String name) {
        this.name = name;
    }*/

    public String getDescription() {
        return description;
    }

    /*public void setDescription(String description) {
        this.description = description;
    }*/

    @NonNull
    @Override
    public String toString() {
        return "Journal{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Journal journal = (Journal) o;
        return id.equals(journal.id) &&
                name.equals(journal.name) &&
                description.equals(journal.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }

    public static DiffUtil.ItemCallback<Journal> itemCallback = new DiffUtil.ItemCallback<Journal>() {
        @Override
        public boolean areItemsTheSame(@NonNull Journal oldItem, @NonNull Journal newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Journal oldItem, @NonNull Journal newItem) {
            return oldItem.equals(newItem);
        }
    };
}
