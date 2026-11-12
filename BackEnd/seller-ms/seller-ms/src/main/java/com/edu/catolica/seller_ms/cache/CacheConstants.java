package com.edu.catolica.seller_ms.cache;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.time.Duration;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CacheConstants {

    public static final String KEY_PREFIX = "seller:%d:products";
    public static final Duration KEY_TTL_30d = Duration.ofDays(30);
}
