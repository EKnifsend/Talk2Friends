package com.example.talk2friends;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FriendsList implements Parcelable {
    public FriendsList(String id, ArrayList<String> list){
        userId = id;
        friends = list;
    }
    public ArrayList<String> getFriends() {
        return friends;
    }
    public String getUserId() {
        return userId;
    }
    public String userId;
    public ArrayList<String> friends;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userId);
        dest.writeStringList(this.friends);
    }

    public void readFromParcel(Parcel source) {
        this.userId = source.readString();
        this.friends = source.createStringArrayList();
    }

    protected FriendsList(Parcel in) {
        this.userId = in.readString();
        this.friends = in.createStringArrayList();
    }

    public static final Parcelable.Creator<FriendsList> CREATOR = new Parcelable.Creator<FriendsList>() {
        @Override
        public FriendsList createFromParcel(Parcel source) {
            return new FriendsList(source);
        }

        @Override
        public FriendsList[] newArray(int size) {
            return new FriendsList[size];
        }
    };
}
