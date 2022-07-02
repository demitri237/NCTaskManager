package ua.edu.sumdu.j2se.zinchenko.tasks;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.StreamSupport;

public class Tasks {

    public static Iterable<Task> incoming(Iterable<Task> tasks, LocalDateTime start, LocalDateTime end) {
        return () -> StreamSupport.stream(tasks.spliterator(), false).filter(task -> task != null && task.nextTimeAfter(start) != null
                && (task.nextTimeAfter(start).isBefore(end) || task.nextTimeAfter(start).isEqual(end))).iterator();
    }

    public static SortedMap<LocalDateTime, Set<Task>> calendar(Iterable<Task> tasks, LocalDateTime start, LocalDateTime end) {
        SortedMap<LocalDateTime, Set<Task>> map = new TreeMap<>();
        for (Task task : tasks) {
            LocalDateTime time = task.nextTimeAfter(start.minusNanos(1));
            while (time != null && !time.isAfter(end)) {
                if (map.containsKey(time)) {
                    map.get(time).add(task);
                } else {
                    Set<Task> taskSet = new HashSet<>();
                    taskSet.add(task);
                    map.put(time, taskSet);
                }
                time = task.nextTimeAfter(time);
            }
        }
        return map;
    }
}

