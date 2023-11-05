package com.example.talk2friends;

public enum Interests {
    FILM(1), READING(2), SPORTS(3), WORKING_OUT(4), ART(5), TRAVEL(6), PHOTOGRAPHY(7);

    final int ID;

    Interests(int ID) {
        this.ID = ID;
    }
}
