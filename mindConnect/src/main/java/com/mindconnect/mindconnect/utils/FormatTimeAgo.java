package com.mindconnect.mindconnect.utils;

import java.time.Duration;
import java.time.LocalDateTime;

public class FormatTimeAgo {
    public static String formatRelativeTime(LocalDateTime timestamp) {
        Duration duration = Duration.between(timestamp, LocalDateTime.now());
        long days = duration.toDays() % 30;
        long hours = duration.toHours() % 24;
        long minutes = duration.toMinutes() % 60;
        return String.format("%dd, %dh, %dm ago", days, hours, minutes);
    }
}
