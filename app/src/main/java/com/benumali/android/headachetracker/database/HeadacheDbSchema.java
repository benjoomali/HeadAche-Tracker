package com.benumali.android.headachetracker.database;

public class HeadacheDbSchema {
    public static final class HeadacheTable {
        public static final String NAME = "headaches";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String TIME = "time";
            public static final String DESC = "desc";
            public static final String INOUT = "inside";
        }
    }
}
