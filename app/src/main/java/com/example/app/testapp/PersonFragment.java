package com.example.app.testapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.UUID;

public class PersonFragment extends Fragment {

    private static final String ARG_PERSON_ID = "person_id";

    private Person mPerson;

    public static PersonFragment newInstance (UUID personId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PERSON_ID, personId);

        PersonFragment fragment = new PersonFragment();
        fragment.setArguments(args);
        return fragment;
    }                                  // Создание экземпляра фрагмента с получением данных о Person для отображения

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UUID personId = (UUID) getArguments().getSerializable(ARG_PERSON_ID);
        mPerson = PersonBank.get(getActivity()).getPerson(personId);

        setRetainInstance(true);
    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_person, container, false);
        TextView flName = v.findViewById(R.id.person_title_full);
        TextView dateBirth = v.findViewById(R.id.person_birth_full);
        TextView ageFull = v.findViewById(R.id.person_age_full);
        TextView spec = v.findViewById(R.id.person_spec_full);

        flName.setText(mPerson.getFirstName() + " " + mPerson.getLastName());
        dateBirth.setText("Дата рождения: " + mPerson.getBirth());
        spec.setText("Специальность: " + mPerson.getSpec());
        if (mPerson.getAge() == -1) {
            ageFull.setText("Возраст: -");
        } else {
            ageFull.setText("Возраст: " + String.valueOf(mPerson.getAge()));
        }

        return v;
    }                             // Заполнение экрана данными о Person
}
