package ui;

import java.io.FileNotFoundException;
import model.Event;
import model.EventLog;

public class Main {
    public static void main(String[] args) {
        new GUI();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Application shutting down. Event Log: ");
            for (Event event : EventLog.getInstance()) {
                System.out.println(event.toString());
            }
        }));
    }
}
