package ua.edu.sumdu.j2se.zinchenko.tasks;




import com.google.gson.Gson;

import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class TaskIO {
    public static void write(AbstractTaskList tasks, OutputStream out) {
        try (DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(out))) {
            dos.writeInt(tasks.size());
            for (Task t : tasks) {
                dos.writeInt(t.getTitle().length());
                dos.writeUTF(t.getTitle());
                if (t.isActive()) {
                    dos.writeInt(1);
                }
                else {
                    dos.writeInt(0);
                }
                dos.writeInt(t.getRepeatInterval());
                if (t.isRepeated()) {
                    dos.writeLong(t.getStartTime().toEpochSecond(ZoneOffset.UTC));
                    dos.writeLong(t.getEndTime().toEpochSecond(ZoneOffset.UTC));
                }
                else {
                    dos.writeLong(t.getTime().toEpochSecond(ZoneOffset.UTC));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void read(AbstractTaskList tasks, InputStream in) {
        try (DataInputStream dis = new DataInputStream(new BufferedInputStream(in))) {
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                int titleLength = dis.readInt();
                String title = dis.readUTF();
                boolean active = (dis.readInt() == 1);
                int interval = dis.readInt();

                Task task;
                if (interval > 0) {
                    task = new Task(title,
                            LocalDateTime.ofEpochSecond(dis.readLong(), 0, ZoneOffset.UTC),
                            LocalDateTime.ofEpochSecond(dis.readLong(), 0, ZoneOffset.UTC),
                            interval);
                } else {
                    task = new Task(title,
                            LocalDateTime.ofEpochSecond(dis.readLong(), 0, ZoneOffset.UTC));
                }
                task.setActive(active);
                tasks.add(task);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeBinary(AbstractTaskList tasks, File file) {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file))) {
            write(tasks, bos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readBinary(AbstractTaskList tasks, File file) {
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            read(tasks, bis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void write(AbstractTaskList tasks, Writer out) {
        String json = new Gson().toJson(tasks);
        try (BufferedWriter writer = new BufferedWriter(out)) {
            writer.write(json);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void read(AbstractTaskList tasks, Reader in) {
        try (BufferedReader reader = new BufferedReader(in)) {
            String json;
            while ((json = reader.readLine()) != null) {
                AbstractTaskList atl = new Gson().fromJson(json, tasks.getClass());
                for (Task t : atl) {
                    tasks.add(t);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeText(AbstractTaskList tasks, File file) {
        String json = new Gson().toJson(tasks);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(json);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void readText(AbstractTaskList tasks, File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String json;
            while ((json = reader.readLine()) != null) {
                AbstractTaskList atl = new Gson().fromJson(json, tasks.getClass());
                for (Task t : atl) {
                    tasks.add(t);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

/*
import com.google.gson.Gson;

import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class TaskIO {
    //записує задачі із списку у потік у бінарному форматі
    public static void write(AbstractTaskList tasks, OutputStream out) throws IOException {
        try (DataOutputStream outStream = new DataOutputStream(out)) {
            outStream.writeInt(tasks.size());
            for (int i = 0; i < tasks.size(); i++) {
                outStream.writeInt(tasks.getTask(i).getTitle().length());
                outStream.writeUTF(tasks.getTask(i).getTitle());
                outStream.writeBoolean(tasks.getTask(i).isActive());
                outStream.writeInt(tasks.getTask(i).getRepeatInterval());

                if (tasks.getTask(i).isRepeated()) {
                    outStream.writeInt(tasks.getTask(i).getStartTime().getNano());
                    outStream.writeInt(tasks.getTask(i).getEndTime().getNano());
                } else {
                    outStream.writeInt(tasks.getTask(i).getTime().getNano());
                }
            }
        }
    }

            //зчитує задачі із потоку у даний список задач
    public static void read(AbstractTaskList tasks, InputStream in) throws IOException {
        try (DataInputStream inStream = new DataInputStream(in)) {
            int size = inStream.readInt();
            for (int i = 0; i < size; i++) {
                int titleLength = inStream.readInt();
                String title = inStream.readUTF();
                boolean isActive = inStream.readBoolean();
                int interval = inStream.readInt();

                if (interval > 0) {
                    LocalDateTime start = LocalDateTime.ofEpochSecond(inStream.readInt(), 0, ZoneOffset.UTC);
                    LocalDateTime end = LocalDateTime.ofEpochSecond(inStream.readInt(), 0, ZoneOffset.UTC);

                    Task repeatTask = new Task(title, start, end, interval);
                    repeatTask.setActive(isActive);
                    tasks.add(repeatTask);
                } else {
                    LocalDateTime time = LocalDateTime.ofEpochSecond(inStream.readInt(), 0, ZoneOffset.UTC);

                    Task nonRepeatTask = new Task(title, time);
                    nonRepeatTask.setActive(isActive);
                    tasks.add(nonRepeatTask);
                }
            }
        }
    }
 // записує задачі зі списку у потік в форматі JSON
    public static void write(AbstractTaskList tasks, Writer out) throws IOException {
        Gson write = new Gson();
        write.toJson(tasks, out);
        out.flush();
    }


       // зчитує задачі із потоку у список
    public static void read(AbstractTaskList tasks, Reader in) {
        Gson read = new Gson();
        AbstractTaskList taskList;
        taskList = read.fromJson(in, tasks.getClass());
        for (Task task : taskList) {
            tasks.add(task);
        }
    }
    //записує задачі із списку у файл
    public static void writeBinary(AbstractTaskList tasks, File file) {
        try (BufferedWriter buffWriter = new BufferedWriter(new FileWriter(file))) {
            write(tasks, buffWriter);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        // зчитує задачі із файлу у список задач
    public static void readBinary(AbstractTaskList tasks, File file) {
        try (BufferedReader buffReader = new BufferedReader(new FileReader(file))) {
            read(tasks, buffReader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //записує задачі у файл у форматі JSON
    public static void writeText(AbstractTaskList tasks, File file) throws IOException {
        try (FileOutputStream outStream = new FileOutputStream(file)) {
            write(tasks, outStream);
        }
    }

        //зчитує задачі із файлу
    public static void readText(AbstractTaskList tasks, File file) throws IOException {
        try (FileInputStream inStream = new FileInputStream(file)) {
            read(tasks, inStream);
        }
    }

}*/