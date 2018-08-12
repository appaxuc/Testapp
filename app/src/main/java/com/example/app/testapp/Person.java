package com.example.app.testapp;

import java.util.Date;
import java.util.UUID;

public class Person {
    private String mId;
    private UUID mUUID;
    private String mFirstName;
    private String mLastName;
    private Date mBirth;
    private String mSpec;
    private String mTitle;

    public Person() {
        this(UUID.randomUUID());
    }

    public Person (UUID uuid){
        mUUID = uuid;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setId(String id) {
        mId = id;
    }

    public UUID getUUID() {
        return mUUID;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public Date getBirth() {
        return mBirth;
    }

    public void setBirth(Date birth) {
        mBirth = birth;
    }

    public String getSpec() {
        return mSpec;
    }

    public void setSpec(String spec) {
        mSpec = spec;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setDate(Date date) {

    }
}
