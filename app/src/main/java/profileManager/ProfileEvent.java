package profileManager;

import eventManager.Event;
import eventManager.EventScheduler;
import eventManager.Task;
import eventManager.TaskScheduler;

public class ProfileEvent extends Event {
    private final EventScheduler eventScheduler = new EventScheduler();
    private final TaskScheduler taskScheduler = new TaskScheduler();
    private Profile profile = null;

    public ProfileEvent(Profile profile) {
        this.profile = profile;
    }

    @Override
    public void run() {
        for (Event event : profile.getEvents())
            eventScheduler.scheduleEvent(event);

        for (Task task : profile.getTasks())
            taskScheduler.scheduleTask(task);
    }
}
