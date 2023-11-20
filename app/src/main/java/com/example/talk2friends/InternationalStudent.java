package com.example.talk2friends;
import android.os.Parcel;

public class InternationalStudent extends User {
    String nativeLanguage;
    public InternationalStudent(String id, String email, String name, int age, String nativeLanguage) {
        super(id, email, name, age, "International Student");

        this.nativeLanguage = nativeLanguage;
    }

    public InternationalStudent(Parcel in) {
        super(in);
        nativeLanguage = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(CLASS_TYPE_INTERNATIONAL);
        super.writeToParcel(parcel, i);
        parcel.writeString(nativeLanguage);
    }
    @Override
    public String getAffiliation() {
        return affiliation;
    }

    public String getNativeLanguage() {
        return nativeLanguage;
    }
}
