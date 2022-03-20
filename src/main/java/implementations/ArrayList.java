package implementations;

import interfaces.List;

import java.util.Arrays;
import java.util.Iterator;


public class ArrayList<E> implements List<E>{

    private static final int INITIAL_SIZE = 4;
    private Object[] elements;
    private int size;

    public ArrayList() {
        this.elements = new Object[INITIAL_SIZE];
        this.size = 0;
    }


    @Override
    public boolean add(E element) {
        if (this.size == this.elements.length) {
            this.elements = grow();
        }
        this.elements[this.size++] = element;

        return true;
    }


    @Override
    public boolean add(int index, E element) {
        checkIndex(index);
        insert(index, element);
        return true;
    }

    @Override
    public E get(int index) {
        checkIndex(index);
        return (E) this.elements[index];
    }

    @Override
    public E set(int index, E element) {
        checkIndex(index);
        E oldElement = (E) this.elements[index];
        this.elements[index] = element;
        return oldElement;
    }

    @Override
    public E remove(int index) {
        checkIndex(index);
        E element = (E) this.elements[index];
        this.elements[index] = null;
        shift(index);
        this.size--;
        ensureCapacity();
        return element;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public int indexOf(E element) {
        int index = -1;
        for (int i = 0; i < elements.length; i++) {
            if (element.equals(elements[i])) {
                index = i;
                return index;
            }
        }
        return index;
    }

    @Override
    public boolean contains(E element) {
        if (!this.isEmpty()) {
            for (int i = 0; i < elements.length; i++) {
                if (elements[i].equals(element)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return this.index < size();
            }

            @Override
            public E next() {
                return get(index++);
            }
        };
    }

    private Object[] grow() {
        return Arrays.copyOf(this.elements, this.elements.length * 2);
    }

    private Object[] shrink() {
        return Arrays.copyOf(this.elements, this.elements.length / 2);
    }

    private void insert(int index, E element) {
        if (this.size == this.elements.length) {
            this.elements = grow();
        }
        E lastElement = (E) this.elements[this.size - 1];
        for (int i = this.size - 1; i > index; i--) {
            this.elements[i] = this.elements[i - 1];
        }
        this.elements[this.size] = lastElement;
        this.elements[index] = element;
        this.size++;
    }

    private void shift(int index) {
        if (index == (this.size - 1)) {
            return;
        }
        for (int i = index; i < this.size; i++) {
            this.elements[i] = this.elements[i + 1];
        }

    }

    private void checkIndex(int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException(String.format("Index out of bounds: %d for size: %d", index, this.size));
        }
    }

    private void ensureCapacity() {
        if (this.size < this.elements.length / 3) {
            this.elements = shrink();
        }
    }
}