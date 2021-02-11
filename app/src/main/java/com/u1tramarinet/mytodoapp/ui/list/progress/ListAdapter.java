package com.u1tramarinet.mytodoapp.ui.list.progress;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.u1tramarinet.mytodoapp.R;
import com.u1tramarinet.mytodoapp.model.Todo;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    @NonNull
    private final List<Todo> todoList;

    public ListAdapter(@NonNull List<Todo> dataSet) {
        this.todoList = dataSet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_todo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setTodo(todoList.get(position));
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;

        public ViewHolder(@NonNull View view) {
            super(view);
            title = view.findViewById(R.id.title);
        }

        void setTodo(@NonNull Todo todo) {
            title.setText(todo.getTitle());
        }
    }
}
