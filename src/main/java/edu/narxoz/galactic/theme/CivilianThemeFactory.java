package edu.narxoz.galactic.theme;

import edu.narxoz.galactic.bodies.Planet;
import edu.narxoz.galactic.bodies.SpaceStation;
import edu.narxoz.galactic.cargo.Cargo;
import edu.narxoz.galactic.drones.Drone;
import edu.narxoz.galactic.drones.LightDrone;

public class CivilianThemeFactory implements ThemeFactory {
    @Override
    public Planet createPlanet(String name, double x, double y) {
        return new Planet(name, x, y, "Standard Atmosphere");
    }
    
    @Override
    public SpaceStation createSpaceStation(String name, double x, double y) {
        return new SpaceStation(name, x, y, 3);
    }
    
    @Override
    public Cargo createCargo(double weightKg, String description) {
        return new Cargo(weightKg, "[CIVILIAN] " + description);
    }
    
    @Override
    public Drone createDrone(String id, double maxPayloadKg) {
        return new LightDrone(id, maxPayloadKg);
    }
    
    @Override
    public String getThemeName() {
        return "Civilian Theme";
    }
}