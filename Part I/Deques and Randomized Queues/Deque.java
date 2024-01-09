import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private class Node {
        private Item data;
        private Node next;
        private Node prev;

        Node(Item data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }
    }


    private Node head, tail;
    private int size;

    public int size() {
        return size;
    }

    public Deque() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public boolean isEmpty() {
        return (size == 0);
    }


    public void addFirst(Item item) {
        if (item==null) {
            throw  new IllegalArgumentException();
        }
        Node newNode = new Node(item);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        this.size++;
    }

    public void addLast(Item item) {
        if (item==null) {
            throw  new IllegalArgumentException();
        }
        Node newNode = new Node(item);
        if (tail == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        this.size++;
    }

    public Item removeFirst() {
        if(isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = head.data;
        size--;
        if(size==0) {
            tail = null;
            head = null;
            return item;
        }
        head = head.next;
        head.prev = null;
        return item;
    }

    public Item removeLast() {
        if(isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = tail.data;
        size--;
        if(size==0) {
            tail = null;
            head = null;
            return item;
        }
        tail = tail.prev;
        tail.next = null;
        return item;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = head;

        @Override
        public boolean hasNext() {
            return this.current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.data;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    public static void main(String[] args) {
    }

}