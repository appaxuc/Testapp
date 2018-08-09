package com.example.app.testapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.app.testapp.httpCon.PersonFetchr;
import com.example.app.testapp.httpCon.PersonItem;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PersonFragment extends Fragment {

    private static final String ARG_PERSON_ID = "person_id";
    private static final String TAG = "PersonListF";

    private Person mPerson;
    private TextView mTextView;
    public List<PersonItem> mItems = new ArrayList<>();

    public static PersonFragment newInstance (UUID personId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PERSON_ID, personId);

        PersonFragment fragment = new PersonFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private class FetchItemTask extends AsyncTask<Void, Void, List<PersonItem>> {
        @Override
        protected List<PersonItem> doInBackground(Void... params) {
            mItems = new PersonFetchr().fetchItems();
            //System.out.println(mItems);
            PersonBank.addPerson(mItems);
            return new PersonFetchr().fetchItems();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UUID personId = (UUID) getArguments().getSerializable(ARG_PERSON_ID);
        mPerson = PersonBank.get(getActivity()).getPerson(personId);

        setRetainInstance(true);
        new FetchItemTask().execute();
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
