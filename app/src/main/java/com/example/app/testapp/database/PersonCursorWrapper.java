package com.example.app.testapp.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.app.testapp.Person;

import java.util.UUID;

import static com.example.app.testapp.database.PersonDbSchema.PersonTable.*;

public class PersonCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public PersonCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Person getPerson() {
        String uuidString = getString(getColumnIndex(Cols.UUID));
        int id = getInt(getColumnIndex(Cols.ID));
        String firstName = getString(getColumnIndex(Cols.FIRST_NAME));
        String lastName = getString(getColumnIndex(Cols.LAST_NAME));
        String birth = getString(getColumnIndex(Cols.BIRTH));
        String spec = getString(getColumnIndex(Cols.SPEC));

        Person person = new Person(UUID.fromString(uuidString));
        person.setId(id);
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setBirth(birth);
        person.setSpec(spec);

        return person;
    }
}
