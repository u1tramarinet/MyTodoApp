package com.u1tramarinet.mytodoapp.model.database.mock;

import androidx.annotation.NonNull;

import com.u1tramarinet.mytodoapp.model.Callback;
import com.u1tramarinet.mytodoapp.model.Todo;
import com.u1tramarinet.mytodoapp.model.database.TodoDataSource;

import java.util.ArrayList;
import java.util.List;

public class MockTodoDataSource implements TodoDataSource {
    @NonNull
    private final List<Todo> todoList;

    public MockTodoDataSource() {
        todoList = new ArrayList<>();
    }

    public MockTodoDataSource(int dummyDataCount) {
        this();
        generateDummyData(dummyDataCount);
    }

    @Override
    public void create(@NonNull Todo todo, @NonNull Callback<Todo> callback) {
        long newId = todoList.size() + 1;
        todo.setId(newId);
        todoList.add(todo);
        callback.onSuccess(todo);
    }

    @Override
    public void read(@NonNull Callback<List<Todo>> callback) {
        callback.onSuccess(todoList);
    }

    @Override
    public void read(long id, @NonNull Callback<Todo> callback) {
        for (Todo todo : todoList) {
            if (todo.getId() == id) {
                callback.onSuccess(todo);
                return;
            }
        }
        callback.onFailure(new Exception("not found todo item [id=" + id + "]"));
    }

    @Override
    public void update(@NonNull Todo todo, @NonNull Callback<Todo> callback) {
        for (int i = 0; i < todoList.size(); i++) {
            Todo target = todoList.get(i);
            if (target.getId() == todo.getId()) {
                todoList.set(i, todo);
                callback.onSuccess(todo);
            }
        }
        callback.onFailure(new Exception("Failed to update todo."));
    }

    @Override
    public void delete(long id, @NonNull Callback<Todo> callback) {
        for (int i = 0; i < todoList.size(); i++) {
            Todo target = todoList.get(i);
            if (target.getId() == id) {
                todoList.remove(i);
                target.setId(Todo.ID_UNALLOCATED);
                callback.onSuccess(target);
                return;
            }
        }
        callback.onFailure(new Exception("Failed to delete todo."));
    }

    private void generateDummyData(int count) {
        for (int i = 0; i < count * 2; i++) {
            long newId = todoList.size() + 1;
            Todo todo = new Todo();
            todo.setId(newId);
            todo.setTitle("○○する(" + (i + 1) + "回目)");
            todo.setContent("今度こそ○○する(" + (i + 1) + "回目)");
            todo.setCompleted(i % 2 != 0);
            todoList.add(todo);
        }
    }
}
