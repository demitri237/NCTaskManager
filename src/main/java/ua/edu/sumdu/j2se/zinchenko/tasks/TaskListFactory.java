package ua.edu.sumdu.j2se.zinchenko.tasks;

public class TaskListFactory {
    public static AbstractTaskList createTaskList(ListTypes.types type) {
        switch (type) {
            case ARRAY:
                return new ArrayTaskList();
            case LINKED:
                return new LinkedTaskList();
            default:
                throw new IllegalArgumentException("Incorrect type! Pls enter type: ARRAY or LINKED");
        }
    }
}



/*
public class TaskListFactory {

    static public AbstractTaskList createTaskList(ListTypes.types type) {
        if(type == ListTypes.types.ARRAY) {
            return new ArrayTaskList();
        }
        else {
            return new LinkedTaskList();
        }
    }

}
*/