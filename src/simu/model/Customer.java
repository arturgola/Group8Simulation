package simu.model;

import simu.framework.Clock;

public class Customer {
    private static final String YELLOW = "\033[0;33m";
    private static final String WHITE = "\033[0;37m";
    private double arrivalTime;
    private double removalTime;
    private static double serviceTimeSum;
    private int id;
    private static int i = 1;

    public Customer() {
        id = i++;

        arrivalTime = Clock.getInstance().getClock();
        System.out.printf("New customer #%d arrived at %.2f. ", id, arrivalTime);
    }

    public double getArrivalTime() {
        return arrivalTime;
    }

    public double getRemovalTime() {
        return removalTime;
    }

    public void setRemovalTime(double removalTime) {
        this.removalTime = removalTime;
    }

    public int getId() {
        return id;
    }

    public static void resetI() {
        Customer.i = 1;
    }

    public static void resetServiceTimeSum() {
        Customer.serviceTimeSum = 0;
    }

    public void reportResults() {
        serviceTimeSum += (removalTime - arrivalTime);
        double meanServiceTime = serviceTimeSum / id;   // id is the number of customers serviced

        System.out.printf("%sCustomer #%d has been serviced. Customer arrived: %.2f, removed: %.2f, stayed: %.2f, mean %.2f%s.",
                YELLOW, id, arrivalTime, removalTime, (removalTime-arrivalTime), meanServiceTime, WHITE);
    }
}
