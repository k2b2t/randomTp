package me.jakub.randomtp.utils;


public class Cooldown {

    public static String getStr(long timeLeft, FormatType formatType) {
        switch (formatType) {
            case HOURS:
                if (timeLeft >= 3600) {
                    return String.valueOf(timeLeft / 3600 + " " + Utils.getHour());
                } else if (timeLeft >= 60) {
                    return String.valueOf(timeLeft / 60 + " " + Utils.getMinute());
                } else {
                    return String.valueOf(timeLeft + " " + Utils.getSecond());
                }
            case MINUTES:
                if (timeLeft >= 60) {
                    return String.valueOf(timeLeft / 60 + " " + Utils.getMinute());
                } else {
                    return String.valueOf(timeLeft + " " + Utils.getSecond());
                }
            case SECONDS:
                return String.valueOf(timeLeft + " " + Utils.getSecond());
            case AUTO:
                if (timeLeft >= 3600) {
                    return String.valueOf(timeLeft / 3600 + " " + Utils.getHour());
                } else if (timeLeft >= 60) {
                    return String.valueOf(timeLeft / 60 + " " + Utils.getMinute());
                } else {
                    return String.valueOf(timeLeft + " " + Utils.getSecond());
                }
            default:
                return String.valueOf(timeLeft + " " + Utils.getSecond());
        }
    }


    public enum FormatType {SECONDS, MINUTES, HOURS, AUTO}
}
