package com.example.talk2friends;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public abstract class User implements Parcelable {

    public static final int CLASS_TYPE_INTERNATIONAL = 1;
    public static final int CLASS_TYPE_NATIVE = 2;

    final String ID;
    String email;
    String name;
    int age;
    String affiliation;

    ArrayList<Interests> interests;

    public static boolean addInterest(Interests interest, int userID) {
        return true;
        // add interest to database;
    }

    public static boolean removeInterest(Interests interest, int userID) {
        // attempt to remove interest
        return true;
    }

    public User(String id, String email, String name, int age, String affiliation) {
        this.ID = id;
        this.email = email;
        this.name = name;
        this.age = age;
        this.affiliation = affiliation;
        this.interests = new ArrayList<Interests>();
    }

    protected User(Parcel in) {
        ID = in.readString();
        email = in.readString();
        name = in.readString();
        age = in.readInt();
        affiliation = in.readString();
        interests = in.readArrayList(null);
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

    public String getID() {
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

    public void setInterests(ArrayList<Interests> interests) {
        this.interests = interests;
    }

    public boolean addInterests(Interests interest) {
        if (interests.contains(interest)) {
            return false;
        }
        else {
            interests.add(interest);
            return true;
        }
    }

    public boolean removeInterests(Interests interest) {
        if (interests.contains(interest)) {
            interests.remove(interest);
            return true;
        }
        else {
            return false;
        }
    }

    public ArrayList<Interests> getInterests() {
        return interests;
    }

    public abstract String getAffiliation();

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(ID);
        parcel.writeString(email);
        parcel.writeString(name);
        parcel.writeInt(age);
        parcel.writeString(affiliation);
        parcel.writeList(interests);
    }
}
