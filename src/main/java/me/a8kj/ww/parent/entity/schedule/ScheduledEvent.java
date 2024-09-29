package me.a8kj.ww.parent.entity.schedule;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import lombok.Data;
import lombok.Getter;
import me.a8kj.ww.parent.entity.schedule.attributes.TimeCalculator;

/**
 * The ScheduledEvent class represents an event that is scheduled to occur
 * on a specific day at a specified time. It implements
 * ConfigurationSerializable
 * for serialization in the Bukkit configuration system.
 */
@Getter
@Data
@SerializableAs("ScheduledEvent")
public class ScheduledEvent implements ConfigurationSerializable {

    private String day; // Day of the week when the event is scheduled
    private int hours; // Hour of the day when the event is scheduled (0-23)
    private int minutes; // Minute of the hour when the event is scheduled (0-59)
    private LocalDateTime executionTime; // The calculated next execution time of the event

    private final TimeCalculator timeCalculator = new TimeCalculator(); // Instance for calculating execution time

    /**
     * Constructor to create a ScheduledEvent with the specified day, hours, and
     * minutes.
     *
     * @param day     the day of the week for the event
     * @param hours   the hour of the day for the event
     * @param minutes the minute of the hour for the event
     */
    public ScheduledEvent(String day, int hours, int minutes) {
        this.day = day;
        this.hours = hours;
        this.minutes = minutes;
        this.executionTime = timeCalculator.calculateNextExecutionTime(day, hours, minutes);
    }

    /**
     * Serializes the ScheduledEvent into a map for storage in the configuration.
     *
     * @return a map representation of the ScheduledEvent
     */
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("day", day);
        map.put("hours", hours);
        map.put("minutes", minutes);
        return map;
    }

    /**
     * Deserializes a map back into a ScheduledEvent object.
     *
     * @param map the map containing serialized data
     * @return a new ScheduledEvent instance
     * @throws IllegalArgumentException if required fields are missing
     */
    public static ScheduledEvent deserialize(Map<String, Object> map) {
        String day = (String) map.get("day");
        Integer hours = (Integer) map.get("hours");
        Integer minutes = (Integer) map.get("minutes");

        // Validate that required fields are present
        if (day == null || hours == null || minutes == null) {
            throw new IllegalArgumentException("Missing required fields in schedule: " + map);
        }

        return new ScheduledEvent(day, hours, minutes);
    }
}
