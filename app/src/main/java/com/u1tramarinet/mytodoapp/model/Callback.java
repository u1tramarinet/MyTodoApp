package com.u1tramarinet.mytodoapp.model;

import androidx.annotation.NonNull;

public interface Callback<T> {
    void onSuccess(@NonNull T data);

    void onFailure(@NonNull Exception exception);
}
