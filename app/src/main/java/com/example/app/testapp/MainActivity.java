package com.example.app.testapp;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

public class MainActivity extends SingleFragmentActivity {

    private static int kkk = 0;

    private static final String EXTRA_PERSON_ID =
            "com.example.app.testapp.person_id";
    private static final String EXTRA_SPEC_ID =
            "com.example.app.testapp.spec_id";

    public static Intent newIntent (Context packageContext, UUID personId) {
        kkk = 1;
        Intent intent = new Intent(packageContext, MainActivity.class);
        intent.putExtra(EXTRA_PERSON_ID, personId);
        return intent;
    }

    public static Intent specIntent (Context packageContext, int specId) {
        kkk = 2;
        Intent intent = new Intent(packageContext, MainActivity.class);
        intent.putExtra(EXTRA_SPEC_ID, specId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        if (kkk == 1) {
            UUID personId = (UUID) getIntent()
                    .getSerializableExtra(EXTRA_PERSON_ID);
            return PersonFragment.newInstance(personId);
        } else {
            int specId = (int) getIntent()
                    .getSerializableExtra(EXTRA_SPEC_ID);
            return PersonListFragment.newInstance(specId);
        }
    }
}
