package com.example.plantreapp.myPlants;

import android.os.Parcel;
import android.os.Parcelable;

public class PlantInfo implements Parcelable {
    private String name, scifiName, uri, description, stage;
    private int seedWaterRate, seedlingWaterRate, matureWaterRate, minSeedMoisture, maxSeedMoisture,
            minSeedlingMoisture, maxSeedlingMoisture, minMatureMoisture, maxMatureMoisture;

    public PlantInfo(String name, String scifiName, String uri, String description, String stage, int seedWaterRate,
                     int seedlingWaterRate, int matureWaterRate, int minSeedMoisture, int maxSeedMoisture,
                     int minSeedlingMoisture, int maxSeedlingMoisture, int minMatureMoisture, int maxMatureMoisture) {
        this.name = name;
        this.scifiName = scifiName;
        this.uri = uri;
        this.description = description;
        this.stage = stage;
        this.seedWaterRate = seedWaterRate;
        this.seedlingWaterRate = seedlingWaterRate;
        this.matureWaterRate = matureWaterRate;
        this.minSeedMoisture = minSeedMoisture;
        this.maxSeedMoisture = maxSeedMoisture;
        this.minSeedlingMoisture = minSeedlingMoisture;
        this.maxSeedlingMoisture = maxSeedlingMoisture;
        this.minMatureMoisture = minMatureMoisture;
        this.maxMatureMoisture = maxMatureMoisture;
    }

    protected PlantInfo(Parcel in) {
        name = in.readString();
        scifiName = in.readString();
        uri = in.readString();
        description = in.readString();
        stage = in.readString();
        seedWaterRate = in.readInt();
        seedlingWaterRate = in.readInt();
        matureWaterRate = in.readInt();
        minSeedMoisture = in.readInt();
        maxSeedMoisture = in.readInt();
        minSeedlingMoisture = in.readInt();
        maxSeedlingMoisture = in.readInt();
        minMatureMoisture = in.readInt();
        maxMatureMoisture = in.readInt();
    }

    public static final Creator<PlantInfo> CREATOR = new Creator<PlantInfo>() {
        @Override
        public PlantInfo createFromParcel(Parcel in) {
            return new PlantInfo(in);
        }

        @Override
        public PlantInfo[] newArray(int size) {
            return new PlantInfo[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScifiName() {
        return scifiName;
    }

    public void setScifiName(String scifiName) {
        this.scifiName = scifiName;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public int getSeedWaterRate() {
        return seedWaterRate;
    }

    public void setSeedWaterRate(int seedWaterRate) {
        this.seedWaterRate = seedWaterRate;
    }

    public int getSeedlingWaterRate() {
        return seedlingWaterRate;
    }

    public void setSeedlingWaterRate(int seedlingWaterRate) {
        this.seedlingWaterRate = seedlingWaterRate;
    }

    public int getMatureWaterRate() {
        return matureWaterRate;
    }

    public void setMatureWaterRate(int matureWaterRate) {
        this.matureWaterRate = matureWaterRate;
    }

    public int getMinSeedMoisture() {
        return minSeedMoisture;
    }

    public void setMinSeedMoisture(int minSeedMoisture) {
        this.minSeedMoisture = minSeedMoisture;
    }

    public int getMaxSeedMoisture() {
        return maxSeedMoisture;
    }

    public void setMaxSeedMoisture(int maxSeedMoisture) {
        this.maxSeedMoisture = maxSeedMoisture;
    }

    public int getMinSeedlingMoisture() {
        return minSeedlingMoisture;
    }

    public void setMinSeedlingMoisture(int minSeedlingMoisture) {
        this.minSeedlingMoisture = minSeedlingMoisture;
    }

    public int getMaxSeedlingMoisture() {
        return maxSeedlingMoisture;
    }

    public void setMaxSeedlingMoisture(int maxSeedlingMoisture) {
        this.maxSeedlingMoisture = maxSeedlingMoisture;
    }

    public int getMinMatureMoisture() {
        return minMatureMoisture;
    }

    public void setMinMatureMoisture(int minMatureMoisture) {
        this.minMatureMoisture = minMatureMoisture;
    }

    public int getMaxMatureMoisture() {
        return maxMatureMoisture;
    }

    public void setMaxMatureMoisture(int maxMatureMoisture) {
        this.maxMatureMoisture = maxMatureMoisture;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(scifiName);
        parcel.writeString(uri);
        parcel.writeString(description);
        parcel.writeString(stage);
        parcel.writeInt(seedWaterRate);
        parcel.writeInt(seedlingWaterRate);
        parcel.writeInt(matureWaterRate);
        parcel.writeInt(minSeedMoisture);
        parcel.writeInt(maxSeedMoisture);
        parcel.writeInt(minSeedlingMoisture);
        parcel.writeInt(maxSeedlingMoisture);
        parcel.writeInt(minMatureMoisture);
        parcel.writeInt(maxMatureMoisture);
    }
}
