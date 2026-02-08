package edu.narxoz.galactic.drones;

public class HeavyDroneFactory extends DroneFactory {
    @Override
    public Drone createDrone(String id, double maxPayloadKg) {
        return new HeavyDrone(id, maxPayloadKg);
    }
}