package com.u1tramarinet.mytodoapp.model.database.realm;

import androidx.annotation.NonNull;

import com.u1tramarinet.mytodoapp.model.Callback;
import com.u1tramarinet.mytodoapp.model.Todo;
import com.u1tramarinet.mytodoapp.model.database.TodoDataSource;

import java.util.List;

public class RealmTodoDataSource implements TodoDataSource {
    @Override
    public void create(@NonNull Todo todo, @NonNull Callback<Todo> callback) {

    }

    @Override
    public void read(@NonNull Callback<List<Todo>> callback) {

    }

    @Override
    public void read(long id, @NonNull Callback<Todo> callback) {

    }

    @Override
    public void update(@NonNull Todo todo, @NonNull Callback<Todo> callback) {

    }

    @Override
    public void delete(long id, @NonNull Callback<Todo> callback) {

    }
}
