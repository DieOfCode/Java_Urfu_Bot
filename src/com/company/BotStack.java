package com.company;

import java.util.Stack;

public class BotStack<E> extends Stack<E> {
    @Override
    public E push(E item) {
        if (this.size() >= 10) {
            this.pop();
        }
        return super.push(item);
    }
}
