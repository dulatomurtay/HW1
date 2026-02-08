package edu.narxoz.galactic.drones;

public abstract class DroneFactory {
    public abstract Drone createDrone(String id, double maxPayloadKg);
    
    public static DroneFactory getFactory(String factoryType) {
        if (factoryType == null) {
            throw new IllegalArgumentException("Factory type cannot be null");
        }
        
        switch (factoryType.toLowerCase()) {
            case "light":
                return new LightDroneFactory();
            case "heavy":
                return new HeavyDroneFactory();
            default:
                throw new IllegalArgumentException("Unknown factory type: " + factoryType);
        }
    }
}