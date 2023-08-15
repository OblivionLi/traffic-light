package traffic;

public class RoadQueue {
    private Road[] roads;
    private int maxSize;
    private int front;
    private int rear;
    private int currentSize;
    private int interval;

    public RoadQueue(int capacity, int interval) {
        this.maxSize = capacity;
        this.roads = new Road[this.maxSize];
        this.front = 0;
        this.rear = -1;
        this.currentSize = 0;
        this.interval = interval;
    }

    public void enqueue(String roadName) {
        if (isFull()) {
            System.out.println("Queue is full. Cannot enqueue.");
            return;
        }

        Road road = new Road(roadName);
        if (currentSize == 0) {
            road.setSeconds(interval);
            road.setOpen(true);
        } else {
            road.setOpen(false);
            int prevIndex = (rear - 1 + maxSize) % maxSize;
            Road prevRoad = roads[prevIndex];
            if (prevRoad != null) {
                road.setSeconds(prevRoad.getSeconds() + interval);
            } else {
                road.setSeconds(interval);
            }
        }

        rear = (rear + 1) % maxSize;
        roads[rear] = road;
        currentSize++;
    }

    public Road dequeue() {
        if (isEmpty()) {
            System.out.println("Queue is empty. Cannot dequeue.");
            return null;
        }

        Road dequeuedVehicle = roads[front];
        roads[front] = null;
        front = (front + 1) % maxSize;
        currentSize--;

        return dequeuedVehicle;
    }

    public boolean isFull() {
        return currentSize == maxSize;
    }

    public boolean isEmpty() {
        return currentSize == 0;
    }

    public void displayQueue() {
        int currentIndex = front;
        int count = 0;

        while (count < currentSize) {
            System.out.println(roads[currentIndex]);
            currentIndex = (currentIndex + 1) % maxSize;
            count++;
        }
    }

    public int getCurrentSize() {
        return currentSize;
    }

    public void addSecond() {
        for (int i = 0; i < currentSize; i++) {

            Road road = roads[(front + i) % maxSize];
            road.setSeconds(road.getSeconds() - 1);

            if (road.isOpen()) {
                if (road.getSeconds() == 0) {
                    if (currentSize == 1) {
                        road.setSeconds(interval);
                    } else {
                        road.setOpen(false);
                        road.setSeconds((currentSize - 1) * interval);
                    }
                }
            } else {
                if (road.getSeconds() == 0) {
                    road.setSeconds(interval);
                    road.setOpen(true);
                }
            }

        }
    }
}