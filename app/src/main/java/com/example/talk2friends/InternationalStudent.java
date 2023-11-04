package com.example.talk2friends;

public class InternationalStudent extends User {
    String nativeLanguage;
    public InternationalStudent(int id, String email, String name, int age, String nativeLanguage) {
        super(id, email, name, age);

        this.affiliation = "International Student";
        this.nativeLanguage = nativeLanguage;
    }
    @Override
    public String getAffiliation() {
        return affiliation;
    }

    public String getNativeLanguage() {
        return nativeLanguage;
    }
}
