package com.and.utility.apiUtils;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class CommonAPIUtil {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");

    // Method to calculate both departure and arrival schedule
    public Map<String, String> calculateScheduleTimes(String departureTimeZone, String arrivalTimeZone, int shedArrivalHours, int shedDepartureHours) {
        String scheduledDeparture = calculateTime(departureTimeZone, shedDepartureHours);
        String scheduledArrival = calculateTime(arrivalTimeZone, shedArrivalHours);

        Map<String, String> scheduleMap = new HashMap<>();
        scheduleMap.put("ScheduledDeparture", scheduledDeparture);
        scheduleMap.put("ScheduledArrival", scheduledArrival);

        return scheduleMap;
    }

    // Method to add given hours to the current time in a specified time zone
    public String calculateTime(String timeZone, int hoursToAdd) {
        ZoneId zone = ZoneId.of(timeZone);
        ZonedDateTime updatedTime = ZonedDateTime.now(zone).plusHours(hoursToAdd);
        return FORMATTER.format(updatedTime);
    }
}
