package com.example.talk2friends;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MeetingInfo {
    public MeetingInfo() {
    }
    public MeetingInfo(String name, int meetingId, String description, String location, String time, int creatorId, ArrayList<Integer> attendees) {
        this.Name = name;
        this.MeetingId = meetingId;
        this.Description = description;
        this.Location = location;
        this.Time = time;
        this.CreatorId = creatorId;
        this.AttendeeIds = attendees;
    }
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
    public ArrayList<Integer> getAttendeeIds() {
        return AttendeeIds;
    }
    public int getCreatorId() {
        return CreatorId;
    }
    public String Name;
    public int MeetingId;
    public String Description;
    public String Location;
    public String Time;
    public int CreatorId;
    public ArrayList<Integer> AttendeeIds;
}