package com.monopoly;

import java.util.function.Consumer;

public interface Event<T>
{
    void addListener(Consumer<T> listener);

    void removeListener(Consumer<T> listener);
}