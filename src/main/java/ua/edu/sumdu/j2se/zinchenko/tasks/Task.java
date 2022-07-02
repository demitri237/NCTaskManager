package ua.edu.sumdu.j2se.zinchenko.tasks;

/**
 * Task
 * @author Zinchenko
 * @version 1.0
 *
 * */

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task implements Cloneable, Serializable {
    private String title;
    private LocalDateTime start;
    private LocalDateTime time;
    private LocalDateTime end;
    private int interval;
    private boolean isActive;
    private boolean isRepeated;

    public Task(String title, LocalDateTime time) {
        this.title = title;
        this.time = time;
        isRepeated = false;
        if (time == null) {
            throw new IllegalArgumentException("Time can not be null");
        }
    }

    public Task(String title, LocalDateTime start, LocalDateTime end, int interval) {
        this.title = title;
        this.start = start;
        this.end = end;
        this.interval = interval;
        isRepeated = true;
        if (start == null || end == null) {
            throw new IllegalArgumentException("Time can not be less than 0");
        }
        if (interval < 0) {
            throw new IllegalArgumentException("Interval cant be less than 0");
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getStartTime() {
        if (isRepeated) {
            return start;
        }
        return time;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getTime() {
        if (isRepeated) {
            return start;
        }
        return time;
    }

    public void setTime(LocalDateTime time) {
        if (isRepeated()) {
            setRepeated(false);
        }
        this.time = time;
    }

    public void setTime(LocalDateTime start, LocalDateTime end, int interval) {
        if (!isRepeated()) {
            setRepeated(true);
        }
        this.start = start;
        this.end = end;
        this.interval = interval;
    }

    public LocalDateTime getEndTime() {
        if (isRepeated) {
            return end;
        }
        return time;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isRepeated() {
        return isRepeated;
    }

    public void setRepeated(boolean repeated) {
        isRepeated = repeated;
    }

    public int getRepeatInterval() {
        if (isRepeated) {
            return interval;
        }
        return 0;
    }

    public LocalDateTime nextTimeAfter(LocalDateTime current) {
        if (isActive()) {
            if (!isRepeated()) {
                if (current.isBefore(getTime())) {
                    return getTime();
                }
                return null;
            } else if (isRepeated()) {
                if (current.isBefore(getStartTime())) {
                    return getStartTime();
                } else if (getStartTime().isBefore(current) || getStartTime().isEqual(current)) {
                    LocalDateTime nextTime = getStartTime();
                    while (nextTime.isBefore(current) || nextTime.isEqual(current)) {
                        nextTime = nextTime.plusSeconds(getRepeatInterval());
                    }
                    if (nextTime.isAfter(getEndTime())) {
                        return null;
                    }
                    return nextTime;
                }
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return start == task.start && time == task.time && end == task.end && interval == task.interval && isActive == task.isActive && isRepeated == task.isRepeated && Objects.equals(title, task.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, start, time, end, interval, isActive, isRepeated);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", start=" + start +
                ", time=" + time +
                ", end=" + end +
                ", interval=" + interval +
                ", isActive=" + isActive +
                ", isRepeated=" + isRepeated +
                '}';
    }
}






