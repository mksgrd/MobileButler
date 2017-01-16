package eventManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;

public class EventScheduler {
    private Timer timer = new Timer();
    private ArrayList<Event> scheduledEvents = new ArrayList<>();
    private Calendar currentMoment = Calendar.getInstance();

    public void scheduleEvent(Event event) {
        scheduledEvents.add(event);

        long waitUntilRun = event.getStartMoment().getTimeInMillis() - currentMoment.getTimeInMillis();

        if (waitUntilRun <= 0)
            waitUntilRun = 1;

        if (event.isRepeatable())
            timer.schedule(event, waitUntilRun, event.getRepeatPeriod().getTimeInMillis());
        else
            timer.schedule(event, waitUntilRun);
    }

    public void cancelEvent(Event event) {
        scheduledEvents.remove(event);
        event.cancel();
        timer.purge();
    }

    public void cancelAllEvents() {
        for (Event scheduledEvent : scheduledEvents)
            scheduledEvent.cancel();
        scheduledEvents.clear();
        timer.purge();
    }
}
