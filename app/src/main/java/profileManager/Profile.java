package profileManager;

import eventManager.Event;
import eventManager.Task;

import java.util.ArrayList;

public class Profile {
    private String name = "Profile";
    private ArrayList<Event> events = new ArrayList<>();
    private ArrayList<Task> tasks = new ArrayList<>();

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
