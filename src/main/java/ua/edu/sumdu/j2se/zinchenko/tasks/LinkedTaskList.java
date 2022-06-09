package ua.edu.sumdu.j2se.zinchenko.tasks;

public class LinkedTaskList {



    private static class Node {

        private final Task data;
        private Node next;

        public Node(Task data) {
            this.data = data;
            next = null;
        }
    }

    private Node head;
    private int size;
    public int size(){ return size;}


    /**
     * return task from specified position
     * @param index-tast to return
     * */
    public Task getTask(int index){
        if (index<0 || index>=size){
            throw new IndexOutOfBoundsException("Your index out of range");
        }

        Node  node = head;
        int tmp = 0;

        while (tmp != index){
            node=node.next;
            tmp++;
        }

        return node.data;
    }

    public void add(Task task){
        if (task==null){
            throw new IllegalArgumentException("Task should not be null");
        }

        Node newNode = new Node(task);
        //Node currentNode = head;

        if (head!=null){
            newNode.next=head;
        }
        head= newNode;
        size++;
    }

    public boolean remove(Task task){
        if(head==null){
            return false;
        } else if (head.data.equals(task)) {
            head=head.next;
            size--;
            return true;
        } else{
            Node currentNode = head;
            while(currentNode.next!=null){
                if(currentNode.next.data.equals(task)){
                    currentNode.next=currentNode.next.next;
                    size--;
                    return  true;
                }
                currentNode=currentNode.next;
            }
        }
        return false;
    }



    public void print(){
        Node currentNode =head;

        if(head!=null){
            System.out.println(head.data);
        }
        while (currentNode.next!=null){
            currentNode=currentNode.next;
            System.out.println(currentNode.data);
        }
    }

    public LinkedTaskList incoming(int from, int to){
        LinkedTaskList taskList = new LinkedTaskList();
        if (size==0){
            return taskList;
        }
        Node node= head;
        while( node != null){
            int nextTime = node.data.nextTimeAfter(from);
            if( from < nextTime && nextTime <= to){
                taskList.add(node.data);
            }
            node=node.next;
        }
        return taskList;
    }

    @Override
    public String toString() {
        return "LinkedTaskList{" +
                "head=" + head +
                ", size=" + size +
                '}';
    }
}
