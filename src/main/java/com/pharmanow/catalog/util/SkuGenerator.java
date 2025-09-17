package com.pharmanow.catalog.util;

import java.util.UUID;

public class SkuGenerator {
    private SkuGenerator(){}
    private static final String PREFIX = "PHARMANOW";

    public static String generateSku() {
        long uniqueId = Math.abs(UUID.randomUUID().getMostSignificantBits());
        String uniqueNumber = String.format("%08d", uniqueId).substring(0, 8);
        return PREFIX + "-" + uniqueNumber;
    }
}
