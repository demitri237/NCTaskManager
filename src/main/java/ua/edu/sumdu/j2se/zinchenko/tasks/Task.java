package ua.edu.sumdu.j2se.zinchenko.tasks;

/**
 * Task
 * @author Zinchenko
 * @version 1.0
 *
 * */

public class Task {
    private String title;
    private int time;
    private int start;
    private int end;
    private int interval;
    private boolean active;
    private boolean repeated;

    /**
     * Конструктор Task(String title, int time), що конструює неактивну задачу,
     * яка виконується у заданий час без повторення із заданою назвою.
     * @param time - задає час задачі
     * @param title - задає назву задачі
     * */

    public Task(String title, int time) {
        this.title = title;
        this.time = time;
        repeated=false;
        active=false;
        if (time<0){
            throw new IllegalArgumentException("Time cannot be negative");
        }
    }

    /**
     * Конструктор Task(String title, int start, int end, int interval),
     * що конструює неактивну задачу, яка виконується у заданому проміжку
     * часу (і початок і кінець включно) із заданим інтервалом і має задану назву.
     *@param title - задає назву задачі
     *@param start -початок інтервалу
     * @param end - кінець інтервалу
     * @param interval - повторюванність інтервалу
     * */

    public Task(String title, int start, int end, int interval) {
        this.title = title;
        this.start = start;
        this.end = end;
        this.interval = interval;
        this.repeated=true;
        this.active=false;
        if (start>end || interval > end || interval>(end-start)  ){
            throw new IllegalArgumentException("not correct condition");
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getTime() {
        if (repeated){
            return start;
        }
        else {
            return time;
        }
    }

    public void setTime(int time) {
        if (repeated){
            repeated= false;
        }
        this.time = time;

    }

    public int getStartTime() {
        if (!repeated){
            return time;
        }
        else {
            return start;
        }
    }

    public int getEndTime() {
        if (!repeated){
            return time;
        }
        else {
            return end;
        }
    }

    public int getRepeatInterval() {
        if (!repeated){
            return 0;
        }
        else {
            return interval;
        }
    }

    public void setTime(int start, int end, int interval) {
        if (!repeated){
            repeated= true;
        }
        else {
            this.start = start;
            this.end = end;
            this.interval = interval;
        }
    }

    public boolean isRepeated() {
        return repeated;
    }

    /**
     * метод int nextTimeAfter(int current), повертає час наступного виконання задачі
     * після вказаного часу ,
     * якщо після вказаного часу задача не виконується, то метод має повертати -1.
     * @param current - теперішній час
     * */

    public int nextTimeAfter(int current){
        //якщо неактривна задача
        if (!active){
            return -1;
        }
        else {
            //для неповторюванних задач
            if (!repeated){
                if (current<time){
                    return time;
                }
                else {
                    return -1;
                }
            }
            //для повторюванних задач
            else {
                //якщо період закінчився
                if (current>end){
                    return -1;
                }
                //якщо період не почався
                else if( current<start){
                    return start;
                }
                // саме в час виконання
                //else if((current-start)%interval==0){
                //    return current;
                //}
                //між останнім виконанням і кінцем періода
                else if((start + (interval * (((current-start)/interval)+1)))>end){
                    return -1;
                }
                //всі інші випадки
                else if ((current>=start) && (current<=end)){

                    return start + interval * (((current-start)/interval)+1);


                }
            }
        }return 1;
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", time=" + time +
                ", start=" + start +
                ", end=" + end +
                ", interval=" + interval +
                ", active=" + active +
                ", repeated=" + repeated +
                '}';
    }
}
