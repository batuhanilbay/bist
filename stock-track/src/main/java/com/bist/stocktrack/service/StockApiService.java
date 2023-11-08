package com.bist.stocktrack.service;


import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Value;


@Service
@RequiredArgsConstructor
@EnableCaching
public class StockApiService {
    private final StringRedisTemplate redisTemplate;
    private final RestTemplate restTemplate;
    @Value("${api.key}")
    private String apiKey;

    @Scheduled(cron = "0 0 10,12,14,16,18 * * MON-FRI")
    public void cacheDataIfNeeded() {
        if (isWeekday() && isValidTime()) {
            String cacheKey = "cached_data";
            String cachedData = redisTemplate.opsForValue().get(cacheKey);

            if (cachedData == null) {
                String response = getStockInfo();
                if (response != null) {
                    redisTemplate.opsForValue().set(cacheKey, response, 2, TimeUnit.HOURS);
                }
            }
        }
    }

    @Scheduled(cron = "0 0 0 * * SAT,SUN")
    @CacheEvict(value = "stockCache", allEntries = true)
    public void clearCacheOnWeekends() {
        String cacheKey = "cached_data";
        redisTemplate.delete(cacheKey);
    }

    private boolean isWeekday() {
        int dayOfWeek = java.time.LocalDate.now().getDayOfWeek().getValue();
        return dayOfWeek >= 1 && dayOfWeek <= 5;
    }

    private boolean isValidTime() {
        int hour = java.time.LocalTime.now().getHour();
        int minute = java.time.LocalTime.now().getMinute();
        return (hour == 10 || hour == 12 || hour == 14 || hour == 17 || (hour == 18 && minute == 45));
    }

    @Cacheable(value = "stockCache", key = "T(java.time.LocalDate).now().dayOfWeek.toString() + T(java.time.LocalTime).now().hour.toString()")
    public String getStockInfo() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("content-type", "application/json");
        httpHeaders.add("authorization", apiKey);
        HttpEntity<?> requestEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange("https://api.collectapi.com/economy/hisseSenedi", HttpMethod.GET, requestEntity, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        } else {
            return "Stock information could not be obtained. Error code: " + responseEntity.getStatusCode();
        }
    }
}
