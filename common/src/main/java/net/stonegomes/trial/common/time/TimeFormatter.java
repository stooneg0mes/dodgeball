package net.stonegomes.trial.common.time;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class TimeFormatter {

    public static String formatTime(long time, boolean compareDifference) {
        return formatTimeToString(false, time, compareDifference);
    }

    public static String formatTime(long time) {
        return formatTime(time, false);
    }

    public static String formatTimeMinimalist(long time, boolean compareDifference) {
        return formatTimeToString(true, time, compareDifference);
    }

    public static String formatTimeMinimalist(long time) {
        return formatTimeMinimalist(time, false);
    }

    private static String formatTimeToString(boolean minimalist, long time, boolean compareDifference) {
        final long finalTime = (compareDifference ? time - System.currentTimeMillis() : time);

        final long days = TimeUnit.MILLISECONDS.toDays(finalTime);
        final long hours = TimeUnit.MILLISECONDS.toHours(finalTime) - days * 24L;
        final long minutes = TimeUnit.MILLISECONDS.toMinutes(finalTime) - TimeUnit.MILLISECONDS.toHours(finalTime) * 60L;
        final long seconds = TimeUnit.MILLISECONDS.toSeconds(finalTime) - TimeUnit.MILLISECONDS.toMinutes(finalTime) * 60L;

        StringBuilder stringBuilder = new StringBuilder();
        if (minimalist) {
            if (finalTime <= 0 || System.currentTimeMillis() == time) return "0s";

            if (days > 0L) {
                stringBuilder.append(days).append("d");
            }

            if (hours > 0L) {
                stringBuilder.append(hours).append("h");
            }

            if (minutes > 0L) {
                stringBuilder.append(minutes).append("m");
            }

            if (seconds > 0L) {
                stringBuilder.append(seconds).append("s");
            }
        } else {
            if (finalTime <= 0) return "0 seconds";

            if (days > 0L) {
                stringBuilder.append(days).append(" ").append("days");
            }

            if (hours > 0L) {
                if (days > 0L) stringBuilder.append(" ");
                stringBuilder.append(hours).append(" ").append("hours");
            }

            if (minutes > 0L) {
                if (hours > 0L) stringBuilder.append(" ");

                stringBuilder.append(minutes).append(" ").append("minutes");
            }

            if (seconds > 0L) {
                if (minutes > 0L) stringBuilder.append(" ");

                stringBuilder.append(seconds).append(" ").append("seconds");
            }
        }

        return stringBuilder.toString();
    }
    public static String formatSeconds(long seconds) {
        return formatTimeMinimalist(seconds * 1000L);
    }

    public static String formatDate() {
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dateTimeFormatter.format(LocalDateTime.now());
    }

}