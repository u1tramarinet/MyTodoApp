package com.u1tramarinet.mytodoapp.model.database;

import androidx.annotation.NonNull;

import com.u1tramarinet.mytodoapp.model.Callback;
import com.u1tramarinet.mytodoapp.model.Todo;

import java.util.List;

public interface TodoDataSource {
    void create(@NonNull Todo todo, @NonNull Callback<Todo> callback);

    void read(@NonNull Callback<List<Todo>> callback);

    void read(long id, @NonNull Callback<Todo> callback);

    void update(@NonNull Todo todo, @NonNull Callback<Todo> callback);

    void delete(long id, @NonNull Callback<Todo> callback);
}
