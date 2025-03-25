package com.r2dbc.movie.stream.id;

import com.r2dbc.movie.stream.util.RandomStringGenerator;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.security.SecureRandom;
import java.util.concurrent.atomic.AtomicInteger;

import static com.r2dbc.movie.stream.util.RandomStringGenerator.Type;

/**
 * The UID contains of timestamp until seconds, incremental value and random values.
 *
 */
public final class UID implements java.io.Serializable, Comparable<UID> {
    private static final AtomicInteger NEXT_COUNTER = new AtomicInteger(new SecureRandom().nextInt(999));
    private static final int LOW_ORDER_THREE_BYTES = 0x00ffffff;
    public static final String DATETIME_FORMAT = "yyyyMMddHHmmss";
    private final String timestamp;
    private final int counter;
    private final String randomValue;

    public UID(String timestamp, int counter, String randomValue) {
        this.timestamp = timestamp;
        this.counter = counter % 999;
        this.randomValue = randomValue;
    }

    @Override
    public int compareTo(UID o) {
        if (this.timestamp.equals(o.timestamp) && this.randomValue.equals(o.randomValue)) {
            return 0;
        }
        return -1;
    }

    public static UID generate() {
        return generate(new Type[]{Type.NUMERIC}, 5);
    }

    public static UID generate(Type[] types, int length) {
        String timestamp = new DateTime().toString(DateTimeFormat.forPattern(UID.DATETIME_FORMAT));
        String randomValue =  RandomStringGenerator.get(length, types);
        return new UID(timestamp, NEXT_COUNTER.getAndIncrement(), randomValue);
    }

    public static UID parse(String uid) {
        String timestamp = uid.substring(0, 14);
        int counter = Integer.valueOf(uid.substring(14, 17));
        String randomValue = uid.substring(17);
        return new UID(timestamp, counter, randomValue);
    }

    public String getTimestamp() {
        return timestamp;
    }

    public int getCounter() {
        return counter;
    }

    public String getRandomValue() {
        return randomValue;
    }

    @Override
    public String toString() {
        return timestamp + String.format("%03d", counter) + randomValue;
    }
}
