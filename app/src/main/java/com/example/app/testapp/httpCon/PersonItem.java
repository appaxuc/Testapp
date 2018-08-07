package com.example.app.testapp.httpCon;

import com.example.app.testapp.Person;

public class PersonItem extends Person{
    private int mId;
    private String mFirstName;
    private String mLastName;
    private String mBirth;
    private String mSpec;

    @Override
    public String toString() {
        return mFirstName;
    }

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
}
