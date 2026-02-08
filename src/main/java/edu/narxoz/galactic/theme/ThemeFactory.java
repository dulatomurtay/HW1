package edu.narxoz.galactic.theme;

import edu.narxoz.galactic.bodies.Planet;
import edu.narxoz.galactic.bodies.SpaceStation;
import edu.narxoz.galactic.cargo.Cargo;
import edu.narxoz.galactic.drones.Drone;

public interface ThemeFactory {
    Planet createPlanet(String name, double x, double y);
    SpaceStation createSpaceStation(String name, double x, double y);
    Cargo createCargo(double weightKg, String description);
    Drone createDrone(String id, double maxPayloadKg);
    String getThemeName();
}