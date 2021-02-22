package me.jakub.randomtp.utils;


public class Cooldown {

    public static String getStr(long timeLeft, FormatType formatType) {
        switch (formatType) {
            case HOURS:
                if (timeLeft >= 3600) {
                    return String.valueOf(timeLeft / 3600 + " hour(s)");
                } else if (timeLeft >= 60) {
                    return String.valueOf(timeLeft / 60 + " minute(s)");
                } else {
                    return String.valueOf(timeLeft + " second(s)");
                }
            case MINUTES:
                if (timeLeft >= 60) {
                    return String.valueOf(timeLeft / 60 + " minute(s)");
                } else {
                    return String.valueOf(timeLeft + " second(s)");
                }
            case SECONDS:
                return String.valueOf(timeLeft + " second(s)");
            case AUTO:
                if (timeLeft >= 3600) {
                    return String.valueOf(timeLeft / 3600 + " hour(s)");
                } else if (timeLeft >= 60) {
                    return String.valueOf(timeLeft / 60 + " minute(s)");
                } else {
                    return String.valueOf(timeLeft + " second(s)");
                }
            default:
                return String.valueOf(timeLeft + " second(s)");
        }
    }


    public enum FormatType {SECONDS, MINUTES, HOURS, AUTO}
}
