package com.u1tramarinet.mytodoapp.model;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.u1tramarinet.mytodoapp.model.database.TodoDataSource;
import com.u1tramarinet.mytodoapp.model.database.sqlite.SQLiteTodoDataSource;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TodoRepository {
    @Nullable
    private static TodoRepository INSTANCE;
    @NonNull
    private final ExecutorService backgroundExecutor = Executors.newSingleThreadExecutor();
    private final TodoDataSource dataSource;

    private TodoRepository(@NonNull Context context) {
        dataSource = new SQLiteTodoDataSource(context);
    }

    public synchronized static TodoRepository getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new TodoRepository(context);
        }
        return INSTANCE;
    }

    private void runOnBackground(@NonNull Runnable r) {
        backgroundExecutor.submit(r);
    }

    private <T> Future<T> runOnBackground(@NonNull Callable<T> c) {
        return backgroundExecutor.submit(c);
    }
}
