/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monopoly;

import java.util.function.Consumer;

/**
 *
 * @author Ruslan
 */
public interface Event<T> {

    void addListener(Consumer<T> listener);
    void removeListener(Consumer<T> listener);
    
}