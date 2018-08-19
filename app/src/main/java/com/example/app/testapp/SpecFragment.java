package com.example.app.testapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.app.testapp.httpCon.PersonFetchr;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class SpecFragment extends Fragment {

    final String LOG_TAG = "myLogs";

    private RecyclerView mSpecRecyclerView;
    private SpecAdapter mAdapter;
    private List<Person> mItems;
    private int mPull = -1;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        PersonBank personBank = PersonBank.get(getActivity());
        Log.d(LOG_TAG, "start FetchItemTask()");
        try {
            new FetchItemTask().execute().get(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        View view = inflater.inflate(R.layout.fragment_spec_list, container, false);
        mSpecRecyclerView = view.findViewById(R.id.spec_recycler_view);
        mSpecRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Log.d(LOG_TAG,"RV spec ready");
        updateUI(personBank);
        Log.d(LOG_TAG, "UpdateUI spec finish");
        return view;
    }

    public class FetchItemTask extends AsyncTask<Void, Void, List<Person>> {
        @Override
        protected List<Person> doInBackground(Void... params) {
            mItems = new PersonFetchr().fetchItems();
            Log.d(LOG_TAG, "FetchItem finish, start addPerson");
            PersonBank.addPerson(mItems);
            return mItems;
        }
    }

    private void updateUI(PersonBank personBank) {
        List<Person> persons = personBank.getPersons();
        mAdapter = new SpecAdapter(persons);
        mSpecRecyclerView.setAdapter(mAdapter);
        mAdapter.setPerson(persons);
    }

    private class SpecHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private Person mPerson;
        private TextView mSpecTextView;

        public SpecHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_spec, parent,false));
            itemView.setOnClickListener(this);

            mSpecTextView = itemView.findViewById(R.id.person_spec);
        }

        @SuppressLint("SetTextI18n")
        public void bind(Person person) {
            mPerson = person;
            mSpecTextView.setText(mPerson.getSpec());
        }

        @Override
        public void onClick(View v) {
            Intent intent = MainActivity.newIntent(getActivity(), mPerson.getUUID());
            startActivity(intent);
        }
    }

    private class SpecAdapter extends RecyclerView.Adapter<SpecHolder> {

        private List<Person> mPersons;

        public SpecAdapter(List<Person> persons) {
            mPersons = persons;
        }

        @NonNull
        @Override
        public SpecHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new SpecHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull SpecHolder holder, int position) {
            Person person = mPersons.get(position);
            holder.bind(person);
        }

        @Override
        public int getItemCount() {
            return mPersons.size();
        }

        public void setPerson(List<Person> persons) {
            mPersons = persons;
        }
    }
}
