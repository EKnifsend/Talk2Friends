package com.example.talk2friends;

import java.lang.reflect.Array;
import java.util.ArrayList;
public class MeetingInfo {
    public MeetingInfo() {
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
    public void AddAttendee(int id){
        AttendeeIds.add(id);
    }
    public void RemoveAttendee(int id){
        AttendeeIds.remove(id);
    }
    public String Name;
    public int MeetingId;
    public String Description;
    public String Location;
    public String Time;
    public int CreatorId;
    public ArrayList<Integer> AttendeeIds;
}
