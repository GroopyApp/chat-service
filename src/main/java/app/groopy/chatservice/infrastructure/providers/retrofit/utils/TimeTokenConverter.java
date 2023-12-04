package app.groopy.chatservice.infrastructure.providers.retrofit.utils;

import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Slf4j
public class TimeTokenConverter {

    private static final Long TIMETOKEN_CONVERTING_CONSTANT = 10000000L;

    public static Long toTimeToken(LocalDateTime localDateTime) {
        Instant instant = localDateTime.toInstant(ZoneOffset.UTC);
        long unixTimestamp = instant.getEpochSecond();
        return unixTimestamp * TIMETOKEN_CONVERTING_CONSTANT;
    }

    public static LocalDateTime toLocalDateTime(Long timeToken) {
        long unixTimestamp = timeToken / TIMETOKEN_CONVERTING_CONSTANT;
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(unixTimestamp), ZoneOffset.UTC);
    }

    public static LocalDateTime toLocalDateTime(String timeToken) {
        try {
            Long timeTokenLong = Long.parseLong(timeToken);
            return toLocalDateTime(timeTokenLong);
        } catch (NumberFormatException e) {
            log.error("Error parsing timeToken: {}", timeToken);
            return null;
        }
    }
}
