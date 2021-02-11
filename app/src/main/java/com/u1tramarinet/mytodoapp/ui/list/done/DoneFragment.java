package com.u1tramarinet.mytodoapp.ui.list.done;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.u1tramarinet.mytodoapp.R;
import com.u1tramarinet.mytodoapp.ui.list.ListFragment;

public class DoneFragment extends ListFragment {
    private Listener listener;

    public DoneFragment() {
    }

    public static DoneFragment newInstance() {
        return new DoneFragment();
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_done, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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