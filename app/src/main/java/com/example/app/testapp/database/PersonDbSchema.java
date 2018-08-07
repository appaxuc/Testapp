package com.example.app.testapp.database;

public class PersonDbSchema {
    public static final class PersonTable {
        public static final String NAME = "persons";

        public static final class Cols {
            public static final String ID = "id";
            public static final String FIRST_NAME = "first_name";
            public static final String LAST_NAME = "last_name";
            public static final String BIRTH = "birth";
            public static final String SPEC = "spec";
        }
    }
}
