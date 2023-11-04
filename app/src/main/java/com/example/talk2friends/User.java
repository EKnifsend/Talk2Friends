package com.example.talk2friends;

public abstract class User {
    final int ID;
    String email;
    String name;
    int age;
    String affiliation;

    public User(int id, String email, String name, int age) {
        this.ID = id;
        this.email = email;
        this.name = name;
        this.age = age;
    }

    public void changeEmail(String email) {
        this.email = email;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changeAge(int age) {
        this.age = age;
    }

    public int getID() {
        return ID;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public abstract String getAffiliation();
}
