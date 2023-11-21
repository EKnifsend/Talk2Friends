package com.example.talk2friends;
import android.os.Bundle;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CreateMeetingUnitTest {


    @Test
    public void Test_Valid_Meeting() {

        Assert.assertEquals(true, CreateMeetingActivity.CheckInput("a","a","a","a","a"));
        Assert.assertEquals(false, CreateMeetingActivity.CheckInput("","a","a","a","a"));
        Assert.assertEquals(false, CreateMeetingActivity.CheckInput(null,"a","a","a","a"));
        Assert.assertEquals(false, CreateMeetingActivity.CheckInput("a","","","",""));
        Assert.assertEquals(false, CreateMeetingActivity.CheckInput("a",null,null,null,null));

    }
}