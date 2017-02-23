package scheduler;

public class TaskScheduler extends EventScheduler {
    public void scheduleTask(Task task) {
        scheduleEvent(task.getStartEvent());
        scheduleEvent(task.getStopEvent());
    }

    public void scheduleAllTasks(Task... tasks) {
        for (Task task : tasks)
            scheduleTask(task);
    }

    public void rescheduleAllTasks() {
        rescheduleAllEvents();
    }

    public void cancelTask(Task task) {
        cancelEvent(task.getStartEvent());
        cancelEvent(task.getStopEvent());
    }

    public void cancelAllTasks() {
        cancelAllEvents();
    }
}
