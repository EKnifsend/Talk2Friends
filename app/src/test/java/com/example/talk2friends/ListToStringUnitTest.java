package com.example.talk2friends;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ListToStringUnitTest {
    ArrayList<String> test1;
    ArrayList<String> test2;
    ArrayList<String> test3;
    @Before
    public void SetUp(){
        test1 = new ArrayList<String>();
        test2 = new ArrayList<String>();
        test2.add("hello");
        test2.add("world");
        test3 = null;
    }
    @Test
    public void IsFriendsTest() {
        assertEquals("", MeetingActivity.listToString(test1));
        assertEquals("", MeetingActivity.listToString(test3));
        assertEquals("hello,world", MeetingActivity.listToString(test2));
    }
}