package com.example.plantreapp.logs;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.util.Objects;
import java.util.UUID;

public class Log {
    private String id;
    private String name;
    private String description;

    public Log(String name, String description) {
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
        return "Log{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Log log = (Log) o;
        return id.equals(log.id) &&
                name.equals(log.name) &&
                description.equals(log.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }

    public static DiffUtil.ItemCallback<Log> itemCallback = new DiffUtil.ItemCallback<Log>() {
        @Override
        public boolean areItemsTheSame(@NonNull Log oldItem, @NonNull Log newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Log oldItem, @NonNull Log newItem) {
            return oldItem.equals(newItem);
        }
    };
}
