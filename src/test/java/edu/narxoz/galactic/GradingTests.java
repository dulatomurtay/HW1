package edu.narxoz.galactic;

import edu.narxoz.galactic.bodies.Planet;
import edu.narxoz.galactic.bodies.SpaceStation;
import edu.narxoz.galactic.cargo.Cargo;
import edu.narxoz.galactic.dispatcher.Dispatcher;
import edu.narxoz.galactic.dispatcher.Result;
import edu.narxoz.galactic.drones.DroneStatus;
import edu.narxoz.galactic.drones.HeavyDrone;
import edu.narxoz.galactic.drones.LightDrone;
import edu.narxoz.galactic.task.DeliveryTask;
import edu.narxoz.galactic.task.TaskState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GradingTests {

    private Dispatcher dispatcher;
    private Planet earth;
    private SpaceStation marsStation;
    private Cargo smallCargo;
    private Cargo heavyCargo;
    private LightDrone lightDrone;
    private HeavyDrone heavyDrone;

    @BeforeEach
    void setUp() {
        dispatcher = new Dispatcher();
        earth = new Planet("Earth", 0, 0, "Nitrogen-Oxygen");
        marsStation = new SpaceStation("Mars Station", 30, 40, 5); // Distance = 50
        smallCargo = new Cargo(10.0, "Medical Supplies");
        heavyCargo = new Cargo(80.0, "Building Materials");
        lightDrone = new LightDrone("L-01", 50.0);
        heavyDrone = new HeavyDrone("H-01", 100.0);
    }

    @Test
    @DisplayName("Distance calculation between celestial bodies should be correct")
    void testDistanceCalculation() {
        double dist = earth.distanceTo(marsStation);
        assertEquals(50.0, dist, 0.001);
    }

    @Test
    @DisplayName("Assigning overweight cargo should fail")
    void testOverweightAssignment() {
        DeliveryTask task = new DeliveryTask(earth, marsStation, heavyCargo);
        Result result = dispatcher.assignTask(task, lightDrone);

        assertFalse(result.ok(), "Assignment should fail for overweight cargo");
        assertNotNull(result.reason(), "Failure reason should not be null");
        assertEquals(TaskState.CREATED, task.getState(), "Task state should remain CREATED");
        assertEquals(DroneStatus.IDLE, lightDrone.getStatus(), "Drone status should remain IDLE");
    }

    @Test
    @DisplayName("Successful task assignment updates states correctly")
    void testSuccessfulAssignment() {
        DeliveryTask task = new DeliveryTask(earth, marsStation, smallCargo);
        Result result = dispatcher.assignTask(task, lightDrone);

        assertTrue(result.ok(), "Assignment should be successful");
        assertEquals(TaskState.ASSIGNED, task.getState());
        assertEquals(lightDrone, task.getAssignedDrone());
        assertEquals(DroneStatus.IN_FLIGHT, lightDrone.getStatus());
    }

    @Test
    @DisplayName("Estimated time calculation should use correct drone speed")
    void testTimeEstimation() {
        DeliveryTask task = new DeliveryTask(earth, marsStation, smallCargo);
        dispatcher.assignTask(task, lightDrone); // Distance 50, Light speed 10

        double expectedTime = 50.0 / 10.0;
        assertEquals(expectedTime, task.estimateTime(), 0.001);
    }

    @Test
    @DisplayName("Completing an assigned task should work")
    void testCompleteTask() {
        DeliveryTask task = new DeliveryTask(earth, marsStation, smallCargo);
        dispatcher.assignTask(task, lightDrone);

        Result result = dispatcher.completeTask(task);
        assertTrue(result.ok());
        assertEquals(TaskState.DONE, task.getState());
        assertEquals(DroneStatus.IDLE, lightDrone.getStatus());
    }

    @Test
    @DisplayName("Completing a task that is not assigned should fail")
    void testCompleteUnassignedTask() {
        DeliveryTask task = new DeliveryTask(earth, marsStation, smallCargo);

        Result result = dispatcher.completeTask(task);
        assertFalse(result.ok());
        assertEquals(TaskState.CREATED, task.getState());
    }

    @Test
    @DisplayName("Validation: Negative payload should throw IllegalArgumentException")
    void testInvalidDronePayload() {
        assertThrows(IllegalArgumentException.class, () -> new LightDrone("Fail", -10));
    }

    @Test
    @DisplayName("Validation: Null in distanceTo should throw IllegalArgumentException")
    void testNullDistance() {
        assertThrows(IllegalArgumentException.class, () -> earth.distanceTo(null));
    }
}