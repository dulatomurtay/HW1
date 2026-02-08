package edu.narxoz.galactic;

import edu.narxoz.galactic.bodies.Planet;
import edu.narxoz.galactic.bodies.SpaceStation;
import edu.narxoz.galactic.cargo.Cargo;
import edu.narxoz.galactic.dispatcher.Dispatcher;
import edu.narxoz.galactic.dispatcher.Result;
import edu.narxoz.galactic.drones.Drone;
import edu.narxoz.galactic.drones.DroneFactory;
import edu.narxoz.galactic.drones.HeavyDrone;
import edu.narxoz.galactic.drones.LightDrone;
import edu.narxoz.galactic.task.DeliveryTask;
import edu.narxoz.galactic.theme.CivilianThemeFactory;
import edu.narxoz.galactic.theme.MilitaryThemeFactory;
import edu.narxoz.galactic.theme.ThemeFactory;

public class GalacticDemo {
    public static void main(String[] args) {
        System.out.println("=== Galactic Delivery Drones Demo ===\n");
        
        testOriginalImplementation();
        
        System.out.println("\n=== Часть 2: Factory Method Pattern ===");
        testFactoryMethod();
        
        System.out.println("\n=== Часть 3: Abstract Factory Pattern ===");
        testAbstractFactory();
        
        System.out.println("\n=== Demo completed ===");
    }
    
    private static void testOriginalImplementation() {
        System.out.println("=== Часть 1: Оригинальная реализация ===");
        
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
        System.out.println("Task state: " + task1.getState());
        
        System.out.println("\n2. Success with HeavyDrone:");
        Result result2 = dispatcher.assignTask(task1, heavyDrone);
        System.out.println("Result: " + (result2.ok() ? "SUCCESS" : "FAILURE"));
        System.out.println("Drone status: " + heavyDrone.getStatus());
        System.out.println("Task state: " + task1.getState());
        
        System.out.println("\n3. Estimated time:");
        double time = task1.estimateTime();
        System.out.println("Estimated time: " + String.format("%.2f", time) + " minutes");
        
        System.out.println("\n4. Completion result + final statuses:");
        Result result3 = dispatcher.completeTask(task1);
        System.out.println("Completion result: " + (result3.ok() ? "SUCCESS" : "FAILURE"));
        System.out.println("Final drone status: " + heavyDrone.getStatus());
        System.out.println("Final task state: " + task1.getState());
    }
    
    private static void testFactoryMethod() {
        DroneFactory lightFactory = DroneFactory.getFactory("light");
        DroneFactory heavyFactory = DroneFactory.getFactory("heavy");
        
        Drone lightDrone = lightFactory.createDrone("LD-FACTORY-001", 10.0);
        Drone heavyDrone = heavyFactory.createDrone("HD-FACTORY-001", 100.0);
        
        System.out.println("Создано через Factory Method:");
        System.out.println("- " + lightDrone.getClass().getSimpleName() + ": " + lightDrone.getId());
        System.out.println("- " + heavyDrone.getClass().getSimpleName() + ": " + heavyDrone.getId());
        System.out.println("- LightDrone speed: " + lightDrone.speedKmPerMin() + " km/min");
        System.out.println("- HeavyDrone speed: " + heavyDrone.speedKmPerMin() + " km/min");
        
        Planet earth = new Planet("Earth", 0, 0, "Air");
        SpaceStation station = new SpaceStation("Station", 100, 100, 1);
        Cargo cargo = new Cargo(60.0, "Factory Test Cargo");
        
        DeliveryTask task = new DeliveryTask(earth, station, cargo);
        Dispatcher dispatcher = new Dispatcher();
        
        System.out.println("\nТест с дроном из фабрики:");
        Result result = dispatcher.assignTask(task, heavyDrone);
        System.out.println("Assignment result: " + (result.ok() ? "SUCCESS" : "FAILURE"));
    }
    
    private static void testAbstractFactory() {
        ThemeFactory militaryFactory = new MilitaryThemeFactory();
        ThemeFactory civilianFactory = new CivilianThemeFactory();
        
        System.out.println("\n1. Military Theme:");
        Planet militaryPlanet = militaryFactory.createPlanet("Mars", 200, 200);
        SpaceStation militaryStation = militaryFactory.createSpaceStation("Military Base", 150, 150);
        Cargo militaryCargo = militaryFactory.createCargo(80.0, "Weapons");
        Drone militaryDrone = militaryFactory.createDrone("MIL-001", 100.0);
        
        System.out.println("Тема: " + militaryFactory.getThemeName());
        System.out.println("- Планета: " + militaryPlanet.getName() + ", атмосфера: " + militaryPlanet.getAtmosphereType());
        System.out.println("- Станция: " + militaryStation.getName() + ", уровень: " + militaryStation.getLevel());
        System.out.println("- Груз: " + militaryCargo.getDescription());
        System.out.println("- Дрон: " + militaryDrone.getClass().getSimpleName() + ", скорость: " + militaryDrone.speedKmPerMin() + " км/мин");
        
        System.out.println("\n2. Civilian Theme:");
        Planet civilianPlanet = civilianFactory.createPlanet("Venus", 300, 300);
        SpaceStation civilianStation = civilianFactory.createSpaceStation("Civilian Port", 250, 250);
        Cargo civilianCargo = civilianFactory.createCargo(15.0, "Food Supplies");
        Drone civilianDrone = civilianFactory.createDrone("CIV-001", 20.0);
        
        System.out.println("Тема: " + civilianFactory.getThemeName());
        System.out.println("- Планета: " + civilianPlanet.getName() + ", атмосфера: " + civilianPlanet.getAtmosphereType());
        System.out.println("- Станция: " + civilianStation.getName() + ", уровень: " + civilianStation.getLevel());
        System.out.println("- Груз: " + civilianCargo.getDescription());
        System.out.println("- Дрон: " + civilianDrone.getClass().getSimpleName() + ", скорость: " + civilianDrone.speedKmPerMin() + " км/мин");
        
        System.out.println("\n3. Демонстрация консистентности:");
        System.out.println("Военная фабрика создает HeavyDrone: " + 
            (militaryDrone instanceof edu.narxoz.galactic.drones.HeavyDrone ? "Да" : "Нет"));
        System.out.println("Гражданская фабрика создает LightDrone: " + 
            (civilianDrone instanceof edu.narxoz.galactic.drones.LightDrone ? "Да" : "Нет"));
        
        System.out.println("\n4. Тест доставки с тематическими объектами:");
        DeliveryTask militaryTask = new DeliveryTask(militaryPlanet, militaryStation, militaryCargo);
        Dispatcher dispatcher = new Dispatcher();
        Result militaryResult = dispatcher.assignTask(militaryTask, militaryDrone);
        System.out.println("Military delivery assignment: " + (militaryResult.ok() ? "SUCCESS" : "FAILURE"));
        
        DeliveryTask civilianTask = new DeliveryTask(civilianPlanet, civilianStation, civilianCargo);
        Result civilianResult = dispatcher.assignTask(civilianTask, civilianDrone);
        System.out.println("Civilian delivery assignment: " + (civilianResult.ok() ? "SUCCESS" : "FAILURE"));
    }
}