package com.example.app.testapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.app.testapp.httpCon.PersonFetchr;

import java.util.UUID;

public class PersonFragment extends Fragment {

    private static final String ARG_PERSON_ID = "person_id";
    private static final String TAG = "PersonListF";

    private Person mPerson;
    private TextView mTextView;

    public static PersonFragment newInstance (UUID personId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PERSON_ID, personId);

        PersonFragment fragment = new PersonFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private class FetchItemTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            new PersonFetchr().fetchItems();
            return null;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        new FetchItemTask().execute();
        UUID personId = (UUID) getArguments().getSerializable(ARG_PERSON_ID);
        mPerson = PersonBank.get(getActivity()).getPerson(personId);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_person, container, false);

        mTextView = v.findViewById(R.id.personBox);
        mTextView.setText(mPerson.getTitle());

        return v;
    }
}
