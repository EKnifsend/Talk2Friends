package com.example.talk2friends;

public class NativeSpeaker extends User {
    public NativeSpeaker(int id, String email, String name, int age) {
        super(id, email, name, age);

        this.affiliation = "Native Speaker";
    }
    @Override
    public String getAffiliation() {
        return affiliation;
    }
}
