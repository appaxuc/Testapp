package com.example.app.testapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.app.testapp.httpCon.PersonFetchr;

import java.util.List;

public class PersonListFragment extends Fragment {

    private RecyclerView mPersonRecyclerView;
    private PersonAdapter mAdapter;
    private List<Person> mItems;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        new FetchItemTask().execute();
        View view = inflater.inflate(R.layout.fragment_person_list, container, false);
        mPersonRecyclerView = view.findViewById(R.id.person_recycler_view);
        mPersonRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }

    public class FetchItemTask extends AsyncTask<Void, Void, List<Person>> {
        @Override
        protected List<Person> doInBackground(Void... params) {
            mItems = new PersonFetchr().fetchItems();
            PersonBank.addPerson(mItems);
            return mItems;
        }
    }

    private void updateUI() {
        PersonBank personBank = PersonBank.get(getActivity());
        List<Person> persons = personBank.getPersons();
        mAdapter = new PersonAdapter(persons);
        mPersonRecyclerView.setAdapter(mAdapter);
    }

    private class PersonHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private Person mPerson;
        private TextView mTitleTextView;

        public PersonHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_person, parent,false));
            itemView.setOnClickListener(this);

            mTitleTextView = itemView.findViewById(R.id.person_title);
        }

        public void bind(Person person) {
            mPerson = person;
            mTitleTextView.setText(mPerson.getFirstName() + " " + mPerson.getLastName());
        }

        @Override
        public void onClick(View v) {
            Intent intent = MainActivity.newIntent(getActivity(), mPerson.getUUID());
            startActivity(intent);
        }
    }

    private class PersonAdapter extends RecyclerView.Adapter<PersonHolder> {

        private List<Person> mPersons;

        public PersonAdapter(List<Person> persons) {
            mPersons = persons;
        }

        @NonNull
        @Override
        public PersonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new PersonHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull PersonHolder holder, int position) {
            Person person = mPersons.get(position);
            holder.bind(person);
        }

        @Override
        public int getItemCount() {
            return mPersons.size();
        }
    }
}
