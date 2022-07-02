package ua.edu.sumdu.j2se.zinchenko.tasks;



import java.io.Serializable;
import java.util.Iterator;
import java.util.stream.Stream;

abstract class AbstractTaskList implements Iterable<Task>, Cloneable, Serializable {

    public abstract void add(Task task);

    public abstract Task getTask(int index);

    public abstract boolean remove(Task task);

    public abstract int size();

    public abstract ListTypes.types getType();

    public abstract Stream<Task> getStream();

    @Override
    public int hashCode() {
        int hashCode = 1;
        for (Task task : this) {
            hashCode = 31 * hashCode + (task == null ? 0 : task.hashCode());
        }
        return hashCode;
    }

    @Override
    public Object clone() {
        AbstractTaskList clone = TaskListFactory.createTaskList(getType());
        for (Task task : this) {
            clone.add(task);
        }
        return clone;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof AbstractTaskList) {
            AbstractTaskList otherList = (AbstractTaskList) o;
            if (this.size() == 0 && otherList.size() == 0) {
                return true;
            } else if (this.size() == otherList.size()) {
                Iterator<Task> list1 = this.iterator();
                Iterator<Task> list2 = otherList.iterator();

                while (list1.hasNext()) {
                    Object e1 = list1.next();
                    Object e2 = list2.next();

                    if (!(e1 == null ? e2 == null : e1.equals(e2))) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }
}
