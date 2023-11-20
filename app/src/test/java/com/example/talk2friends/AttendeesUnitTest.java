package com.example.talk2friends;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

public class AttendeesUnitTest {
    String test1;
    String test2;
    String test3;
    @Before
    public void SetUp(){
        test1 = "";
        test2 = "hello,world";
        test3 = null;
    }
    @Test
    public void IsFriendsTest() {
        assertEquals(0, MeetingActivity.parseList(test1).size());
        assertEquals(2, MeetingActivity.parseList(test2).size());
        assertEquals("hello", MeetingActivity.parseList(test2).get(0));
        assertEquals("world", MeetingActivity.parseList(test2).get(1));
        assertEquals(0, MeetingActivity.parseList(test3).size());
    }
}