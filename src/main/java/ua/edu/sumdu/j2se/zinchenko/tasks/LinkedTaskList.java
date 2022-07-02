package ua.edu.sumdu.j2se.zinchenko.tasks;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

public class LinkedTaskList extends AbstractTaskList implements Iterable<Task>, Cloneable {

    static class Node {
        Task task;
        Node next;

        public Node(Task task) {
            this.task = task;
        }

        public Task getTaskData() {
            return this.task;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }

    private Node first;
    private Node last;
    private int size;

    public void add(Task task) {
        if (task != null) {
            Node newNode = new Node(task);
            if (first == null) {
                first = last = newNode;
            } else {
                last.next = newNode;
                last = newNode;
            }
            size++;
        } else {
            throw new IllegalArgumentException("Tasks cant be null!");
        }
    }

    public Task getTask(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        if (index < size() - 1) {
            Node current = first;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current.getTaskData();
        }
        return last.getTaskData();
    }

    public boolean remove(Task task) {
        Node curr = first;
        Node prev = null;
        for (int i = 0; i < size; i++) {
            if (getTask(i).equals(task)) {
                if (prev != null) {
                    prev.next = curr.next;
                    if (curr.next == null)
                        last = prev;
                } else {
                    first = first.next;
                }
                size--;
                return true;
            }
            prev = curr;
            curr = curr.next;
        }
        return false;
    }

    public int size() {
        return size;
    }

    @Override
    public ListTypes.types getType() {
        return ListTypes.types.LINKED;
    }

    @Override
    public Stream<Task> getStream() {
        Task[] tasks = new Task[this.size()];
        for (int i = 0; i < this.size(); i++) {
            tasks[i] = this.getTask(i);
        }
        return Stream.of(tasks);
    }

    @Override
    public Iterator<Task> iterator() {
        Iterator<Task> LinkedIterator = new Iterator<Task>() {
            Node curr = first;
            Node prev = null;
            Node beforePrev = null;
            boolean removeCalled = false;

            @Override
            public boolean hasNext() {
                return curr != null;
            }

            @Override
            public Task next() {
                if (curr == null) {
                    throw new NoSuchElementException();
                }
                Task tas = curr.getTaskData();
                beforePrev = prev;
                prev = curr;
                curr = curr.getNext();
                removeCalled = false;
                return tas;
            }

            @Override
            public void remove() {
                if (prev == null || removeCalled) {
                    throw new IllegalStateException();
                }
                if (beforePrev == null) {
                    first = curr;
                } else {
                    beforePrev.setNext(curr);
                    prev = beforePrev;
                }
                size--;
                removeCalled = true;
            }
        };
        return LinkedIterator;
    }

    @Override
    public String toString() {
        return "LinkedTaskList{" +
                "first=" + first +
                ", last=" + last +
                ", size=" + size +
                '}';
    }
}

