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

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class SpecFragment extends Fragment {

    final String LOG_TAG = "myLogs";

    private RecyclerView mSpecRecyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        PersonBank personBank = PersonBank.get(getActivity());                                      // Вызвали создание БД поместили в personBank
        try {
            new FetchItemTask().execute().get(2, TimeUnit.SECONDS);                         // Получение данных с сайта и заполнение БД
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
        updateUI(personBank);                                                                       // Заполнение 1го экрана приложения
        Log.d(LOG_TAG, "UpdateUI spec finish");                                                // данными из БД: виды специализаций
        return view;
    }

    @SuppressLint("StaticFieldLeak")
    public class FetchItemTask extends AsyncTask<Void, Void, List<Person>> {
        @Override
        protected List<Person> doInBackground(Void... params) {
            List<Person> items = new PersonFetchr().fetchItems();
            Log.d(LOG_TAG, "FetchItem finish, start addPerson");
            PersonBank.addPerson(items);
            return items;
        }
    }

    private void updateUI(PersonBank personBank) {
        List<Person> persons = personBank.getSpecPerson();                                          // Запрос выборки видов специализаций,
        SpecAdapter adapter = new SpecAdapter(persons);                                             // получение специализаций и
        mSpecRecyclerView.setAdapter(adapter);                                                      // заполнение ими 1го экрана
        adapter.setPerson(persons);
    }

    private class SpecHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private Person mPerson;
        private TextView mSpecTextView;

        SpecHolder(LayoutInflater inflater, ViewGroup parent) {
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
            Log.d(LOG_TAG, mPerson.getSpec());
            Intent intent = MainActivity.specIntent(getActivity(), mPerson.getSpecId());
            startActivity(intent);
        }                                                           // Передача id выбранной специализации для формирования списка 2го экрана
    }

    private class SpecAdapter extends RecyclerView.Adapter<SpecHolder> {

        private List<Person> mPersons;

        SpecAdapter(List<Person> persons) {
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

        void setPerson(List<Person> persons) {
            mPersons = persons;
        }
    }
}
