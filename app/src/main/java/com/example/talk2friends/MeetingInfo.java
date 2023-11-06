package com.example.talk2friends;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MeetingInfo implements Parcelable {
    public MeetingInfo() {
    }
    public MeetingInfo(String name, int meetingId, String description, String location, String time, String creatorId, ArrayList<String> attendees) {
        this.name = name;
        this.meetingId = meetingId;
        this.description = description;
        this.location = location;
        this.time = time;
        this.creatorId = creatorId;
        this.attendeeIds = attendees;
    }

    protected MeetingInfo(Parcel in) {
        name = in.readString();
        meetingId = in.readInt();
        description = in.readString();
        location = in.readString();
        time = in.readString();
        creatorId = in.readString();
        attendeeIds = in.readArrayList(null);
    }

    public static final Creator<MeetingInfo> CREATOR = new Creator<MeetingInfo>() {
        @Override
        public MeetingInfo createFromParcel(Parcel in) {
            return new MeetingInfo(in);
        }

        @Override
        public MeetingInfo[] newArray(int size) {
            return new MeetingInfo[size];
        }
    };

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
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(meetingId);
        parcel.writeString(location);
        parcel.writeString(name);
        parcel.writeString(time);
        parcel.writeString(description);
        parcel.writeString(creatorId);
        parcel.writeList(attendeeIds);
    }
}