package br.com.fiap.totem_express.shared.converters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class LocalDateTimeFromArrayNodeTest {

    @Test
    void should_return_null_when_null_array() {
        assertThat(LocalDateTimeFromArrayNode.buildLocalDateTime(null)).isNull();
    }

    @Test
    void should_return_null_if_array_is_empty() {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode array = mapper.createArrayNode();
        assertThat(LocalDateTimeFromArrayNode.buildLocalDateTime(array)).isNull();
    }

    @Test
    void should_build_correctly_local_date_time_from_array() {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode array = mapper.createArrayNode();
        int year = 2025;
        int month = 2;
        int dayOfTheMonth = 9;
        int hour = 9;
        int minute = 5 ;
        int seconds = 32;
        int milliseconds = 256;
        array.add(year);
        array.add(month);
        array.add(dayOfTheMonth);
        array.add(hour);
        array.add(minute);
        array.add(seconds);
        array.add(milliseconds);
        assertThat(LocalDateTimeFromArrayNode.buildLocalDateTime(array)).isEqualTo(
                LocalDateTime.of(year, month, dayOfTheMonth, hour, minute, seconds, milliseconds)
        );
    }

    @Test
    void should_build_correctly_local_date_time_from_array_without_full_time() {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode array = mapper.createArrayNode();
        int year = 2025;
        int month = 2;
        int dayOfTheMonth = 9;
        int hour = 0;
        int minute = 0 ;
        int seconds = 0;
        int milliseconds = 0;
        array.add(year);
        array.add(month);
        array.add(dayOfTheMonth);
        assertThat(LocalDateTimeFromArrayNode.buildLocalDateTime(array)).isEqualTo(
                LocalDateTime.of(year, month, dayOfTheMonth, hour, minute, seconds, milliseconds)
        );
    }
}