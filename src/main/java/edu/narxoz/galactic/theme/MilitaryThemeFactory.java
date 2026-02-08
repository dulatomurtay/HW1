package edu.narxoz.galactic.theme;

import edu.narxoz.galactic.bodies.Planet;
import edu.narxoz.galactic.bodies.SpaceStation;
import edu.narxoz.galactic.cargo.Cargo;
import edu.narxoz.galactic.drones.Drone;
import edu.narxoz.galactic.drones.HeavyDrone;

public class MilitaryThemeFactory implements ThemeFactory {
    @Override
    public Planet createPlanet(String name, double x, double y) {
        return new Planet(name, x, y, "Military Atmosphere");
    }
    
    @Override
    public SpaceStation createSpaceStation(String name, double x, double y) {
        return new SpaceStation(name, x, y, 10);
    }
    
    @Override
    public Cargo createCargo(double weightKg, String description) {
        return new Cargo(weightKg, "[MILITARY] " + description);
    }
    
    @Override
    public Drone createDrone(String id, double maxPayloadKg) {
        return new HeavyDrone(id, maxPayloadKg);
    }
    
    @Override
    public String getThemeName() {
        return "Military Theme";
    }
}