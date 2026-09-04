package com.pgs.llm;

public class FunctionCall {
    private final String name;
    private final String arguments;

    public FunctionCall(String name, String arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    public String getName() {
        return name;
    }

    public String getArguments() {
        return arguments;
    }
}
