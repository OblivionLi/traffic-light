package traffic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
  private static BufferedReader reader =
          new BufferedReader(new InputStreamReader(System.in));
  public static int roads;
  public static int interval;

  private static Thread systemThread;
  private static TrafficSystem trafficSystem = new TrafficSystem();
  public static RoadsQueue roadsQueue;

  public static void main(String[] args) throws Exception {

    System.out.println("Welcome to the traffic management system!");
    System.out.print("Input the number of roads: ");
    roads = getPositiveInt();
    System.out.print("Input the interval: ");
    interval = getPositiveInt();

    systemThread = new Thread(trafficSystem, "QueueThread");
    systemThread.start();

    roadsQueue = new RoadsQueue(roads);

    menu();
    System.out.println("Bye!");

    systemThread.interrupt();
  }

  private static int getPositiveInt() {
    while (true) {
      int response = 0;

      try {
        response = Integer.parseInt(reader.readLine());
      } catch (NumberFormatException | IOException e) {
      }

      if (response > 0) return response;

      System.out.print("Error! Incorrect Input. Try again: ");
    }
  }

  private static void menu() throws IOException {
    while (true) {

      clearScreen();

      System.out.println("Menu:\n" +
              "1. Add road\n" +
              "2. Delete road\n" +
              "3. Open system\n" +
              "0. Quit");

      int option = getMenuOption();

      switch (option) {
        case 0 -> {
          return;
        }
        case 1 -> addRoad();
        case 2 -> deleteRoad();
        case 3 -> openSystem();
        default -> {
          System.out.println("Incorrect option");
          reader.readLine();
        }
      }
    }
  }

  public static void clearScreen() {
    try {
      var clearCommand = System.getProperty("os.name").contains("Windows")
              ? new ProcessBuilder("cmd", "/c", "cls")
              : new ProcessBuilder("clear");
      clearCommand.inheritIO().start().waitFor();
    }
    catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }

  private static int getMenuOption() {
    int response = -1;

    try {
      response = Integer.parseInt(reader.readLine());
    } catch (NumberFormatException | IOException e) {
    }

    return response;
  }

  private static void openSystem() throws IOException {
    trafficSystem.setSystemState(true);
    reader.readLine();
    trafficSystem.setSystemState(false);

  }

  private static void deleteRoad() throws IOException {
    String road = roadsQueue.dequeue();
    if (road == null) {
      System.out.println("The queue is empty");
    } else {
      System.out.println("Road " + road + " deleted");
    }
    reader.readLine();
  }

  private static void addRoad() throws IOException {
    System.out.print("Input road name: ");
    String roadName = reader.readLine();
    if (!roadsQueue.enqueue(roadName)) {
      System.out.println("The queue is full");
    } else {
      System.out.println("Road " + roadName + " added");
    }
    reader.readLine();
  }
}