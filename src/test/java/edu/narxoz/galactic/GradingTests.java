package edu.narxoz.galactic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import edu.narxoz.galactic.bodies.Planet;
import edu.narxoz.galactic.bodies.SpaceStation;
import edu.narxoz.galactic.cargo.Cargo;
import edu.narxoz.galactic.dispatcher.Dispatcher;
import edu.narxoz.galactic.dispatcher.Result;
import edu.narxoz.galactic.drones.HeavyDrone;
import edu.narxoz.galactic.drones.LightDrone;
import edu.narxoz.galactic.task.DeliveryTask;

public class GradingTests {
    private Planet earth;
    private SpaceStation marsStation;
    private Cargo smallCargo;
    private Cargo heavyCargo;
    private LightDrone lightDrone;
    private HeavyDrone heavyDrone;
    private Dispatcher dispatcher;

    @BeforeEach
    void setup() {
        earth = new Planet("Earth", 0, 0, "N2-O2");
        marsStation = new SpaceStation("Mars Station", 100, 0, 1);
        smallCargo = new Cargo(5.0, "Small package");
        heavyCargo = new Cargo(30.0, "Heavy equipment");
        lightDrone = new LightDrone("L1", 10.0);
        heavyDrone = new HeavyDrone("H1", 50.0);
        dispatcher = new Dispatcher();
    }

    @Test
    void testLightDroneCanCarrySmallCargo() {
        DeliveryTask task = new DeliveryTask(earth, marsStation, smallCargo);
        Result result = dispatcher.assignTask(task, lightDrone);
        assertTrue(result.ok());
        assertEquals(edu.narxoz.galactic.drones.DroneStatus.IN_FLIGHT, lightDrone.getStatus());
    }

    @Test
    void testHeavyDroneCanCarryHeavyCargo() {
        DeliveryTask task = new DeliveryTask(earth, marsStation, heavyCargo);
        Result result = dispatcher.assignTask(task, heavyDrone);
        assertTrue(result.ok());
        assertEquals(edu.narxoz.galactic.drones.DroneStatus.IN_FLIGHT, heavyDrone.getStatus());
    }

    @Test
    void testLightDroneCannotCarryHeavyCargo() {
        DeliveryTask task = new DeliveryTask(earth, marsStation, heavyCargo);
        Result result = dispatcher.assignTask(task, lightDrone);
        assertFalse(result.ok());
        assertTrue(result.reason().contains("exceeds"));
        assertEquals(edu.narxoz.galactic.drones.DroneStatus.IDLE, lightDrone.getStatus());
    }

    @Test
    void testTaskCompletion() {
        DeliveryTask task = new DeliveryTask(earth, marsStation, smallCargo);
        dispatcher.assignTask(task, lightDrone);
        Result result = dispatcher.completeTask(task);
        assertTrue(result.ok());
        assertEquals(edu.narxoz.galactic.drones.DroneStatus.IDLE, lightDrone.getStatus());
        assertEquals(edu.narxoz.galactic.task.TaskState.DONE, task.getState());
    }

    @Test
    void testDroneSpeeds() {
        assertEquals(10.0, lightDrone.speedKmPerMin(), 0.01);
        assertEquals(5.0, heavyDrone.speedKmPerMin(), 0.01);
        assertTrue(lightDrone.speedKmPerMin() > heavyDrone.speedKmPerMin());
    }

    @Test
    void testHeavyDroneInitialization() {
        assertNotNull(heavyDrone);
        assertEquals("H1", heavyDrone.getId());
        assertEquals(50.0, heavyDrone.getMaxPayloadKg(), 0.01);
        assertEquals(edu.narxoz.galactic.drones.DroneStatus.IDLE, heavyDrone.getStatus());
    }
}