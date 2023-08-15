package traffic;

import traffic.Main;

import static traffic.Main.interval;

public class RoadsQueue {
    private Road[] queue;
    private int capacity;
    private int start;
    private int end;
    private int numElements;


    public RoadsQueue(int capacity) throws Exception {
        if (capacity <= 0) throw new Exception("Wrong queue capacity");
        this.queue = new Road[capacity];
        this.capacity = capacity;
        this.start = 0;
        this.end = 0;
        this.numElements = 0;
    }

    public boolean enqueue(String elem) {
        if (numElements == capacity || elem == null) return false;

        Road road = new Road(elem);
        if (numElements == 0) {
            road.setSeconds(interval + 1);
            road.setOpen(true);
        } else {
            road.setOpen(false);
            int prevIndex = (end - 1) % capacity;
            Road prevRoad = queue[prevIndex];
            if (prevRoad.isOpen()) road.setSeconds(prevRoad.getSeconds());
            else road.setSeconds(prevRoad.getSeconds() + interval);
        }

        queue[end] = road;
        end = (end + 1) % capacity;
        numElements++;
        return true;
    }

    public String dequeue() {
        if (numElements == 0) return null;

        Road elem = queue[start];
        start = (start + 1) % capacity;
        numElements--;
        return elem.getName();
    }

    @Override
    public String toString() {
        if (numElements == 0) return "";
        StringBuilder builder = new StringBuilder("\n");
        for (int i = 0; i < numElements; i++) {
            builder.append(queue[(start + i) % capacity] + "\n");
        }
        builder.append("\n");
        return builder.toString();
    }

    public void print() {
        if (numElements == 0) return;
        System.out.println("");
        ;
        for (int i = 0; i < numElements; i++) {
            System.out.println(queue[(start + i) % capacity]);
        }
        System.out.println("");
    }

    public void addSecond() {
        for (int i = 0; i < numElements; i++) {

            Road road = queue[(start + i) % capacity];
            road.setSeconds(road.getSeconds() - 1);

            if (road.isOpen()) {
                if (road.getSeconds() == 0) {
                    if (numElements == 1) {
                        road.setSeconds(interval);
                    } else {
                        road.setOpen(false);
                        road.setSeconds((numElements - 1) * interval);
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