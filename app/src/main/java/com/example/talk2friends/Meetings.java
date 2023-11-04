package com.example.talk2friends;

import java.util.ArrayList;

public class Meetings {
    private int UserId;
    private ArrayList<MeetingInfo> MeetingIds;

    public Meetings(int userId){
        UserId = userId;
    }
    public void GetMeetings(){
        // populate the Meetings Arraylist
    }
    public void CreateMeeting(String name, String description, String time, String location){
        // Create a firebase entry with new meeting
    }
}
