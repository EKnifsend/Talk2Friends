package com.example.talk2friends;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MeetingInfo implements Parcelable {
    public MeetingInfo() {
    }
    public MeetingInfo(String name, int meetingId, String description, String location, String time, String creatorId, ArrayList<String> attendeeIds) {
        this.name = name;
        this.meetingId = meetingId;
        this.description = description;
        this.location = location;
        this.time = time;
        this.creatorId = creatorId;
        this.attendeeIds = attendeeIds;
    }

    public MeetingInfo(String name, int meetingId, String description, String location, String time, String creatorId, String attendeeIds) {
        this.name = name;
        this.meetingId = meetingId;
        this.description = description;
        this.location = location;
        this.time = time;
        this.creatorId = creatorId;
    }

    public String getName() {
        return name;
    }
    public int getMeetingId() {
        return meetingId;
    }
    public String getDescription() {
        return description;
    }
    public String getLocation() {
        return location;
    }
    public String getTime() {
        return time;
    }
    public ArrayList<String> getAttendeeIds() {
        return attendeeIds;
    }
    public String getCreatorId() {
        return creatorId;
    }
    public String name;
    public int meetingId;
    public String description;
    public String location;
    public String time;
    public String creatorId;
    public ArrayList<String> attendeeIds = new ArrayList<String>();

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.meetingId);
        dest.writeString(this.description);
        dest.writeString(this.location);
        dest.writeString(this.time);
        dest.writeString(this.creatorId);
        dest.writeStringList(this.attendeeIds);
    }

    public void readFromParcel(Parcel source) {
        this.name = source.readString();
        this.meetingId = source.readInt();
        this.description = source.readString();
        this.location = source.readString();
        this.time = source.readString();
        this.creatorId = source.readString();
        this.attendeeIds = source.createStringArrayList();
    }

    protected MeetingInfo(Parcel in) {
        this.name = in.readString();
        this.meetingId = in.readInt();
        this.description = in.readString();
        this.location = in.readString();
        this.time = in.readString();
        this.creatorId = in.readString();
        this.attendeeIds = in.createStringArrayList();
    }

    public static final Creator<MeetingInfo> CREATOR = new Creator<MeetingInfo>() {
        @Override
        public MeetingInfo createFromParcel(Parcel source) {
            return new MeetingInfo(source);
        }

        @Override
        public MeetingInfo[] newArray(int size) {
            return new MeetingInfo[size];
        }
    };
}