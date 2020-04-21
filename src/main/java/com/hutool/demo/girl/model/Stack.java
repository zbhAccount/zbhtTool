package com.hutool.demo.girl.model;


import lombok.ToString;

import java.util.Arrays;
import java.util.Collection;
import java.util.EmptyStackException;
import java.util.List;

/**
 * @author ：bohan.zhou@ucarinc.com
 * @date ：Created in 2020/4/20 16:54
 * @description：自定义泛型类 模拟栈
 * E - Element (在集合中使用，因为集合中存放的是元素)
 * K - Key（键）
 * N - Number（数值类型）
 * T - Type（Java 类）
 * V - Value（值）
 * @version: $
 */
@ToString
public class Stack<E> {
    private E[] elements;
    private int size = 0;
    private static final int DEFAULT_INTTAL_CAPACITY = 16;

    @SuppressWarnings("unchecked")
    public Stack() {
        elements = (E[]) new Object[DEFAULT_INTTAL_CAPACITY];
    }

    public void push(E e) {
        ensureCapacity();
        elements[size++] = e;
    }

    public E pop() {
        if (size == 0) {
            throw new EmptyStackException();
        }
        E result = elements[--size];
        elements[size] = null;
        return result;
    }

    private void ensureCapacity() {
        if (elements.length == size) {
            elements = Arrays.copyOf(elements, 2 * size + 1);
        }
    }

    public void pushAll(Iterable<? extends E> src) {
        for (E e : src) {
            push(e);
        }
    }

    public void popAll(Collection<? super E> dst) {
        while (!isEmpty()) {
            dst.add(pop());
        }
    }

    private boolean isEmpty() {
        return size == 0;
    }

    private <T> void test(List<T> h) {
    }

}

