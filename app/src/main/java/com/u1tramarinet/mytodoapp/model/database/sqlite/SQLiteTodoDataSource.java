package com.u1tramarinet.mytodoapp.model.database.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

import androidx.annotation.NonNull;

import com.u1tramarinet.mytodoapp.model.Callback;
import com.u1tramarinet.mytodoapp.model.Todo;
import com.u1tramarinet.mytodoapp.model.database.TodoDataSource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SQLiteTodoDataSource implements TodoDataSource {
    @NonNull
    private final TodoDbHelper helper;
    private final ExecutorService databaseIO = Executors.newSingleThreadExecutor();

    public SQLiteTodoDataSource(@NonNull Context context) {
        helper = new TodoDbHelper(context);
    }

    @Override
    public void create(@NonNull Todo todo, @NonNull Callback<Todo> callback) {
        runOnDatabaseIO(new Runnable() {
            @Override
            public void run() {
                long newRowId = helper.getWritableDatabase().insert(TodoContract.TodoEntry.TABLE_NAME, null, convertToContentValues(todo));
                if (newRowId != -1) {
                    callback.onSuccess(todo);
                } else {
                    callback.onFailure(new Exception("Failed to insert todo..."));
                }
            }
        });
    }

    @Override
    public void read(@NonNull Callback<List<Todo>> callback) {
        runOnDatabaseIO(new Runnable() {
            @Override
            public void run() {
                SQLiteDatabase db = helper.getReadableDatabase();
                String[] projection = {
                        BaseColumns._ID,
                        TodoContract.TodoEntry.COLUMN_TITLE,
                        TodoContract.TodoEntry.COLUMN_CONTENT,
                        TodoContract.TodoEntry.COLUMN_CREATED_DATE,
                        TodoContract.TodoEntry.COLUMN_DUE_DATE
                };
                List<Todo> todoItems = new ArrayList<>();
                try (Cursor cursor = db.query(TodoContract.TodoEntry.TABLE_NAME, projection, null, null, null, null, null)) {
                    while (cursor.moveToNext()) {
                        todoItems.add(convertToTodo(cursor));
                    }
                }
                callback.onSuccess(todoItems);
            }
        });
    }

    @Override
    public void read(long id, @NonNull Callback<Todo> callback) {
        runOnDatabaseIO(new Runnable() {
            @Override
            public void run() {
                readInternal(id, callback);
            }
        });
    }

    @Override
    public void update(@NonNull Todo todo, @NonNull Callback<Todo> callback) {
        runOnDatabaseIO(new Runnable() {
            @Override
            public void run() {
                SQLiteDatabase db = helper.getWritableDatabase();
                String selection = TodoContract.TodoEntry._ID + " = ?";
                String[] selectionArgs = {String.valueOf(todo.getId())};
                int count = db.update(TodoContract.TodoEntry.TABLE_NAME, convertToContentValues(todo), selection, selectionArgs);
                if (count > 0) {
                    callback.onSuccess(todo);
                } else {
                    callback.onFailure(new Exception("Failed to update..."));
                }
            }
        });
    }

    @Override
    public void delete(long id, @NonNull Callback<Todo> callback) {
        runOnDatabaseIO(new Runnable() {
            @Override
            public void run() {
                readInternal(id, new Callback<Todo>() {
                    @Override
                    public void onSuccess(@NonNull Todo data) {
                        deleteInternal(data, callback);
                    }

                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        callback.onFailure(exception);
                    }
                });
            }
        });
    }

    private void readInternal(long id, @NonNull Callback<Todo> callback) {
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] projection = {
                BaseColumns._ID,
                TodoContract.TodoEntry.COLUMN_TITLE,
                TodoContract.TodoEntry.COLUMN_CONTENT,
                TodoContract.TodoEntry.COLUMN_CREATED_DATE,
                TodoContract.TodoEntry.COLUMN_DUE_DATE
        };
        String selection = TodoContract.TodoEntry._ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        List<Todo> todoItems = new ArrayList<>();
        try (Cursor cursor = db.query(TodoContract.TodoEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null)) {
            if (cursor.moveToNext()) {
                callback.onSuccess(convertToTodo(cursor));
            } else {
                callback.onFailure(new Exception("Not found..."));
            }
        }
    }

    private void deleteInternal(@NonNull Todo todo, @NonNull Callback<Todo> callback) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String selection = TodoContract.TodoEntry._ID + " = ?";
        String[] selectionArgs = {String.valueOf(todo.getId())};
        int deletedRows = db.delete(TodoContract.TodoEntry.TABLE_NAME, selection, selectionArgs);
        if (deletedRows > 0) {
            todo.setId(Todo.ID_UNALLOCATED);
            callback.onSuccess(todo);
        } else {
            callback.onFailure(new Exception("Failed to delete..."));
        }
    }

    private ContentValues convertToContentValues(@NonNull Todo todo) {
        ContentValues values = new ContentValues();
        if (todo.getId() != Todo.ID_UNALLOCATED) {
            values.put(TodoContract.TodoEntry._ID, todo.getId());
        }
        values.put(TodoContract.TodoEntry.COLUMN_TITLE, todo.getTitle());
        values.put(TodoContract.TodoEntry.COLUMN_CONTENT, todo.getContent());
        values.put(TodoContract.TodoEntry.COLUMN_CREATED_DATE, todo.getCreatedDate());
        values.put(TodoContract.TodoEntry.COLUMN_DUE_DATE, todo.getDueDate());
        return values;
    }

    private Todo convertToTodo(@NonNull Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndex(TodoContract.TodoEntry._ID));
        String title = cursor.getString(cursor.getColumnIndex(TodoContract.TodoEntry.COLUMN_TITLE));
        String content = cursor.getString(cursor.getColumnIndex(TodoContract.TodoEntry.COLUMN_CONTENT));
        String createdDate = cursor.getString(cursor.getColumnIndex(TodoContract.TodoEntry.COLUMN_CREATED_DATE));
        String dueDate = cursor.getString(cursor.getColumnIndex(TodoContract.TodoEntry.COLUMN_DUE_DATE));
        Todo item = new Todo();
        item.setId(id);
        item.setTitle(title);
        item.setContent(content);
        item.setCreatedDate(createdDate);
        item.setDueDate(dueDate);
        return item;
    }

    private void runOnDatabaseIO(@NonNull Runnable r) {
        databaseIO.submit(r);
    }

    private void d(String message) {
        Log.d(SQLiteTodoDataSource.class.getSimpleName(), message);
    }
}
