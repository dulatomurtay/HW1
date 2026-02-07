package edu.narxoz.galactic.dispatcher;

import edu.narxoz.galactic.drones.Drone;
import edu.narxoz.galactic.drones.DroneStatus;
import edu.narxoz.galactic.task.DeliveryTask;
import edu.narxoz.galactic.task.TaskState;

public class Dispatcher {
    
    public Result assignTask(DeliveryTask task, Drone drone) {
        if (task == null || drone == null) {
            return new Result(false, "Task and drone must not be null");
        }
        
        if (drone.getStatus() != DroneStatus.IDLE) {
            return new Result(false, "Drone is not IDLE");
        }
        
        if (task.getCargo().getWeightKg() > drone.getMaxPayloadKg()) {
            return new Result(false, 
                String.format("Cargo weight (%.2f kg) exceeds drone capacity (%.2f kg)", 
                    task.getCargo().getWeightKg(), drone.getMaxPayloadKg()));
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
            return new Result(false, "Task must not be null");
        }
        
        if (task.getState() != TaskState.ASSIGNED) {
            return new Result(false, "Task is not in ASSIGNED state");
        }
        
        Drone drone = task.getAssignedDrone();
        if (drone == null) {
            return new Result(false, "No drone assigned to the task");
        }
        
        if (drone.getStatus() != DroneStatus.IN_FLIGHT) {
            return new Result(false, "Drone is not IN_FLIGHT");
        }
        
        task.setState(TaskState.DONE);
        drone.setStatus(DroneStatus.IDLE);
        
        return new Result(true, null);
    }
}