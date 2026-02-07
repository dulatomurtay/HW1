package edu.narxoz.galactic.task;

import edu.narxoz.galactic.bodies.CelestialBody;
import edu.narxoz.galactic.cargo.Cargo;
import edu.narxoz.galactic.drones.Drone;

public class DeliveryTask {
    private final CelestialBody origin;
    private final CelestialBody destination;
    private final Cargo cargo;
    private TaskState state;
    private Drone assignedDrone;

    public DeliveryTask(CelestialBody origin, CelestialBody destination, Cargo cargo) {
        this.origin = origin;
        this.destination = destination;
        this.cargo = cargo;
        this.state = TaskState.CREATED;
        this.assignedDrone = null;
    }

    public CelestialBody getOrigin() {
        return origin;
    }

    public CelestialBody getDestination() {
        return destination;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public TaskState getState() {
        return state;
    }

    public Drone getAssignedDrone() {
        return assignedDrone;
    }

    // ИЗМЕНЕНО: добавлен public
    public void setState(TaskState state) {
        this.state = state;
    }

    // ИЗМЕНЕНО: добавлен public
    public void setAssignedDrone(Drone drone) {
        this.assignedDrone = drone;
    }

    public double estimateTime() {
        if (assignedDrone == null) {
            throw new IllegalStateException("Cannot estimate time: no drone assigned");
        }
        if (assignedDrone.speedKmPerMin() <= 0) {
            throw new IllegalStateException("Drone speed must be positive");
        }
        
        double distance = origin.distanceTo(destination);
        return distance / assignedDrone.speedKmPerMin();
    }
}