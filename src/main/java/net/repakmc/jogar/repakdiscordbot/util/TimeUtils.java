package net.repakmc.jogar.repakdiscordbot.util;

import lombok.experimental.UtilityClass;
import lombok.val;

import java.text.SimpleDateFormat;
import java.util.Date;

@UtilityClass
public class TimeUtils {

    /*
     * https://github.com/arantesxyz/TempMute/blob/master/src/main/java/dev/arantes/spigot/tempmute/utils/TimeUtils.java
     * Credits for Gustavo Arantes.
     * */
    public String formatMillisToString(long millis) {
        long seconds = millis / 1000;
        if (seconds < 60) {
            return seconds + "s";
        }

        long minutes = seconds / 60;
        seconds = seconds % 60;

        if (minutes < 60) {
            return String.format("%dm %ds", minutes, seconds);
        }

        long hours = minutes / 60;
        minutes = minutes % 60;

        if (hours < 24) {
            return String.format("%dh %dm %ds", hours, minutes, seconds);
        }

        int days = (int) hours / 24;
        hours = hours % 24;

        if (days < 7) {
            return String.format("%dd %dh %dm %ds", days, hours, minutes, seconds);
        }

        int weeks = days / 7;
        days = days % 7;

        if (weeks < 4) {
            return String.format("%dw %dd %dh %dm %ds", weeks, days, hours, minutes, seconds);
        }

        int months = weeks / 4;
        weeks = weeks % 4;

        if (months < 12) {
            return String.format("%dM %dw %dd %dh %dm %ds", months, weeks, days, hours, minutes, seconds);
        }

        int years = months / 12;
        months = months % 12;

        return String.format("%dy %dM %dw %dd %dh %dm %ds", years, months, weeks, days, hours, minutes, seconds);
    }

    public String getTimeStamp() {
        val simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        return simpleDateFormat.format(new Date());
    }

}
