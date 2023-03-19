package com.example.betelgeuseapi.util;

import java.math.BigInteger;
import java.sql.Timestamp;

public final class TimestampUtils {
    public static Timestamp now() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static Timestamp ofDateTime(String datetime) {
        return Timestamp.valueOf(datetime);
    }

    public static Timestamp formatBlockTime(BigInteger txTime) {
        return new Timestamp(Long.parseLong(String.valueOf(txTime)) * 1000L);
    }

}

