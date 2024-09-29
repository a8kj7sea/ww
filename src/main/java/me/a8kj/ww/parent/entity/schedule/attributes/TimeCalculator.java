package me.a8kj.ww.parent.entity.schedule.attributes;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * The TimeCalculator class provides functionality to calculate the next
 * execution time for scheduled events based on the specified day and time.
 */
public class TimeCalculator {

    /**
     * Calculates the next execution time based on the provided day, hours, and
     * minutes.
     *
     * @param day     the day of the week for the event (e.g., "MONDAY")
     * @param hours   the hour of the day for the event (0-23)
     * @param minutes the minute of the hour for the event (0-59)
     * @return the next LocalDateTime when the event is scheduled to occur
     */
    public LocalDateTime calculateNextExecutionTime(String day, int hours, int minutes) {
        // Convert the input day to a DayOfWeek enum
        DayOfWeek targetDay = DayOfWeek.valueOf(day.toUpperCase());

        // Get the current date and time
        LocalDateTime now = LocalDateTime.now();

        // Create a LocalTime object for the event time
        LocalTime eventTime = LocalTime.of(hours, minutes);

        // Create the next occurrence of the event based on the current time
        LocalDateTime nextOccurrence = now.with(eventTime);

        // If the current time is past the scheduled time or on a different day
        if (nextOccurrence.isBefore(now) || now.getDayOfWeek() != targetDay) {
            // Move to the next week to find the next target day
            LocalDate nextDate = now.toLocalDate().plusDays(1);
            while (nextDate.getDayOfWeek() != targetDay) {
                nextDate = nextDate.plusDays(1);
            }
            // Set the next occurrence to the calculated day and time
            nextOccurrence = nextDate.atTime(eventTime);
        }

        return nextOccurrence; // Return the calculated next occurrence
    }
}
