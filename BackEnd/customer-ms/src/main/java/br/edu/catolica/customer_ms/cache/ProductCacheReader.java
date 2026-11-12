package br.edu.catolica.customer_ms.cache;

import br.edu.catolica.customer_ms.client.SellerServiceClient;
import br.edu.catolica.customer_ms.dto.ProductDTO;
import br.edu.catolica.customer_ms.dto.SellerProductsDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProductCacheReader {

    private final RedisTemplate<String, Object> redisTemplate;
    private final SellerServiceClient sellerServiceClient;

    public SellerProductsDTO getProductsBySeller(Long sellerId) {

        String key = String.format("seller:%d:products", sellerId);

        SellerProductsDTO productCache = readFromCacheRedis(key);

        if (productCache != null) {
            log.info("******* Product do Redis *********** = {}", productCache);
            return productCache;
        }

        var sellerProducts = sellerServiceClient.getSellerProducts(sellerId);

        populateRedis(key, sellerProducts);
        return sellerProducts;
    }

    private SellerProductsDTO readFromCacheRedis(String key) {
        try {
            Map<Object, Object> produtcFromSellerKey = redisTemplate.opsForHash().entries(key);
            if (produtcFromSellerKey.isEmpty()) return null;

            String sellerName = String.valueOf(produtcFromSellerKey.remove("sellerName"));
            List<ProductDTO> products = produtcFromSellerKey.values().stream().map(p -> (ProductDTO) p).toList();

            return new SellerProductsDTO(sellerName, products);
        } catch (Exception e) {
            log.error("m=readFromCache, was not possible to save o Redis to seller id = {}",extractSellerIdFromKey(key));
            throw new RuntimeException(e);
        }
    }

    private void populateRedis(String key, SellerProductsDTO sellerProductsDTO) {
        redisTemplate.opsForHash().put(key, "sellerName", sellerProductsDTO.name());
        sellerProductsDTO.products().forEach(product -> {
            redisTemplate.opsForHash().put(key, "product:"+product.id(), product);
        });
        redisTemplate.expire(key, Duration.ofDays(30));
    }

    private String extractSellerIdFromKey(String key) {
        return key.replace("seller:", "").replace(":products", "");
    }



}
