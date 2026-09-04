package com.pgs.llm;

public class ToolCall {
    private final String id;
    private final String type;
    private final FunctionCall function;

    public ToolCall(String id, String type, FunctionCall function) {
        this.id = id;
        this.type = type;
        this.function = function;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public FunctionCall getFunction() {
        return function;
    }
}
