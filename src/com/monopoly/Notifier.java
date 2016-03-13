package com.monopoly;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Notifier<T> implements Event<T>
{
    private final List<Consumer<T>> listeners = new ArrayList<>();

    @Override
    public void addListener(Consumer<T> listener)
    {
        if (listener == null)
        {
            throw new NullPointerException();
        }

        listeners.add(listener);
    }

    public void doNotify(T value)
    { //we want to pass value to listener
        listeners.stream().forEach((listener) -> listener.accept(value));
    }

    @Override
    public void removeListener(Consumer<T> listener)
    {
        listeners.remove(listener);
    }

}
