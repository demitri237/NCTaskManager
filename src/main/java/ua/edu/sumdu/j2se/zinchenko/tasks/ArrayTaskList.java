package ua.edu.sumdu.j2se.zinchenko.tasks;



import java.util.Arrays;

public class ArrayTaskList {

    private int size;
    int capacity;
    private Task[] taskList;

    public ArrayTaskList() {
            capacity=10;
            size=0;
        taskList = new Task[capacity];
    }
    public ArrayTaskList(int capacity) {
        this.capacity =capacity;
        size=0;
        taskList = new Task[capacity];
    }

    public void add(Task task){
        if(task != null){
            if(size==capacity){
                grow();
            }
            taskList[size] = task;
            size++;
        }
    }

    private void grow(){
        capacity = (int)(capacity*1.5);
        Task[] newTaskList = new Task[capacity];
        for (int i = 0;i<taskList.length;i++){
            newTaskList[i]=taskList[i];
        }
        taskList=newTaskList;
    }



    public boolean remove(Task task){
        if(task != null){
            for (int i = 0; i<size;i++){
                if (getTask(i).equals(task)){
                    System.arraycopy(taskList,(i+1),taskList,i,size-i-1);
                    taskList[size-1]= null;
                    size--;
                    return true;
                    } //break;
            }
        }
        return false;
   }



    public int size(){ return size;}


    public Task getTask(int index){ return taskList[index];  }

    public ArrayTaskList incoming(int from, int to){
        ArrayTaskList result = new ArrayTaskList();
        for(int i = 0; i< size; i++){

            if(taskList[i].nextTimeAfter(from) >from && taskList[i].nextTimeAfter(from) <to  ){
                result.add(taskList[i]);
            }

        }
        return result;
    }


    @Override
    public String toString() {
        return "ArrayTaskList{" +
                "size=" + size +
                ", capacity=" + capacity +
                ", taskList=" + Arrays.toString(taskList) +
                '}';
    }
}
