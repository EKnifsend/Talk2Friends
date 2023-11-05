package com.example.talk2friends;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public abstract class User implements Parcelable {

    public static final int CLASS_TYPE_INTERNATIONAL = 1;
    public static final int CLASS_TYPE_NATIVE = 2;

    final int ID;
    String email;
    String name;
    int age;
    String affiliation;

    public static boolean addInterest(Interests interest, int userID) {
        return true;
        // add interest to database;
    }

    public static boolean removeInterest(Interests interest, int userID) {
        // attempt to remove interest
        return true;
    }

    public User(int id, String email, String name, int age, String affiliation) {
        this.ID = id;
        this.email = email;
        this.name = name;
        this.age = age;
        this.affiliation = affiliation;
    }

    protected User(Parcel in) {
        ID = in.readInt();
        email = in.readString();
        name = in.readString();
        age = in.readInt();
        affiliation = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return User.getChildClass(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public static User getChildClass(Parcel in) {
        switch (in.readInt()) {
            case CLASS_TYPE_INTERNATIONAL:
                return new InternationalStudent(in);
            case CLASS_TYPE_NATIVE:
                return new NativeSpeaker(in);
            default:
                return null;
        }
    }

    public void changeEmail(String email) {
        this.email = email;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changeAge(int age) {
        this.age = age;
    }

    public int getID() {
        return ID;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public abstract String getAffiliation();

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(ID);
        parcel.writeString(email);
        parcel.writeString(name);
        parcel.writeInt(age);
        parcel.writeString(affiliation);
    }
}
