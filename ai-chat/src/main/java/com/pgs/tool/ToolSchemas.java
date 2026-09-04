package com.pgs.tool;

import java.util.LinkedHashMap;
import java.util.Map;

public final class ToolSchemas {
    private ToolSchemas() {
    }

    public static Map<String, Object> objectSchema(Map<String, Object> properties) {
        Map<String, Object> schema = new LinkedHashMap<String, Object>();
        schema.put("type", "object");
        schema.put("properties", properties);
        schema.put("additionalProperties", false);
        return schema;
    }

    public static Map<String, Object> properties(Object... namesAndSchemas) {
        Map<String, Object> properties = new LinkedHashMap<String, Object>();
        for (int index = 0; index < namesAndSchemas.length; index += 2) {
            properties.put((String) namesAndSchemas[index], namesAndSchemas[index + 1]);
        }
        return properties;
    }

    public static Map<String, Object> stringProperty(String description) {
        Map<String, Object> property = new LinkedHashMap<String, Object>();
        property.put("type", "string");
        property.put("description", description);
        return property;
    }

    public static Map<String, Object> integerProperty(String description, int minimum, int maximum) {
        Map<String, Object> property = new LinkedHashMap<String, Object>();
        property.put("type", "integer");
        property.put("description", description);
        property.put("minimum", minimum);
        property.put("maximum", maximum);
        return property;
    }
}
