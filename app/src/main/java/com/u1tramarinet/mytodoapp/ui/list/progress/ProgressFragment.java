package com.u1tramarinet.mytodoapp.ui.list.progress;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.u1tramarinet.mytodoapp.R;
import com.u1tramarinet.mytodoapp.model.Callback;
import com.u1tramarinet.mytodoapp.model.Todo;
import com.u1tramarinet.mytodoapp.model.TodoRepository;
import com.u1tramarinet.mytodoapp.ui.list.ListFragment;

import java.util.ArrayList;
import java.util.List;

public class ProgressFragment extends ListFragment {
    @Nullable
    private Listener listener;
    @NonNull
    private final TodoRepository repository;
    private final List<Todo> todoList = new ArrayList<>();

    public ProgressFragment() {
        repository = TodoRepository.getInstance(getContext());
    }

    public static ProgressFragment newInstance() {
        return new ProgressFragment();
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.fragment_progress, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        ListAdapter adapter = new ListAdapter(todoList);
        recyclerView.setAdapter(adapter);
        repository.getAll(new Callback<List<Todo>>() {
            @Override
            public void onSuccess(@NonNull List<Todo> data) {
                todoList.clear();
                todoList.addAll(data);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (Listener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement " + Listener.class.getSimpleName());
        }
    }

    public interface Listener {
        void onEvent(Event event);
    }

    public enum Event {

    }
}