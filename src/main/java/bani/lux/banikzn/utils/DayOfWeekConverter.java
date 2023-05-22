package bani.lux.banikzn.utils;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DayOfWeekConverter {
    public static String convertToIndices(Map<DayOfWeek, OpenCloseTime> workSchedule) {
        Map<DayOfWeek, Integer> dayMap = new HashMap<>();
        dayMap.put(DayOfWeek.SUNDAY, 0);
        dayMap.put(DayOfWeek.MONDAY, 1);
        dayMap.put(DayOfWeek.TUESDAY, 2);
        dayMap.put(DayOfWeek.WEDNESDAY, 3);
        dayMap.put(DayOfWeek.THURSDAY, 4);
        dayMap.put(DayOfWeek.FRIDAY, 5);
        dayMap.put(DayOfWeek.SATURDAY, 6);

        StringBuilder indicesBuilder = new StringBuilder();
        for (DayOfWeek day : dayMap.keySet()) {
            if (!workSchedule.containsKey(day)) {
                int index = dayMap.get(day);
                indicesBuilder.append(index).append(", ");
            }
        }

        if (indicesBuilder.length() > 0) {
            indicesBuilder.setLength(indicesBuilder.length() - 2);
        }

        return indicesBuilder.toString();
    }
}