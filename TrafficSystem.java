package traffic;

import static java.lang.Thread.sleep;

public class TrafficSystem implements Runnable{
    private RoadQueue circularQueue;
    private boolean isStarted = false;
    private int seconds = 0;
    private int numberOfRoads;
    private int interval;

    public TrafficSystem(int numberOfRoads, int interval) {
        this.numberOfRoads = numberOfRoads;
        this.interval = interval;
        circularQueue = new RoadQueue(numberOfRoads, interval);
    }

    private void printInfo() {
        System.out.println("! " + seconds + "s. have passed since system startup !");
        System.out.println("! Number of roads: " + numberOfRoads + " !");
        System.out.println("! Interval: " + interval + " !");
        if (circularQueue.getCurrentSize() > 0) {
            circularQueue.displayQueue();
        }
        System.out.println("! Press \"Enter\" to open menu !");
    }

    public void setStarted(boolean started) {
        isStarted = started;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public void enqueueRoad(String roadName) {
        circularQueue.enqueue(roadName);
    }

    public Road dequeueRoad() {
        return circularQueue.dequeue();
    }

    public boolean isQueueFull() {
        return circularQueue.isFull();
    }

    public boolean isQueueEmpty() {
        return circularQueue.isEmpty();
    }

    public void displayQueue() {
        circularQueue.displayQueue();
    }

    @Override
    public void run() {
        while (true) {
            if (isStarted) {
                printInfo();
            }

            try {
                sleep(1000);
            } catch (InterruptedException e) {
                return;
            }

            seconds++;
            circularQueue.addSecond();
        }
    }
}
