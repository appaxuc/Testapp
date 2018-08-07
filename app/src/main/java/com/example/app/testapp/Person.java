package com.example.app.testapp;

import java.util.UUID;

public class Person {

    private UUID mUUID;
    private String mTitle;

    Person() {
        mUUID = UUID.randomUUID();
    }

    public UUID getUUID() {
        return mUUID;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
}
