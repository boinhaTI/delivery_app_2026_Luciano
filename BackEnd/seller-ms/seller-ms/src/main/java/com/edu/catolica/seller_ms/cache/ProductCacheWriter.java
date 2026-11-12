package com.edu.catolica.seller_ms.cache;

import com.edu.catolica.seller_ms.modal.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import static com.edu.catolica.seller_ms.cache.CacheConstants.KEY_PREFIX;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductCacheWriter {

    private final RedisTemplate<String, Object>  redisTemplate;

    public void save(Product product) {
        try {
            String key = String.format(KEY_PREFIX, product.getSellerId());
            redisTemplate.opsForHash().put(key, "product:" + product.getSellerId(), product);
            redisTemplate.expire(key, CacheConstants.KEY_TTL_30d);

        } catch (Exception e) {
            log.warn("m=save, failed to try save product = {} on redis", product.getId());
        }
    }
}
