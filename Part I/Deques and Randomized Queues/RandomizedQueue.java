import java.util.Iterator;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size;
    private Item[] items;

    public RandomizedQueue() {
        size = 0;
        items = (Item[]) new Object[1];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            copy[i] = items[i];
        }
        items = copy;
    }

    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        items[size++] = item;
        if (size == items.length) resize(2 * items.length);
    }

    public Item dequeue() {
        if (size == 0) {
            throw new java.util.NoSuchElementException();
        }

        int position = StdRandom.uniformInt(size);

        Item item = items[position];
        items[position] = items[size - 1];
        items[--size] = null;
        if (size > 0 && size == items.length / 4)
            resize(items.length / 2);
        return item;
    }

    public Item sample() {
        if (size == 0) {
            throw new java.util.NoSuchElementException();
        }
        int position = StdRandom.uniformInt(size);
        return items[position];
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int size = size();
        private int position = 0;
        private int[] current;

        public RandomizedQueueIterator() {
            current = new int[size];
            for(int i=0;i<size;++i) {
                current[i] = i;
            }
            StdRandom.shuffle(current);
        }
        @Override
        public boolean hasNext() {
            return position < size;
        }

        @Override
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            return items[current[position++]];
        }
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    public static void main(String[] args) {
    }
}