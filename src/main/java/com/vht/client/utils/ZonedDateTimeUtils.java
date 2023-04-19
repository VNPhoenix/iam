package com.vht.client.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ZonedDateTimeUtils {

    public static ZonedDateTime now(String timezone) {
        return ZonedDateTime.now(ZoneId.of(timezone));
    }

    public static ZonedDateTime ofInstant(Instant instant, String timezone) {
        return ZonedDateTime.ofInstant(instant, ZoneId.of(timezone));
    }

    public static ZonedDateTime ofDate(Date date, String timezone) {
        return ZonedDateTime.ofInstant(date.toInstant(), ZoneId.of(timezone));
    }

    public static Date toDate(ZonedDateTime dt) {
        return Date.from(dt.toInstant());
    }
}
