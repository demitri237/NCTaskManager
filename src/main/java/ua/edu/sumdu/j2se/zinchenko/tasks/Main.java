package ua.edu.sumdu.j2se.zinchenko.tasks;

public class Main {

	public static void main(String[] args) {
		Task task1 = new Task("run1",8);
		Task task2 = new Task("run2",24);
		Task task3 = new Task("run3",10);
	//	Task task5 = new Task("run5",10);

		task1.setActive(true);
		task2.setActive(true);
		task3.setActive(true);

		ArrayTaskList list1 = new ArrayTaskList();
		list1.add(task1);
		list1.add(task2);
		list1.add(task3);
		System.out.println(list1);

		LinkedTaskList list2 = new LinkedTaskList();
		list2.add(task1);
		list2.add(task2);
		list2.add(task3);
		System.out.println(list2);

		System.out.println(list2.getTask(0));







	}

}

