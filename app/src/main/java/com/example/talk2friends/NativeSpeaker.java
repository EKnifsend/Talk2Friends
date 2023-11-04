package com.example.talk2friends;
import android.os.Parcel;

import android.annotation.SuppressLint;
import android.os.Parcel;

import androidx.annotation.NonNull;

public class NativeSpeaker extends User {
    public NativeSpeaker(int id, String email, String name, int age) {
        super(id, email, name, age, "Native Speaker");
    }

    public NativeSpeaker(Parcel in) {
        super(in);
    }
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(CLASS_TYPE_NATIVE);
        super.writeToParcel(parcel, i);
    }
    @Override
    public String getAffiliation() {
        return affiliation;
    }
}
