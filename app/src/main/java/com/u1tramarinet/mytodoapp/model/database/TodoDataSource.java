package com.u1tramarinet.mytodoapp.model.database;

import androidx.annotation.NonNull;

import com.u1tramarinet.mytodoapp.model.Todo;

import java.util.List;

public interface TodoDataSource {
    interface Callback<T> {
        void onSuccess(@NonNull T data);

        void onFailure(@NonNull Exception exception);
    }

    void create(@NonNull Todo todo, @NonNull Callback<Todo> callback);

    void read(@NonNull Callback<List<Todo>> callback);

    void read(long id, @NonNull Callback<Todo> callback);

    void update(@NonNull Todo todo, @NonNull Callback<Todo> callback);

    void delete(long id, @NonNull Callback<Todo> callback);
}
