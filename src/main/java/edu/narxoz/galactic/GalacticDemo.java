package edu.narxoz.galactic;

import edu.narxoz.galactic.bodies.Planet;
import edu.narxoz.galactic.bodies.SpaceStation;
import edu.narxoz.galactic.cargo.Cargo;
import edu.narxoz.galactic.dispatcher.Dispatcher;
import edu.narxoz.galactic.dispatcher.Result;
import edu.narxoz.galactic.drones.HeavyDrone;
import edu.narxoz.galactic.drones.LightDrone;
import edu.narxoz.galactic.task.DeliveryTask;

public class GalacticDemo {
    public static void main(String[] args) {
        System.out.println("=== Galactic Delivery Drones Demo ===\n");

        Planet earth = new Planet("Earth", 0, 0, "Nitrogen-Oxygen");
        SpaceStation alpha = new SpaceStation("Alpha Station", 100, 100, 1);

        Cargo heavyCargo = new Cargo(50.0, "Construction materials");
        Cargo lightCargo = new Cargo(5.0, "Medical supplies");

        LightDrone lightDrone = new LightDrone("LD-001", 10.0);
        HeavyDrone heavyDrone = new HeavyDrone("HD-001", 100.0);

        DeliveryTask task1 = new DeliveryTask(earth, alpha, heavyCargo);
        DeliveryTask task2 = new DeliveryTask(alpha, earth, lightCargo);

        Dispatcher dispatcher = new Dispatcher();

        System.out.println("1. Failure to assign overweight cargo to LightDrone:");
        Result result1 = dispatcher.assignTask(task1, lightDrone);
        System.out.println("Result: " + (result1.ok() ? "SUCCESS" : "FAILURE"));
        System.out.println("Reason: " + result1.reason());
        System.out.println("Drone status: " + lightDrone.getStatus());
        System.out.println("Task state: " + task1.getState() + "\n");

        System.out.println("2. Success with HeavyDrone:");
        Result result2 = dispatcher.assignTask(task1, heavyDrone);
        System.out.println("Result: " + (result2.ok() ? "SUCCESS" : "FAILURE"));
        System.out.println("Drone status: " + heavyDrone.getStatus());
        System.out.println("Task state: " + task1.getState() + "\n");

        System.out.println("3. Estimated time:");
        double time = task1.estimateTime();
        System.out.println("Estimated time: " + String.format("%.2f", time) + " minutes\n");

        System.out.println("4. Completion result + final statuses:");
        Result result3 = dispatcher.completeTask(task1);
        System.out.println("Completion result: " + (result3.ok() ? "SUCCESS" : "FAILURE"));
        System.out.println("Final drone status: " + heavyDrone.getStatus());
        System.out.println("Final task state: " + task1.getState() + "\n");

        System.out.println("5. Testing LightDrone with light cargo:");
        Result result4 = dispatcher.assignTask(task2, lightDrone);
        System.out.println("Light cargo assignment: " + (result4.ok() ? "SUCCESS" : "FAILURE"));

        System.out.println("\n=== Demo completed ===");
    }
}