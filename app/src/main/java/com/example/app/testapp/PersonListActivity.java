package com.example.app.testapp;

import android.support.v4.app.Fragment;

public class PersonListActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment() {
        return new SpecFragment();
    }
}
