package com.ivanmagda.habito.models;

import android.os.Parcel;
import android.os.Parcelable;

public final class Habit implements Parcelable, Cloneable {

    private String mId;
    private HabitRecord mRecord;

    public Habit() {
        this.mRecord = new HabitRecord();
    }

    public Habit(String id, HabitRecord record) {
        this.mId = id;
        this.mRecord = record;
    }

    public Habit(Parcel in) {
        this.mId = in.readString();
        this.mRecord = in.readParcelable(HabitRecord.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeParcelable(mRecord, flags);
    }

    public static final Parcelable.Creator<Habit> CREATOR = new Parcelable.Creator<Habit>() {
        @Override
        public Habit createFromParcel(Parcel parcel) {
            return new Habit(parcel);
        }

        @Override
        public Habit[] newArray(int size) {
            return new Habit[size];
        }
    };

    public Habit copy() {
        return new Habit(mId, mRecord.copy());
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public HabitRecord getRecord() {
        return mRecord;
    }

    public void setRecord(HabitRecord record) {
        this.mRecord = record;
    }

    public boolean isReminderOn() {
        return (mRecord.getReminderHour() != HabitRecord.REMINDER_OFF &&
                mRecord.getReminderMin() != HabitRecord.REMINDER_OFF);
    }

    public synchronized void increaseScore() {
        HabitRecord record = getRecord();
        record.getCheckmarks().add(System.currentTimeMillis());
        record.setScore(record.getCheckmarks().size());
    }

    public synchronized void decreaseScore() {
        HabitRecord record = getRecord();

        int checkmarksCount = record.getCheckmarks().size();
        if (checkmarksCount > 0) {
            record.getCheckmarks().remove(checkmarksCount - 1);
        }

        record.setScore(record.getCheckmarks().size());
    }

    @Override
    public String toString() {
        return "Habit{" +
                "id='" + mId + '\'' +
                ", record=" + mRecord +
                '}';
    }

}
