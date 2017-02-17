package scheduler;

public class TaskScheduler {
    private final EventScheduler eventScheduler = new EventScheduler();

    public void scheduleTask(Task task) {
        eventScheduler.scheduleEvent(task.getStartEvent());
        eventScheduler.scheduleEvent(task.getStopEvent());
    }

    public void scheduleAllTasks(Task... tasks) {
        for (Task task : tasks)
            scheduleTask(task);
    }

    public void rescheduleAllTasks() {
        eventScheduler.rescheduleAllEvents();
    }

    public void cancelTask(Task task) {
        eventScheduler.cancelEvent(task.getStartEvent());
        eventScheduler.cancelEvent(task.getStopEvent());
    }

    public void cancelAllTasks() {
        eventScheduler.cancelAllEvents();
    }
}
