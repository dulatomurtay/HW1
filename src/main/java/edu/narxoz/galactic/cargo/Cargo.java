package edu.narxoz.galactic.cargo;

public class Cargo {
    private final double weightKg;
    private final String description;

    public Cargo(double weightKg, String description) {
        if (weightKg <= 0) {
            throw new IllegalArgumentException("Cargo weight must be positive");
        }
        this.weightKg = weightKg;
        this.description = description;
    }

    public double getWeightKg() {
        return weightKg;
    }

    public String getDescription() {
        return description;
    }
}