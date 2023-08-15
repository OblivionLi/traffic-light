package traffic;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UserInterface {
    private final Scanner scanner;
    private TrafficSystem trafficSystem;

    public UserInterface(Scanner scanner) {
        this.scanner = scanner;
    }

    public void start() {
        System.out.println("Welcome to the traffic management system!");
        int roads = getInputValue("Input the number of roads: ");
        int interval = getInputValue("Input the interval: ");

        this.trafficSystem = new TrafficSystem(roads, interval);
        Thread queueThread = new Thread(trafficSystem, "QueueThread");

        scanner.nextLine();
        cleanConsole();

        queueThread.start();

        outerLoop:
        while (true) {
            displayMenu();
            int option = getOption();

            switch (option) {
                case 0 -> {
                    System.out.println("Bye");
                    queueThread.interrupt();
                    break outerLoop;
                }
                case 1 -> addRoad();
                case 2 -> deleteRoad();
                case 3 -> displaySystem();
                default -> {
                    System.out.println("Incorrect option");
                    scanner.nextLine();
                }
            }
            cleanConsole();
        }
        cleanConsole();
    }

    private void displaySystem() {
        trafficSystem.setStarted(true);
        scanner.nextLine();
        trafficSystem.setStarted(false);
    }

    private void addRoad() {
        System.out.println("Input road name: ");
        String roadName = scanner.nextLine();

        if (trafficSystem.isQueueFull()) {
            System.out.println("Queue is full. Cannot add road.");
        } else {
            trafficSystem.enqueueRoad(roadName);
            System.out.println("Added road: " + roadName);
        }

        scanner.nextLine();
    }

    private void deleteRoad() {
        if (trafficSystem.isQueueEmpty()) {
            System.out.println("Queue is empty. Cannot delete road.");
        } else {
            Road deletedRoad = trafficSystem.dequeueRoad();
            System.out.println("Deleted road: " + deletedRoad.getName());
        }

        scanner.nextLine();
    }

    private void displaySystemInfo() {
        System.out.println("System Information:");
        if (trafficSystem.isQueueEmpty()) {
            System.out.println("Queue is empty.");
        } else {
            System.out.println("Roads in the queue:");
            trafficSystem.displayQueue();
        }
    }

    private int getOption() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (InputMismatchException | NumberFormatException e) {
            return -1;
        }
    }

    private int getInputValue(String message) {
        System.out.print(message);
        while (true) {
            try {
                int input = scanner.nextInt();
                if (input > 0) {
                    return input;
                }
                System.out.print("Error! Incorrect Input. Try again: ");
                scanner.nextLine();
            } catch (InputMismatchException | NumberFormatException e) {
                System.out.print("Error! Incorrect Input. Try again: ");
                scanner.nextLine();
            }
        }
    }

    private void displayMenu() {
        System.out.println("Menu:");
        System.out.println("1. Add");
        System.out.println("2. Delete");
        System.out.println("3. System");
        System.out.println("0. Quit");
    }

    private void cleanConsole() {
        try {
            var clearCommand = System.getProperty("os.name").contains("Windows")
                    ? new ProcessBuilder("cmd", "/c", "cls")
                    : new ProcessBuilder("clear");
            clearCommand.inheritIO().start().waitFor();
        }
        catch (IOException | InterruptedException e) {}
    }
}
