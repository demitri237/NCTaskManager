package ua.edu.sumdu.j2se.zinchenko.tasks;


import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Stream;

public class ArrayTaskList extends AbstractTaskList implements Iterable<Task>, Cloneable {

    private int size = 0;

    Task[] tasks = new Task[10];

    public void add(Task task) {
        if (tasks[tasks.length - 1] != null) {
            tasks = Arrays.copyOf(tasks, tasks.length + size);
        } else {
            size++;
            for (int i = 0; i < size; i++) {
                if (tasks[i] == null) {
                    tasks[i] = task;
                }
            }
        }
    }

    public boolean remove(Task task) {
        for (int i = 0; i < tasks.length; i++) {
            if (tasks[i].equals(task)) {
                System.arraycopy(tasks, i + 1, tasks, i, tasks.length - 1 - i);
                size--;
                return true;

            }
        }
        return false;
    }

    public int size() {
        return size;
    }

    public Task getTask(int index) {
        if (index > tasks.length) {
            throw new IndexOutOfBoundsException("Index greater than the length of the array");
        }
        return tasks[index];
    }

    @Override
    public ListTypes.types getType() {
        return ListTypes.types.ARRAY;
    }

    @Override
    public Stream<Task> getStream() {
        return Stream.of(tasks);
    }

    @Override
    public Iterator<Task> iterator() {
        Iterator<Task> ArrayIterator = new Iterator<Task>() {
            private int current = 0;

            @Override
            public boolean hasNext() {
                return getTask(current) != null;
            }

            @Override
            public Task next() {
                return getTask(current++);
            }

            @Override
            public void remove() throws IllegalStateException {
                ArrayTaskList arrayTaskList = new ArrayTaskList();
                if (current == 0) {
                    throw new IllegalStateException();
                } else {
                    arrayTaskList.tasks = tasks;
                    Task tasksVoid = getTask(--current);
                    arrayTaskList.remove(tasksVoid);
                    tasks = Arrays.copyOf(tasks, tasks.length - 1);
                }
            }
        };
        return ArrayIterator;
    }

    @Override
    public String toString() {
        return "ArrayTaskList{" +
                "size=" + size +
                ", tasks=" + Arrays.toString(tasks) +
                '}';
    }
}

