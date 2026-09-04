package com.pgs.tool;

public class ToolExecutionResult {
    private final boolean success;
    private final Object data;
    private final String message;

    private ToolExecutionResult(boolean success, Object data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
    }

    public static ToolExecutionResult success(Object data) {
        return new ToolExecutionResult(true, data, null);
    }

    public static ToolExecutionResult failure(String message) {
        return new ToolExecutionResult(false, null, message);
    }

    public boolean isSuccess() {
        return success;
    }

    public Object getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
