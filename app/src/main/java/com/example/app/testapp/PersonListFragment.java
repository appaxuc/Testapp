package com.example.app.testapp;

import android.annotation.SuppressLint;
import android.content.Intent;
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

import java.util.List;

public class PersonListFragment extends Fragment {

    private static final String ARG_SPEC_ID = "spec_id";

    private RecyclerView mPersonRecyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        PersonBank personBank = PersonBank.get(getActivity());
        int specId = (int) getArguments().getSerializable(ARG_SPEC_ID);
        View view = inflater.inflate(R.layout.fragment_person_list, container, false);
        mPersonRecyclerView = view.findViewById(R.id.person_recycler_view);
        mPersonRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI(personBank, specId);
        return view;
    }

    public static PersonListFragment newInstance (int specId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_SPEC_ID, specId);

        PersonListFragment fragment = new PersonListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void updateUI(PersonBank personBank, int specId) {
        List<Person> persons = personBank.getPersonFromSpec(specId);
        PersonAdapter adapter = new PersonAdapter(persons);
        mPersonRecyclerView.setAdapter(adapter);
        adapter.setPerson(persons);
    }

    private class PersonHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private Person mPerson;
        private TextView mTitleTextView;
        private TextView mAgeTextView;

        PersonHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_person, parent,false));
            itemView.setOnClickListener(this);

            mTitleTextView = itemView.findViewById(R.id.person_title);
            mAgeTextView = itemView.findViewById(R.id.person_age);
        }

        @SuppressLint("SetTextI18n")
        public void bind(Person person) {
            mPerson = person;
            mTitleTextView.setText(mPerson.getFirstName() + " " + mPerson.getLastName());
            if (mPerson.getAge() == -1) {
                mAgeTextView.setText("Возраст: -");
            } else {
                mAgeTextView.setText("Возраст: " + String.valueOf(mPerson.getAge()));
            }
        }

        @Override
        public void onClick(View v) {
            Intent intent = MainActivity.newIntent(getActivity(), mPerson.getUUID());
            startActivity(intent);
        }
    }

    private class PersonAdapter extends RecyclerView.Adapter<PersonHolder> {

        private List<Person> mPersons;

        PersonAdapter(List<Person> persons) {
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

        void setPerson(List<Person> persons) {
            mPersons = persons;
        }
    }
}
