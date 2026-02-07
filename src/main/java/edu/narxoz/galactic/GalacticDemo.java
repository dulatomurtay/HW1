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
        System.out.println("=== Galactic Drone Delivery Demo ===\n");
        
        // Create celestial bodies
        Planet earth = new Planet("Earth", 0, 0, "Nitrogen-Oxygen");
        SpaceStation alpha = new SpaceStation("Alpha Station", 100, 100, 5);
        
        // Create cargo - both will be used
        Cargo lightCargo = new Cargo(5.0, "Medical supplies");
        Cargo heavyCargo = new Cargo(25.0, "Construction materials");
        
        // Create drones
        LightDrone lightDrone = new LightDrone("LD-001", 10.0);
        HeavyDrone heavyDrone = new HeavyDrone("HD-001", 50.0);
        
        // Create tasks - both will be used
        DeliveryTask lightTask = new DeliveryTask(earth, alpha, lightCargo);
        DeliveryTask heavyTask = new DeliveryTask(earth, alpha, heavyCargo);
        
        Dispatcher dispatcher = new Dispatcher();
        
        System.out.println("1. Assign light cargo to light drone:");
        Result lightResult = dispatcher.assignTask(lightTask, lightDrone);
        System.out.println("   Result: " + (lightResult.ok() ? "Success" : "Failure"));
        System.out.println("   Reason: " + lightResult.reason());
        System.out.println("   Drone status: " + lightDrone.getStatus());
        System.out.println("   Task state: " + lightTask.getState() + "\n");
        
        System.out.println("2. Try to assign heavy cargo to light drone:");
        Result result1 = dispatcher.assignTask(heavyTask, lightDrone);
        System.out.println("   Result: " + (result1.ok() ? "Success" : "Failure"));
        System.out.println("   Reason: " + result1.reason());
        System.out.println("   Drone status: " + lightDrone.getStatus());
        System.out.println("   Task state: " + heavyTask.getState() + "\n");
        
        System.out.println("3. Assign heavy cargo to heavy drone:");
        Result result2 = dispatcher.assignTask(heavyTask, heavyDrone);
        System.out.println("   Result: " + (result2.ok() ? "Success" : "Failure"));
        System.out.println("   Drone status: " + heavyDrone.getStatus());
        System.out.println("   Task state: " + heavyTask.getState() + "\n");
        
        System.out.println("4. Estimate delivery time:");
        double time = heavyTask.estimateTime();
        System.out.println("   Distance: " + earth.distanceTo(alpha) + " km");
        System.out.println("   Drone speed: " + heavyDrone.speedKmPerMin() + " km/min");
        System.out.println("   Delivery time: " + time + " minutes\n");
        
        System.out.println("5. Complete the heavy task:");
        Result result3 = dispatcher.completeTask(heavyTask);
        System.out.println("   Result: " + (result3.ok() ? "Success" : "Failure"));
        System.out.println("   Final drone status: " + heavyDrone.getStatus());
        System.out.println("   Final task state: " + heavyTask.getState());
        
        System.out.println("\n6. Complete the light task:");
        Result lightCompleteResult = dispatcher.completeTask(lightTask);
        System.out.println("   Result: " + (lightCompleteResult.ok() ? "Success" : "Failure"));
        System.out.println("   Light drone status: " + lightDrone.getStatus());
        System.out.println("   Light task state: " + lightTask.getState());
        
        System.out.println("\n=== Demo Completed ===");
    }
}