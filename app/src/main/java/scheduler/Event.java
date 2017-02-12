package scheduler;

import java.util.Calendar;
import java.util.TimerTask;

public abstract class Event extends TimerTask {
    private String name = "Event";
    private Calendar startMoment = Calendar.getInstance();
    private boolean repeatableState = false;
    private Calendar repeatPeriod = Calendar.getInstance();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Calendar getStartMoment() {
        return startMoment;
    }

    public void setStartMoment(Calendar startMoment) {
        this.startMoment = startMoment;
    }

    public void setRepeatableState(boolean repeatableState) {
        this.repeatableState = repeatableState;
    }

    public boolean isRepeatable() {
        return repeatableState;
    }

    public Calendar getRepeatPeriod() {
        return repeatPeriod;
    }

    public void setRepeatPeriod(Calendar repeatPeriod) {
        this.repeatPeriod = repeatPeriod;
    }
}
