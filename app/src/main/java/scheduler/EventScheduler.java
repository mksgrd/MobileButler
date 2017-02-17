package scheduler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;

public class EventScheduler {
    private final Timer timer = new Timer();
    private final ArrayList<Event> scheduledEvents = new ArrayList<>();

    public void scheduleEvent(Event event) {
        scheduledEvents.add(event);

        long waitUntilRun = event.getStartMoment().getTimeInMillis() - Calendar.getInstance().getTimeInMillis();

        if (waitUntilRun <= 0)
            throw new RuntimeException("Event cannot be scheduled. The start time has already passed.");

        if (event.isRepeatable())
            timer.schedule(event, waitUntilRun, event.getRepeatPeriod().getTimeInMillis());
        else
            timer.schedule(event, waitUntilRun);
    }

    public void rescheduleAllEvents() {
        for (Event scheduledEvent : scheduledEvents)
            scheduleEvent(scheduledEvent);
    }

    public void cancelEvent(Event event) {
        scheduledEvents.remove(event);
        event.cancel();
        timer.purge();
    }

    public void cancelAllEvents() {
        for (Event scheduledEvent : scheduledEvents)
            cancelEvent(scheduledEvent);
    }
}
