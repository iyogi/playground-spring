package com.yrs.common.utils;

import java.time.Duration;

public class CommonUtil {

    public static void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void sleep(Duration duration) {
        sleep(duration.toMillis());
    }
}
