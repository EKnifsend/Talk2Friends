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
        this.Name = name;
        this.MeetingId = meetingId;
        this.Description = description;
        this.Location = location;
        this.Time = time;
        this.CreatorId = creatorId;
        this.AttendeeIds = attendees;
    }

    protected MeetingInfo(Parcel in) {
        Name = in.readString();
        MeetingId = in.readInt();
        Description = in.readString();
        Location = in.readString();
        Time = in.readString();
        CreatorId = in.readString();
        AttendeeIds = in.readArrayList(null);
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
        return Name;
    }
    public int getMeetingId() {
        return MeetingId;
    }
    public String getDescription() {
        return Description;
    }
    public String getLocation() {
        return Location;
    }
    public String getTime() {
        return Time;
    }
    public ArrayList<String> getAttendeeIds() {
        return AttendeeIds;
    }
    public String getCreatorId() {
        return CreatorId;
    }
    public String Name;
    public int MeetingId;
    public String Description;
    public String Location;
    public String Time;
    public String CreatorId;
    public ArrayList<String> AttendeeIds = new ArrayList<String>();
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(MeetingId);
        parcel.writeString(Location);
        parcel.writeString(Name);
        parcel.writeString(Time);
        parcel.writeString(Description);
        parcel.writeString(CreatorId);
        parcel.writeList(AttendeeIds);
    }
}