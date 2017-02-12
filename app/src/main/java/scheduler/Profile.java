package scheduler;

import java.util.ArrayList;

public class Profile {
    private final ArrayList<Event> events = new ArrayList<>();
    private final ArrayList<Task> tasks = new ArrayList<>();
    private String name = "Profile";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public void removeEvent(Event event) {
        events.remove(event);
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }
}
