package com.u1tramarinet.mytodoapp.model.database.sqlite;

import android.provider.BaseColumns;

public final class TodoContract {
    private TodoContract() {
    }

    public static class TodoEntry implements BaseColumns {
        public static final String TABLE_NAME = "todo_entry";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_CONTENT = "content";
        public static final String COLUMN_CREATED_DATE = "created_date";
        public static final String COLUMN_DUE_DATE = "due_date";
    }
}
