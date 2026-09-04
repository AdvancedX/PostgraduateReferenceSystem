package com.pgs.tool;

import java.util.Map;

public class ToolDefinition {
    private final String type = "function";
    private final FunctionDefinition function;

    public ToolDefinition(String name, String description, Map<String, Object> parameters) {
        this.function = new FunctionDefinition(name, description, parameters);
    }

    public String getType() {
        return type;
    }

    public FunctionDefinition getFunction() {
        return function;
    }

    public static class FunctionDefinition {
        private final String name;
        private final String description;
        private final Map<String, Object> parameters;

        public FunctionDefinition(String name, String description, Map<String, Object> parameters) {
            this.name = name;
            this.description = description;
            this.parameters = parameters;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public Map<String, Object> getParameters() {
            return parameters;
        }
    }
}
