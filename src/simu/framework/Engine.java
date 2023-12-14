package simu.framework;


import simu.model.Customer;

public abstract class Engine {
    private static final String RED = "\033[0;31m"; // https://gist.github.com/fnky/458719343aabd01cfb17a3a4f7296797
    private static final String WHITE = "\033[0;37m"; // ANSI escape code for white color
    private double simulationTime = 0;
    protected EventList eventList;

    protected double delay = 1;

    public Engine() {
        eventList = new EventList();
        // Service Points are created in the subclass
    }

    public void setSimulationTime(double simulationTime) {
        Clock.getInstance().setClock(0);
        Customer.resetI();
        Customer.resetServiceTimeSum();
        this.simulationTime = simulationTime;
    }

    public void setDelay(double delay) {
        this.delay = delay;
    }

    public double getDelay() {
        return delay;
    }

    public void run() {
        initialize();

        while (simulate()) {
            System.out.printf("\n%sA-phase:%s time is %.2f\n", RED, WHITE, currentTime());
            Clock.getInstance().setClock(currentTime());

            System.out.printf("%sB-phase:%s ", RED, WHITE);
            runBEvents();

            System.out.printf("%s\nC-phase:%s ", RED, WHITE);
            tryCEvents();
            System.out.println("");

            try {
                Thread.sleep((int) (10/delay));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        results();
    }

    private boolean simulate() {
        return Clock.getInstance().getClock() < simulationTime;
    }

    private double currentTime() {
        return eventList.getNextEventTime();
    }

    private void runBEvents() {
        while (eventList.getNextEventTime() == Clock.getInstance().getClock()) {
            runEvent(eventList.remove());
        }
    }

    protected abstract void initialize();
    protected abstract void runEvent(Event e);
    protected abstract void tryCEvents();
    protected abstract void results();

}
