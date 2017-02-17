package scheduler;

import java.util.Calendar;
import java.util.Timer;

public class EventScheduler {
    private final Timer timer = new Timer();
    private final Calendar currentMoment = Calendar.getInstance();

    public void scheduleEvent(Event event) {
        long waitUntilRun = event.getStartMoment().getTimeInMillis() - currentMoment.getTimeInMillis();

        if (waitUntilRun <= 0)
            throw new RuntimeException("Event cannot be scheduled. The start time has already passed.");

        if (event.isRepeatable())
            timer.schedule(event, waitUntilRun, event.getRepeatPeriod().getTimeInMillis());
        else
            timer.schedule(event, waitUntilRun);
    }

    public void cancelEvent(Event event) {
        event.cancel();
        timer.purge();
    }

    public void cancelAllEvents(Event... events) {
        for (Event event : events)
            event.cancel();
        timer.purge();
    }
}
