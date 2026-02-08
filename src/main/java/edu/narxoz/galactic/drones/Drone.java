package edu.narxoz.galactic.drones;

public abstract class Drone {
    private final String id;
    private DroneStatus status;
    private final double maxPayloadKg;

    protected Drone(String id, double maxPayloadKg) {
        if (maxPayloadKg <= 0) {
            throw new IllegalArgumentException("maxPayloadKg must be positive");
        }
        this.id = id;
        this.maxPayloadKg = maxPayloadKg;
        this.status = DroneStatus.IDLE;
    }

    public String getId() {
        return id;
    }

    public DroneStatus getStatus() {
        return status;
    }

    public double getMaxPayloadKg() {
        return maxPayloadKg;
    }

    public  void setStatus(DroneStatus status) {
        this.status = status;
    }

    public abstract double speedKmPerMin();
}