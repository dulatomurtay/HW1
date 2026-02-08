package edu.narxoz.galactic.dispatcher;

import edu.narxoz.galactic.drones.Drone;
import edu.narxoz.galactic.drones.DroneStatus;
import edu.narxoz.galactic.task.DeliveryTask;
import edu.narxoz.galactic.task.TaskState;

public class Dispatcher {
    public Result assignTask(DeliveryTask task, Drone drone) {
        if (task == null || drone == null) {
            return new Result(false, "Task or drone cannot be null");
        }
        if (drone.getStatus() != DroneStatus.IDLE) {
            return new Result(false, "Drone is not IDLE");
        }
        if (task.getCargo().getWeightKg() > drone.getMaxPayloadKg()) {
            return new Result(false, "Cargo weight exceeds drone capacity");
        }
        if (task.getState() != TaskState.CREATED) {
            return new Result(false, "Task is not in CREATED state");
        }

        task.setState(TaskState.ASSIGNED);
        task.setAssignedDrone(drone);
        drone.setStatus(DroneStatus.IN_FLIGHT);
        
        return new Result(true, null);
    }

    public Result completeTask(DeliveryTask task) {
        if (task == null) {
            return new Result(false, "Task cannot be null");
        }
        if (task.getState() != TaskState.ASSIGNED) {
            return new Result(false, "Task is not in ASSIGNED state");
        }
        if (task.getAssignedDrone() == null) {
            return new Result(false, "No drone assigned to task");
        }
        Drone drone = task.getAssignedDrone();
        if (drone.getStatus() != DroneStatus.IN_FLIGHT) {
            return new Result(false, "Assigned drone is not IN_FLIGHT");
        }

        task.setState(TaskState.DONE);
        drone.setStatus(DroneStatus.IDLE);
        
        return new Result(true, null);
    }
}