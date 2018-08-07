package com.example.app.testapp;

public class Person {
    private int mId;
    private String mFirstName;
    private String mLastName;
    private String mBirth;
    private String mSpec;
    private String mTitle;

    public String getFirstName() {
        return mFirstName;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
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
}
