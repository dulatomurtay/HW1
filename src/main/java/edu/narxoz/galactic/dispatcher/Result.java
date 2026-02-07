package edu.narxoz.galactic.dispatcher;

public record Result(boolean ok, String reason) {
    public Result {
        if (!ok && (reason == null || reason.isEmpty())) {
            throw new IllegalArgumentException("Failure result must have a non-empty reason");
        }
    }
    
    public boolean ok() {
        return ok;
    }
    
    public String reason() {
        return reason;
    }
}