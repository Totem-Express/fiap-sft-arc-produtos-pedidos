package br.com.fiap.totem_express.shared.converters;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.time.LocalDateTime;
import java.util.Optional;

public class LocalDateTimeFromArrayNode {

    public static LocalDateTime buildLocalDateTime(ArrayNode arrayNode) {
        if (arrayNode == null || arrayNode.size() == 0) {
            return null;
        }
        int year = arrayNode.get(0).asInt();
        int month = arrayNode.get(1).asInt();
        int dayOfTheMonth = arrayNode.get(2).asInt();
        int hour = getHour(arrayNode);
        int minute = getMinute(arrayNode);
        int seconds = getSeconds(arrayNode);
        int milliseconds = getMilliseconds(arrayNode);
        return LocalDateTime.of(year, month, dayOfTheMonth, hour, minute, seconds, milliseconds);
    }

    private static int getMilliseconds(ArrayNode arrayNode) {
        return getWithDefaultValue(arrayNode, 6, 0);
    }

    private static int getSeconds(ArrayNode arrayNode) {
        return getWithDefaultValue(arrayNode, 5, 0);
    }

    private static int getMinute(ArrayNode arrayNode) {
        return getWithDefaultValue(arrayNode, 4, 0);
    }

    private static int getHour(ArrayNode arrayNode) {
        return getWithDefaultValue(arrayNode, 3, 0);

    }

    private static int getWithDefaultValue(ArrayNode arrayNode, int index, int defaultValue) {
        JsonNode jsonNode = arrayNode.get(index);
        if(jsonNode == null) {
           return defaultValue;
        }
        return jsonNode.asInt();
    }
}
