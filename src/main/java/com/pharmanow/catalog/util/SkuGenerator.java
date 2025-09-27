package com.pharmanow.catalog.util;

import java.util.UUID;

public class SkuGenerator {
    private SkuGenerator(){}

    public static String generateSku() {
       return UUID.randomUUID().toString();
    }

}
