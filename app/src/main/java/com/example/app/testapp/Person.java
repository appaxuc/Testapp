package com.example.app.testapp;

import java.util.UUID;

public class Person {
    private int mId;
    private UUID mUUID;
    private String mFirstName;
    private String mLastName;
    private String mBirth;
    private String mSpec;
    private String mTitle;
    private int mAge;

    public int getAge() {
        return mAge;
    }

    public void setAge(int age) {
        mAge = age;
    }

    public Person() {
        this(UUID.randomUUID());
    }

    public Person (UUID uuid){
        mUUID = uuid;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setId(int id) {
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

    public String getBirth() {
        return mBirth;
    }

    public void setBirth(String birth) {
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

    public int getId() {
        return mId;
    }
}
