package eventManager;

public abstract class Task {
    private final Event startEvent = new Event() {
        @Override
        public void run() {
            onStartEvent();
        }
    };
    private final Event stopEvent = new Event() {
        @Override
        public void run() {
            onStopEvent();
        }
    };

    public abstract void onStartEvent();

    public abstract void onStopEvent();

    public Event getStartEvent() {
        return startEvent;
    }

    public Event getStopEvent() {
        return stopEvent;
    }
}
