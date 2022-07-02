package ua.edu.sumdu.j2se.zinchenko.tasks;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Iterator;


public class Main {

	public static void main(String[] args) throws CloneNotSupportedException, IOException {
		System.out.println("Hello");
	Task task1 = new Task("jdj", LocalDateTime.now());
		System.out.println(LocalDateTime.now());

		LinkedTaskList list1 = new LinkedTaskList();
		LinkedTaskList list2 = new LinkedTaskList();
		list1.add(task1);
		list2 = (LinkedTaskList) list1.clone();
		list1.toString();
		list2.toString();

		//TaskIO.writeText(task1, new FileWriter("test.json"));

	}

}

